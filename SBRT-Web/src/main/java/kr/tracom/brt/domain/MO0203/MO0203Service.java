package kr.tracom.brt.domain.MO0203;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.platform.attribute.common.AtBrtAction;
import kr.tracom.platform.net.config.TimsConfig;
import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.TimsMessageBuilder;
import kr.tracom.platform.service.TService;
import kr.tracom.platform.service.config.KafkaTopics;
import kr.tracom.tims.kafka.KafkaProducer;

@Service
public class MO0203Service extends ServiceSupport{
	
	@Autowired
	private MO0203Mapper mo0203Mapper;
	
	@Autowired
	KafkaProducer kafkaProducer;
	
	public List<Map> MO0203G0R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return mo0203Mapper.MO0203G0R0(param);
	}
	
	public List<Map> MO0203SHI0() throws Exception{
		return mo0203Mapper.MO0203SHI0();
	}
	
	public List MO0203SHI1() throws Exception {
		return mo0203Mapper.MO0203SHI1();
	}
	
	public List MO0203SHI2() throws Exception {
		return mo0203Mapper.MO0203SHI2();
	}	
	
	public List MO0203G2R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_sub_search");
		return mo0203Mapper.MO0203G2R0(param);
	}	
	
	public List<Map> MO0203SCK() throws Exception{
		
		Map param = getSimpleDataMap("dma_sub_search");
		
		String sttnId = String.valueOf(param.get("NODE_ID"));
		
		//정류장정보 요청 데이터 생성
		AtBrtAction brtRequest = new AtBrtAction();

		brtRequest.setActionCode((byte)AtBrtAction.bitInfoRequest);
		brtRequest.setData(sttnId);

        
        TimsConfig timsConfig = TService.getInstance().getTimsConfig();
        TimsMessageBuilder builder = new TimsMessageBuilder(timsConfig);
        TimsMessage timsMessage = builder.actionRequest(brtRequest);
        
        //정류장정보 요청 전송
        kafkaProducer.sendKafka(KafkaTopics.T_BRT, timsMessage, sttnId);	
		
		return null;
		//return mo0203Mapper.MO0203SCK(param);
	}
	
}
