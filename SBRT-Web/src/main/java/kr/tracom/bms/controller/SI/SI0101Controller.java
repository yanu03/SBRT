package kr.tracom.bms.controller.SI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.SI0101.SI0101Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class SI0101Controller extends ControllerSupport {
	@Autowired
	private SI0101Service si0101Service;
	
	@RequestMapping("/si/SI0101G0R0")
	public @ResponseBody Map<String, Object> SI0101G0R0() throws Exception {
		result.setData("dlt_BMS_GRG_MST", si0101Service.SI0101G0R0());
		return result.getResult();
	}
	
	@RequestMapping(value = "/si/SI0101G0S0")
	public @ResponseBody Map<String, Object> SI0101G0S0() throws Exception {
		result.setData("dma_result", si0101Service.SI0101G0S0());

		return result.getResultSave();
	}	
	
	@RequestMapping("/si/SI0101G0K0")
	public @ResponseBody Map<String, Object> SI0101G0K0() throws Exception {
		result.setData("dma_SEQ_BMS_GRG_MST_0", si0101Service.SI0101G0K0());
		return result.getResult();
	}

	@RequestMapping("/si/SI0101SHI0")
	public @ResponseBody Map<String, Object> SI0101SHI0() throws Exception {
		result.setData("dlt_grgSearchItem", si0101Service.SI0101SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0101P0R0")
	public @ResponseBody Map<String, Object> SI0101P0R0() throws Exception {
		result.setData("dlt_GRG_RDS_INFO", si0101Service.SI0101P0R0());
		return result.getResult();
	}
	
	@RequestMapping(value = "/si/SI0101P0S0")
	public @ResponseBody Map<String, Object> SI0101P0S0() throws Exception {
		result.setData("dma_result", si0101Service.SI0101P0S0());

		return result.getResultSave();
	}
}
