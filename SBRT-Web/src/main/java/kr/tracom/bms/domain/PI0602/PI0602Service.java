package kr.tracom.bms.domain.PI0602;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.bms.domain.PI0602.PI0602Mapper;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class PI0602Service extends ServiceSupport {
	@Autowired
	private PI0602Mapper pi0502Mapper;
	
	public List PI0602G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return pi0502Mapper.PI0602G0R0(map);
	}

	public Map PI0602G0K0() throws Exception {
		return pi0502Mapper.PI0602G0K0(); 
	}
	
	public List PI0602SHI0() throws Exception {
		return pi0502Mapper.PI0602SHI0();
	}	
	
	public Map PI0602G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BIT_VDO_ORGA_INFO");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += pi0502Mapper.PI0602G0I0(data);
				} else if (rowStatus.equals("U")) {
					uCnt += pi0502Mapper.PI0602G0U0(data);
				} else if (rowStatus.equals("D")) {
					dCnt += pi0502Mapper.PI0602G0D0(data);
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
	
	public List PI0602G1R0() throws Exception {
		// TODO Auto-generated method stub
		return pi0502Mapper.PI0602G1R0();
	}
	
	
	public List PI0602G2R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_subsearch");
		return pi0502Mapper.PI0602G2R0(map);
	}
	
	public Map PI0602G2S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BIT_VDO_ORGA_LIST");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += pi0502Mapper.PI0602G2I0(data);
				} else if (rowStatus.equals("U")) {
					uCnt += pi0502Mapper.PI0602G2U0(data);
				}
				else if (rowStatus.equals("D")) {
					dCnt += pi0502Mapper.PI0602G2D0(data);
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
