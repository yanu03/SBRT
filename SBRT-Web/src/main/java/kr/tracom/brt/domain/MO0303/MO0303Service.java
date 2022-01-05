package kr.tracom.brt.domain.MO0303;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
import kr.tracom.ws.WsClient;

@Service
public class MO0303Service extends ServiceSupport {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	

	@Autowired
	private MO0303Mapper mo0303Mapper;
	
	@Autowired
	VhcMapper vhcMapper;
	
	@Autowired
	WsClient webSocketClient;
	
	@Autowired
	KafkaProducer kafkaProducer;	
	
	public List MO0303G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return mo0303Mapper.MO0303G0R0(map);
	}

	public Map MO0303G0K0() throws Exception {
		return mo0303Mapper.MO0303G0K0(); 
	}	
	
	public List MO0303SHI0() throws Exception {
		return mo0303Mapper.MO0303SHI0();
	}	
	
	public Map MO0303G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BRT_INCDNT_INFO");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += mo0303Mapper.MO0303G0I0(data);
				} else if (rowStatus.equals("U")) {
					uCnt += mo0303Mapper.MO0303G0U0(data);
				} else if (rowStatus.equals("D")) {
					dCnt += mo0303Mapper.MO0303G0D0(data);
				} 
			}			
		} catch(Exception e) {
			if (e instanceof DuplicateKeyException)
			{
				throw new MessageException(Result.ERR_KEY, "중복된 키값의 데이터가 존재합니다.");
			}
			else
			{
				throw e;
			}		
		}

		
		Map result = saveResult(iCnt, uCnt, dCnt);
		
		return result;		
		
		
	}
	
	public List MO0303G1R0() throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = getSimpleDataMap("dma_subsearch");
		return mo0303Mapper.MO0303G1R0(map);
	}
	
	public Map MO0303G1K0() throws Exception {
		return mo0303Mapper.MO0303G1K0(); 
	}
	
	public Map MO0303G1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BRT_INCDNT_RES_INFO");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += mo0303Mapper.MO0303G1I0(data);
				} else if (rowStatus.equals("U")) {
					uCnt += mo0303Mapper.MO0303G1U0(data);
				}
				else if (rowStatus.equals("D")) {
					dCnt += mo0303Mapper.MO0303G1D0(data);
				} 
			}			
		} catch(Exception e) {
			if (e instanceof DuplicateKeyException)
			{
				throw new MessageException(Result.ERR_KEY, "중복된 키값의 데이터가 존재합니다.");
			}
			else
			{
				throw e;
			}		
		}

		
		Map result = saveResult(iCnt, uCnt, dCnt);
		
		return result;	
	}

	public List MO0303P2R0() throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return mo0303Mapper.MO0303P2R0(map);
	}	

	public List MO0303SHI1() throws Exception {
		return mo0303Mapper.MO0303SHI1();
	}
	
	public List MO0303P3R1() throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return mo0303Mapper.MO0303P3R1(map);
	}
	
	public List allocVhcList() throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return mo0303Mapper.allocVhcList(map);
	}	
	
	//돌발 메시지 소켓
	public List<Map> MO0303SCK0() throws Exception {
		
		Map param = getSimpleDataMap("dma_search");
		
//		String vhcId = String.valueOf(param.get("VHC_ID")); //차량 아이디
		String message = String.valueOf(param.get("MSG_CONTS")); //메시지 내용
		String vhcIds[] = param.get("VHC_IDS").toString().replace(" ",  "").replace("[",  "").replace("]",  "").split(","); //차량 아이디

		
		if(vhcIds == null || vhcIds.length <= 0) {
			logger.info("돌발 디스패치 전송할 차량 없음");
			return null;
		}
		
		//쿼리를 위해 따옴표 붙이기
		for(int i=0; i<vhcIds.length; i++) {
			String vhcIdStr = "\'" + vhcIds[i] + "\'";
			vhcIds[i] = vhcIdStr;
		}		
		

		//전송할 차량목록 검색
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("VHC_IDS", vhcIds);
        List<Map<String, Object>> vhcDvcInfoList = vhcMapper.selectVhcDvcInfoList(paramMap);
        
        
		//디스패치 전송
        for(Map<String, Object> vhcDvcInfo : vhcDvcInfoList) {
        	
        	//logger.info("===========VHC_ID:{}, MNG_ID:{}", vhcDvcInfo.get("VHC_ID").toString(), vhcDvcInfo.get("MNG_ID").toString());
        	
        	String impId = vhcDvcInfo.get("MNG_ID").toString();
        	
        	if(!StringUtils.isEmpty(impId)) {
	        	AtDispatch dispatchReq = new AtDispatch();
	    		
	    		dispatchReq.setUpdateTm(new AtTimeStamp(DateUtil.now("yyyyMMddHHmmss")));
	    		dispatchReq.setMessageType((byte)Constants.DispatchType.DISPATCH_TYPE_1); 
	    		dispatchReq.setMessageLevel((byte)Constants.DispatchType.DISPATCH_LV_3);
	    		dispatchReq.setMessage(message);
	    		
	    		TimsConfig timsConfig = TService.getInstance().getTimsConfig();
	            TimsMessageBuilder builder = new TimsMessageBuilder(timsConfig);
	            TimsMessage timsMessage = builder.eventRequest(dispatchReq);
	        	
	        	kafkaProducer.sendKafka(KafkaTopics.T_COMMUNICATION, timsMessage, impId);
        	}
        	
        }
        		
		return null;
	}
	
	public List<Map> MO0303SCK1() throws Exception {
		
		Map param = getSimpleDataMap("dma_search");
		
//		String vhcId = String.valueOf(param.get("VHC_ID")); //차량 아이디
		String message = String.valueOf(param.get("MSG_CONTS")); //메시지 내용
		String vhcIds[] = param.get("VHC_IDS").toString().replace(" ",  "").replace("[",  "").replace("]",  "").split(","); //차량 아이디
		
		
		if(vhcIds == null || vhcIds.length <= 0) {
			logger.info("돌발 디스패치 전송할 차량 없음");
			return null;
		}
		
		//쿼리를 위해 따옴표 붙이기
		for(int i=0; i<vhcIds.length; i++) {
			String vhcIdStr = "\'" + vhcIds[i] + "\'";
			vhcIds[i] = vhcIdStr;
		}		
		
		
		//전송할 차량목록 검색
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("VHC_IDS", vhcIds);
		List<Map<String, Object>> vhcDvcInfoList = vhcMapper.selectVhcDvcInfoList(paramMap);
		
		
		//디스패치 전송
		for(Map<String, Object> vhcDvcInfo : vhcDvcInfoList) {
			
			//logger.info("===========VHC_ID:{}, MNG_ID:{}", vhcDvcInfo.get("VHC_ID").toString(), vhcDvcInfo.get("MNG_ID").toString());
			
			String impId = vhcDvcInfo.get("MNG_ID").toString();
			
			if(!StringUtils.isEmpty(impId)) {
				AtDispatch dispatchReq = new AtDispatch();
				
				dispatchReq.setUpdateTm(new AtTimeStamp(DateUtil.now("yyyyMMddHHmmss")));
				dispatchReq.setMessageType((byte)Constants.DispatchType.DISPATCH_TYPE_1); 
				dispatchReq.setMessageLevel((byte)Constants.DispatchType.DISPATCH_LV_2);
				dispatchReq.setMessage(message);
				
				TimsConfig timsConfig = TService.getInstance().getTimsConfig();
				TimsMessageBuilder builder = new TimsMessageBuilder(TService.getInstance().getTimsConfig(), new TimsAddress(), new TimsAddress(TimsAddress.APP_ID_DRIVER_TERMINAL));
				
				TimsMessage timsMessage = builder.eventRequest(dispatchReq);
				
				kafkaProducer.sendKafka(KafkaTopics.T_COMMUNICATION, timsMessage, impId);
			}
			
		}
		
		return null;
	}
	
	public List sttnList() throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return mo0303Mapper.sttnList(map);
	}		
}
