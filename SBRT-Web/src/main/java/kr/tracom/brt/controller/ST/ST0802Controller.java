package kr.tracom.brt.controller.ST;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.PI0503.PI0503Service;
import kr.tracom.brt.domain.ST0200.ST0200Service;
import kr.tracom.brt.domain.ST0800.ST0800Service;
import kr.tracom.brt.domain.ST0801.ST0801Service;
import kr.tracom.brt.domain.ST0802.ST0802Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class ST0802Controller extends ControllerSupport {

	@Autowired
	private ST0802Service st0802Service;
	
	@RequestMapping("/st/ST0802G0R0")
	public @ResponseBody Map<String, Object> ST0802G0R0() throws Exception {
		result.setData("dlt_BMS_ROUT_MST", st0802Service.ST0802G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0802G1R0")
	public @ResponseBody Map<String, Object> ST0802G1R0() throws Exception {
		result.setData("dlt_BRT_OPER_PNCTLTY_STAT", st0802Service.ST0802G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0802G1R1")
	public @ResponseBody Map<String, Object> ST0802G1R1() throws Exception {
		result.setData("dlt_BRT_OPER_PNCTLTY_STAT_PIVOT_2", st0802Service.ST0802G1R1());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0802G2R0")
	public @ResponseBody Map<String, Object> ST0802G2R0() throws Exception {
		result.setData("dlt_BRT_OPER_PNCTLTY_STAT_PIVOT", st0802Service.ST0802G2R0());
		return result.getResult();
	}
	
	
	
}
