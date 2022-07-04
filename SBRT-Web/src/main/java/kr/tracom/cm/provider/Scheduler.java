package kr.tracom.cm.provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
							//Type resultType = new TypeToken<Map<String, Object>>(){}.getType();
							List<Map<String, Object>> jsonList = gson.fromJson(result, resultType);
							//Map<String, Object> jsonList = gson.fromJson(result, resultType);
							
							for (int j = 0; j < jsonList.size(); j++) {
								Map<String, Object> data2 = (Map) jsonList.get(j);
								
								data2.put("COOL_SET", data2.get("coolingSetpoint"));
								data2.put("TEMP", data2.get("temperature"));
								data2.put("FCLT_ID", data.get("FCLT_ID"));
								data2.put("FCLT_KIND", data.get("FCLT_KIND"));
								data2.put("NODE_ID", data.get("NODE_ID"));
								data2.put("MNG_ID", data.get("MNG_ID"));
								data2.put("ATTR_ID", "5050"); //에어컨 attr_id 5050
								
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
	
	/*@Scheduled(fixedDelay = 15000)
	public void schedule_15sec() {
		//logger.info("schedule_1sec");
		try {
			//String actionData = "[{'MNG_ID' : 'IMP0090000SD0004', 'DATA_VAL' : '1', 'ATTR_ID' : '5051' },{'MNG_ID' : 'IMP0090000SD0005', 'DATA_VAL' : '1', 'ATTR_ID' : '5051'},"
			//		+ "{'MNG_ID' : 'IMP0090000SD0006', 'DATA_VAL' : '1', 'ATTR_ID' : '5051'}]";
			
			String actionData = "{'MNG_ID' : 'IMP0090000SD0004', 'DATA_VAL' : '1', 'ATTR_ID' : '5051' }";
			String actionData2 = "{'MNG_ID' : 'IMP0090000SD0005', 'DATA_VAL' : '1', 'ATTR_ID' : '5051' }";
			
    		Gson gson = new Gson();
			Type resultType = new TypeToken<Map<String, Object>>(){}.getType();
			Map<String, Object> map= gson.fromJson(actionData, resultType);
			Map<String, Object> map2= gson.fromJson(actionData2, resultType);
			map.put("ATTR_ID", "5051");
			map2.put("ATTR_ID", "5051");
    		
			//Map<String, Object> map = new HashMap<String, Object>();
			ArrayList jsonList = new ArrayList<Map<String, Object>>();
			ArrayList jsonList2 = new ArrayList<Map<String, Object>>();
			jsonList.add(map);
			jsonList2.add(map2);
			webSocketClient.sendMessageList(jsonList);
			webSocketClient.sendMessageList(jsonList2);
    		
    		logger.info("======== 시설물 매개변수: {}", actionData);
    		logger.info("======== 시설물 매개변수2: {}", actionData2);
		} catch (Exception e) {
			logger.error("schedule_30sec Exception!!! {}", e);
		}
	}
	*/
	/*
	@Scheduled(fixedDelay = 22000)
	public void schedule_22sec() {
		//logger.info("schedule_1sec");
		try {
			//String actionData = "[{'MNG_ID' : 'IMP0090000SD0004', 'DATA_VAL' : '0', 'ATTR_ID' : '5051' },{'MNG_ID' : 'IMP0090000SD0005', 'DATA_VAL' : '0', 'ATTR_ID' : '5051'},"
			//		+ "{'MNG_ID' : 'IMP0090000SD0006', 'DATA_VAL' : '0', 'ATTR_ID' : '5051'}]";
			String actionData = "{'NODE_TYPE':'NT001','NEXT_NODE_NM':'세종차고지','DVC_ID':'DV00000180','NODE_NM':'여울초교앞','EVT_CODE':'ET020','NEXT_NODE_TYPE':'NT002','CUR_NODE_ID':'293064210','CUR_NODE_SN':22,'CUR_SPD':41,'LINK_ID':'LK00014281','EVT_DATA':'46','VHC_ID':'VH00000005','CUR_NODE_TYPE':'NT002','ROUT_NM':'B0상(착)','PREV_NODE_NM':null,'GPS_Y':36.468826,'NODE_ID':'CR00000009','NODE_SN':17,'GPS_X':127.27388,'CUR_NODE_NM':'세종시청.교육청.시의회','EVT_TYPE':'차고지 출발','NEXT_NODE_ID':'293064216','VHC_NO':'세종70자1509','ATTR_ID':4012,'OPER_STS':'OS001','ROUT_ID':'RT00000048'}";
			//String actionData = "{'MNG_ID' : 'IMP0090000SD0004', 'DATA_VAL' : '0', 'ATTR_ID' : '5051' }";
			//String actionData2 = "{'MNG_ID' : 'IMP0090000SD0005', 'DATA_VAL' : '0', 'ATTR_ID' : '5051' }";
			
    		Gson gson = new Gson();
			Type resultType = new TypeToken<Map<String, Object>>(){}.getType();
			Map<String, Object> map= gson.fromJson(actionData, resultType);
			//Map<String, Object> map2= gson.fromJson(actionData2, resultType);
			//map.put("ATTR_ID", "5051");
			//map2.put("ATTR_ID", "5051");
    		
			//Map<String, Object> map = new HashMap<String, Object>();
			ArrayList jsonList = new ArrayList<Map<String, Object>>();
			//ArrayList jsonList2 = new ArrayList<Map<String, Object>>();
			jsonList.add(map);
			//jsonList2.add(map2);
			webSocketClient.sendMessageList(jsonList);
			//webSocketClient.sendMessageList(jsonList2);
    		
    		logger.info("======== 시설물 매개변수: {}", actionData);
    		//logger.info("======== 시설물 매개변수2: {}", actionData2);
		} catch (Exception e) {
			logger.error("schedule_35sec Exception!!! {}", e);
		}
	}*/
}
