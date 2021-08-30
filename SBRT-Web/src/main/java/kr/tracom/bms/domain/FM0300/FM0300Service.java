package kr.tracom.bms.domain.FM0300;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class FM0300Service extends ServiceSupport {
	
	@Autowired
	private FM0300Mapper fm0300Mapper;
	
	public List FM0300G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return fm0300Mapper.FM0300G0R0(map);
	}
	
	public List FM0300SHI0() throws Exception {
		return fm0300Mapper.FM0300SHI0();
	}
	
	public List FM0300SHI1() throws Exception {
		return fm0300Mapper.FM0300SHI1();
	}
	
	public List FM0300SHI2() throws Exception {
		return fm0300Mapper.FM0300SHI2();
	}
	
	public List FM0300G1R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_subsearch");
		return fm0300Mapper.FM0300G1R0(param);
	}
	
	public List FM0300G2R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_subsearch2");
		return fm0300Mapper.FM0300G2R0(param);
	}
	
	
	
	public List FM0300G3R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_subsearch3");
		return fm0300Mapper.FM0300G3R0(param);
	}	
	
	
	public Map FM0300G2S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_FCLT_HIS");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += fm0300Mapper.FM0300G2I0(data);
				} else if (rowStatus.equals("U")) {
					uCnt += fm0300Mapper.FM0300G2U0(data);
				} else if (rowStatus.equals("D")) {
					dCnt += fm0300Mapper.FM0300G2D0(data);
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
