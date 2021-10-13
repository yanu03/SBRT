package kr.tracom.ws;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
public class WsClient {
	
	private static final Logger logger = LoggerFactory.getLogger(WsClient.class);
	
	@Autowired
	SimpMessageSendingOperations messagingTemplate;


	public void sendMessage(Map<String, Object> wsMap) {
	

		//logger.info("################# sendMessage : " + message);
		
		try {
			messagingTemplate.convertAndSend("/topic/public", wsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
