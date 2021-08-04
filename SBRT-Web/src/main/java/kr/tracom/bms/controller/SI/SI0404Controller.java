package kr.tracom.bms.controller.SI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.SI0404.SI0404Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class SI0404Controller extends ControllerSupport {
	
	@Autowired
	private SI0404Service si0404Service;
	
	
	@RequestMapping("/si/SI0404G1R0")
	public @ResponseBody Map<String, Object> SI0404G1R0() throws Exception {
		result.setData("dlt_BMS_ROUT_NODE_CMPSTN", si0404Service.SI0404G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0404G1S0")
	public @ResponseBody Map<String, Object> SI0404G1S0() throws Exception {
		Map map = si0404Service.SI0404G1S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}	
	
	@RequestMapping("/si/SI0404P0R0")
	public @ResponseBody Map<String, Object> SI0404P0R0() throws Exception {
		result.setData("dlt_BMS_NODE_MST", si0404Service.SI0404P0R0());
		return result.getResult();
	}
	

}
