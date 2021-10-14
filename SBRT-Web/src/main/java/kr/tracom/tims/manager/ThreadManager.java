package kr.tracom.tims.manager;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import kr.tracom.tims.handler.EventThread;

@Component
public class ThreadManager {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Map<String, Object> eventThreadMap = new HashMap<String, Object>();
	
	
	public EventThread getEventThread(String sessionId) {
		
		EventThread worker = null;
		
		//logger.info("getEventThread >> sessionId:{}", sessionId);
		
		if(eventThreadMap.containsKey(sessionId)) {			
			
			worker = (EventThread)eventThreadMap.get(sessionId);
			//logger.info("getEventThread >> sessionId:{}, worker:{}", sessionId, worker);
			
		} else {
			worker = new EventThread(sessionId);
			worker.setDaemon(true);
			worker.start();
			
			eventThreadMap.put(sessionId, worker);
			
			logger.info("Create EventThread >> sessionId:{}, thread count:{}", sessionId, eventThreadMap.size());
		}
		
		return worker;
	}
	
	public void removeEventThread(String sessionId) {
		
		if(eventThreadMap.containsKey(sessionId)) {
			EventThread worker = (EventThread)eventThreadMap.get(sessionId);
			worker.stop(true);			
			
			eventThreadMap.remove(sessionId);
			
			logger.info("Remove EventThread >> sessionId:{}, thread count:{}", sessionId, eventThreadMap.size());
		}
		
	}
	
	
	
}
