package kr.tracom.bms.domain.PI1003;

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
public class PI1003Service extends ServiceSupport{

	@Autowired
	private PI1003Mapper PI1003Mapper;
		
	public List PI1003G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return PI1003Mapper.PI1003G0R0(map);
	}
	
	public List PI1003SHI0() throws Exception {
		return PI1003Mapper.PI1003SHI0();
	}
	
	public Map PI1003G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;

		List<Map<String, Object>> param = getSimpleList("dlt_BMS_NOTICE_SCNRO_MST");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map<String, Object> data = param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				
				if(rowStatus.equals("C")) {
					
					iCnt += PI1003Mapper.PI1003G0I0(data);
					
				}
				else if (rowStatus.equals("U")) {

					uCnt += PI1003Mapper.PI1003G0U0(data);

				}
				
				 else if (rowStatus.equals("D")) { 
					 
					dCnt += PI1003Mapper.PI1003G0D0(data); 
					 
				 }
				 
			}
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				throw new MessageException(Result.ERR_KEY, "중복된 키값의 데이터가 존재합니다.");
			} else {
				throw e;
			}
		}

		Map result = saveResult(iCnt, uCnt, dCnt);

		return result;

	}
	
	public Map PI1003G0K0() throws Exception {
		return PI1003Mapper.PI1003G0K0();
	}
	
	
	
	
}
