package kr.tracom.tims.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import kr.tracom.platform.attribute.BisAtCode;
import kr.tracom.platform.attribute.bis.AtBusEvent;
import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.attribute.AtMessage;
import kr.tracom.platform.net.protocol.payload.PlEventRequest;
import kr.tracom.ws.WsMessage;

@Component
public class EventRequest {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    public WsMessage handle(TimsMessage timsMessage, String sessionId){
    	
    	WsMessage wsMsg = null;
    	
    	
        PlEventRequest request = (PlEventRequest) timsMessage.getPayload();

        for(int i=0; i<request.getAttrCount(); i++){
            AtMessage atMessage = request.getAttrList().get(i);
            short attrId = atMessage.getAttrId();

            switch(attrId){
                case BisAtCode.BUS_EVENT :
                    AtBusEvent atData = (AtBusEvent) atMessage.getAttrData();
                    
                    //busEvent.handler(atData);
                    
                    logger.info("BUS EVENT 발생");                    
                    //TODO: 이벤트 코드에 따른 핸들링 처리 필요
                    break;
            }
        }
        
        
        return wsMsg;
    }
    
    
}
