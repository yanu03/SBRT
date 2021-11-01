package kr.tracom.brt.controller.ST;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.ST0301.ST0301Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class ST0301Controller extends ControllerSupport {

	@Autowired
	private ST0301Service st0301Service;
	
	@RequestMapping("/st/ST0301G0R0")
	public @ResponseBody Map<String, Object> ST0301G0R0() throws Exception {
		result.setData("dlt_BMS_ROUT_MST", st0301Service.ST0301G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0301G1R0")
	public @ResponseBody Map<String, Object> ST0301G1R0() throws Exception {
		result.setData("dlt_BRT_INCDNT_STAT", st0301Service.ST0301G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0301G2R0")
	public @ResponseBody Map<String, Object> ST0301G2R0() throws Exception {
		result.setData("dlt_BRT_INCDNT_STAT2", st0301Service.ST0301G2R0());
		return result.getResult();
	}
	
}
