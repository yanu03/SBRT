package kr.tracom.cm.provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.tracom.cm.domain.Intg.IntgMapper;
import kr.tracom.tims.domain.HistoryMapper;
import kr.tracom.ws.WsClient;


@Service
public class Scheduler {
	
	@Value("${api.gateway.url}")
	private String apiGatewayUrl;
	
	@Autowired
	private IntgMapper intgMapper;
	
	@Autowired
	private HistoryMapper historyMapper;
	
	@Autowired
	WsClient webSocketClient;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Scheduled(fixedDelay = 10000)
	public void schedule_10sec() {
		//logger.info("schedule_10sec");
		try {
			//List<Map<String, Object>> param = getSimpleList("dlt_airconItem");
			
			List<Map<String, Object>> param = intgMapper.selectIntgList(null);
			
			Map<String, Object> paramSr = new HashMap();
			paramSr.put("INTG_TYPE", "SR");
			
			List<Map<String, Object>> token = intgMapper.selectIntgMstList(paramSr);
			String key = (String) token.get(0).get("INTG_API_KEY");
			String intgUrl = (String) token.get(0).get("INTG_URL");
			
			HttpURLConnection conn = null;
			String result = "";
			
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String api = apiGatewayUrl + intgUrl + key;
				api = api + "&deviceId=" + data.get("INTG_FCLT_ID");
				
				try {
					URL url = new URL(api);
					try {
						conn = (HttpURLConnection) url.openConnection();
						conn.setRequestMethod("GET");
						
						
						BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						StringBuilder sb = new StringBuilder();
						String line = "";
						while ((line = br.readLine()) != null) {
							sb.append(line);
						}
						
						try {
							result = sb.toString();
							
							Gson gson = new Gson();
							Type resultType = new TypeToken<List<Map<String, Object>>>(){}.getType();
							List<Map<String, Object>> jsonList = gson.fromJson(result, resultType);
							
							for (int j = 0; j < jsonList.size(); j++) {
								Map<String, Object> data2 = (Map) jsonList.get(j);
								
								data2.put("COOL_SET", data2.get("coolingSetpoint"));
								data2.put("TEMP", data2.get("temperature"));
								/*data2.put("SWITCH", data.get("switch"));*/
								data2.put("FCLT_ID", data.get("FCLT_ID"));
								
								if(data2.get("switch").equals("on")) {
									data2.put("SWITCH", "1");
								}else if(data2.get("switch").equals("off")) {
									data2.put("SWITCH", "0");
								}
								
								historyMapper.updateFcltCondParamInfo(data2);
								
							}	
							//웹소켓 전송이 필요한 경우
							if(jsonList != null) {
								webSocketClient.sendMessageList(jsonList);
							}
							
							
						} catch (Exception e) {
							//logger.error("error");
							logger.error("error : {}", e );
						}
					} catch (IOException e) {
						logger.error("IOException");
					}
				} catch (MalformedURLException e) {
					logger.error("MalformedURLException");
				}
			}			
				
		} catch (Exception e) {
			logger.error("schedule_10sec Exception!!! {}", e);
		}
	}
	
	@Scheduled(fixedDelay = 1000)
	public void schedule_1sec() {
		//logger.info("schedule_1sec");
		try {

		} catch (Exception e) {
			logger.error("schedule_1sec Exception!!! {}", e);
		}
	}
}
