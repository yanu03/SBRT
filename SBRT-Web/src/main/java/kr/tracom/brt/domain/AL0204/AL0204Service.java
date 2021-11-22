package kr.tracom.brt.domain.AL0204;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.brt.domain.AL0204.AL0204Mapper;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class AL0204Service extends ServiceSupport {

	@Autowired
	private AL0204Mapper al0204Mapper;
	
	public List AL0204G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return al0204Mapper.AL0204G0R0(map);
	}
	
	public List AL0204G1R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_sub_search");
		return al0204Mapper.AL0204G1R0(param);
	}
	
	public List AL0204G1CNT() throws Exception {
		Map param = getSimpleDataMap("dma_sub_search");
		return al0204Mapper.AL0204G1CNT(param);
	}
	
	public Map AL0204G1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;
		List<Map<String, Object>> param = getSimpleList("dlt_BRT_DAY_OPER_ALLOC_PL_NODE_INFO");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
				} else if (rowStatus.equals("U")) {
					uCnt += al0204Mapper.AL0204G0U0(data);
				} else if (rowStatus.equals("D")) {
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
