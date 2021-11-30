package kr.tracom.tims.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.tracom.cm.domain.Common.CommonMapper;
import kr.tracom.platform.service.kafka.model.KafkaMessage;
import kr.tracom.tims.domain.CurInfoMapper;
import kr.tracom.tims.domain.HistoryMapper;
import kr.tracom.tims.domain.TimsMapper;
import kr.tracom.tims.manager.ThreadManager;
import kr.tracom.ws.WsClient;

@Component
public class EventRequest {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    TimsMapper timsMapper;
    
    @Autowired
    HistoryMapper historyMapper;
    
    @Autowired
    CurInfoMapper curInfoMapper;
    
    @Autowired
    CommonMapper commonMapper;
    
    @Autowired
	WsClient webSocketClient;
    
    @Autowired
    ThreadManager threadManager;


	public void receiveKafka(KafkaMessage kafkaMessage) {		
		//this.addKafkaMessage(kafkaMessage);
		
		//thread 로 하나씩 돌리기
		String sessionId = kafkaMessage.getSessionId();
		
		logger.debug("<== PlCode.OP_EVENT_REQ message:{}, sessionId:{}", kafkaMessage.getTimsMessage(), sessionId);
		
		
		//sessionId 에 따라
		EventThread eventThread = threadManager.getEventThread(sessionId);
		eventThread.addKafkaMessage(kafkaMessage);
		
	}
	

    
}
