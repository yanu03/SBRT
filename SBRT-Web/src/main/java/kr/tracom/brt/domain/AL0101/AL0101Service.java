package kr.tracom.brt.domain.AL0101;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.brt.domain.AL0101.AL0101Mapper;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class AL0101Service extends ServiceSupport {


	@Autowired
	private AL0101Mapper al0101Mapper;
	
	public List AL0101G1R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_sub_search");
		return al0101Mapper.AL0101G1R0(param);
	}
	
	public Map AL0101G1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BRT_MAIN_ROUT_NODE_INFO");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += al0101Mapper.AL0101G1I0(data);
				} else if (rowStatus.equals("D")) {
					dCnt += al0101Mapper.AL0101G1D0(data);
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
	
	public List AL0101P0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return al0101Mapper.AL0101P0R0(map);
	}
	
		
	
}
