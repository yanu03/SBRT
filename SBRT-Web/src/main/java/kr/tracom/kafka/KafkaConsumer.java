package kr.tracom.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.service.config.KafkaTopics;
import kr.tracom.platform.service.kafka.model.KafkaMessage;

@Component
public class KafkaConsumer {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(topics = {KafkaTopics.T_BRT})
    public void processResult(ConsumerRecord<String, KafkaMessage> record) throws Exception {
       
    	KafkaMessage message = record.value();
    	

        if(message != null) {
        	
        	TimsMessage timsMessage = message.getTimsMessage();
        	
            //logger.info("tims message: {}", timsMessage);


        }
    }
    
    
}
