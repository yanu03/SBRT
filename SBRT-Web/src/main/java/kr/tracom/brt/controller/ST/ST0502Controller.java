package kr.tracom.brt.controller.ST;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.ST0502.ST0502Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class ST0502Controller extends ControllerSupport {

	@Autowired
	private ST0502Service st0502Service;
	
	@RequestMapping("/st/ST0502G0R0")
	public @ResponseBody Map<String, Object> ST0502G0R0() throws Exception {
		result.setData("dlt_BMS_ROUT_MST", st0502Service.ST0502G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0502G0R1")
	public @ResponseBody Map<String, Object> ST0502G0R1() throws Exception {
		result.setData("dlt_BMS_NODE_MST", st0502Service.ST0502G0R1());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0502G1R0")
	public @ResponseBody Map<String, Object> ST0502G1R0() throws Exception {
		result.setData("dlt_BRT_FCLT_STAT_PIVOT", st0502Service.ST0502G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0502G2R0")
	public @ResponseBody Map<String, Object> ST0502G2R0() throws Exception {
		result.setData("dlt_BRT_FCLT_STAT_PIVOT_2", st0502Service.ST0502G2R0());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0502SHI0")
	public @ResponseBody Map<String, Object> ST0502SHI0() throws Exception {
		result.setData("dlt_searchitem", st0502Service.ST0502SHI0());
		return result.getResult();
	}
	
}
