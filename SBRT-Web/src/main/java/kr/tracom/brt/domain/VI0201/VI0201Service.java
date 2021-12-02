package kr.tracom.brt.domain.VI0201;

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
public class VI0201Service extends ServiceSupport{
	
	@Autowired
	private VI0201Mapper vi0201Mapper;
	
	public List VI0201G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return vi0201Mapper.VI0201G0R0(map);
	}
	
	public List VI0201SHI0() throws Exception {
		return vi0201Mapper.VI0201SHI0();
	}	
	
	public Map VI0201G0K0() throws Exception {
		return vi0201Mapper.VI0201G0K0();
	}
	
	public Map VI0201G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;

		List<Map<String, Object>> param = getSimpleList("dlt_BRT_CPLNT_HIS");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map<String, Object> data = param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				
				if (rowStatus.equals("C")) {

					uCnt += vi0201Mapper.VI0201G0I0(data);

				}
				
				else if (rowStatus.equals("U")) {

					uCnt += vi0201Mapper.VI0201G0U0(data);

				}
				
				else if (rowStatus.equals("D")) { 
					
					dCnt += vi0201Mapper.VI0201G0D0(data); 
					
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
	
	
	/*public List VI0201P0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return vi0201Mapper.VI0201P0R0(map);
	}
	
	public Map VI0201P0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BRT_CPLNT_HIS");
		
		if(param.size()>0) {
			Map data = (Map) param.get(0);
			vi0201Mapper.VI0201P0DA0(data);
		}
		
		for (int i = 0; i < param.size(); i++) {

			Map data = (Map) param.get(i);
			//data.put("SN", i+1);
			String rowStatus = (String) data.get("rowStatus");
			if (rowStatus.equals("C")) {
				iCnt += vi0201Mapper.VI0201P0I0(data);
			} else if (rowStatus.equals("U")) {
				uCnt += vi0201Mapper.VI0201P0U0(data);
			} else if (rowStatus.equals("D")) {
				dCnt += vi0201Mapper.VI0201P0D0(data);
			}
		}
		Map result = saveResult(iCnt, uCnt, dCnt);
		
		return result;		
		
	}*/
	
	
}
