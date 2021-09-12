package kr.tracom.tims.handler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import kr.tracom.platform.attribute.AtCode;
import kr.tracom.platform.attribute.common.AtServiceLogInOut;
import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.attribute.AtMessage;
import kr.tracom.platform.net.protocol.payload.PlActionRequest;

@Component
public class ActionRequest {

    Logger logger = LoggerFactory.getLogger(this.getClass());



    public Map<String, Object> handle(TimsMessage timsMessage, String sessionId){
    	
    	Map<String, Object> resultMap = null;
    	
        PlActionRequest request = (PlActionRequest) timsMessage.getPayload();
        //PlActionResponse response = new PlActionResponse();

        AtMessage atMessage = request.getAtMessage();
        short attrId = atMessage.getAttrId();

        switch(attrId){
            case AtCode.SERVICE_LOGINOUT :
                AtServiceLogInOut atData = (AtServiceLogInOut) atMessage.getAttrData();
                byte inOut = atData.getInOut();

                if(inOut == (byte)0) {
                    logger.info("login {}", sessionId);

                }
                else if(inOut == (byte)1){
                    logger.info("logout");
                }

                break;
        }
        
        
        return resultMap;
        
    }
    
    
}
