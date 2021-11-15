package kr.tracom.bms.domain.SM0700;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;

@Service
public class SM0700Service extends ServiceSupport{
	@Autowired
	private SM0700Mapper sm0700Mapper;
	
	public List SM0700G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return sm0700Mapper.SM0700G0R0(map);
	}
	
	public Map SM0700G0K0() throws Exception {
		return sm0700Mapper.SM0700G0K0();
	}
	
	public List SM0700SHI0() throws Exception {
		return sm0700Mapper.SM0700SHI0();
	}	
	
	public Map SM0700G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_INTG_MST");
		
		for (int i = 0; i < param.size(); i++) {

			Map data = (Map) param.get(i);
			String rowStatus = (String) data.get("rowStatus");
			if (rowStatus.equals("C")) {
				iCnt += sm0700Mapper.SM0700G0I0(data);
			} else if (rowStatus.equals("U")) {
				uCnt += sm0700Mapper.SM0700G0U0(data);
			} else if (rowStatus.equals("D")) {
				dCnt += sm0700Mapper.SM0700G0D0(data);
			}
		}
		Map result = saveResult(iCnt, uCnt, dCnt);
		
		return result;		
		
	}
	
	
}
