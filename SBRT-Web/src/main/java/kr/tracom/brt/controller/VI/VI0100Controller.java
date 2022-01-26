package kr.tracom.brt.controller.VI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.VI0100.VI0100Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class VI0100Controller extends ControllerSupport {
	
	@Autowired
	private VI0100Service vi0100Service;
	
	@RequestMapping("/vi/VI0100G0R0")
	public @ResponseBody Map<String, Object> VI0100G0R0() throws Exception {
		result.setData("dlt_BRT_CUR_ALLOC_PL_INFO", vi0100Service.VI0100G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/vi/VI0100G1R0")
	public @ResponseBody Map<String, Object> VI0100G1R0() throws Exception {
		result.setData("dlt_BRT_OPER_VIOLT_HIS", vi0100Service.VI0100G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/vi/VI0100P0R0")
	public @ResponseBody Map<String, Object> VI0100P0R0() throws Exception {
		result.setData("dlt_BMS_NODE_MST", vi0100Service.VI0100P0R0());
		return result.getResult();
	}

	@RequestMapping("/vi/VI0100G1S0")
	public @ResponseBody Map<String, Object> VI0100G1S0() throws Exception {
		Map map = vi0100Service.VI0100G1S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}	
	
	@RequestMapping("/vi/VI0100SHI0")
	public @ResponseBody Map<String, Object> VI0100SHI0() throws Exception {
		result.setData("dlt_searchitem", vi0100Service.VI0100SHI0());
		return result.getResult();
	}	
	
}
