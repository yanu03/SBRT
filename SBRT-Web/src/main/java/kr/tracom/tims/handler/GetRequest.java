package kr.tracom.tims.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.payload.PlGetRequest;
import kr.tracom.platform.net.protocol.payload.PlGetResponse;
import kr.tracom.ws.WsMessage;

@Component
public class GetRequest {

    Logger logger = LoggerFactory.getLogger(this.getClass());
   

    public WsMessage handle(TimsMessage timsMessage, String sessionId){
    	
    	WsMessage wsMsg = null;

		PlGetRequest request = (PlGetRequest) timsMessage.getPayload();
		PlGetResponse response = new PlGetResponse();

        for(Short attrId : request.getAttrList()) {
            //attrID 별 처리

        }
        
        
        return wsMsg;
        
        
    }
}
