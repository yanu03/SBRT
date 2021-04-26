package kr.tracom.bms.controller.SI;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.SI0101.SI0101Service;
import kr.tracom.util.Result;

@Controller
public class SI0101Controller {
	@Autowired
	private SI0101Service si0101Service;
	
	
	@RequestMapping("/si/SI0101G0R0")
	public @ResponseBody Map<String, Object> SI0101G0R0() {
		Result result = new Result();
		
		try {
			result.setData("dlt_BMS_GRG_MST", si0101Service.SI0101G0R0());
			result.setMsg(result.STATUS_SUCESS);
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg(result.STATUS_ERROR);
		}
		
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0101G0R1")
	public @ResponseBody Map<String, Object> SI0101G0R1() {
		Result result = new Result();
		
		try {
			result.setData("dlt_BMS_GRG_RDS_INFO", si0101Service.SI0101G0R1());
			result.setMsg(result.STATUS_SUCESS);
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg(result.STATUS_ERROR);
		}
		
		return result.getResult();
	}
	
	
	@RequestMapping(value = "/si/SI0101G0S0")
	public @ResponseBody Map<String, Object> SI0101G0S0(@RequestBody Map<String, Object> param) {
		Result result = new Result();
		try {
			Map map = si0101Service.SI0101G0S0((List)param.get("dlt_BMS_GRG_MST"));
			result.setData("dma_result", map);
			result.setMsg(result.STATUS_SUCESS, "Garage 정보가 저장 되었습니다. 입력 : " + (String) map.get("ICNT") + "건, 수정 : "
					+ (String) map.get("UCNT") + "건, 삭제 : " + (String) map.get("DCNT") + "건");
		} catch (Exception ex) {
			result.setMsg(result.STATUS_ERROR, "정보 저장도중 오류가 발생하였습니다.", ex);
		}
		return result.getResult();
	}	
	
}
