package kr.tracom.brt.controller.AL;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.AL0600.AL0600Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class AL0600Controller extends ControllerSupport{
	
	@Autowired
	private AL0600Service al0600Service;
	
	@RequestMapping("/al/AL0600G0R0")
	public @ResponseBody Map<String, Object> AL0600G0R0() throws Exception {
		result.setData("dlt_BMS_ROUT_MST", al0600Service.AL0600G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/al/AL0600SHI0")
	public @ResponseBody Map<String, Object> AL0600SHI0() throws Exception {
		result.setData("dlt_searchitem", al0600Service.AL0600SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/al/AL0600G1R0")
	public @ResponseBody Map<String, Object> AL0600G1R0() throws Exception {
		result.setData("dlt_BRT_OPER_ALLOC_PL_NODE_INFO", al0600Service.AL0600G1R0());
		result.setData("dlt_BRT_OPER_ALLOC_PL_NODE_CNT", al0600Service.AL0600G1CNT());
		return result.getResult();
	}
	
	@RequestMapping("/al/AL0600G0P1")
	public @ResponseBody Map<String, Object> AL0600G0P1() throws Exception {
		result.setData("dlt_BRT_OPER_ALLOC_PL_NODE_CNT", al0600Service.AL0600G1CNT());
		return result.getResult();
	}
	
}
