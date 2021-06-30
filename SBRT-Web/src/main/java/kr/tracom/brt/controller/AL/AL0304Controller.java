package kr.tracom.brt.controller.AL;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.AL0301.AL0301Service;
import kr.tracom.brt.domain.AL0304.AL0304Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class AL0304Controller extends ControllerSupport {

	@Autowired
	private AL0304Service al0304Service;
	
	@RequestMapping("/AL/AL0304G0R0")
	public @ResponseBody Map<String, Object> AL0304G0R0() throws Exception {
		result.setData("dlt_OPER_PL_ROUT_INFO", al0304Service.AL0304G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/AL/AL0304G1R0")
	public @ResponseBody Map<String, Object> AL0301G1R0() throws Exception {
		result.setData("dlt_OPER_ALLOC_PL_ROUT_INFO", al0304Service.AL0304G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/AL/AL0304P0R0")
	public @ResponseBody Map<String, Object> AL0304P0R0() throws Exception {
		result.setData("dlt_COR_MST", al0304Service.AL0304P0R0());
		return result.getResult();
	}
	
	@RequestMapping("/AL/AL0304P0R1")
	public @ResponseBody Map<String, Object> AL0304P0R1() throws Exception {
		result.setData("dlt_OPER_PL_ROUT_INFO", al0304Service.AL0304P0R1());
		return result.getResult();
	}	
}
