package kr.tracom.bms.domain.SI0101;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SI0101Service {
	@Autowired
	private SI0101Mapper si0101Mapper;
	
	public List SI0101G0R0() {
		return si0101Mapper.SI0101G0R0();
	}
	
	public List SI0101G0R1() {
		return si0101Mapper.SI0101G0R1();
	}
	
	public Map SI0101G0S0(List param) {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		for (int i = 0; i < param.size(); i++) {

			Map data = (Map) param.get(i);
			String rowStatus = (String) data.get("rowStatus");
			if (rowStatus.equals("C")) {
				iCnt += si0101Mapper.SI0101G0I0(data);
			} else if (rowStatus.equals("U")) {
				uCnt += si0101Mapper.SI0101G0U0(data);
			} else if (rowStatus.equals("D")) {
				dCnt += si0101Mapper.SI0101G0D0(data);
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
