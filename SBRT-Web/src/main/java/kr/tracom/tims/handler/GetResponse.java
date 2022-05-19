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
import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.attribute.AtMessage;
import kr.tracom.platform.net.protocol.payload.PlGetResponse;
import kr.tracom.tims.domain.HistoryMapper;
import kr.tracom.ws.WsClient;

@Component
public class GetResponse {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    int i = 0;
    
    
    @Autowired
	WsClient webSocketClient;
    
    @Autowired
    HistoryMapper historyMapper;


    public Map<String, Object> handle(TimsMessage timsMessage, String sessionId){
    	
    	
    	//logger.info("<== PlCode.OP_GET_RES message:{}, sessionId:{}", timsMessage, sessionId);
    	
    	
    	Map<String, Object> resultMap = null;
        PlGetResponse payload = (PlGetResponse)timsMessage.getPayload();
        

        for(AtMessage atMessage : payload.getAttrList()){
            short attrId = atMessage.getAttrId();

            switch(attrId){
            
            	case BrtAtCode.TRAFFIC_LIGHT_STATUS_RESPONSE:
            		
            		List<HashMap <String, Object>> phaseInfoMapList = new ArrayList<>();
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
    				//historyMapper.updateFcltCondParamInfo(phaseInfoMap);
    				
    				/*phaseInfoMap.put("DATA_VAL", contSt);
    				phaseInfoMap.put("PARAM_DIV", "PD003");
    				phaseInfoMap.put("PARAM_KIND", "PK030");
    				historyMapper.updateFcltCondParamInfo(phaseInfoMap);
    				
    				phaseInfoMap.put("DATA_VAL", conMode);
    				phaseInfoMap.put("PARAM_DIV", "PD003");
    				phaseInfoMap.put("PARAM_KIND", "PK039");
    				historyMapper.updateFcltCondParamInfo(phaseInfoMap);
    				
    				phaseInfoMap.put("DATA_VAL", phaseNoA);
    				phaseInfoMap.put("PARAM_DIV", "PD003");
    				phaseInfoMap.put("PARAM_KIND", "PK040");
    				historyMapper.updateFcltCondParamInfo(phaseInfoMap);
    				
    				phaseInfoMap.put("DATA_VAL", phaseNoB);
    				phaseInfoMap.put("PARAM_DIV", "PD003");
    				phaseInfoMap.put("PARAM_KIND", "PK042");
    				historyMapper.updateFcltCondParamInfo(phaseInfoMap);
    				
    				phaseInfoMap.put("DATA_VAL", pahseTmA);
    				phaseInfoMap.put("PARAM_DIV", "PD003");
    				phaseInfoMap.put("PARAM_KIND", "PK041");
    				historyMapper.updateFcltCondParamInfo(phaseInfoMap);
    				
    				phaseInfoMap.put("DATA_VAL", pahseTmB);
    				phaseInfoMap.put("PARAM_DIV", "PD003");
    				phaseInfoMap.put("PARAM_KIND", "PK043");
    				historyMapper.updateFcltCondParamInfo(phaseInfoMap);*/
    				
    				
    				phaseInfoMapList.add(phaseInfoMap);
            		
            		
        			//웹소켓 데이터 세팅
        	    	Map<String, Object> wsDataMap = new HashMap<>();
                	wsDataMap.put("ATTR_ID", BrtAtCode.TRAFFIC_LIGHT_STATUS_RESPONSE);
                	wsDataMap.put("LIST", phaseInfoMapList);
                	
                	//logger.info("================ 교차로아이디:{}, 현시번호 : {}", crsId, phaseNo);
                	
            		webSocketClient.sendMessage(wsDataMap);
            		
            		
            		break;
            
                default:
                    break;


            } //switch(attrId)

        } //for(AtMessage atMessage : payload.getAttrList())


        return resultMap;
        
    } //handler()


} //GetResponse()
