package kr.tracom.brt.controller.AL;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.AL0205.AL0205Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class AL0205Controller extends ControllerSupport{
	
	@Autowired
	private AL0205Service al0205Service;
	
	@RequestMapping("/al/AL0205G0R0")
	public @ResponseBody Map<String, Object> AL0205G0R0() throws Exception {
		result.setData("dlt_BMS_STTN_MST", al0205Service.AL0205G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/al/AL0205G0R1")
	public @ResponseBody Map<String, Object> AL0205G0R1() throws Exception {
		result.setData("dlt_BMS_CRS_MST", al0205Service.AL0205G0R1());
		return result.getResult();
	}
	
	@RequestMapping("/al/sttnItem")
	public @ResponseBody Map<String, Object> sttnItem() throws Exception {
		result.setData("dlt_sttnItem", al0205Service.sttnItem());
		return result.getResult();
	}
	
	@RequestMapping("/al/crsItem")
	public @ResponseBody Map<String, Object> crsItem() throws Exception {
		result.setData("dlt_crsItem", al0205Service.crsItem());
		return result.getResult();
	}
	
	@RequestMapping("/al/AL0205G1R0")
	public @ResponseBody Map<String, Object> AL0205G1R0() throws Exception {
		result.setData("dlt_BRT_OPER_ALLOC_PL_NODE_INFO", al0205Service.AL0205G1R0());
		result.setData("dlt_BRT_OPER_ALLOC_PL_NODE_CNT", al0205Service.AL0205G1CNT());
		result.setData("dlt_BRT_OPER_ALLOC_PL_NODE_ROUT_NM", al0205Service.AL0205G1RM());
		return result.getResult();
	}
	
	
	
}
