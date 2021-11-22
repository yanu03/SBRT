package kr.tracom.brt.controller.AL;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.AL0103.AL0103Service;
import kr.tracom.brt.domain.AL0203.AL0203Service;
import kr.tracom.brt.domain.AL0204.AL0204Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class AL0204Controller extends ControllerSupport{
	
	@Autowired
	private AL0204Service al0204Service;
	
	@RequestMapping("/al/AL0204G0R0")
	public @ResponseBody Map<String, Object> AL0204G0R0() throws Exception {
		result.setData("dlt_BMS_ROUT_MST", al0204Service.AL0204G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/al/AL0204G1R0")
	public @ResponseBody Map<String, Object> AL0204G1R0() throws Exception {
		result.setData("dlt_BRT_DAY_OPER_ALLOC_PL_NODE_INFO", al0204Service.AL0204G1R0());
		result.setData("dlt_BRT_DAY_OPER_ALLOC_PL_NODE_INFO_CNT", al0204Service.AL0204G1CNT());
		return result.getResult();
	}
	
	@RequestMapping("/al/AL0204G1S0")
	public @ResponseBody Map<String, Object> AL0203G1S0() throws Exception {
		Map map = al0204Service.AL0204G1S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}	
}
