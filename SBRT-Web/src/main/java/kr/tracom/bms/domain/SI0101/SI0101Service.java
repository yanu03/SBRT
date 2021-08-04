package kr.tracom.bms.domain.SI0101;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;

@Service
public class SI0101Service extends ServiceSupport{
	@Autowired
	private SI0101Mapper si0101Mapper;
	
	public List SI0101G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return si0101Mapper.SI0101G0R0(map);
	}
	
	public Map SI0101G0K0() throws Exception {
		return si0101Mapper.SI0101G0K0();
	}
	
	public List SI0101SHI0() throws Exception {
		return si0101Mapper.SI0101SHI0();
	}	
	
	public Map SI0101G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_GRG_MST");
		
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
		Map result = saveResult(iCnt, uCnt, dCnt);
		
		return result;		
		
	}
	
	public List SI0101P0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return si0101Mapper.SI0101P0R0(map);
	}
	
	public Map SI0101P0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_GRG_RDS_INFO");
		
		if(param.size()>0) {
			Map data = (Map) param.get(0);
			si0101Mapper.SI0101P0DA0(data);
		}
		
		for (int i = 0; i < param.size(); i++) {

			Map data = (Map) param.get(i);
			//data.put("SN", i+1);
			String rowStatus = (String) data.get("rowStatus");
			if (rowStatus.equals("C")) {
				iCnt += si0101Mapper.SI0101P0I0(data);
			} else if (rowStatus.equals("U")) {
				uCnt += si0101Mapper.SI0101P0U0(data);
			} else if (rowStatus.equals("D")) {
				dCnt += si0101Mapper.SI0101P0D0(data);
			}
		}
		Map result = saveResult(iCnt, uCnt, dCnt);
		
		return result;		
		
	}
	
	
}
