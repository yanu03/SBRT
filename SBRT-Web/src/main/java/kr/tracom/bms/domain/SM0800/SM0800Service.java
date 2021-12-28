package kr.tracom.bms.domain.SM0800;

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
public class SM0800Service extends ServiceSupport{

	@Autowired
	private SM0800Mapper sm0800Mapper;
	
	public List SM0800G0R0() throws Exception{
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return sm0800Mapper.SM0800G0R0(map);
	}
	
	public List SM0800SHI0() throws Exception {
		return sm0800Mapper.SM0800SHI0();
	}
	
	public Map SM0800G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_API_INFO");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += sm0800Mapper.SM0800G0I0(data);
				} else if (rowStatus.equals("U")) {
					uCnt += sm0800Mapper.SM0800G0U0(data);
				} else if (rowStatus.equals("D")) {
					dCnt += sm0800Mapper.SM0800G0D0(data);
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
	
	public Map SM0800G0K0() throws Exception {
		return sm0800Mapper.SM0800G0K0(); 
	}
		
	/*public List<Map> SM0601G0R0() throws Exception{
		Map param = getSimpleDataMap("dma_EMAIL_MST");
		return sM0403Mapper.SM0601G0R0(param);
	}
	
	public List<Map> SM0601G1R0() throws Exception{
		Map param = getSimpleDataMap("dma_USER_MST");
		String temp[] = param.get("RCPT_IDS").toString().split(",");
		return sM0403Mapper.SM0601G1R0(temp);
	}
	
	public List<Map> SM0601P0R0() throws Exception{
		Map param = getSimpleDataMap("dma_USER_MST");
		String temp[] = param.get("RCPT_IDS").toString().split(",");
		param.put("RCPT_IDS", temp);
		return sM0403Mapper.SM0601P0R0(param);
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
				iCnt += sM0403Mapper.SM0601G0I0(data);
			} else if (rowStatus.equals("U")) {
				uCnt += sM0403Mapper.SM0601G0U0(data);
			} else if (rowStatus.equals("E")) {
				dCnt += sM0403Mapper.SM0601G0D0(data);
			}
		}
		Map result = new HashMap();
		result.put("STATUS", "S");
		result.put("ICNT", String.valueOf(iCnt));
		result.put("UCNT", String.valueOf(uCnt));
		result.put("DCNT", String.valueOf(dCnt));
		
		return result;
	}*/
}
