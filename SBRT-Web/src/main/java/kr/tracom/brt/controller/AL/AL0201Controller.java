package kr.tracom.brt.controller.AL;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.AL0201.AL0201Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class AL0201Controller extends ControllerSupport {
	
	@Autowired
	private AL0201Service al0201Service;
	
	@RequestMapping("/al/AL0201G0R0")
	public @ResponseBody Map<String, Object> AL0201G0R0() throws Exception {
		result.setData("dlt_BRT_OPER_PL_MST", al0201Service.AL0201G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/al/AL0201G0S0")
	public @ResponseBody Map<String, Object> AL0201G0S0() throws Exception {
		Map map = al0201Service.AL0201G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}	
	
	@RequestMapping("/al/AL0201G1R0")
	public @ResponseBody Map<String, Object> AL0102G1R0() throws Exception {
		result.setData("dlt_BRT_OPER_PL_ROUT_INFO", al0201Service.AL0201G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/al/AL0201G1S0")
	public @ResponseBody Map<String, Object> AL0201G1S0() throws Exception {
		Map map = al0201Service.AL0201G1S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}	
}
