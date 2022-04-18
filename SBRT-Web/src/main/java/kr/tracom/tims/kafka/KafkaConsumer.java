package kr.tracom.tims.kafka;

import java.util.Map;

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
	

    @KafkaListener(topics = "#{'${kafka.topic.member}'.split(',')}")
    public void processResult(ConsumerRecord<String, KafkaMessage> record) throws Exception {
    	
    	Map<String, Object> map = null;
//    	WsMessage wsMessage = null;
    	KafkaMessage kafkaMessage = record.value();
    	
    	//logger.debug("======= Received Kafka message:{}", kafkaMessage);
    	
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
				map = getRequest.handle(timsMessage, sessionId);
				break;
				
			case PlCode.OP_GET_RES:
				map = getResponse.handle(timsMessage, sessionId);
				break;
				
			case PlCode.OP_SET_REQ:
				//map = setRequest.handle(timsMessage, sessionId);
				eventRequest.receiveKafka(kafkaMessage); 
				break;
				
			case PlCode.OP_SET_RES:
				map = setResponse.handle(timsMessage, sessionId);
				break;
				
			case PlCode.OP_ACTION_REQ:
				map = actionRequest.handle(timsMessage, sessionId);
				break;
				
			case PlCode.OP_ACTION_RES:
				map = actionResponse.handle(timsMessage, sessionId);
				break;
				
			case PlCode.OP_EVENT_REQ:
				//map = eventRequest.handle(timsMessage, sessionId);
				eventRequest.receiveKafka(kafkaMessage); //이벤트는 쓰레드로 별도 처리
				
				break;
				
			case PlCode.OP_EVENT_RES:
				map = eventResponse.handle(timsMessage, sessionId);
				break;

			default:
				break;
			}
            

        } //if(kafkaMessage != null)
        
        
        //웹소켓 전송이 필요한 경우
        if(map != null) {    		
    		webSocketClient.sendMessage(map);
        }
        
        
        
    } //processResult()
    
    
    
    @KafkaListener(topics = {KafkaTopics.T_COMMUNICATION})
    public void processCommuicationMsg(ConsumerRecord<String, KafkaMessage> record) throws Exception {
    
    	/* Communication 으로 가는것도 필요에 따라 처리해야함 */
    	
    	//logger.info("================ Received Kafka message :: KafkaTopics.T_COMMUNICATION");
    	
    	Map<String, Object> map = null;
//    	WsMessage wsMessage = null;
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

            
            //디스패치 처리
            
            switch (opCode) {
			case PlCode.OP_GET_REQ:
				break;
				
			case PlCode.OP_GET_RES:
				break;
				
			case PlCode.OP_SET_REQ:
				break;
				
			case PlCode.OP_SET_RES:
				break;
				
			case PlCode.OP_ACTION_REQ:
				break;
				
			case PlCode.OP_ACTION_RES:
				break;
				
			case PlCode.OP_EVENT_REQ:
				//map = eventRequest.handle(timsMessage, sessionId);
				eventRequest.receiveKafka(kafkaMessage); //이벤트는 쓰레드로 별도 처리
				break;
				
			case PlCode.OP_EVENT_RES:
				break;

			default:
				break;
			}
            

        } //if(kafkaMessage != null)
        
        
        //웹소켓 전송이 필요한 경우
        if(map != null) {    		
    		webSocketClient.sendMessage(map);
        }
        
        
        
    } //processResult()
    
	
    
    
}
