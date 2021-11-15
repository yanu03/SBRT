package kr.tracom.bms.domain.FM0100;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.VD0100.VD0100Mapper;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class FM0100Service extends ServiceSupport {
	
	@Autowired
	private FM0100Mapper fm0100Mapper;
	
	public List FM0100G0R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_search");
		return fm0100Mapper.FM0100G0R0(param);
	}
	
	public List FM0100G1R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_subsearch");
		return fm0100Mapper.FM0100G1R0(param);
	}
	
	
	public List FM0100SHI1() throws Exception {
		return fm0100Mapper.FM0100SHI1();
	}
	
	public List FM0100SHI2() throws Exception {
		return fm0100Mapper.FM0100SHI2();
	}
	
	public Map FM0100G1K0() throws Exception {
		return fm0100Mapper.FM0100G1K0(); 
	}
	
	
	public Map FM0100G1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_FCLT_INFO");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += fm0100Mapper.FM0100G1I0(data);
				} else if (rowStatus.equals("U")) {
					uCnt += fm0100Mapper.FM0100G1U0(data);
				} else if (rowStatus.equals("D")) {
					dCnt += fm0100Mapper.FM0100G1D0(data);
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
