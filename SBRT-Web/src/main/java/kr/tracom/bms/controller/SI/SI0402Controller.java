package kr.tracom.bms.controller.SI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.SI0402.SI0402Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class SI0402Controller extends ControllerSupport {
	
	@Autowired
	private SI0402Service si0402Service;
		
	@RequestMapping("/si/SI0402G1R0")
	public @ResponseBody Map<String, Object> SI0402G1R0() throws Exception {
		result.setData("dlt_BMS_ROUT_NODE_CMPSTN", si0402Service.SI0402G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0402G1S0")
	public @ResponseBody Map<String, Object> SI0402G1S0() throws Exception {
		Map map = si0402Service.SI0402G1S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}	
	
	@RequestMapping("/si/SI0402P0R0")
	public @ResponseBody Map<String, Object> SI0402P0R0() throws Exception {
		result.setData("dlt_BMS_NODE_MST", si0402Service.SI0402P0R0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0402P1R0")
	public @ResponseBody Map<String, Object> SI0402P1R0() throws Exception {
		result.setData("dlt_BMS_MOCK_LINK_NODE", si0402Service.SI0402P1R0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0402P2R0")
	public @ResponseBody Map<String, Object> SI0402P2R0() throws Exception {
		result.setData("dlt_BMS_STTN_MST", si0402Service.SI0402P2R0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0402P2S0")
	public @ResponseBody Map<String, Object> SI0402P2S0() throws Exception {
		Map map = si0402Service.SI0402P2S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}	
	
	@RequestMapping("/si/SI0402P2K0")
	public @ResponseBody Map<String, Object> SI0402P2K0() throws Exception {
		result.setData("dma_SEQ_BMS_STTN_MST_0", si0402Service.SI0402P2K0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0402P3R0")
	public @ResponseBody Map<String, Object> SI0402P3R0() throws Exception {
		result.setData("dlt_BMS_CRS_MST", si0402Service.SI0402P3R0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0402P3S0")
	public @ResponseBody Map<String, Object> SI0402P3S0() throws Exception {
		Map map = si0402Service.SI0402P3S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/si/SI0402P3K0")
	public @ResponseBody Map<String, Object> SI0402P3K0() throws Exception {
		result.setData("dma_SEQ_BMS_CRS_MST_0", si0402Service.SI0402P3K0());
		return result.getResult();
	}
}
