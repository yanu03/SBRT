package kr.tracom.cm.domain.Intg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Arrays;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.tracom.cm.support.ServiceSupport;


@Service
public class IntgService extends ServiceSupport {

	@Value("${api.gateway.url}")
	private String apiGatewayUrl;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
		
	@Autowired
	private IntgMapper intgMapper;
	
	//에어컨 정보 select
	public List<Map<String, Object>> selectIntgList() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return intgMapper.selectIntgList(map);
		
	}
	
	//에어컨 정보 insert 
	public String insertIntgList() throws Exception {
		Map<String, Object> param = getSimpleDataMap("dma_search");
		Map<String, Object> paramSl = new HashMap();
		paramSl.put("INTG_TYPE", "SL");
		
		List<Map<String, Object>> token = intgMapper.selectIntgMstList(param);
		String key = (String) token.get(0).get("INTG_API_KEY");
		String intgUrl = (String) token.get(0).get("INTG_URL");
		
		String api = apiGatewayUrl + intgUrl + key;
		HttpURLConnection conn = null;
		String result = "";
		
		List<Map<String, Object>> tokenSl = intgMapper.selectIntgMstList(paramSl);
		String keySl = (String) tokenSl.get(0).get("INTG_API_KEY");
		String intgUrlSl = (String) tokenSl.get(0).get("INTG_URL");
		
		String apiSl = apiGatewayUrl + intgUrlSl + keySl;
		HttpURLConnection connSl = null;
		String resultSl = "";

		try {
			URL url = new URL(api);
			URL urlSl = new URL(apiSl);
			try {
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				
				connSl = (HttpURLConnection) urlSl.openConnection();
				connSl.setRequestMethod("GET");

				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = "";
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				
				BufferedReader brSl = new BufferedReader(new InputStreamReader(connSl.getInputStream()));
				StringBuilder sbSl = new StringBuilder();
				String lineSl = "";
				while ((lineSl = brSl.readLine()) != null) {
					sbSl.append(lineSl);
				}
				try {
					result = sb.toString();
					resultSl = sbSl.toString();
					
					Gson gson = new Gson();
					Gson gsonSl = new Gson();
					Type resultType = new TypeToken<List<Map<String, Object>>>(){}.getType();
					List<Map<String, Object>> jsonList = gson.fromJson(result, resultType);
					
					Type resultTypeSl = new TypeToken<List<Map<String, Object>>>(){}.getType();
					List<Map<String, Object>> jsonListSl = gsonSl.fromJson(resultSl, resultTypeSl);
					
					for (int i = 0; i < jsonList.size(); i++) {
						Map data = (Map) jsonList.get(i);
						data.put("INTG_FCLT_ID", data.get("deviceId"));
						data.put("FCLT_NM", data.get("name"));
						data.put("FCLT_LABEL", data.get("label"));
						data.put("LOC_ID", data.get("locationId"));
						data.put("ROOM_ID", data.get("roomId"));
						
						for(int x = 0; x < jsonListSl.size(); x++) {
							Map dataSl = (Map) jsonListSl.get(x);
							if(data.get("LOC_ID").equals(dataSl.get("locationId"))) {
								data.put("LOC_NM", dataSl.get("name"));
							}
						}
						
						intgMapper.insertAirconInfo(data);
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

		//return result;
		return null;
		
	}

	
}
