package kr.tracom.bms.controller.SI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.SI0300.SI0300Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class SI0300Controller extends ControllerSupport{
	
	@Autowired
	private SI0300Service si0300Service;
	
	@RequestMapping("/si/SI0300G0R0")
	public @ResponseBody Map<String, Object> SI0300G0R0() throws Exception {
		result.setData("dlt_BMS_DRV_MST", si0300Service.SI0300G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0300P0R0")
	public @ResponseBody Map<String, Object> SI0300P0R0() throws Exception {
		result.setData("dlt_TRANSCOMP_MST", si0300Service.SI0300P0R0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0300SHI0")
	public @ResponseBody Map<String, Object> SI0300SHI0() throws Exception {
		result.setData("dlt_searchitem", si0300Service.SI0300SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0300G0K0")
	public @ResponseBody Map<String, Object> SI0300G0K0() throws Exception {
		result.setData("dma_SEQ_BMS_DRV_MST_0", si0300Service.SI0300G0K0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0300G0S0")
	public @ResponseBody Map<String, Object> SI0300G0S0() throws Exception {
		Map map = si0300Service.SI0300G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}	
	
}
