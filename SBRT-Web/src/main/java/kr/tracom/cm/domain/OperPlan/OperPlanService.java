package kr.tracom.cm.domain.OperPlan;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;


@Service
public class OperPlanService extends ServiceSupport {
	@Autowired
	private OperPlanMapper operPlanMapper;
	
	public void insertSimpleOperPlan(Map data) throws Exception {
		if("Y".equals(data.get("WAY_YN"))) {			
			data.put("WAY_DIV", "WYD01"); //상행
			data.put("DAY_DIV", "DY001"); //평일
			
			operPlanMapper.insertSimpleOperPlan(data);
			
			if("Y".equals(data.get("HOLI_YN"))) {
				data.put("DAY_DIV", "DY002"); //휴일
				operPlanMapper.insertSimpleOperPlan(data);
			}
			
			data.put("WAY_DIV", "WYD02"); //하행
			data.put("DAY_DIV", "DY001"); //평일

			//하행일때 정류소 위치 변경
			String stSttnId = (String)data.get("ST_STTN_ID");
			String stSttnNm = (String)data.get("ST_STTN_NM");
			String edSttnId = (String)data.get("ED_STTN_ID");
			String edSttnNm = (String)data.get("ED_STTN_NM");
			data.put("ST_STTN_ID", edSttnId); 
			data.put("ST_STTN_NM", edSttnNm);
			data.put("ED_STTN_ID", stSttnId);
			data.put("ED_STTN_NM", stSttnNm);
			
			operPlanMapper.insertSimpleOperPlan(data);
			
			if("Y".equals(data.get("HOLI_YN"))) {
				data.put("DAY_DIV", "DY002"); //휴일
				operPlanMapper.insertSimpleOperPlan(data);
			}
		}
		else {
			data.put("DAY_DIV", "DY001"); //평일
			operPlanMapper.insertSimpleOperPlan(data);
			
			if("Y".equals(data.get("HOLI_YN"))) {
				data.put("DAY_DIV", "DY002"); //휴일
				operPlanMapper.insertSimpleOperPlan(data);
			}
		}
	}
}
