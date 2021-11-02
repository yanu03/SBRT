package kr.tracom.bms.controller.SI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.SI0600.SI0600Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class SI0600Controller extends ControllerSupport {

	@Autowired
	private SI0600Service si0600Service;
	
	@RequestMapping("/si/SI0600T0R0")
	public @ResponseBody Map<String, Object> SI0600T0R0() throws Exception {
		result.setData("dlt_EMER_CLSFCTN_MST", si0600Service.SI0600T0R0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0600T0K0")
	public @ResponseBody Map<String, Object> SI0503T0K0() throws Exception {
		result.setData("dma_SEQ_BMS_EMER_CLSFCTN_MST_0", si0600Service.SI0600T0K0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0600SHI0")
	public @ResponseBody Map<String, Object> SI0503SHI0() throws Exception {
		result.setData("dlt_searchitem", si0600Service.SI0600SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0600T0S0")
	public @ResponseBody Map<String, Object> SI0503T0S0() throws Exception {
		Map map = si0600Service.SI0600T0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}		
	
	@RequestMapping("/si/SI0600G0R0")
	public @ResponseBody Map<String, Object> SI0600G0R0() throws Exception {
		result.setData("dlt_EMER_MEMBER_INFO", si0600Service.SI0600G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0600P0R0")
	public @ResponseBody Map<String, Object> SI0600P0R0() throws Exception {
		result.setData("dlt_MEMBER_INFO", si0600Service.SI0600P0R0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0600G2S0")
	public @ResponseBody Map<String, Object> SI0600G2S0() throws Exception {
		Map map = si0600Service.SI0600G2S0();
		result.setData("dma_resultG2", map);
		return result.getResultSave();
	}
}
