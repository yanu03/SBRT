package kr.tracom.bms.domain.SI0600;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class SI0600Service extends ServiceSupport {

	@Autowired
	private SI0600Mapper si0600Mapper;
	
	
	public List SI0600T0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return si0600Mapper.SI0600T0R0(map);		
	}
	
	public Map SI0600T0K0() throws Exception {
		return si0600Mapper.SI0600T0K0(); 
	}
	
	public List SI0600SHI0() throws Exception {
		return si0600Mapper.SI0600SHI0();
	}	
	
	public Map SI0600T0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_EMER_CLSFCTN_MST");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += si0600Mapper.SI0600T0I0(data);
				} else if (rowStatus.equals("U")) {
					uCnt += si0600Mapper.SI0600T0U0(data);
				} else if (rowStatus.equals("D")) {
					dCnt += si0600Mapper.SI0600T0D0(data);
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
	
	public List SI0600G0R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_param_CLSFCTN");
		return si0600Mapper.SI0600G0R0(param);
	}
	
	public List SI0600P0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_MEMBER_MST");
		return si0600Mapper.SI0600P0R0(map);
	}
	
	
	
	public Map SI0600G2S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_EMER_MEMBER_INFO");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += si0600Mapper.SI0600G2I0(data);
				} 
			/*	else if (rowStatus.equals("U")) {
					uCnt += si0600Mapper.SI0600T0U0(data);
				}*/
				else if (rowStatus.equals("D")) {
					dCnt += si0600Mapper.SI0600G2D0(data);
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
