package kr.tracom.tims.handler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.tracom.platform.attribute.BisAtCode;
import kr.tracom.platform.attribute.bis.AtFacilityParam;
import kr.tracom.platform.attribute.integration.bluemobile.AtBluemobileStatus;
import kr.tracom.platform.net.protocol.TimsHeaderTypeA;
import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.attribute.AtData;
import kr.tracom.platform.net.protocol.attribute.AtMessage;
import kr.tracom.platform.net.protocol.payload.PlSetRequest;

@Component
public class SetRequest {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    FacilityParam facilityParam;

    public Map<String, Object> handle(TimsMessage timsMessage, String sessionId){
    	
    	logger.info("<== PlCode.OP_SET_REQ message:{}, sessionId:{}", timsMessage, sessionId);

    	Map<String, Object> resultMap = null;
    
    	 PlSetRequest payload = (PlSetRequest) timsMessage.getPayload(); 

         for (AtMessage atMessage : payload.getAttrList()) {
             short attrId = atMessage.getAttrId();
             AtData atData = atMessage.getAttrData();

             switch(attrId){
             
	             case BisAtCode.FACILITY_PARAM:
	            	 if(atMessage.getAttrData()!=null) {
	            		 facilityParam.handle(attrId, atMessage.getAttrData(), sessionId);
	            	 }
	                 break;
	             
	             case BisAtCode.BLUEMOBILE_STATUS_INFO:
	            	 facilityParam.handle(attrId,  atMessage.getAttrData(), sessionId);
	                 break;
	         }
             
         }    	
         
         return resultMap;
    	
    }


} 
