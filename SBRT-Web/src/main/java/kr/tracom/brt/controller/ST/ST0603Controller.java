package kr.tracom.brt.controller.ST;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.ST0603.ST0603Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class ST0603Controller extends ControllerSupport {

	@Autowired
	private ST0603Service st0603Service;
	
	
	@RequestMapping("/st/ST0603G0R0")
	public @ResponseBody Map<String, Object> ST0603G0R0() throws Exception {
		result.setData("dlt_BMS_ROUT_MST", st0603Service.ST0603G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0603G1R0")
	public @ResponseBody Map<String, Object> ST0603G1R0() throws Exception {
		result.setData("dlt_BRT_OPER_PNCTLTY_STAT", st0603Service.ST0603G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0603G1R1")
	public @ResponseBody Map<String, Object> ST0603G1R1() throws Exception {
		result.setData("dlt_BRT_OPER_PNCTLTY_STAT_PIVOT_2", st0603Service.ST0603G1R1());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0603G2R0")
	public @ResponseBody Map<String, Object> ST0603G2R0() throws Exception {
		result.setData("dlt_BRT_OPER_PNCTLTY_STAT_PIVOT", st0603Service.ST0603G2R0());
		return result.getResult();
	}
	
	
	
}
