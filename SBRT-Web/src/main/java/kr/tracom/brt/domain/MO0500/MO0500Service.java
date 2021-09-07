package kr.tracom.brt.domain.MO0500;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class MO0500Service extends ServiceSupport{
	
	@Autowired
	private MO0500Mapper MO0500Mapper;
	
	public List MO0500G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return MO0500Mapper.MO0500G0R0(map);
	}
	
	public List MO0500SHI0() throws Exception {
		return MO0500Mapper.MO0500SHI0();
	}	
	
	public Map MO0500G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;

		List<Map<String, Object>> param = getSimpleList("dlt_BRT_OPER_APPR_INFO");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map<String, Object> data = param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				
				if (rowStatus.equals("C")) {

					uCnt += MO0500Mapper.MO0500G0I0(data);

				}
				
				else if (rowStatus.equals("U")) {

					uCnt += MO0500Mapper.MO0500G0U0(data);

				}
				
				else if (rowStatus.equals("D")) { 
					
					dCnt += MO0500Mapper.MO0500G0D0(data); 
					
				}
				 
			}
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				throw new MessageException(Result.ERR_KEY, "중복된 키값의 데이터가 존재합니다.");
			} else {
				throw e;
			}
		}

		Map result = saveResult(iCnt, uCnt, dCnt);

		return result;

	}
	/*
	public Map MO0500G0K0() throws Exception {
		return MO0500Mapper.MO0500G0K0();
	}
	
	public List MO0500P0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return MO0500Mapper.MO0500P0R0(map);
	}
	
	public Map MO0500P0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BRT_CPLNT_HIS");
		
		if(param.size()>0) {
			Map data = (Map) param.get(0);
			MO0500Mapper.MO0500P0DA0(data);
		}
		
		for (int i = 0; i < param.size(); i++) {

			Map data = (Map) param.get(i);
			//data.put("SN", i+1);
			String rowStatus = (String) data.get("rowStatus");
			if (rowStatus.equals("C")) {
				iCnt += MO0500Mapper.MO0500P0I0(data);
			} else if (rowStatus.equals("U")) {
				uCnt += MO0500Mapper.MO0500P0U0(data);
			} else if (rowStatus.equals("D")) {
				dCnt += MO0500Mapper.MO0500P0D0(data);
			}
		}
		Map result = saveResult(iCnt, uCnt, dCnt);
		
		return result;		
		
	}
	*/
	
}
