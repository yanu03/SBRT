package kr.tracom.bms.controller.PI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.PI0602.PI0602Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class PI0602Controller extends ControllerSupport {
	
	@Autowired
	private PI0602Service pi0602Service;
	
	@RequestMapping("/pi/PI0602G0R0")
	public @ResponseBody Map<String, Object> PI0602G0R0() throws Exception {
		result.setData("dlt_BIT_VDO_ORGA_INFO", pi0602Service.PI0602G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0602G0K0")
	public @ResponseBody Map<String, Object> PI0602G0R1() throws Exception {
		result.setData("dma_SEQ_BMS_BIT_VDO_ORGA_0", pi0602Service.PI0602G0K0());
		return result.getResult();
	}

	@RequestMapping("/pi/PI0602SHI0")
	public @ResponseBody Map<String, Object> PI0602G0R2() throws Exception {
		result.setData("dlt_searchitem", pi0602Service.PI0602SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0602G1R0")
	public @ResponseBody Map<String, Object> PI0602G1R0() throws Exception {
		result.setData("dlt_BIT_VDO_INFO", pi0602Service.PI0602G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0602G0S0")
	public @ResponseBody Map<String, Object> PI0602G0S0() throws Exception {
		Map map = pi0602Service.PI0602G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/pi/PI0602G2R0")
	public @ResponseBody Map<String, Object> PI0602G2R0() throws Exception {
		result.setData("dlt_BIT_VDO_ORGA_LIST", pi0602Service.PI0602G2R0());
		return result.getResult();
	}	
	
	@RequestMapping("/pi/PI0602G2S0")
	public @ResponseBody Map<String, Object> PI0602G2S0() throws Exception {
		Map map = pi0602Service.PI0602G2S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
}
