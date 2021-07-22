package kr.tracom.bms.domain.VD0300;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.bms.domain.VD0300.VD0300Mapper;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class VD0300Service extends ServiceSupport {
	
	@Autowired
	private VD0300Mapper vd0300Mapper;
	
	public List VD0300G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return vd0300Mapper.VD0300G0R0(map);
	}

	public List VD0300SHI0() throws Exception {
		return vd0300Mapper.VD0300SHI0();
	}
	
	public List VD0300SHI1() throws Exception {
		return vd0300Mapper.VD0300SHI1();
	}
	
	public List VD0300G1R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_subsearch");
		return vd0300Mapper.VD0300G1R0(param);
	}

	public List VD0300G2R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_subsearch2");
		return vd0300Mapper.VD0300G2R0(param);
	}
	
	public Map VD0300G2S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_DVC_HIS");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += vd0300Mapper.VD0300G2I0(data);
				} else if (rowStatus.equals("U")) {
					uCnt += vd0300Mapper.VD0300G2U0(data);
				} else if (rowStatus.equals("D")) {
					dCnt += vd0300Mapper.VD0300G2D0(data);
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
	
	public List VD0300G3R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_subsearch3");
		return vd0300Mapper.VD0300G3R0(param);
	}	
	
}
