package kr.tracom.brt.controller.ST;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.ST0701.ST0701Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class ST0701Controller extends ControllerSupport {

	@Autowired
	private ST0701Service st0701Service;
	
	@RequestMapping("/st/ST0701G0R0")
	public @ResponseBody Map<String, Object> ST0701G0R0() throws Exception {
		result.setData("dlt_BRT_CPLNT_STAT", st0701Service.ST0701G0R0());
		return result.getResult();
	}
	
	
	@RequestMapping("/st/ST0701G1R0")
	public @ResponseBody Map<String, Object> ST0701G1R0() throws Exception {
		result.setData("dlt_BRT_CPLNT_STAT_PIVOT", st0701Service.ST0701G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0701G2R0")
	public @ResponseBody Map<String, Object> ST0701G2R0() throws Exception {
		result.setData("dlt_BRT_CPLNT_STAT_PIVOT_2", st0701Service.ST0701G2R0());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0701SHI0")
	public @ResponseBody Map<String, Object> ST0701SHI0() throws Exception {
		result.setData("dlt_cplntItem", st0701Service.ST0701SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0701SHI1")
	public @ResponseBody Map<String, Object> ST0701SHI1() throws Exception {
		result.setData("dlt_cplntTypeItem", st0701Service.ST0701SHI1());
		return result.getResult();
	}
	
}
