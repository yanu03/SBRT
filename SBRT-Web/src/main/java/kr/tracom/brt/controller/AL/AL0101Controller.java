package kr.tracom.brt.controller.AL;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.AL0101.AL0101Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class AL0101Controller extends ControllerSupport {
	
	@Autowired
	private AL0101Service al0101Service;
	
	@RequestMapping("/al/AL0101G1R0")
	public @ResponseBody Map<String, Object> AL0101G1R0() throws Exception {
		result.setData("dlt_BRT_MAIN_ROUT_NODE_INFO", al0101Service.AL0101G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/al/AL0101G1S0")
	public @ResponseBody Map<String, Object> SI0401G1S0() throws Exception {
		Map map = al0101Service.AL0101G1S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}	
	
	@RequestMapping("/al/AL0101P0R0")
	public @ResponseBody Map<String, Object> AL0101P0R0() throws Exception {
		result.setData("dlt_BMS_NODE_MST", al0101Service.AL0101P0R0());
		return result.getResult();
	}

	

}
