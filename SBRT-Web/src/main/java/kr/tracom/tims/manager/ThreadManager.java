package kr.tracom.tims.manager;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import kr.tracom.tims.handler.EventThread;
import kr.tracom.tims.handler.MorEventThread;

@Component
public class ThreadManager {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Map<String, Object> eventThreadMap = new HashMap<String, Object>();
	
	private Map<String, Object> morEventThreadMap = new HashMap<String, Object>();
	
	
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
	
	public MorEventThread getMorEventThread(String sessionId) {
		
		MorEventThread worker = null;
		
		//logger.info("getMorEventThread >> sessionId:{}", sessionId);
		
		if(morEventThreadMap.containsKey(sessionId)) {			
			
			worker = (MorEventThread)morEventThreadMap.get(sessionId);
			//logger.info("getMorEventThread >> sessionId:{}, worker:{}", sessionId, worker);
			
		} else {
			worker = new MorEventThread(sessionId);
			worker.setDaemon(true);
			worker.start();
			
			morEventThreadMap.put(sessionId, worker);
			
			logger.info("Create MorEventThread >> sessionId:{}, thread count:{}", sessionId, morEventThreadMap.size());
		}
		
		return worker;
	}
	
	public void removeMorEventThread(String sessionId) {
		
		if(morEventThreadMap.containsKey(sessionId)) {
			MorEventThread worker = (MorEventThread)morEventThreadMap.get(sessionId);
			worker.stop(true);			
			
			morEventThreadMap.remove(sessionId);
			
			logger.info("Remove MorEventThread >> sessionId:{}, thread count:{}", sessionId, morEventThreadMap.size());
		}
		
	}	
	
	
	
}
