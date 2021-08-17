package kr.tracom.bms.controller.SI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.SI0502.SI0502Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class SI0502Controller extends ControllerSupport {
	
	@Autowired
	private SI0502Service si0502Service;
	
	@RequestMapping("/si/SI0502G0R0")
	public @ResponseBody Map<String, Object> SI0502G0R0() throws Exception {
		result.setData("dlt_BMS_STTN_MST", si0502Service.SI0502G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0502SHI0")
	public @ResponseBody Map<String, Object> SI0502G0R2() throws Exception {
		result.setData("dlt_searchitem", si0502Service.SI0502SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0502G1R0")
	public @ResponseBody Map<String, Object> SI0502G1R0() throws Exception {
		result.setData("dlt_TRANSFER_INFO", si0502Service.SI0502G1R0());
		return result.getResult();
	}

	@RequestMapping("/si/SI0502G1K0")
	public @ResponseBody Map<String, Object> SI0502G1K0() throws Exception {
		result.setData("dma_SEQ_BMS_TRANSFER_INFO_0", si0502Service.SI0502G1K0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0502G1S0")
	public @ResponseBody Map<String, Object> SI0502G1S0() throws Exception {
		Map map = si0502Service.SI0502G1S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}	
	
}
