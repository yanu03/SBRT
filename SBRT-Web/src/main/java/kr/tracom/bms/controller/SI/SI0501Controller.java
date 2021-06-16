package kr.tracom.bms.controller.SI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.SI0501.SI0501Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class SI0501Controller extends ControllerSupport {
	
	@Autowired
	private SI0501Service si0501Service;
	
	@RequestMapping("/si/SI0501G0R0")
	public @ResponseBody Map<String, Object> SI0501G0R0() throws Exception {
		result.setData("dlt_BMS_STTN_MST", si0501Service.SI0501G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0501G0K0")
	public @ResponseBody Map<String, Object> SI0501G0R1() throws Exception {
		result.setData("dma_SEQ_BMS_STTN_MST_0", si0501Service.SI0501G0K0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0501SHI0")
	public @ResponseBody Map<String, Object> SI0501G0R2() throws Exception {
		result.setData("dlt_searchitem", si0501Service.SI0501SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0501G1R0")
	public @ResponseBody Map<String, Object> SI0501G1R0() throws Exception {
		result.setData("dlt_BMS_ROUT_MST", si0501Service.SI0501G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0501G0S0")
	public @ResponseBody Map<String, Object> SI0501G0S0() throws Exception {
		Map map = si0501Service.SI0501G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}	
	
	@RequestMapping("/si/SI0501G1S0")
	public @ResponseBody Map<String, Object> SI0401G1S0() throws Exception {
		Map map = si0501Service.SI0501G1S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}	
	
	@RequestMapping("/si/SI0501P0R0")
	public @ResponseBody Map<String, Object> SI0501P0R0() throws Exception {
		result.setData("dlt_BMS_ROUT_MST", si0501Service.SI0501P0R0());
		return result.getResult();
	}

	

}
