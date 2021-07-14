package kr.tracom.bms.domain.PI0603;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.bms.domain.PI0603.PI0603Mapper;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class PI0603Service extends ServiceSupport {

	@Autowired
	private PI0603Mapper pi0503Mapper;
	
	public List PI0603G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return pi0503Mapper.PI0603G0R0(map);
	}
	
	public List PI0603SHI0() throws Exception {
		return pi0503Mapper.PI0603SHI0();
	}
	
	public List PI0603G1R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_subsearch");		
		return pi0503Mapper.PI0603G1R0(map);
	}
	
	public Map PI0603G1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_FCLT_INFO");
		Map<String, Object> map = getSimpleDataMap("dma_subsearch");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("U")) {
					
					data.put("ORGA_ID",map.get("ORGA_ID"));
					iCnt += pi0503Mapper.PI0603G1I0(data);
				} 
				/*else if (rowStatus.equals("D")) {
					dCnt += pi0503Mapper.PI0603G1D0(data);
				}*/ 
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
	
	public Map PI0603G1D0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_FCLT_INFO");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("U")) {
					iCnt += pi0503Mapper.PI0603G1D0(data);
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
}
