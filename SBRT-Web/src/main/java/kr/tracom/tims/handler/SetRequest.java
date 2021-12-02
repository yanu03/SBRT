package kr.tracom.tims.handler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.attribute.AtData;
import kr.tracom.platform.net.protocol.attribute.AtMessage;
import kr.tracom.platform.net.protocol.payload.PlSetRequest;

@Component
public class SetRequest {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    public Map<String, Object> handle(TimsMessage timsMessage, String sessionId){
    	
    	logger.info("<== PlCode.OP_SET_REQ message:{}, sessionId:{}", timsMessage, sessionId);

    	Map<String, Object> resultMap = null;
    	
    	 PlSetRequest payload = (PlSetRequest) timsMessage.getPayload();

         for (AtMessage atMessage : payload.getAttrList()) {
             short attrId = atMessage.getAttrId();
             AtData atData = atMessage.getAttrData();

             //attrID 별 처리
             
         }    	
         
         return resultMap;
    	
    }


} 
