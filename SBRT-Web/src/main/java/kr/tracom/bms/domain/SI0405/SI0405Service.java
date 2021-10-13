package kr.tracom.bms.domain.SI0405;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.cm.domain.OperPlan.OperPlanService;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class SI0405Service extends ServiceSupport {

	@Autowired
	private SI0405Mapper si0405Mapper;
	
	@Autowired
	private OperPlanService operPlanService;

	
	public List SI0405G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return si0405Mapper.SI0405G0R0(map);
	}
	
	public List SI0405SHI0() throws Exception {
		return si0405Mapper.SI0405SHI0();
	}
	
	public Map SI0405G0K0() throws Exception {
		return si0405Mapper.SI0405G0K0(); 
	}
	
	
	public Map SI0405G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_REP_ROUT_MST");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += si0405Mapper.SI0405G0I0(data);
					operPlanService.insertSimpleOperPlan(data);
				} else if (rowStatus.equals("U")) {
					uCnt += si0405Mapper.SI0405G0U0(data);
					operPlanService.insertSimpleOperPlan(data);
				} else if (rowStatus.equals("D")) {
					dCnt += si0405Mapper.SI0405G0D0(data);
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
