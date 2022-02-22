package kr.tracom.tims;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.platform.attribute.common.AtBrtAction;
import kr.tracom.platform.attribute.common.AtTimeStamp;
import kr.tracom.platform.attribute.manager.AttributeManager;
import kr.tracom.platform.net.config.TimsConfig;
import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.TimsMessageBuilder;
import kr.tracom.platform.service.TService;
import kr.tracom.platform.service.config.KafkaTopics;
import kr.tracom.tims.domain.CurInfoMapper;
import kr.tracom.tims.kafka.KafkaProducer;
import kr.tracom.util.DateUtil;

@Service
public class TimsService {

	private static final Logger logger = LoggerFactory.getLogger(TimsService.class);
	
	@Autowired
    KafkaProducer kafkaProducer;
	
	@Autowired
	CurInfoMapper curInfoMapper;
	
	
    @PostConstruct
    public void initialize() {
        bindAttribute();
    }

    public void bindAttribute() {
        AttributeManager.bind(AttributeManager.COMMON_ATTRIBUTE);
        AttributeManager.bind(AttributeManager.BMS_ATTRIBUTE);
        AttributeManager.bind(AttributeManager.BRT_ATTRIBUTE);
    }


    public void refreshCurOperAllocPL() {
    	curInfoMapper.refreshCurOperAllocPLRoutInfo();
    	curInfoMapper.refreshCurOperAllocPLNodeInfo();
    }
    
    
    public void notifyOperAllocCompleted() {
    	
    	
    	refreshCurOperAllocPL(); //현재운행계획 갱신
    	
    	
    	AtBrtAction brtRequest = new AtBrtAction();

		brtRequest.setTimeStamp(new AtTimeStamp(DateUtil.now("yyyyMMddHHmmssSSS")));
		brtRequest.setActionCode(AtBrtAction.makeOperDone);
		brtRequest.setData("");
		brtRequest.setReserved("");

        
        TimsConfig timsConfig = TService.getInstance().getTimsConfig();
        TimsMessageBuilder builder = new TimsMessageBuilder(timsConfig);
        TimsMessage tMessage = builder.actionRequest(brtRequest);
        
        logger.info("======== 운행계획 생성 완료 전송 : {}", tMessage);		
        
        kafkaProducer.sendKafka(KafkaTopics.T_BRT, tMessage, "");	
    }
    
    

}
