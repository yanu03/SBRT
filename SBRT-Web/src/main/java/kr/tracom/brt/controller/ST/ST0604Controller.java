package kr.tracom.brt.controller.ST;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.ST0604.ST0604Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class ST0604Controller extends ControllerSupport {

	@Autowired
	private ST0604Service st0604Service;
	
	@RequestMapping("/st/ST0604G0R0")
	public @ResponseBody Map<String, Object> ST0604G0R0() throws Exception {
		result.setData("dlt_BMS_ROUT_MST", st0604Service.ST0604G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0604G1R0")
	public @ResponseBody Map<String, Object> ST0604G1R0() throws Exception {
		result.setData("dlt_BRT_STTN_DSPTCH_STAT", st0604Service.ST0604G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0604G1R1")
	public @ResponseBody Map<String, Object> ST0604G1R1() throws Exception {
		result.setData("dlt_BRT_STTN_DSPTCH_STAT_PIVOT_2", st0604Service.ST0604G1R1());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0604G2R0")
	public @ResponseBody Map<String, Object> ST0604G2R0() throws Exception {
		result.setData("dlt_BRT_STTN_DSPTCH_STAT_PIVOT", st0604Service.ST0604G2R0());
		return result.getResult();
	}
	
	
	
}
