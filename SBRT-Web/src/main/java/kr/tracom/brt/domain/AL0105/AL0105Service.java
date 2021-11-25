package kr.tracom.brt.domain.AL0105;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.brt.domain.AL0105.AL0105Mapper;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class AL0105Service extends ServiceSupport{

	@Autowired
	private AL0105Mapper al0105Mapper;
	
	public List AL0105G1R0() throws Exception {
		return al0105Mapper.AL0105G1R0();
	}
	
	public List AL0105G2R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		return al0105Mapper.AL0105G2R0(map);
	}
	
	public Map AL0105G2S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BRT_REP_ROUT_DRV_CMPSTN");
		
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += al0105Mapper.AL0105G2I0(data);
				} else if (rowStatus.equals("U")) {
					uCnt += al0105Mapper.AL0105G2U0(data);
				}
				else if (rowStatus.equals("D")) {
					dCnt += al0105Mapper.AL0105G2D0(data);
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
