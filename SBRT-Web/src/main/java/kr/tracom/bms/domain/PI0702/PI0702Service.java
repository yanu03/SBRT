package kr.tracom.bms.domain.PI0702;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.bms.domain.PI0702.PI0702Mapper;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class PI0702Service extends ServiceSupport {

	@Autowired
	private PI0702Mapper pi0702Mapper;
	
	public List PI0702G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return pi0702Mapper.PI0702G0R0(map);
	}
	
	public List PI0702SHI0() throws Exception {
		return pi0702Mapper.PI0702SHI0();
	}
	
	public List PI0702G1R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_subsearch");		
		return pi0702Mapper.PI0702G1R0(map);
	}
	
	public Map PI0702G1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_VHC_MST");
		Map<String, Object> map = getSimpleDataMap("dma_subsearch");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("U")) {
					
					data.put("ORGA_ID",map.get("ORGA_ID"));
					iCnt += pi0702Mapper.PI0702G1I0(data);
				} 
				/*else if (rowStatus.equals("D")) {
					dCnt += pi0702Mapper.PI0702G1D0(data);
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
	
	public Map PI0702G1D0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_VHC_MST");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("U")) {
					iCnt += pi0702Mapper.PI0702G1D0(data);
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
