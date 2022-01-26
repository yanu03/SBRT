package kr.tracom.brt.controller.ST;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.ST0803.ST0803Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class ST0803Controller extends ControllerSupport {

	@Autowired
	private ST0803Service st0803Service;
	
	@RequestMapping("/st/ST0803G0R0")
	public @ResponseBody Map<String, Object> ST0803G0R0() throws Exception {
		result.setData("dlt_BMS_ROUT_MST", st0803Service.ST0803G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0803G1R0")
	public @ResponseBody Map<String, Object> ST0803G1R0() throws Exception {
		result.setData("dlt_BRT_STTN_PNCTLTY_STAT", st0803Service.ST0803G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0803G1R1")
	public @ResponseBody Map<String, Object> ST0803G1R1() throws Exception {
		result.setData("dlt_BRT_STTN_PNCTLTY_STAT_PIVOT_2", st0803Service.ST0803G1R1());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0803G2R0")
	public @ResponseBody Map<String, Object> ST0803G2R0() throws Exception {
		result.setData("dlt_BRT_STTN_PNCTLTY_STAT_PIVOT", st0803Service.ST0803G2R0());
		return result.getResult();
	}
	
	
}
