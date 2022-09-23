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

import kr.tracom.cm.domain.Common.CommonMapper;
import kr.tracom.cm.domain.Intg.IntgMapper;
import kr.tracom.platform.attribute.BrtAtCode;
import kr.tracom.platform.attribute.brt.AtTrafficModule2;
import kr.tracom.platform.attribute.brt.AtTrafficModule3;
import kr.tracom.platform.attribute.common.AtTimeStamp;
import kr.tracom.tims.domain.CurInfoMapper;
import kr.tracom.tims.domain.HistoryMapper;
import kr.tracom.util.CommonUtil;
import kr.tracom.util.Constants;
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
	
	@Autowired
    CurInfoMapper curInfoMapper;
	
	@Autowired
	CommonMapper commonMapper;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static Map<String, Object> g_operCodeMap  = new HashMap<>();
	
	private static String curToday = "";

	private Map<String, Object> getCommonCode( String coCd,String ValType, String value) {
		//String eventCd = paramMap.get("EVENT_CD")+"";
		logger.debug("getEventCode() coCd="+coCd +", eventCd="+value);
		Map<String, Object> param = new HashMap<>();
		String key = coCd+value;
		param.put("CO_CD", coCd);
		param.put("VAL_TYPE", ValType);
		param.put("VAL", value);
		
		Map<String, Object> eventCodeMap = null;
		if(g_operCodeMap==null) {
			g_operCodeMap = new HashMap<>();
			eventCodeMap = curInfoMapper.getEventCode(param);
			if(eventCodeMap!=null) {
				g_operCodeMap.put(key, eventCodeMap);
				return eventCodeMap;
			}
		}
		
		eventCodeMap = (Map<String, Object>)g_operCodeMap.get(key); 
		if ((eventCodeMap != null)) {
			return eventCodeMap;
		}
		else {
			eventCodeMap = curInfoMapper.getEventCode(param);
			g_operCodeMap.put(key, eventCodeMap);
			return eventCodeMap;
		}
	}
	
	@Scheduled(fixedDelay = 60000)
	public void schedule_60sec() {
		try {
				
		} catch (Exception e) {
			logger.error("schedule_10sec Exception!!! {}", e);
		}
	}
	
	
	@Scheduled(fixedDelay = 10000)
	public void schedule_10sec() {
		//Map<String, Object> paramMap = new HashMap<>();
		//paramMap.put("COL", "TXT_VAL1");
		//paramMap.put("CO_CD", "SCHEDULE_TEST");
		//paramMap.put("COL3", "DL_CD_NM");
		//paramMap.put("COL_VAL3", "10sec");
		//String scheduleOnOff = commonMapper.selectDlCdCol(paramMap);
				
		Map<String, Object> commonCode = getCommonCode("SCHEDULE_TEST","DL_CD_NM","10sec");
		String scheduleOnOff = (String)commonCode.get("TXT_VAL1");
		//logger.info("schedule_10sec");
		try {
			
			if (scheduleOnOff.equals("off") == true) {
				return;
			}
			//
			try {
				Map param = new HashMap();
				param.put("FCLT_KIND", "FK005");
				param.put("PARAM_DIV ", "PD002");
				param.put("PARAM_KIND", "PK002");
				List<Map<String, Object>> list =intgMapper.selectFcltSchedule(param);
				for(int x = 0; x < list.size(); x++) {
					Map data = (Map) list.get(x);
					String intgFcltId = (String) data.get("INTG_FCLT_ID");
					String stTime = (String) data.get("ST_TIME");
					String edTime = (String) data.get("ED_TIME");
					String power = "";
					logger.error("CommonUtil.todayHM= {}, {}, {}", CommonUtil.todayHM(), stTime, edTime);

					
					data.put("FCLT_ID", data.get("FCLT_ID"));
					data.put("FCLT_KIND", Constants.FcltKinds.FK005);
					data.put("NODE_ID", data.get("NODE_ID"));
					data.put("MNG_ID", data.get("MNG_ID"));				
					data.put("PARAM_DIV", Constants.ParamDivs.PD002);
					data.put("PARAM_KIND", Constants.ParamKinds.PK002);
					
					if(CommonUtil.todayHM().equals(stTime)) {
						power = "1";
						data.put("DATA_VAL", "1");
					}
					else if(CommonUtil.todayHM().equals(edTime)) {
						power = "0";
						data.put("DATA_VAL", "0");
					}
					
					//전원제어
					if(power.isEmpty()==false) {
						historyMapper.updateFcltCondParamInfo(data);
						data.put("INTG_TYPE", "PC");
						
						List<Map<String, Object>> token = intgMapper.selectIntgMstList(data);
						String key = (String) token.get(0).get("INTG_API_KEY");
						String intgUrl = (String) token.get(0).get("INTG_URL");
						
						String api = apiGatewayUrl + intgUrl + key + "&deviceId=" + intgFcltId + "&value=" + power;
						logger.debug("airconControl() api = "+ api);
						
						BufferedReader in = null;
						
						try {
							URL url = new URL(api);
							try {
								HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // 접속 
								conn.setRequestMethod("GET"); // 전송 방식은 GET
								
								in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
								
							} catch (IOException e) {
								e.printStackTrace();
							}
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} catch (Exception e) {
				logger.error("schedule_10sec Exception!!! {}", e);
			}
			
			List<Map<String, Object>> param = intgMapper.selectAirconIntgList(null);
			
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
								
								data2.put("FCLT_ID", data.get("FCLT_ID"));
								data2.put("FCLT_KIND", data.get("FCLT_KIND"));
								data2.put("NODE_ID", data.get("NODE_ID"));
								data2.put("MNG_ID", data.get("MNG_ID"));
								data2.put("ATTR_ID", "5050"); //에어컨 attr_id 5050
								
								data2.put("PARAM_DIV", Constants.ParamDivs.PD003);
								
								data2.put("PARAM_KIND", Constants.ParamKinds.PK002);
								if(data2.get("switch").equals("on")) {
									//data2.put("SWITCH", "1");
									data2.put("DATA_VAL", "1");
								}else if(data2.get("switch").equals("off")) {
									//data2.put("SWITCH", "0");
									data2.put("DATA_VAL", "0");
								}
								historyMapper.updateFcltCondParamInfo(data2);
								
								
								data2.put("PARAM_KIND", Constants.ParamKinds.PK022);
								data2.put("DATA_VAL", data2.get("temperature"));
								historyMapper.updateFcltCondParamInfo(data2);
								
								data2.put("PARAM_KIND", Constants.ParamKinds.PK047);
								data2.put("DATA_VAL", data2.get("coolingSetpoint"));
								historyMapper.updateFcltCondParamInfo(data2);
								
							}
							
							//웹소켓 전송이 필요한 경우
							if(jsonList != null) {
								webSocketClient.sendMessageList("/topic/aircon",jsonList);
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
	
	/*
	@Scheduled(fixedDelay = 1000)
	public void schedule_1sec() {
		//logger.info("schedule_1sec");
		try {

		} catch (Exception e) {
			logger.error("schedule_1sec Exception!!! {}", e);
		}
	}
	*/
	
/*	
	@Scheduled(fixedDelay = 15000)
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
	}
	*/
	@Scheduled(fixedDelay = 30000)
	public void schedule_Dispatch() {
		//Map<String, Object> paramMap = new HashMap<>();
		//paramMap.put("COL", "TXT_VAL1");
		//paramMap.put("CO_CD", "SCHEDULE_TEST");
		//paramMap.put("COL3", "DL_CD_NM");
		//paramMap.put("COL_VAL3", "Dispatch");
		//String scheduleOnOff = commonMapper.selectDlCdCol(paramMap);
		Map<String, Object> commonCode = getCommonCode("SCHEDULE_TEST","DL_CD_NM","Dispatch");
		String scheduleOnOff = (String)commonCode.get("TXT_VAL1");
		//logger.info("schedule_10sec");
		try {
			if (scheduleOnOff.equals("off") == true) {
				return;
			}
			Map<String, Object> wsDataMap = null;
			wsDataMap = new HashMap<>();
			
			wsDataMap.put("ATTR_ID", "4020");
			wsDataMap.put("VHC_ID", "VH00000091");
			wsDataMap.put("VHC_NO", "우진43자5876");
			wsDataMap.put("ROUT_ID", "RT00000166");
			wsDataMap.put("ROUT_NM", "W0(상행)");
			wsDataMap.put("DSPTCH_DIV", "DP002");
			wsDataMap.put("DSPTCH_KIND", "DK003");
			wsDataMap.put("MESSAGE", "24");		
			
			webSocketClient.sendMessage(wsDataMap);
			
		} catch (Exception e) {
			logger.error("schedule_35sec Exception!!! {}", e);
		}
	}	
	
	@Scheduled(fixedDelay = 60000)
	public void schedule_AtTrafficModule2() {
		//Map<String, Object> paramMap = new HashMap<>();
		//paramMap.put("COL", "TXT_VAL1");
		//paramMap.put("CO_CD", "SCHEDULE_TEST");
		//paramMap.put("COL3", "DL_CD_NM");
		//paramMap.put("COL_VAL3", "AtTrafficModule2");
		//String scheduleOnOff = commonMapper.selectDlCdCol(paramMap);
		Map<String, Object> commonCode = getCommonCode("SCHEDULE_TEST","DL_CD_NM","AtTrafficModule2");
		String scheduleOnOff = (String)commonCode.get("TXT_VAL1");
		
		if (scheduleOnOff.equals("off") == true) {
			return;
		}
		
		try {
			AtTrafficModule2 trafficModule2 = new AtTrafficModule2();
			trafficModule2.setUpdateTm(new AtTimeStamp("2022-08-18 13:04:24.00"));
			trafficModule2.setStationNodeId("293064209");
			trafficModule2.setWaitTm((byte)0x00);
			trafficModule2.setBusNum("경기76자3453");
			
			logger.info("TRAFFIC_MODULE_TWO : {}", trafficModule2);
			List<HashMap <String, Object>> trafficModule2MapList = new ArrayList<>();
			
			HashMap<String, Object> moduleTwoMap = new HashMap<>();
	        moduleTwoMap.put("VHC_NO",trafficModule2.getBusNum());
	        moduleTwoMap.put("OPER_DT", CommonUtil.getOperDt());
	        moduleTwoMap.put("NODE_ID",trafficModule2.getStationNodeId());
	        Map<String, Object> result1 = curInfoMapper.selectCurOperInfoByVhcNo(moduleTwoMap);
	        if(result1!=null) {
		        moduleTwoMap.put("REP_ROUT_ID",result1.get("REP_ROUT_ID"));
		        moduleTwoMap.put("ROUT_ID",result1.get("ROUT_ID"));
	        }
	        moduleTwoMap.put("CTRL_LV",2);
	        moduleTwoMap.put("STOP_SEC",trafficModule2.getWaitTm());
	        moduleTwoMap.put("OCR_DTM",trafficModule2.getUpdateTm().toString());
			
	        trafficModule2MapList.add(moduleTwoMap);
			
			//웹소켓 데이터 세팅
	    	Map<String, Object> wsModuleTwoDataMap = new HashMap<>();
	    	wsModuleTwoDataMap.put("ATTR_ID", BrtAtCode.TRAFFIC_MODULE_TWO);
	    	wsModuleTwoDataMap.put("LIST", trafficModule2MapList);
	    	
			webSocketClient.sendMessage(wsModuleTwoDataMap);
			curInfoMapper.insertOrUpdateSigOperEventInfo(moduleTwoMap);
		} catch (Exception e) {
			logger.error("Exception {}", e);
		}
	}
	
	
	@Scheduled(fixedDelay = 60000)
	public void schedule_AtTrafficModule3() {
		//Map<String, Object> paramMap = new HashMap<>();
		//paramMap.put("COL", "TXT_VAL1");
		//paramMap.put("CO_CD", "SCHEDULE_TEST");
		//paramMap.put("COL3", "DL_CD_NM");
		//paramMap.put("COL_VAL3", "AtTrafficModule3");
		//String scheduleOnOff = commonMapper.selectDlCdCol(paramMap);
		Map<String, Object> commonCode = getCommonCode("SCHEDULE_TEST","DL_CD_NM","AtTrafficModule2");
		String scheduleOnOff = (String)commonCode.get("TXT_VAL1");
		
		try {
			if (scheduleOnOff.equals("off") == true) {
				return;
			}

			AtTrafficModule3 trafficModule3 = new AtTrafficModule3();
			trafficModule3.setUpdateTm(new AtTimeStamp("2022-08-18 13:04:24.00"));
			trafficModule3.setCrossNodeId("CR00000009");
			trafficModule3.setControlType((byte)0x01);
			trafficModule3.setControlPhaseNum((byte)0x01);
			trafficModule3.setBusNum("우진76자5876");
			
			logger.info("TRAFFIC_MODULE_THREE : {}", trafficModule3);
			List<HashMap <String, Object>> trafficModule3MapList = new ArrayList<>();
			
			HashMap<String, Object> moduleThreeMap = new HashMap<>();
	        moduleThreeMap.put("VHC_NO",trafficModule3.getBusNum());
	        moduleThreeMap.put("OPER_DT", CommonUtil.getOperDt());
	        moduleThreeMap.put("NODE_ID",trafficModule3.getCrossNodeId());
	        Map<String, Object> result2 = curInfoMapper.selectCurOperInfoByVhcNo(moduleThreeMap);
	        if(result2!=null) {
	        moduleThreeMap.put("REP_ROUT_ID",result2.get("REP_ROUT_ID"));
	        moduleThreeMap.put("ROUT_ID",result2.get("ROUT_ID"));
	        }
	        moduleThreeMap.put("CTRL_LV",3);
	        //moduleThreeMap.put("CTRL_TYPE",trafficModule3.getControlType());
	        moduleThreeMap.put("CTRL_TYPE",getCommonCode( "SIG_CTL_TYPE", "TXT_VAL1",trafficModule3.getControlType()+"").get("DL_CD"));
	        moduleThreeMap.put("CTRL_PHASE_NO",trafficModule3.getControlPhaseNum());
	        moduleThreeMap.put("OCR_DTM",trafficModule3.getUpdateTm().toString());
			
	        trafficModule3MapList.add(moduleThreeMap);
			
			//웹소켓 데이터 세팅
	    	Map<String, Object> wsModuleThreeDataMap = new HashMap<>();
	    	wsModuleThreeDataMap.put("ATTR_ID", BrtAtCode.TRAFFIC_MODULE_THREE);
	    	wsModuleThreeDataMap.put("LIST", trafficModule3MapList);
	    	
			webSocketClient.sendMessage(wsModuleThreeDataMap);
			curInfoMapper.insertOrUpdateSigOperEventInfo(moduleThreeMap);
		} catch (Exception e) {
			logger.error("Exception {}", e);
		}
	}
}
