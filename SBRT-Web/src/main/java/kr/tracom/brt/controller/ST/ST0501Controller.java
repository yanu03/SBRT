package kr.tracom.brt.controller.ST;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.ST0501.ST0501Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class ST0501Controller extends ControllerSupport {

	@Autowired
	private ST0501Service st0501Service;
	
	@RequestMapping("/st/ST0501G0R0")
	public @ResponseBody Map<String, Object> ST0501G0R0() throws Exception {
		result.setData("dlt_BMS_ROUT_MST", st0501Service.ST0501G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0501G0R1")
	public @ResponseBody Map<String, Object> ST0501G0R1() throws Exception {
		result.setData("dlt_BMS_VHC_MST", st0501Service.ST0501G0R1());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0501G1R0")
	public @ResponseBody Map<String, Object> ST0501G1R0() throws Exception {
		result.setData("dlt_BRT_DVC_STAT_PIVOT", st0501Service.ST0501G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0501G2R0")
	public @ResponseBody Map<String, Object> ST0501G2R0() throws Exception {
		result.setData("dlt_BRT_DVC_STAT_PIVOT_2", st0501Service.ST0501G2R0());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0501SHI0")
	public @ResponseBody Map<String, Object> ST0501SHI0() throws Exception {
		result.setData("dlt_searchitem", st0501Service.ST0501SHI0());
		return result.getResult();
	}
	
}
