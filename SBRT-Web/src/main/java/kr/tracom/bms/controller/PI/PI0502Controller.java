package kr.tracom.bms.controller.PI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.PI0502.PI0502Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class PI0502Controller extends ControllerSupport {

	@Autowired
	private PI0502Service pi0502Service;
	
	@RequestMapping("/pi/PI0502G0R0")
	public @ResponseBody Map<String, Object> PI0502G0R0() throws Exception {
		result.setData("dlt_VDO_ORGA_INFO", pi0502Service.PI0502G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0502G0K0")
	public @ResponseBody Map<String, Object> PI0502G0R1() throws Exception {
		result.setData("dma_SEQ_BMS_VDO_ORGA_INFO_0", pi0502Service.PI0502G0K0());
		return result.getResult();
	}

	@RequestMapping("/pi/PI0502SHI0")
	public @ResponseBody Map<String, Object> PI0502G0R2() throws Exception {
		result.setData("dlt_searchitem", pi0502Service.PI0502SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0502G1R0")
	public @ResponseBody Map<String, Object> PI0502G1R0() throws Exception {
		result.setData("dlt_VDO_INFO", pi0502Service.PI0502G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0502G0S0")
	public @ResponseBody Map<String, Object> PI0502G0S0() throws Exception {
		Map map = pi0502Service.PI0502G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/pi/PI0502G2R0")
	public @ResponseBody Map<String, Object> PI0502G2R0() throws Exception {
		result.setData("dlt_VDO_ORGA_LIST", pi0502Service.PI0502G2R0());
		return result.getResult();
	}	
	
	@RequestMapping("/pi/PI0502G2S0")
	public @ResponseBody Map<String, Object> PI0502G2S0() throws Exception {
		Map map = pi0502Service.PI0502G2S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
}
