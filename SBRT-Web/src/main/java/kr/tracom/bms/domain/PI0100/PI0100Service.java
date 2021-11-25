package kr.tracom.bms.domain.PI0100;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.DateUtil;
import kr.tracom.util.Result;

@Service
public class PI0100Service extends ServiceSupport{

	@Autowired
	private PI0100Mapper pI0100Mapper;
		
	public List<Map> PI0100G0R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return pI0100Mapper.PI0100G0R0(param);
	}
	
	public Map PI0100G0K0() throws Exception {
		return pI0100Mapper.PI0100G0K0();
	}	
	
	public List PI0100SHI0() throws Exception {
		return pI0100Mapper.PI0100SHI0();
	}
	
	public Map PI0100G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;
		List param = getSimpleList("dlt_BMS_USER_NEWS_CFG_INFO");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += pI0100Mapper.PI0100G0I0(data);
				} else if (rowStatus.equals("U")) {
					uCnt += pI0100Mapper.PI0100G0U0(data);
				} else if (rowStatus.equals("D")) {
					dCnt += pI0100Mapper.PI0100G0D0(data);
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
