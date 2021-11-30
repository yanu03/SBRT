package kr.tracom.bms.controller.SI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.SI0200.SI0200Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class SI0200Controller extends ControllerSupport{

	@Autowired
	private SI0200Service si0200Service;
	
	@RequestMapping("/si/SI0200G0R0")
	public @ResponseBody Map<String, Object> SI0200G0R0() throws Exception {
		result.setData("dlt_BMS_VHC_MST", si0200Service.SI0200G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0200P0R0")
	public @ResponseBody Map<String, Object> SI0200P0R0() throws Exception {
		result.setData("dlt_TRANSCOMP_MST", si0200Service.SI0200P0R0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0200G0K0")
	public @ResponseBody Map<String, Object> SI0200G0K0() throws Exception {
		result.setData("dma_SEQ_BMS_VHC_MST_0", si0200Service.SI0200G0K0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0200G0S0")
	public @ResponseBody Map<String, Object> SI0200G0S0() throws Exception {
		Map map = si0200Service.SI0200G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/si/SI0200SHI0")
	public @ResponseBody Map<String, Object> SI0200SHI0() throws Exception {
		result.setData("dlt_vhcSearchItem", si0200Service.SI0200SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0200SHI1")
	public @ResponseBody Map<String, Object> SI0200SHI1() throws Exception {
		result.setData("dlt_searchitem", si0200Service.SI0200SHI1());
		return result.getResult();
	}
}
