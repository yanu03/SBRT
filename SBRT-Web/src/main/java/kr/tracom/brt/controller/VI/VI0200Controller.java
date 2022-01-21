package kr.tracom.brt.controller.VI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.VI0200.VI0200Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class VI0200Controller extends ControllerSupport {
	
	@Autowired
	private VI0200Service vi0200Service;
	
	@RequestMapping("/vi/VI0200G0R0")
	public @ResponseBody Map<String, Object> VI0200G0R0() throws Exception {
		result.setData("dlt_BRT_CPLNT_HIS", vi0200Service.VI0200G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/vi/VI0200SHI0")
	public @ResponseBody Map<String, Object> VI0200SHI0() throws Exception {
		result.setData("dlt_grgSearchItem", vi0200Service.VI0200SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/vi/VI0200G0S0")
	public @ResponseBody Map<String, Object> VI0200G0S0() throws Exception{
		Map map = vi0200Service.VI0200G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/vi/VI0200G0K0")
	public @ResponseBody Map<String, Object> VI0200G0K0() throws Exception{
		result.setData("dma_SEQ_BRT_CPLNT_HIS_0", vi0200Service.VI0200G0K0());
		return result.getResult();
	}
	
	@RequestMapping("/vi/VI0200P0R0")
	public @ResponseBody Map<String, Object> VI0200P0R0() throws Exception {
		result.setData("dlt_BMS_NODE_MST", vi0200Service.VI0200P0R0());
		return result.getResult();
	}
	
	@RequestMapping("/vi/VI0200P1R0")
	public @ResponseBody Map<String, Object> VI0200P1R0() throws Exception {
		result.setData("dlt_BMS_LINK_MST", vi0200Service.VI0200P1R0());
		return result.getResult();
	}
	
	@RequestMapping("/vi/VI0200P2R0")
	public @ResponseBody Map<String, Object> VI0200P2R0() throws Exception {
		result.setData("dlt_BMS_ROUT_MST", vi0200Service.VI0200P2R0());
		return result.getResult();
	}
	
	@RequestMapping("/vi/VI0200P3R0")
	public @ResponseBody Map<String, Object> VI0200P3R0() throws Exception {
		result.setData("dlt_BMS_VHC_MST", vi0200Service.VI0200P3R0());
		return result.getResult();
	}
	
	@RequestMapping("/vi/VI0200P4R0")
	public @ResponseBody Map<String, Object> VI0200P4R0() throws Exception {
		result.setData("dlt_BMS_DRV_MST", vi0200Service.VI0200P4R0());
		return result.getResult();
	}
	
	
	
	/*@RequestMapping("/vi/VI0200P0R0")
	public @ResponseBody Map<String, Object> VI0200P0R0() throws Exception {
		result.setData("dlt_BRT_CPLNT_HIS", vi0200Service.VI0200P0R0());
		return result.getResult();
	}
	
	@RequestMapping(value = "/vi/VI0200P0S0")
	public @ResponseBody Map<String, Object> VI0200P0S0() throws Exception {
		result.setData("dma_result", vi0200Service.VI0200P0S0());

		return result.getResultSave();
	}*/
	
}
