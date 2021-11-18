package kr.tracom.brt.controller.ST;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.ST0402.ST0402Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class ST0402Controller extends ControllerSupport {

	@Autowired
	private ST0402Service st0402Service;
	
	@RequestMapping("/st/ST0402G0R0")
	public @ResponseBody Map<String, Object> ST0402G0R0() throws Exception {
		result.setData("dlt_BMS_ROUT_MST", st0402Service.ST0402G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0402G1R0")
	public @ResponseBody Map<String, Object> ST0402G1R0() throws Exception {
		result.setData("dlt_BRT_OPER_VIOLT_STAT", st0402Service.ST0402G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0402G2R0")
	public @ResponseBody Map<String, Object> ST0402G2R0() throws Exception {
		result.setData("dlt_BRT_OPER_VIOLT_STAT2", st0402Service.ST0402G2R0());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0402SHI0")
	public @ResponseBody Map<String, Object> ST0402SHI0() throws Exception {
		result.setData("dlt_searchitem", st0402Service.ST0402SHI0());
		return result.getResult();
	}
	
}
