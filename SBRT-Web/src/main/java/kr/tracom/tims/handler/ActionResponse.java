package kr.tracom.tims.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.attribute.AtData;
import kr.tracom.platform.net.protocol.attribute.AtMessage;
import kr.tracom.platform.net.protocol.payload.PlActionResponse;
import kr.tracom.ws.WsMessage;

@Component
public class ActionResponse {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public WsMessage handle(TimsMessage timsMessage, String sessionId){
    	
    	WsMessage wsMsg = null;
    	
    	PlActionResponse response = (PlActionResponse)timsMessage.getPayload();
        AtMessage atMessage = response.getAtMessage();
        
        short attrId = atMessage.getAttrId();
        AtData atData = atMessage.getAttrData();    
        
        //attrID 별 처리
        
        
        
        
        return wsMsg;
        
    }
}
