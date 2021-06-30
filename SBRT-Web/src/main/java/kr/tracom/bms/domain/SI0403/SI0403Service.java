package kr.tracom.bms.domain.SI0403;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class SI0403Service extends ServiceSupport {

	@Autowired
	private SI0403Mapper SI0403Mapper;
	
	public List SI0403G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return SI0403Mapper.SI0403G0R0(map);
	}

	public Map SI0403G0K0() throws Exception {
		return SI0403Mapper.SI0403G0K0(); 
	}
	
	public List SI0403SHI0() throws Exception {
		return SI0403Mapper.SI0403SHI0();
	}	
	
	public Map SI0403G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_ROUT_MST");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += SI0403Mapper.SI0403G0I0(data);
				} else if (rowStatus.equals("U")) {
					uCnt += SI0403Mapper.SI0403G0U0(data);
				} else if (rowStatus.equals("D")) {
					dCnt += SI0403Mapper.SI0403G0D0(data);
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
	
	public List SI0403G1R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_sub_search");
		return SI0403Mapper.SI0403G1R0(param);
	}
	
	public List SI0403P0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_TRANSCOMP_MST");
		return SI0403Mapper.SI0403P0R0(map);
	}
	
	public List SI0403P1R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_REP_ROUT_MST");
		return SI0403Mapper.SI0403P1R0(map);
	}
	
	public Map SI0403P1K0() throws Exception {
		return SI0403Mapper.SI0403P1K0(); 
	}
	
	public Map SI0403P1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_REP_ROUT_MST");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += SI0403Mapper.SI0403P1I0(data);
				} else if (rowStatus.equals("U")) {
					uCnt += SI0403Mapper.SI0403P1U0(data);
				} else if (rowStatus.equals("D")) {
					dCnt += SI0403Mapper.SI0403P1D0(data);
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
	
	public List SI0403P2R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_STTN_MST");
		return SI0403Mapper.SI0403P2R0(map);
	}
	
	public List SI0403P3R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_TRANSCOMP_MST");
		return SI0403Mapper.SI0403P3R0(map);
	}

	public Map SI0403G1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_TRANSCOMP_MST");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += SI0403Mapper.SI0403G1I0(data);
				} else if (rowStatus.equals("D")) {
					dCnt += SI0403Mapper.SI0403G1D0(data);
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
