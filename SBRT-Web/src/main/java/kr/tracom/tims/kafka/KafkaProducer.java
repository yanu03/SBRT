package kr.tracom.tims.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.service.config.KafkaTopics;
import kr.tracom.platform.service.kafka.model.KafkaMessage;

@Component
public class KafkaProducer {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;

    @Async
    public void produceTimsMessage(String topic, KafkaMessage message) {
    	
        kafkaTemplate.send(topic, message);
        
    }
    
    
    public void sendKafka(TimsMessage timsMessage, String deviceId) {

        if(timsMessage == null) {
        	logger.info("TimsMessage is NULL!!");
            return;
        }

        if(timsMessage.getPayload() == null) {
        	logger.info("TimsMessage Payload is NULL!!");
            return;
        }

        KafkaMessage kafkaMessage = new KafkaMessage((byte)deviceId.length(), deviceId, timsMessage, (byte)0, false, false);
        produceTimsMessage(KafkaTopics.T_COMMUNICATION, kafkaMessage);

    }
    
    
    public void sendKafka(String topic, TimsMessage timsMessage, String deviceId) {
    	
    	//logger.info("sendKafka() >> topic:{}, timsMessage:{}", topic, timsMessage);
    	

        if(timsMessage == null) {
        	logger.info("TimsMessage is NULL!!");
            return;
        }

        if(timsMessage.getPayload() == null) {
        	logger.info("TimsMessage Payload is NULL!!");
            return;
        }

        KafkaMessage kafkaMessage = new KafkaMessage((byte)deviceId.length(), deviceId, timsMessage, (byte)0, false, false);
        produceTimsMessage(topic, kafkaMessage);

    }
    
}
