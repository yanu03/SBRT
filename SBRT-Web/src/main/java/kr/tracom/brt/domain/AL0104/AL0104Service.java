package kr.tracom.brt.domain.AL0104;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.brt.domain.AL0104.AL0104Mapper;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class AL0104Service extends ServiceSupport{

	@Autowired
	private AL0104Mapper al0104Mapper;	
	
	public List AL0104G1R0() throws Exception {
		return al0104Mapper.AL0104G1R0();
	}
	
	public List AL0104G2R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		return al0104Mapper.AL0104G2R0(map);
	}
	
	public Map AL0104G2S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BRT_REP_ROUT_VHC_CMPSTN");
		
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += al0104Mapper.AL0104G2I0(data);
				} else if (rowStatus.equals("U")) {
					uCnt += al0104Mapper.AL0104G2U0(data);
				}
				else if (rowStatus.equals("D")) {
					dCnt += al0104Mapper.AL0104G2D0(data);
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
