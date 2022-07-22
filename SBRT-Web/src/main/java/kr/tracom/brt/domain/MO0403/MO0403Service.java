package kr.tracom.brt.domain.MO0403;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.cm.domain.Vhc.VhcMapper;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.platform.attribute.brt.AtDispatch;
import kr.tracom.platform.attribute.common.AtTimeStamp;
import kr.tracom.platform.net.config.TimsConfig;
import kr.tracom.platform.net.protocol.TimsAddress;
import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.TimsMessageBuilder;
import kr.tracom.platform.service.TService;
import kr.tracom.platform.service.config.KafkaTopics;
import kr.tracom.tims.kafka.KafkaProducer;
import kr.tracom.util.Constants;
import kr.tracom.util.DateUtil;
import kr.tracom.util.Result;

@Service
public class MO0403Service extends ServiceSupport {

	@Autowired
	private MO0403Mapper mo0403Mapper;
	
	@Autowired
	VhcMapper vhcMapper;
	
	@Autowired
	KafkaProducer kafkaProducer;
	
	public List MO0403G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return mo0403Mapper.MO0403G0R0(map);
	}
	
	public List MO0403SHI0() throws Exception {
		return mo0403Mapper.MO0403SHI0();
	}
	
	public List MO0403SHI1() throws Exception {
		return mo0403Mapper.MO0403SHI1();
	}
	
	//메시지 전송 소켓
	public List<Map> MO0403SCK0() throws Exception{
		
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
            TimsMessageBuilder builder = new TimsMessageBuilder(TService.getInstance().getTimsConfig(), new TimsAddress(), new TimsAddress(TimsAddress.APP_ID_DRIVER_TERMINAL));
            TimsMessage timsMessage = builder.eventRequest(dispatchReq);
        	
        	kafkaProducer.sendKafka(KafkaTopics.T_COMMUNICATION, timsMessage, impId);
        }
		
		
		return null;
	}
		
	public List MO0403G1R0() throws Exception{
		return mo0403Mapper.MO0403G1R0();
	}	
}
