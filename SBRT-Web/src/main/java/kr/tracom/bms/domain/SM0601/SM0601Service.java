package kr.tracom.bms.domain.SM0601;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.util.DateUtil;

@Service
public class SM0601Service extends ServiceSupport{

	@Autowired
	private SM0601Mapper sM0601Mapper;
		
	public List<Map> SM0601G0R0() throws Exception{
		Map param = getSimpleDataMap("dma_EMAIL_MST");
		return sM0601Mapper.SM0601G0R0(param);
	}
	
	public List<Map> SM0601G1R0() throws Exception{
		Map param = getSimpleDataMap("dma_USER_MST");
		String temp[] = param.get("RCPT_IDS").toString().split(",");
		return sM0601Mapper.SM0601G1R0(temp);
	}
	
	public List<Map> SM0601P0R0() throws Exception{
		Map param = getSimpleDataMap("dma_USER_MST");
		String temp[] = param.get("RCPT_IDS").toString().split(",");
		param.put("RCPT_IDS", temp);
		return sM0601Mapper.SM0601P0R0(param);
	}
	
	// save 수정
	public Map SM0601G0S0() throws Exception {

		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;
		List param = getSimpleList("dlt_EMAIL_MST");
		for (int i = 0; i < param.size(); i++) {
			Map<String, Object> data = (Map) param.get(i);
			String rowStatus = (String) data.get("rowStatus");
			// 데이터베이스 data 타입일때 공백으로 들어가면 에러나는 사항 임시 수정
			for (String key : data.keySet()) {
				if (data.get(key).equals("")) {
					data.put(key, null);
				}
			}
			if (rowStatus.equals("C")) {
				iCnt += sM0601Mapper.SM0601G0I0(data);
			} else if (rowStatus.equals("U")) {
				uCnt += sM0601Mapper.SM0601G0U0(data);
			} else if (rowStatus.equals("E")) {
				dCnt += sM0601Mapper.SM0601G0D0(data);
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
