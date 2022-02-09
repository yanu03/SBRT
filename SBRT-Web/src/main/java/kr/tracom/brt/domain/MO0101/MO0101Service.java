package kr.tracom.brt.domain.MO0101;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.domain.Vhc.VhcMapper;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.platform.attribute.brt.AtDispatch;
import kr.tracom.platform.attribute.brt.AtTrafficLightStatusRequest;
import kr.tracom.platform.attribute.common.AtBrtAction;
import kr.tracom.platform.attribute.common.AtTimeStamp;
import kr.tracom.platform.net.config.TimsConfig;
import kr.tracom.platform.net.protocol.TimsAddress;
import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.TimsMessageBuilder;
import kr.tracom.platform.net.protocol.attribute.AtMessage;
import kr.tracom.platform.net.protocol.payload.PlGetResponse;
import kr.tracom.platform.service.TService;
import kr.tracom.platform.service.config.KafkaTopics;
import kr.tracom.tims.kafka.KafkaProducer;
import kr.tracom.util.Constants;
import kr.tracom.util.DateUtil;
import kr.tracom.ws.WsClient;

@Service
public class MO0101Service extends ServiceSupport{

	 Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MO0101Mapper mo0101Mapper;
	
	@Autowired
	VhcMapper vhcMapper;
	
	@Autowired
	WsClient webSocketClient;
	
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
	
	public List selectCategory() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_search");
		return mo0101Mapper.selectCategory(param);
	}
	
	public List selectSigOper() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_search");
		return mo0101Mapper.selectSigOper(param);
	}
	
	public List selectSigPeriod() throws Exception {
		// TODO Auto-generated method stub
		return mo0101Mapper.selectSigPeriod();
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
            TimsMessageBuilder builder = new TimsMessageBuilder(TService.getInstance().getTimsConfig(), new TimsAddress(), new TimsAddress(TimsAddress.APP_ID_DRIVER_TERMINAL));
            TimsMessage timsMessage = builder.eventRequest(dispatchReq);
        	
        	kafkaProducer.sendKafka(KafkaTopics.T_COMMUNICATION, timsMessage, impId);
        }
		
		
		return null;
	}	
	
	public List MO0101G3R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return mo0101Mapper.MO0101G3R0(param);
	}
	
	public List MO0101G4R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return mo0101Mapper.MO0101G4R0(param);
	}
	
	public List MO0101G5R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return mo0101Mapper.MO0101G5R0(param);
	}
	
	public List MO0101G6R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return mo0101Mapper.MO0101G6R0(param);
	}
	
	public List MO0101G7R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return mo0101Mapper.MO0101G7R0(param);
	}
	
	public List selectCommuMap() throws Exception{
		return mo0101Mapper.selectCommuMap();
	}
	
	//신호등 현시 요청 소켓
	public List<Map> MO0101SCK2() throws Exception{
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_ROUT_NODE_CMPSTN4");
		List<String> crsList = new ArrayList<>(); //교차로 리스트
		
		try {
			
			String currTime = DateUtil.now();
			List<HashMap <String, Object>> phaseInfoMapList = new ArrayList<>();       
			
			
			//bhmin 현시 받아오는 로직은 실제 신호데이터 받아오도록 변경
			/*
			if(false) { //DB에서 TOD에 따른 현시 조회
				for (int i=0; i<param.size(); i++) {
					Map data = (Map) param.get(i);
					String crsId = (String)data.get("CRS_ID");
					crsList.add(crsId);
					
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("CRS_ID", crsId);
					paramMap.put("UPD_DTM", currTime);
					
					int phaseNo = mo0101Mapper.selectCurPhaseNo(paramMap);
					
					HashMap<String, Object> phaseInfoMap = new HashMap<>();
					phaseInfoMap.put("CRS_ID", crsId);
					phaseInfoMap.put("PHASE_NO", phaseNo);
					
					phaseInfoMapList.add(phaseInfoMap);
				}
				
				
				//웹소켓 데이터 세팅
		    	Map<String, Object> wsDataMap = new HashMap<>();
	        	wsDataMap.put("ATTR_ID", BrtAtCode.TRAFFIC_LIGHT_STATUS_RESPONSE);
	        	wsDataMap.put("LIST", phaseInfoMapList);
	        	
	        	
	        	logger.info("현시정보 : {}", phaseInfoMapList);
	        	
	    		webSocketClient.sendMessage(wsDataMap);
	    		
	    		
			} else
			*/
			{ //실 신호데이터 연계				
				
				for (int i=0; i<param.size(); i++) {
					Map data = (Map) param.get(i);
					String crsId = (String)data.get("CRS_ID");
					
					if(crsList.contains(crsId) == false) {
						
						crsList.add(crsId);
						
						AtTrafficLightStatusRequest request = new AtTrafficLightStatusRequest();
		
						request.setUpdateTm(new AtTimeStamp(DateUtil.now("yyyyMMddHHmmss")));
						request.setCrossNodeId(crsId);
				        
				        
				        PlGetResponse getResponse = new PlGetResponse();
				        AtMessage atMessage = new AtMessage();
	
				        atMessage.setAttrId(request.getAttrId());
				        atMessage.setAttrSize((short) request.getSize());
				        atMessage.setAttrData(request);
	
				        getResponse.addAttribute(atMessage);
	
				        TimsConfig timsConfig = TService.getInstance().getTimsConfig();
				        TimsMessageBuilder builder = new TimsMessageBuilder(timsConfig);
				        TimsMessage timsMessage = builder.getResponse(getResponse);
				        
				        
				        //통신서버로 현시 요청 날림
				        kafkaProducer.sendKafka(KafkaTopics.T_COMMUNICATION, timsMessage, "TRF0000001");	
					}
				}
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}		
}
