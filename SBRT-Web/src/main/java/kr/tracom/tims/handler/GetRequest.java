package kr.tracom.tims.handler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.payload.PlCode;
import kr.tracom.platform.net.protocol.payload.PlGetRequest;
import kr.tracom.platform.net.protocol.payload.PlGetResponse;

@Component
public class GetRequest {

    Logger logger = LoggerFactory.getLogger(this.getClass());
   

    public Map<String, Object> handle(TimsMessage timsMessage, String sessionId){    	
    	
    	logger.info("<== PlCode.OP_GET_REQ message:{}, sessionId:{}", timsMessage, sessionId);    	
    	
    	Map<String, Object> resultMap = null;

		PlGetRequest request = (PlGetRequest) timsMessage.getPayload();
		PlGetResponse response = new PlGetResponse();

        for(Short attrId : request.getAttrList()) {
            //attrID 별 처리

        }
        
        
        return resultMap;
        
        
    }
}
