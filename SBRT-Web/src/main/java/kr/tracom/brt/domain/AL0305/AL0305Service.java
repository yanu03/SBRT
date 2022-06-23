package kr.tracom.brt.domain.AL0305;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.platform.attribute.common.AtBrtAction;
import kr.tracom.platform.attribute.common.AtTimeStamp;
import kr.tracom.platform.net.config.TimsConfig;
import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.TimsMessageBuilder;
import kr.tracom.platform.service.TService;
import kr.tracom.platform.service.config.KafkaTopics;
import kr.tracom.tims.kafka.KafkaProducer;
import kr.tracom.util.CommonUtil;
import kr.tracom.util.DateUtil;
import kr.tracom.util.Result;

@Service
public class AL0305Service extends ServiceSupport {

	@Autowired
	KafkaProducer kafkaProducer;
	
	@Autowired
	private AL0305Mapper al0305Mapper;
	
	/*public List AL0302G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return al0302Mapper.AL0302G0R0(map);
	}
	
	public List AL0302SHI1() throws Exception{
		Map param = getSimpleDataMap("dma_search");		
		return al0302Mapper.AL0302SHI1(param);
	}
	
	public List AL0302G1R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_param_AL0302G1");
		return al0302Mapper.AL0302G1R0(param);
	}
	
	public List AL0302P0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return al0302Mapper.AL0302P0R0(map);
	}
	
	public List AL0302P1R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return al0302Mapper.AL0302P1R0(map);
	}
	
	//저장(수정)
	public Map AL0302G1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BRT_ALLOC_PL_MST");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("U")) {
					uCnt += al0302Mapper.AL0302G1U0(data);
					uCnt += al0302Mapper.AL0302G1U1(data);
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
	
	//배포
	public Map AL0302G1S1() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BRT_ALLOC_PL_MST");
		Map<String, Object> map = getSimpleDataMap("dma_param_AL0302G1");
		String temp[] = map.get("relDt").toString().replace("[","").replace("]","").replace(" ","").split(",");
		String rel_way = (String) map.get("REL_WAY");
		
		//고정배포
		if(rel_way.equals("FIX")){
			for(int i=0; i<temp.length; i++) {
				try {
					for (int j = 0; j < param.size(); j++) {
						Map data = (Map) param.get(j);
						String rowStatus = (String) data.get("rowStatus");
						if (rowStatus.equals("U") || rowStatus.equals("R")) {
							data.put("OPER_DT", temp[i]);
							uCnt += al0302Mapper.AL0302G1I0(data);
							uCnt += al0302Mapper.AL0302G1I1(data);
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
				
			}
		}
		//순환배포
		else if(rel_way.equals("ROTATE")) {
			for(int i=0; i<temp.length; i++) {
				try {
					for (int j = 0; j < param.size(); j++) {
						Map data = (Map) param.get(j);
						String rowStatus = (String) data.get("rowStatus");
						if (rowStatus.equals("U") || rowStatus.equals("R")) {
							data.put("OPER_DT", temp[i]);
							data.put("ALLOC_NO", j+1);
							uCnt += al0302Mapper.AL0302G1I0(data);
							uCnt += al0302Mapper.AL0302G1I1(data);
						}
					}			
					Collections.rotate(param, 1);
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
				
			}
		}
		
		Map result = saveResult(iCnt, uCnt, dCnt);
		
		return result;	
	}*/
	
	public List AL0305G0R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_param_AL0305G1");
		return al0305Mapper.AL0305G0R0(param);
	}
	
	//운행일 선택해서 수정
	public Map AL0305G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> sendList = new ArrayList<Map<String, Object>>();
		
		List<Map<String, Object>> param = getSimpleList("dlt_BRT_OPER_DT_ALLOC_PL_MST");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("U")) {
					uCnt += al0305Mapper.AL0305G0U0(data);
					uCnt += al0305Mapper.AL0305G0U1(data);
					
					/*if(data.get("OPER_DT").equals(CommonUtil.getOperDt())) {
						sendList.add(data);
					}*/
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
		
		//send_T_BRT(sendList);
		
		Map result = saveResult(iCnt, uCnt, dCnt);
		
		return result;	
	}
	
	public Map AL0305G0SEND() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;
		
	
		List<Map<String, Object>> param = getSimpleList("dlt_BRT_OPER_DT_ALLOC_PL_MST");
		List<Map<String, Object>> sendList = new ArrayList<Map<String, Object>>();
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("U")) {
					uCnt += al0305Mapper.AL0305G0U0(data);
					uCnt += al0305Mapper.AL0305G0U1(data);
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
		al0305Mapper.makeCurAllocPlInfo();
		
		TimsConfig timsConfig = TService.getInstance().getTimsConfig();
		TimsMessageBuilder builder = new TimsMessageBuilder(timsConfig);

		AtBrtAction brtRequest = new AtBrtAction();

		brtRequest.setTimeStamp(new AtTimeStamp(DateUtil.now("yyyyMMddHHmmssSSS")));
		brtRequest.setActionCode(AtBrtAction.allocChangeRequest);
		brtRequest.setData("");
		TimsMessage timsMessage = builder.actionRequest(brtRequest);
		kafkaProducer.sendKafka(KafkaTopics.T_BRT, timsMessage, "");
		
		Map result = saveResult(iCnt, uCnt, dCnt);
		
		return result;	
	}
	
	/*public int send_T_BRT(List<Map<String, Object>> sendList) throws Exception {

		TimsConfig timsConfig = TService.getInstance().getTimsConfig();
		TimsMessageBuilder builder = new TimsMessageBuilder(timsConfig);

		AtBrtAction brtRequest = new AtBrtAction();
		List<Map<String, Object>> param = getSimpleList("dlt_BRT_OPER_DT_ALLOC_PL_MST");

		int count = 0;
		for (int i = 0; i < sendList.size(); i++) {
			Map<String, Object> data = param.get(i);
			if (data.get("OPER_DT").equals(CommonUtil.getOperDt())) {
				String actionData = CommonUtil.mapToJson(data);

				brtRequest.setTimeStamp(new AtTimeStamp(DateUtil.now("yyyyMMddHHmmssSSS")));
				brtRequest.setActionCode(AtBrtAction.allocChangeRequest);
				brtRequest.setData("");

				brtRequest.setReserved(actionData);

				TimsMessage timsMessage = builder.actionRequest(brtRequest);
				kafkaProducer.sendKafka(KafkaTopics.T_BRT, timsMessage, "");
				count++;
			}
		}
		return count;
	}*/
}
