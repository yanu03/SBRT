package kr.tracom.bms.domain.SI0502;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class SI0502Service extends ServiceSupport {

	@Autowired
	private SI0502Mapper si0502Mapper;
	
	public List SI0502G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return si0502Mapper.SI0502G0R0(map);
	}

	public List SI0502SHI0() throws Exception {
		return si0502Mapper.SI0502SHI0();
	}	
	
	public Map SI0502G1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_TRANSFER_INFO");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += si0502Mapper.SI0502G1I0(data);
				} else if (rowStatus.equals("D")) {
					dCnt += si0502Mapper.SI0502G1D0(data);
				}
				else if (rowStatus.equals("U")) {
					uCnt += si0502Mapper.SI0502G1U0(data);
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
	
	public List SI0502G1R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_param_SI0502G1");
		return si0502Mapper.SI0502G1R0(param);
	}
	
	public Map SI0502G1K0() throws Exception {
		return si0502Mapper.SI0502G1K0(); 
	}	
}
