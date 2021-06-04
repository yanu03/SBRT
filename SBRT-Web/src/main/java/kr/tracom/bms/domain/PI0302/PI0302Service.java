package kr.tracom.bms.domain.PI0302;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.util.DateUtil;

@Service
public class PI0302Service extends ServiceSupport{

	@Autowired
	private PI0302Mapper PI0302Mapper;
		
	public List<Map> PI0302G0R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return PI0302Mapper.PI0302G0R0(param);
	}
	
	public Map PI0302G0K0() throws Exception {
		return PI0302Mapper.PI0302G0K0();
	}	
	
	public Map PI0302G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;
		List param = getSimpleList("dlt_BMS_NEWS_CFG_INFO");
		for (int i = 0; i < param.size(); i++) {
			Map<String, Object> data = (Map) param.get(i);
			String rowStatus = (String) data.get("rowStatus");
			// 데이터베이스 date 타입일때 공백으로 들어가면 에러나는 사항 임시 수정
			for (String key : data.keySet()) {
				if (data.get(key).equals("")) {
					data.put(key, null);
				}
			}			
			if (rowStatus.equals("C")) {
				iCnt += PI0302Mapper.PI0302G0I0(data);
			} else if (rowStatus.equals("U")) {
				uCnt += PI0302Mapper.PI0302G0U0(data);
			} else if (rowStatus.equals("D")) {
				dCnt += PI0302Mapper.PI0302G0D0(data);
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
