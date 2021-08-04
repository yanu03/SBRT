package kr.tracom.bms.controller.SI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.SI0503.SI0503Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class SI0503Controller extends ControllerSupport {
	
	@Autowired
	private SI0503Service si0503Service;
	
	@RequestMapping("/si/SI0503G0R0")
	public @ResponseBody Map<String, Object> SI0503G0R0() throws Exception {
		result.setData("dlt_BMS_CRS_MST", si0503Service.SI0503G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0503G0K0")
	public @ResponseBody Map<String, Object> SI0503G0K0() throws Exception {
		result.setData("dma_SEQ_BMS_CRS_MST_0", si0503Service.SI0503G0K0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0503SHI0")
	public @ResponseBody Map<String, Object> SI0503SHI0() throws Exception {
		result.setData("dlt_searchitem", si0503Service.SI0503SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0503G0S0")
	public @ResponseBody Map<String, Object> SI0503G0S0() throws Exception {
		Map map = si0503Service.SI0503G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}	
	
	@RequestMapping("/si/SI0503G1R0")
	public @ResponseBody Map<String, Object> SI0503G1R0() throws Exception {
		result.setData("dlt_BMS_ROUT_MST", si0503Service.SI0503G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0503P0R0")
	public @ResponseBody Map<String, Object> SI0503P0R0() throws Exception {
		result.setData("", si0503Service.SI0503P0R0());
		return result.getResult();
	}

	

}
