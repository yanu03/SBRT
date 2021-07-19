package kr.tracom.tims.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.TimsPayload;
import kr.tracom.platform.net.protocol.payload.PlCode;
import kr.tracom.platform.service.config.KafkaTopics;
import kr.tracom.platform.service.kafka.model.KafkaMessage;
import kr.tracom.tims.handler.ActionRequest;
import kr.tracom.tims.handler.ActionResponse;
import kr.tracom.tims.handler.EventRequest;
import kr.tracom.tims.handler.EventResponse;
import kr.tracom.tims.handler.GetRequest;
import kr.tracom.tims.handler.GetResponse;
import kr.tracom.tims.handler.SetRequest;
import kr.tracom.tims.handler.SetResponse;
import kr.tracom.ws.WsClient;
import kr.tracom.ws.WsMessage;




@Component
public class KafkaConsumer {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	WsClient webSocketClient;
	
    @Autowired
    private GetRequest getRequest;

    @Autowired
    private GetResponse getResponse;
    
    @Autowired
    private SetRequest setRequest;
    
    @Autowired
    private SetResponse setResponse;

    @Autowired
    private ActionRequest actionRequest;

    @Autowired
    private ActionResponse actionResponse;

    @Autowired
    private EventRequest eventRequest;
    
    @Autowired
    private EventResponse eventResponse;
	

    @KafkaListener(topics = {KafkaTopics.T_BMS, KafkaTopics.T_BRT, KafkaTopics.T_COMMON})
    public void processResult(ConsumerRecord<String, KafkaMessage> record) throws Exception {
    	
    	//logger.info("================ Received Kafka message");
    	
    	WsMessage wsMessage = null;
    	KafkaMessage kafkaMessage = record.value();
    	
        if(kafkaMessage != null) {
        	
        	String sessionId = kafkaMessage.getSessionId();
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
				wsMessage = getRequest.handle(timsMessage, sessionId);
				break;
				
			case PlCode.OP_GET_RES:
				wsMessage = getResponse.handle(timsMessage, sessionId);
				break;
				
			case PlCode.OP_SET_REQ:
				wsMessage = setRequest.handle(timsMessage, sessionId);
				break;
				
			case PlCode.OP_SET_RES:
				wsMessage = setResponse.handle(timsMessage, sessionId);
				break;
				
			case PlCode.OP_ACTION_REQ:
				wsMessage = actionRequest.handle(timsMessage, sessionId);
				break;
				
			case PlCode.OP_ACTION_RES:
				wsMessage = actionResponse.handle(timsMessage, sessionId);
				break;
				
			case PlCode.OP_EVENT_REQ:
				wsMessage = eventRequest.handle(timsMessage, sessionId);
				break;
				
			case PlCode.OP_EVENT_RES:
				wsMessage = eventResponse.handle(timsMessage, sessionId);
				break;

			default:
				break;
			}
            

        } //if(kafkaMessage != null)
        
        
        //웹소켓 전송이 필요한 경우
        if(wsMessage != null) {    		
    		webSocketClient.sendMessage(wsMessage);
        }
        
        
        
    } //processResult()
    
    
	
    
    
}
