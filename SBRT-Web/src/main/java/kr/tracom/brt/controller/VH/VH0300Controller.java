package kr.tracom.brt.controller.VH;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.VH0300.VH0300Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class VH0300Controller extends ControllerSupport {
	
	@Autowired
	private VH0300Service vh0300Service;
	
	@RequestMapping("/vh/VH0300G0R0")
	public @ResponseBody Map<String, Object> VH0300G0R0() throws Exception {
		result.setData("dlt_BMS_STTN_MST", vh0300Service.VH0300G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/vh/VH0300G1R0")
	public @ResponseBody Map<String, Object> VH0300G1R0() throws Exception {
		result.setData("dlt_BRT_ACRT_LOC_STOP_LOG", vh0300Service.VH0300G1R0());
		return result.getResult();
	}

	@RequestMapping("/vh/VH0300G1S0")
	public @ResponseBody Map<String, Object> VH0300G1S0() throws Exception {
		Map map = vh0300Service.VH0300G1S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}	
	
	@RequestMapping("/vh/VH0300SHI0")
	public @ResponseBody Map<String, Object> VH0300SHI0() throws Exception {
		result.setData("dlt_searchitem", vh0300Service.VH0300SHI0());
		return result.getResult();
	}	
	
}
