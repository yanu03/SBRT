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
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class ST0800Controller extends ControllerSupport {

	@Autowired
	private ST0800Service st0800Service;
	
	@RequestMapping("/st/ST0800G0R0")
	public @ResponseBody Map<String, Object> ST0800G0R0() throws Exception {
		result.setData("dlt_BMS_ROUT_MST", st0800Service.ST0800G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0800G1R0")
	public @ResponseBody Map<String, Object> ST0800G1R0() throws Exception {
		result.setData("dlt_BRT_STTN_STAT", st0800Service.ST0800G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0800G1R1")
	public @ResponseBody Map<String, Object> ST0800G1R1() throws Exception {
		result.setData("dlt_BRT_STTN_STAT_PIVOT_2", st0800Service.ST0800G1R1());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0800G2R0")
	public @ResponseBody Map<String, Object> ST0800G2R0() throws Exception {
		result.setData("dlt_BRT_STTN_STAT_PIVOT", st0800Service.ST0800G2R0());
		return result.getResult();
	}
	
	
}
