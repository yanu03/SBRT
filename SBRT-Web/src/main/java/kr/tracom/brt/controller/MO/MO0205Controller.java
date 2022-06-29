package kr.tracom.brt.controller.MO;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.MO0205.MO0205Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class MO0205Controller extends ControllerSupport {

	@Autowired
	private MO0205Service MO0205Service;
	
	
	
	@RequestMapping("/mo/MO0205G0R0")
	public @ResponseBody Map<String, Object> MO0205G0R0() throws Exception {
		result.setData("dlt_BMS_NODE_MST", MO0205Service.MO0205G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0205G1R0")
	public @ResponseBody Map<String, Object> MO0205G1R0() throws Exception {
		result.setData("dlt_BMS_CRS_MST", MO0205Service.MO0205G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0205G2R0")
	public @ResponseBody Map<String, Object> MO0205G2R0() throws Exception {
		result.setData("dlt_BMS_FCLT_INFO", MO0205Service.MO0205G2R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0205G0R1")
	public @ResponseBody Map<String, Object> MO0205G0R1() throws Exception {
		result.setData("dlt_PARAM_KIND", MO0205Service.MO0205G0R1());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0205SHI0")
	public @ResponseBody Map<String, Object> MO0205SHI0() throws Exception {
		result.setData("dlt_searchitem", MO0205Service.MO0205SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0205SHI1")
	public @ResponseBody Map<String, Object> MO0205SHI1() throws Exception {
		result.setData("dlt_searchitem2", MO0205Service.MO0205SHI1());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0205SHI2")
	public @ResponseBody Map<String, Object> MO0205SHI2() throws Exception {
		result.setData("dlt_searchitem3", MO0205Service.MO0205SHI2());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0205SHI3")
	public @ResponseBody Map<String, Object> MO0205SHI3() throws Exception {
		result.setData("dlt_searchitem4", MO0205Service.MO0205SHI3());
		return result.getResult();
	}	
	
	@RequestMapping("/mo/MO0205P0R0")
	public @ResponseBody Map<String, Object> MO0205P0R0() throws Exception {
		result.setData("dlt_airconControl", MO0205Service.MO0205P0R0());
		return result.getResult();
	}	
	
	@RequestMapping("/mo/airconControl")
	public @ResponseBody Map<String, Object> airconControl() throws Exception {
		MO0205Service.airconControl();
		return result.getResult();
	}
	
}
