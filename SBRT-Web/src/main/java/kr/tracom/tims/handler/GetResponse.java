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
import kr.tracom.ws.WsClient;

@Component
public class GetResponse {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    int i = 0;
    
    
    @Autowired
	WsClient webSocketClient;


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
            		
            		
            		
    				int phaseNo = lightStatus.getPhaseNumA();
    				String crsId = lightStatus.getCrossNodeId();
    				
    				HashMap<String, Object> phaseInfoMap = new HashMap<>();
    				phaseInfoMap.put("CRS_ID", crsId);
    				phaseInfoMap.put("PHASE_NO", phaseNo);
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
