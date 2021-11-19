package kr.tracom.brt.domain.MO0303;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.brt.domain.MO0303.MO0303Mapper;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class MO0303Service extends ServiceSupport {

	@Autowired
	private MO0303Mapper mo0303Mapper;
	
	public List MO0303G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return mo0303Mapper.MO0303G0R0(map);
	}

	public Map MO0303G0K0() throws Exception {
		return mo0303Mapper.MO0303G0K0(); 
	}
	
	public List MO0303SHI0() throws Exception {
		return mo0303Mapper.MO0303SHI0();
	}	
	
	public Map MO0303G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BIT_VDO_ORGA_INFO");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += mo0303Mapper.MO0303G0I0(data);
				} else if (rowStatus.equals("U")) {
					uCnt += mo0303Mapper.MO0303G0U0(data);
				} else if (rowStatus.equals("D")) {
					dCnt += mo0303Mapper.MO0303G0D0(data);
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
	
	public List MO0303G1R0() throws Exception {
		// TODO Auto-generated method stub
		return mo0303Mapper.MO0303G1R0();
	}
	
	
	public List MO0303G2R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_subsearch");
		return mo0303Mapper.MO0303G2R0(map);
	}
	
	public Map MO0303G2S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BIT_VDO_ORGA_LIST");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += mo0303Mapper.MO0303G2I0(data);
				} else if (rowStatus.equals("U")) {
					uCnt += mo0303Mapper.MO0303G2U0(data);
				}
				else if (rowStatus.equals("D")) {
					dCnt += mo0303Mapper.MO0303G2D0(data);
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
