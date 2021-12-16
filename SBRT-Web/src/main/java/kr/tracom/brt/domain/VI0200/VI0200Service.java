package kr.tracom.brt.domain.VI0200;

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
public class VI0200Service extends ServiceSupport{
	
	@Autowired
	private VI0200Mapper vi0200Mapper;
	
	public List VI0200G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return vi0200Mapper.VI0200G0R0(map);
	}
	
	public List VI0200P0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return vi0200Mapper.VI0200P0R0(map);
	}
	
	public List VI0200P1R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return vi0200Mapper.VI0200P1R0(map);
	}
	
	public List VI0200P2R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return vi0200Mapper.VI0200P2R0(map);
	}
	
	public List VI0200P3R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return vi0200Mapper.VI0200P3R0(map);
	}
	
	public List VI0200P4R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return vi0200Mapper.VI0200P4R0(map);
	}
	
	public List VI0200SHI0() throws Exception {
		return vi0200Mapper.VI0200SHI0();
	}	
	
	public Map VI0200G0K0() throws Exception {
		return vi0200Mapper.VI0200G0K0();
	}
	
	public Map VI0200G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;

		List<Map<String, Object>> param = getSimpleList("dlt_BRT_CPLNT_HIS");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map<String, Object> data = param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				
				if (rowStatus.equals("C")) {

					uCnt += vi0200Mapper.VI0200G0I0(data);

				}
				
				else if (rowStatus.equals("U")) {

					uCnt += vi0200Mapper.VI0200G0U0(data);

				}
				
				else if (rowStatus.equals("D")) { 
					
					dCnt += vi0200Mapper.VI0200G0D0(data); 
					
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
	
	
	/*public List VI0200P0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return vi0200Mapper.VI0200P0R0(map);
	}
	
	public Map VI0200P0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BRT_CPLNT_HIS");
		
		if(param.size()>0) {
			Map data = (Map) param.get(0);
			vi0200Mapper.VI0200P0DA0(data);
		}
		
		for (int i = 0; i < param.size(); i++) {

			Map data = (Map) param.get(i);
			//data.put("SN", i+1);
			String rowStatus = (String) data.get("rowStatus");
			if (rowStatus.equals("C")) {
				iCnt += vi0200Mapper.VI0200P0I0(data);
			} else if (rowStatus.equals("U")) {
				uCnt += vi0200Mapper.VI0200P0U0(data);
			} else if (rowStatus.equals("D")) {
				dCnt += vi0200Mapper.VI0200P0D0(data);
			}
		}
		Map result = saveResult(iCnt, uCnt, dCnt);
		
		return result;		
		
	}*/
	
	
}
