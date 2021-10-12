package kr.tracom.brt.domain.MO0101;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.domain.Vhc.VhcMapper;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.platform.attribute.brt.AtDispatch;
import kr.tracom.platform.attribute.common.AtBrtAction;
import kr.tracom.platform.attribute.common.AtTimeStamp;
import kr.tracom.platform.net.config.TimsConfig;
import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.TimsMessageBuilder;
import kr.tracom.platform.service.TService;
import kr.tracom.platform.service.config.KafkaTopics;
import kr.tracom.tims.kafka.KafkaProducer;
import kr.tracom.util.Constants;
import kr.tracom.util.DateUtil;

@Service
public class MO0101Service extends ServiceSupport{

	@Autowired
	private MO0101Mapper mo0101Mapper;
	
	@Autowired
	VhcMapper vhcMapper;
	
	
	@Autowired
	KafkaProducer kafkaProducer;	
	
	public List MO0101G0R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return mo0101Mapper.MO0101G0R0(param);
	}

	public List MO0101G1R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return mo0101Mapper.MO0101G1R0(param);
	}
	
	public List MO0101SHI0() throws Exception{
		return mo0101Mapper.MO0101SHI0();
	}
	
	public List MO0101SHI1() throws Exception{
		Map param = getSimpleDataMap("dma_search");		
		return mo0101Mapper.MO0101SHI1(param);
	}
	
	public List MO0101G2R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_search");
		return mo0101Mapper.MO0101G2R0(param);
	}
	
	public List MO0101G4R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_search");
		return mo0101Mapper.MO0101G4R0(param);
	}	

	public List<Map> MO0101SCK() throws Exception{
		
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
	}	
	
	public List MO0101P0R0() throws Exception{
		return mo0101Mapper.MO0101P0R0();
	}
	
	//메시지 전송 소켓
	public List<Map> MO0101SCK1() throws Exception{
		
		Map param = getSimpleDataMap("dma_search");
		
		String vhcId = String.valueOf(param.get("VHC_ID")); //차량 아이디
		String message = String.valueOf(param.get("MSG_CONTS")); //메시지 내용
		
        //디스패치 전송
        Map<String, Object> vhcDvcInfo = vhcMapper.selectVhcDvcInfo(param);
		
        if(vhcDvcInfo != null) {
        	String impId = vhcDvcInfo.get("MNG_ID").toString();
        	
        	AtDispatch dispatchReq = new AtDispatch();
    		
    		dispatchReq.setUpdateTm(new AtTimeStamp(DateUtil.now("yyyyMMddHHmmss")));
    		dispatchReq.setMessageType((byte)Constants.DispatchType.DISPATCH_TYPE_1); 
    		dispatchReq.setMessageLevel((byte)Constants.DispatchType.DISPATCH_LV_1);
    		dispatchReq.setMessage(message);
    		
    		TimsConfig timsConfig = TService.getInstance().getTimsConfig();
            TimsMessageBuilder builder = new TimsMessageBuilder(timsConfig);
            TimsMessage timsMessage = builder.eventRequest(dispatchReq);
        	
        	kafkaProducer.sendKafka(KafkaTopics.T_COMMUNICATION, timsMessage, impId);
        }
		
		
		return null;
	}		
}
