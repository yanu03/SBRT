package kr.tracom.cm.domain.Dashboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.clipsoft.org.json.simple.JSONArray;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import kr.tracom.cm.domain.Intg.IntgMapper;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.platform.attribute.common.AtBrtAction;
import kr.tracom.platform.net.config.TimsConfig;
import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.TimsMessageBuilder;
import kr.tracom.platform.service.TService;
import kr.tracom.platform.service.config.KafkaTopics;
import kr.tracom.tims.kafka.KafkaProducer;
import kr.tracom.util.Constants;
import kr.tracom.util.DataInterface;
import kr.tracom.ws.WsClient;


@Service
public class DashboardService extends ServiceSupport {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${api.gateway.url}")
	private String apiGatewayUrl;
	
	@Autowired
	private DashboardMapper dashboardMapper;
	
	@Autowired
	private IntgMapper intgMapper;
	
	@Autowired
	WsClient webSocketClient;
	
	@Autowired
	KafkaProducer kafkaProducer;

	public List<Map<String, Object>> selectDashboardList() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return dashboardMapper.selectDashboardList(map);
	}
	
	public List<Map<String, Object>> selectAllocDashboardList() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		String temp[] = map.get("VHC_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
		map.put("VHC_ID", temp);
		return dashboardMapper.selectAllocDashboardList(map);
	}
	
	public List<Map<String, Object>> selectCurOperDashboardList() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return dashboardMapper.selectCurOperDashboardList(map);
	}
	
	public List<Map<String, Object>> selectDashboardItem() throws Exception {
		return dashboardMapper.selectDashboardItem();
	}
	
	/*public List<Map<String, Object>> selectRoutList() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return routMapper.selectRoutList(map);
	}
	
	public List<Map<String, Object>> selectRoutItem() throws Exception {
		return routMapper.selectRoutItem();
	}
	
	public List<Map<String, Object>> selectNodeListByRouts() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String temp[] = map.get("ROUT_IDS").toString().replace("[","").replace("]","").replace(" ","").split(",");
		map.put("ROUT_IDS", temp);
		return routMapper.selectNodeListByRouts(map);
	}
	
	public List<Map<String, Object>> selectNodeListByRout() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		return routMapper.selectNodeListByRout(map);
	}
	
	public List<Map<String, Object>> selectNodeListByRepRout() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return routMapper.selectNodeListByRepRout(map);
	}

	public List<Map<String, Object>> selectSttnList() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return routMapper.selectSttnList(map);
	}
	
	public List<Map<String, Object>> selectSttnItem() throws Exception {
		return routMapper.selectSttnItem();
	}*/
	
	public List selectDashboardBit() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");	
		Map<String, Object> param = new HashMap<>();
		List<Map<String, Object>> returnList = null;
		List<Map<String, Object>> nodeList = null;
		List<Map<String, Object>> staList = null;
		
		String result = "";
		
		String sttnId = (String) map.get("PUB_STTN_ID");
		String cityCode = (String) map.get("AREA");
		param.put("INTG_ID", Constants.OPENAPI_BIT_ID);
		
		List<Map<String, Object>> list = intgMapper.selectIntg(param);
		String baseUrl = (String) list.get(0).get("INTG_URL");
		String apiKey = (String) list.get(0).get("INTG_API_KEY");
		
		String encodeParams = URLEncoder.encode("pageNo=1&numOfRows=999&_type=xml&cityCode="+cityCode+"&nodeId="+ sttnId, "UTF-8");
		//String url = baseUrl + "serviceKey=" + apiKey + "&pageNo=1&numOfRows=999&_type=xml&cityCode="+cityCode+"&nodeId="+ sttnId;
		String url = apiGatewayUrl + "local/requestOpenApi?method=get&baseurl=" + baseUrl + "&params=" + encodeParams + "&keyname=serviceKey&authKey=" + apiKey;
		
		
		staList = new ArrayList<>();
		
		BufferedReader in = null;
		try {
				URL apiUrl = new URL(url);
			try {
				HttpURLConnection conn = (HttpURLConnection) apiUrl.openConnection();
				conn.setRequestMethod("GET");
				
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                   sb.append(line);
                }
				
                try {
                    result = sb.toString();
            		// JSONParser로 JSONObject 객체
            		JSONObject objData = (JSONObject)new JSONParser().parse(result);

            		// 첫 번째 JSONObject
            		JSONObject responseJson = (JSONObject)objData.get("response");  
            		Object bodyObject = responseJson.get("body");
            		JSONObject bodyJson = (JSONObject)new JSONParser().parse(bodyObject.toString());
            		logger.info("selectDashboardBit() in " +bodyObject.toString());
            		Object itemsObject = bodyJson.get("items");
            		if(itemsObject.equals("")) {
            			return null;
            		}
            		JSONObject itemsJson = (JSONObject)new JSONParser().parse(itemsObject.toString());
            		Object itemObject = itemsJson.get("item");
            		
            		if(new JSONParser().parse(itemObject.toString()) instanceof JSONObject) {
            			
            			JSONObject itemJson = (JSONObject)new JSONParser().parse(itemObject.toString());
            			Map<String, Object> data = new HashMap<>();
                		
            			data.put("ATTR_ID",(Object)"BIT0000001");
                		data.put("NODE_NM",itemJson.get("nodenm"));
                		data.put("ROUTE_TP",itemJson.get("routetp"));
                		data.put("REMAIN_STTN",itemJson.get("arrprevstationcnt").toString()); //남은정류소
                		data.put("ROUT_ID", itemJson.get("routeid"));
                		data.put("VEHICLE_TP",itemJson.get("vehicletp")); //버스종류
                		data.put("NODE_ID",itemJson.get("nodeid")) ;
                		data.put("ROUT_NM",itemJson.get("routeno"));
                		data.put("REMAIN_TM",itemJson.get("arrtime").toString()); //남은시간
                		logger.info("sendMessage() before {}",data);
                		webSocketClient.sendMessage(data);
            		}
            		
            		else if(new JSONParser().parse(itemObject.toString()) instanceof ArrayList) {
            			//JSONArray jsonArray = (JSONArray)new JSONParser().parse(itemObject.toString());
            			
            			List<JSONObject> bitList = (ArrayList)new JSONParser().parse(itemObject.toString());
            			
    					/* 모니터링용 데이터 생성 */
            			Map<String, Object> wsDataMap = new HashMap<>();
    					wsDataMap.put("ATTR_ID", "BIT0000001");
    					List<HashMap<String, Object>> arrivalInfoMapList = new ArrayList<>();
    					
            			for(int i=0; i<bitList.size(); i++) {
            				
            				JSONObject itemJson = (JSONObject) bitList.get(i);
                    		Map<String, Object> data = new HashMap<>();
                    		wsDataMap.put("STTN_ID", itemJson.get("nodeid"));
                    		data.put("NODE_NM",itemJson.get("nodenm"));
                    		data.put("ROUTE_TP",itemJson.get("routetp"));
                    		data.put("REMAIN_STTN",itemJson.get("arrprevstationcnt").toString()); //남은정류소
                    		data.put("ROUT_ID", itemJson.get("routeid"));
                    		data.put("VEHICLE_TP",itemJson.get("vehicletp")); //버스종류
                    		data.put("NODE_ID",itemJson.get("nodeid")) ;
                    		data.put("ROUT_NM",itemJson.get("routeno"));
                    		data.put("REMAIN_TM",itemJson.get("arrtime").toString()); //남은시간
                    		
                    		arrivalInfoMapList.add((HashMap<String, Object>) data);
                    		
                    		logger.info("sendMessage() before {}",data);
                    		
                		}
            			wsDataMap.put("LIST", arrivalInfoMapList);
                		webSocketClient.sendMessage(wsDataMap);
            		}
            		
                 } catch (Exception e) {
                    //logger.error("error");
                    e.printStackTrace();
                 }
                
				//in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		/*
		NodeList tempList = DataInterface.interface_XML(url);
		for(int i = 0; i < tempList.getLength(); i++) {
			Map<String, Object> tmp = new HashMap<String, Object>();
			Node child = tempList.item(i);

			//한 정류장의 bit parse
			if(child.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element)child;
				tmp.put("REMAIN_STTN",DataInterface.getTagValue("arrprevstationcnt", eElement)); //남은 정거장 수
				tmp.put("REMAIN_TM",DataInterface.getTagValue("arrtime", eElement)); // 남은 도착 시간(초)
				tmp.put("NODE_ID",DataInterface.getTagValue("nodeid", eElement).substring(3));
				tmp.put("NODE_NM",DataInterface.getTagValue("nodenm", eElement));
				tmp.put("ROUT_ID",DataInterface.getTagValue("routeid", eElement).substring(3));
				tmp.put("ROUT_NM",DataInterface.getTagValue("routeno", eElement));
				tmp.put("VHC_KIND",DataInterface.getTagValue("routetp", eElement));
				tmp.put("VHC_TYPE",DataInterface.getTagValue("vehicletp", eElement));
				
				staList.add(tmp);
			}
		}
		*/
		
		
		
		
		/*if(staList!=null && staList.size() > 0) {
			returnList = DataInterface.generalNode2(staList,nodeList);
			DataInterface.insertNodeToNode(returnList, staList);
			
			map.put("NODE_TYPE",Constants.NODE_TYPE_CROSS);
			List<Map<String, Object>> crsList = routMapper.selectNodeListByRout(map);
			if(crsList!=null && crsList.size() > 0) {
				returnList = DataInterface.generalNode2(crsList,returnList);
				DataInterface.insertNodeToNode(returnList, crsList);
			}
			map.put("NODE_TYPE",Constants.NODE_TYPE_SOUND);
			List<Map<String, Object>> sndList = routMapper.selectNodeListByRout(map);
			if(sndList!=null && sndList.size() > 0) {
				DataInterface.insertNodeToNode(returnList, sndList);
			}
		}*/
		
		return staList;
	}
	
	public List<Map> selectB0Bit() throws Exception{
		
		Map param = getSimpleDataMap("dma_sub_search");
		
		String sttnId = String.valueOf(param.get("NODE_ID"));
		
		//정류장정보 요청 데이터 생성
		AtBrtAction brtRequest = new AtBrtAction();

		brtRequest.setActionCode((byte)AtBrtAction.bitInfoRequest);
		brtRequest.setData(sttnId);

        
        TimsConfig timsConfig = TService.getInstance().getTimsConfig();
        TimsMessageBuilder builder = new TimsMessageBuilder(timsConfig);
        TimsMessage timsMessage = builder.actionRequest(brtRequest);
        
        //정류장정보 요청 전송
        kafkaProducer.sendKafka(KafkaTopics.T_BRT, timsMessage, sttnId);	
		
		return null;
	}		
	
}
