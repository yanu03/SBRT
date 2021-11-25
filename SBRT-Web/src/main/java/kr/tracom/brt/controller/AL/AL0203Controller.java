package kr.tracom.brt.controller.AL;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.AL0103.AL0103Service;
import kr.tracom.brt.domain.AL0203.AL0203Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class AL0203Controller extends ControllerSupport{
	
	@Autowired
	private AL0203Service al0203Service;
	
	@RequestMapping("/al/AL0203G0R0")
	public @ResponseBody Map<String, Object> AL0203G0R0() throws Exception {
		result.setData("dlt_BMS_ROUT_MST", al0203Service.AL0203G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/al/AL0203SHI0")
	public @ResponseBody Map<String, Object> AL0203SHI0() throws Exception {
		result.setData("dlt_searchitem", al0203Service.AL0203SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/al/AL0203G1R0")
	public @ResponseBody Map<String, Object> AL0203G1R0() throws Exception {
		result.setData("dlt_BRT_OPER_ALLOC_PL_NODE_INFO", al0203Service.AL0203G1R0());
		result.setData("dlt_BRT_OPER_ALLOC_PL_NODE_CNT", al0203Service.AL0203G1CNT());
		return result.getResult();
	}
	
	@RequestMapping("/al/AL0203G0P1")
	public @ResponseBody Map<String, Object> AL0203G0P1() throws Exception {
		result.setData("dlt_BRT_OPER_ALLOC_PL_NODE_CNT", al0203Service.AL0203G1CNT());
		return result.getResult();
	}
	

	@RequestMapping("/al/AL0203G1S0")
	public @ResponseBody Map<String, Object> AL0203G1S0() throws Exception {
		Map map = al0203Service.AL0203G1S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}	
	
	@RequestMapping("/al/AL0203P0S0")
	public @ResponseBody Map<String, Object> AL0203P0S0() throws Exception {
		Map map = al0203Service.AL0203P0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}	
	
	@RequestMapping("/al/AL0203P0R0")
	public @ResponseBody Map<String, Object> AL0203P0R0() throws Exception {
		result.setData("dlt_BRT_OPER_ALLOC_PL_NODE_INFO", al0203Service.AL0203P0R0());
		return result.getResult();
	}
	
	
}
