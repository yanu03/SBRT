package kr.tracom.tims.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.tracom.platform.attribute.BrtAtCode;
import kr.tracom.platform.attribute.brt.AtTrafficLightStatusResponse;
import kr.tracom.platform.attribute.brt.AtTrafficModule2;
import kr.tracom.platform.attribute.brt.AtTrafficModule3;
import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.attribute.AtMessage;
import kr.tracom.platform.net.protocol.payload.PlGetResponse;
import kr.tracom.tims.domain.CurInfoMapper;
import kr.tracom.tims.domain.HistoryMapper;
import kr.tracom.util.CommonUtil;
import kr.tracom.util.Constants;
import kr.tracom.ws.WsClient;

@Component
public class GetResponse {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    int i = 0;
    
    
    @Autowired
	WsClient webSocketClient;
    
    @Autowired
    HistoryMapper historyMapper;
    
    @Autowired
    CurInfoMapper curInfoMapper;

    private static Map<String, Object> g_operCodeMap  = new HashMap<>();

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
	
    public Map<String, Object> handle(TimsMessage timsMessage, String sessionId){
    	
    	
    	//logger.info("<== PlCode.OP_GET_RES message:{}, sessionId:{}", timsMessage, sessionId);
    	
    	
    	Map<String, Object> resultMap = null;
        PlGetResponse payload = (PlGetResponse)timsMessage.getPayload();
        

        for(AtMessage atMessage : payload.getAttrList()){
            short attrId = atMessage.getAttrId();

            switch(attrId){
            	
            	//신호정보
            	case BrtAtCode.TRAFFIC_LIGHT_STATUS_RESPONSE:
            		
            		List<HashMap <String, Object>> phaseInfoMapList= new ArrayList<>();
            		AtTrafficLightStatusResponse lightStatus = (AtTrafficLightStatusResponse)atMessage.getAttrData();
            		
    				String crsId = lightStatus.getCrossNodeId(); //교차로id
    				int contSt = lightStatus.getControllerStatus(); //제어기상태
    				int conMode = lightStatus.getControlMode(); //신호제어 모드
    				int phaseNoA = lightStatus.getPhaseNumA(); //현시 A
    				int phaseNoB = lightStatus.getPhaseNumB(); //현지 B
    				short pahseTmA = lightStatus.getPhaseTimeA(); //현시 진행시간 A
    				short pahseTmB = lightStatus.getPhaseTimeB(); //현시 진행시간 B
    				
    				HashMap<String, Object> phaseInfoMap = new HashMap<>();
    				phaseInfoMap.put("CRS_ID", crsId);
    				phaseInfoMap.put("CONT_ST", contSt);
    				phaseInfoMap.put("CONT_MODE", conMode);
    				phaseInfoMap.put("PHASE_NO", phaseNoA);
    				phaseInfoMap.put("PHASE_NO_B", phaseNoB);
    				phaseInfoMap.put("PHASE_TM_A", pahseTmA);
    				phaseInfoMap.put("PHASE_TM_B", pahseTmB);
    				//historyMapper.updateSigFcltCondParamInfo(phaseInfoMap);
    				
    				/*phaseInfoMap.put("NODE_ID", crsId);
    				phaseInfoMap.put("PARAM_DIV", Constants.ParamDivs.PD003);
    				
    				phaseInfoMap.put("DATA_VAL", contSt);
    				phaseInfoMap.put("PARAM_KIND",  Constants.ParamKinds.PK030); //상태
    				historyMapper.updateFcltCondParamInfo(phaseInfoMap);
    				
    				phaseInfoMap.put("DATA_VAL", conMode);
    				phaseInfoMap.put("PARAM_KIND",  Constants.ParamKinds.PK039); //신호제어 모드
    				historyMapper.updateFcltCondParamInfo(phaseInfoMap);
    				
    				phaseInfoMap.put("DATA_VAL", phaseNoA);
    				phaseInfoMap.put("PARAM_KIND",  Constants.ParamKinds.PK040); //RING-A 현시
    				historyMapper.updateFcltCondParamInfo(phaseInfoMap);
    			
    				phaseInfoMap.put("DATA_VAL", pahseTmA);
    				phaseInfoMap.put("PARAM_KIND", Constants.ParamKinds.PK041); //RING-A 현시 진행시간(초)
    				historyMapper.updateFcltCondParamInfo(phaseInfoMap);
    				
    				phaseInfoMap.put("DATA_VAL", phaseNoB);
    				phaseInfoMap.put("PARAM_KIND",  Constants.ParamKinds.PK042); //RING-B 현시
    				historyMapper.updateFcltCondParamInfo(phaseInfoMap);
    				
    				phaseInfoMap.put("DATA_VAL", pahseTmB);
    				phaseInfoMap.put("PARAM_KIND",  Constants.ParamKinds.PK043);  //RING-B 현시 진행시간(초)
    				historyMapper.updateFcltCondParamInfo(phaseInfoMap);*/
    				
    				phaseInfoMapList.add(phaseInfoMap);
            		
        			//웹소켓 데이터 세팅
        	    	Map<String, Object> wsDataMap = new HashMap<>();
                	wsDataMap.put("ATTR_ID", BrtAtCode.TRAFFIC_LIGHT_STATUS_RESPONSE);
                	wsDataMap.put("LIST", phaseInfoMapList);
                	
                	//logger.info("================ 교차로아이디:{}, 현시번호 : {}", crsId, phaseNo);
                	
            		webSocketClient.sendMessage(wsDataMap);
            		
            		
            		break;
            	case BrtAtCode.TRAFFIC_MODULE_TWO:
            		try {
	            		AtTrafficModule2 trafficModule2 = (AtTrafficModule2)atMessage.getAttrData();
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
            		break;
            	case BrtAtCode.TRAFFIC_MODULE_THREE:
            		try {
	            		AtTrafficModule3 trafficModule3 = (AtTrafficModule3)atMessage.getAttrData();
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
	                    moduleThreeMap.put("CTRL_TYPE",getCommonCode( "SIG_CTL_TYPE", "TXT_VAL1",trafficModule3.getControlType()+"").get("DL_CD"));
	                    
	                    moduleThreeMap.put("CTRL_PHASE_NO",trafficModule3.getControlPhaseNum());
	                    moduleThreeMap.put("OCR_DTM",trafficModule3.getUpdateTm().toString());
	    				
	                    trafficModule3MapList.add(moduleThreeMap);
	            		
	        			//웹소켓 데이터 세팅
	        	    	Map<String, Object> wsModuleThreeDataMap = new HashMap<>();
	        	    	wsModuleThreeDataMap.put("ATTR_ID", BrtAtCode.TRAFFIC_MODULE_TWO);
	        	    	wsModuleThreeDataMap.put("LIST", trafficModule3MapList);
	                	
	            		webSocketClient.sendMessage(wsModuleThreeDataMap);
	            		curInfoMapper.insertOrUpdateSigOperEventInfo(moduleThreeMap);
		    		} catch (Exception e) {
		    			logger.error("Exception {}", e);
		    		}            		
            		break;
            		
            
                default:
                    break;


            } //switch(attrId)

        } //for(AtMessage atMessage : payload.getAttrList())


        return resultMap;
        
    } //handler()


} //GetResponse()
