package kr.tracom.brt.controller.AL;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.AL0206.AL0206Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class AL0206Controller extends ControllerSupport {
	
	@Autowired
	private AL0206Service al0206Service;
	
	@RequestMapping("/al/AL0206G0R0")
	public @ResponseBody Map<String, Object> AL0206G0R0() throws Exception {
		result.setData("dlt_OPER_ALLOC_PL_ROUT_INFO", al0206Service.AL0206G0R0());
		result.setData("dlt_OPER_ALLOC_PL_ROUT_CNT", al0206Service.AL0206G0CNT());
		return result.getResult();
	}
	/*
	@RequestMapping("/al/AL0206G1R0")
	public @ResponseBody Map<String, Object> AL0206G1R0() throws Exception {
		result.setData("dlt_BRT_ALLOC_PL_MST", al0206Service.AL0206G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/al/AL0206G1S0")
	public @ResponseBody Map<String, Object> AL0206G1S0() throws Exception {
		Map map = al0206Service.AL0206G1S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/al/AL0206G1S1")
	public @ResponseBody Map<String, Object> AL0206G1S1() throws Exception {
		Map map = al0206Service.AL0206G1S1();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/al/AL0206SHI1")
	public @ResponseBody Map<String, Object> AL0206SHI1() throws Exception {
		result.setData("dlt_searchitem2", al0206Service.AL0206SHI1());
		return result.getResult();
	}
	
	@RequestMapping("/al/AL0206G2R0")
	public @ResponseBody Map<String, Object> AL0206G2R0() throws Exception {
		result.setData("dlt_BRT_OPER_DT_ALLOC_PL_MST", al0206Service.AL0206G2R0());
		return result.getResult();
	}
	
	@RequestMapping("/al/AL0206G2S0")
	public @ResponseBody Map<String, Object> AL0206G2S0() throws Exception {
		Map map = al0206Service.AL0206G2S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/al/AL0206P0R0")
	public @ResponseBody Map<String, Object> AL0206P0R0() throws Exception {
		result.setData("dlt_BMS_VHC_MST", al0206Service.AL0206P0R0());
		return result.getResult();
	}
	
	@RequestMapping("/al/AL0206P1R0")
	public @ResponseBody Map<String, Object> AL0206P1R0() throws Exception {
		result.setData("dlt_BMS_DRV_MST", al0206Service.AL0206P1R0());
		return result.getResult();
	}*/
}
