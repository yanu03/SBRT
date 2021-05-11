package kr.tracom.bms.domain.SI0102;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.bms.domain.SI0101.SI0101Mapper;

@Service
public class SI0102Service {
	
	@Autowired
	private SI0102Mapper si0102Mapper;
	
	public List SI0102G0R0(Map param) {
		return si0102Mapper.SI0102G0R0(param);
	}

	public Map SI0102G0R1() {
		return si0102Mapper.SI0102G0R1();
	}
	
	public List SI0102G0R2() {
		return si0102Mapper.SI0102G0R2();
	}	
	
	public Map SI0102G0S0(List param) {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		for (int i = 0; i < param.size(); i++) {

			Map data = (Map) param.get(i);
			String rowStatus = (String) data.get("rowStatus");
			if (rowStatus.equals("C")) {
				iCnt += si0102Mapper.SI0102G0I0(data);
			} else if (rowStatus.equals("U")) {
				uCnt += si0102Mapper.SI0102G0U0(data);
			} else if (rowStatus.equals("D")) {
				dCnt += si0102Mapper.SI0102G0D0(data);
			}  else if (rowStatus.equals("V")) {
				dCnt += si0102Mapper.SI0102G0D0(data);
			}
		}
		
		Map result = new HashMap();
		result.put("STATUS", "S");
		result.put("ICNT", String.valueOf(iCnt));
		result.put("UCNT", String.valueOf(uCnt));
		result.put("DCNT", String.valueOf(dCnt));
		
		return result;		
		
		
	}


}
