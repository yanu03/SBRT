package kr.tracom.brt.controller.AL;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.AL0104.AL0104Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class AL0104Controller extends ControllerSupport{

	@Autowired
	private AL0104Service al0104service;
	
	@RequestMapping("/al/AL0104G1R0")
	public @ResponseBody Map<String, Object> AL0104G0R0() throws Exception {
		result.setData("dlt_BMS_VHC_MST", al0104service.AL0104G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/al/AL0104G2R0")
	public @ResponseBody Map<String, Object> AL0104G2R0() throws Exception {
		result.setData("dlt_BRT_REP_ROUT_VHC_CMPSTN", al0104service.AL0104G2R0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/AL0104G2S0")
	public @ResponseBody Map<String, Object> AL0104G2S0() throws Exception {
		Map map = al0104service.AL0104G2S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
}
