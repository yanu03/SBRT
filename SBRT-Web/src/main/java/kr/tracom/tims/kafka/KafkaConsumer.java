package kr.tracom.tims.kafka;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.TimsPayload;
import kr.tracom.platform.net.protocol.attribute.AtData;
import kr.tracom.platform.net.protocol.attribute.AtMessage;
import kr.tracom.platform.net.protocol.attribute.AtResult;
import kr.tracom.platform.net.protocol.payload.PlActionResponse;
import kr.tracom.platform.net.protocol.payload.PlCode;
import kr.tracom.platform.net.protocol.payload.PlEventRequest;
import kr.tracom.platform.net.protocol.payload.PlEventResponse;
import kr.tracom.platform.net.protocol.payload.PlGetRequest;
import kr.tracom.platform.net.protocol.payload.PlGetResponse;
import kr.tracom.platform.net.protocol.payload.PlSetRequest;
import kr.tracom.platform.net.protocol.payload.PlSetResponse;
import kr.tracom.platform.service.config.KafkaTopics;
import kr.tracom.platform.service.kafka.model.KafkaMessage;

@Component
public class KafkaConsumer {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(topics = {KafkaTopics.T_BMS, KafkaTopics.T_BRT, KafkaTopics.T_COMMON})
    public void processResult(ConsumerRecord<String, KafkaMessage> record) throws Exception {
       
    	KafkaMessage kafkaMessage = record.value();
    	
        if(kafkaMessage != null) {
        	
        	TimsMessage timsMessage = kafkaMessage.getTimsMessage();
        	
        	//logger.info("tims message: {}", timsMessage);    	
            
        	if(timsMessage == null) {
            	//logger.info("TimsMessage is NULL!!");        
            	return;
            }
        	
            String impId = kafkaMessage.getSessionId();
            TimsPayload timsPayload = timsMessage.getPayload();
            
            if(timsPayload == null) {
            	//logger.info("TimsMessage Payload is NULL!!");        
            	return;
            }
            
            byte opCode = timsPayload.OpCode;

            
            switch (opCode) {
			case PlCode.OP_GET_REQ:
			{
				PlGetRequest request = (PlGetRequest) timsMessage.getPayload();

                for(Short attrId : request.getAttrList()) {
                    //attrID 별 처리

                }
				
			}
				break;
				
			case PlCode.OP_GET_RES:
			{
                PlGetResponse payload = (PlGetResponse)timsMessage.getPayload();

                for (AtMessage atMessage : payload.getAttrList()) {
                    short attrId = atMessage.getAttrId();
                    AtData atData = atMessage.getAttrData();
                    

                    //attrID 별 처리

                }
                
				
			}
				break;
				
			case PlCode.OP_SET_REQ:
			{
                PlSetRequest payload = (PlSetRequest) timsMessage.getPayload();

                for (AtMessage atMessage : payload.getAttrList()) {
                    short attrId = atMessage.getAttrId();
                    AtData atData = atMessage.getAttrData();

                    //attrID 별 처리

                }
                
				
			}
				break;
				
			case PlCode.OP_SET_RES:
			{
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
                
				
			}
				break;
				
			case PlCode.OP_ACTION_REQ:
			{
				
				
			}
				break;
				
			case PlCode.OP_ACTION_RES:
			{
            	PlActionResponse response = (PlActionResponse)timsMessage.getPayload();
                AtMessage atMessage = response.getAtMessage();
                
                short attrId = atMessage.getAttrId();
                AtData atData = atMessage.getAttrData();    
                
                //attrID 별 처리				
				
				
			}
				break;
				
			case PlCode.OP_EVENT_REQ:
			{
                List<Short> attributeList = new ArrayList<>();
                PlEventRequest payload = (PlEventRequest) timsMessage.getPayload();

                for (int i = 0; i < payload.getAttrCount(); i++) {
                    AtMessage atMessage = payload.getAttrList().get(i);
                    short attrId = atMessage.getAttrId();
                    AtData atData = atMessage.getAttrData();    
                    
                    //attrID 별 처리
                    
                }   
                
				
			}
				break;
				
			case PlCode.OP_EVENT_RES:
			{
                boolean bResult = true;
                PlEventResponse payload = (PlEventResponse) timsMessage.getPayload();
                short attrId = 0;
                AtData attrData = null;
                List<Short> attributeList = new ArrayList<>();

                for (int i = 0; i < payload.getAttrCount(); i++) {
                    AtResult atResult = payload.getResultList().get(i);
                    if(0x00 != atResult.getResult()) {
                        bResult = false;
                        break;
                    } else {
                    	attrId = payload.getResultList().get(i).getAtId();
                    	
                        //attrID 별 처리
                    	
                    }
                }
                
				
			}
				break;

			default:
				break;
			}
            
            
            
            
            

        }
    }
    
    
}
