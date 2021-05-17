package kr.tracom.tims.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

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
}
