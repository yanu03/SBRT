package kr.tracom.tims.handler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import kr.tracom.platform.attribute.BrtAtCode;
import kr.tracom.platform.attribute.brt.AtTrafficLightStatusResponse;
import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.attribute.AtMessage;
import kr.tracom.platform.net.protocol.payload.PlGetResponse;

@Component
public class GetResponse {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    public Map<String, Object> handle(TimsMessage timsMessage, String sessionId){
    	
    	
    	logger.info("<== PlCode.OP_GET_RES message:{}, sessionId:{}", timsMessage, sessionId);
    	
    	
    	Map<String, Object> resultMap = null;
        PlGetResponse payload = (PlGetResponse)timsMessage.getPayload();
        

        for(AtMessage atMessage : payload.getAttrList()){
            short attrId = atMessage.getAttrId();

            switch(attrId){
            
            	case BrtAtCode.TRAFFIC_LIGHT_STATUS_RESPONSE:
            		AtTrafficLightStatusResponse lightStatus = (AtTrafficLightStatusResponse)atMessage.getAttrData();
            		
            		logger.info("현시정보 : {}", lightStatus);
            		
            		break;
            
                default:
                    break;


            } //switch(attrId)

        } //for(AtMessage atMessage : payload.getAttrList())


        return resultMap;
        
    } //handler()


} //GetResponse()
