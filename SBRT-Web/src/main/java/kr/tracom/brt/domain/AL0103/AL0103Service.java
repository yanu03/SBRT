package kr.tracom.brt.domain.AL0103;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.brt.domain.AL0103.AL0103Mapper;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class AL0103Service extends ServiceSupport {

	@Autowired
	private AL0103Mapper al0103Mapper;
	
	public List AL0103G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return al0103Mapper.AL0103G0R0(map);
	}

	public Map AL0103G0K0() throws Exception {
		return al0103Mapper.AL0103G0K0(); 
	}
	
	public List AL0103SHI0() throws Exception {
		return al0103Mapper.AL0103SHI0();
	}	
	
	public Map AL0103G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BRT_COR_MST");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += al0103Mapper.AL0103G0I0(data);
				} else if (rowStatus.equals("U")) {
					uCnt += al0103Mapper.AL0103G0U0(data);
				} else if (rowStatus.equals("D")) {
					dCnt += al0103Mapper.AL0103G0D0(data);
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
	
	public Map AL0103G1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BRT_COR_DTL_INFO");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += al0103Mapper.AL0103G1I0(data);
				} else if (rowStatus.equals("D")) {
					dCnt += al0103Mapper.AL0103G1D0(data);
				} else if (rowStatus.equals("U")) {
					dCnt += al0103Mapper.AL0103G1U0(data);
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
	
	public List AL0103G1R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_sub_search");
		return al0103Mapper.AL0103G1R0(param);
	}
	
	public List AL0103P0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_ROUT_MST");
		return al0103Mapper.AL0103P0R0(map);
	}
	
	public List AL0103P01R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return al0103Mapper.AL0103P01R0(map);
	}
	
	public List AL0103P1SH() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return al0103Mapper.AL0103P1SH(map);
	}
		
	
}
