package kr.tracom.tims.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import kr.tracom.platform.attribute.BrtAtCode;
import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.attribute.AtData;
import kr.tracom.platform.net.protocol.attribute.AtMessage;
import kr.tracom.platform.net.protocol.attribute.AtResult;
import kr.tracom.platform.net.protocol.payload.PlGetResponse;
import kr.tracom.platform.net.protocol.payload.PlSetResponse;
import kr.tracom.ws.WsMessage;

@Component
public class SetResponse {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    public WsMessage handle(TimsMessage timsMessage, String sessionId){
    	
    	WsMessage wsMsg = null;
    	
    	boolean bResponse = true;
        PlSetResponse payload = (PlSetResponse) timsMessage.getPayload();
        short attrId = 0;
        AtData attrData = null;
        List<Short> attributeList = new ArrayList<>();

        for (int i = 0; i < payload.getAttrCount(); i++) {
            AtResult atResult = payload.getResultList().get(i);
            if(0x00 != atResult.getResult()) {
                bResponse = false;
                break;
            } else {
            	attrId = payload.getResultList().get(i).getAtId();

                //attrID 별 처리
            	
            }
            
        }   
        
        return wsMsg;

    }


}
