package kr.tracom.bms.controller.PI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.PI0207.PI0207Service;
import kr.tracom.bms.domain.PI0502.PI0502Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class PI0207Controller extends ControllerSupport {
	
	@Autowired
	private PI0207Service pi0207Service;
	
	@RequestMapping("/pi/PI0207G0R0")
	public @ResponseBody Map<String, Object> PI0207G0R0() throws Exception {
		result.setData("dlt_VOC_ORGA_INFO", pi0207Service.PI0207G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0207G0K0")
	public @ResponseBody Map<String, Object> PI0207G0R1() throws Exception {
		result.setData("dma_SEQ_BMS_VOC_ORGA_INFO_0", pi0207Service.PI0207G0K0());
		return result.getResult();
	}

	@RequestMapping("/pi/PI0207SHI0")
	public @ResponseBody Map<String, Object> PI0207G0R2() throws Exception {
		result.setData("dlt_searchitem", pi0207Service.PI0207SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0207G1R0")
	public @ResponseBody Map<String, Object> PI0207G1R0() throws Exception {
		result.setData("dlt_VOC_INFO", pi0207Service.PI0207G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0207G0S0")
	public @ResponseBody Map<String, Object> PI0207G0S0() throws Exception {
		Map map = pi0207Service.PI0207G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/pi/PI0207G2R0")
	public @ResponseBody Map<String, Object> PI0207G2R0() throws Exception {
		result.setData("dlt_VOC_ORGA_LIST", pi0207Service.PI0207G2R0());
		return result.getResult();
	}	
	
	@RequestMapping("/pi/PI0207G2S0")
	public @ResponseBody Map<String, Object> PI0207G2S0() throws Exception {
		Map map = pi0207Service.PI0207G2S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
}
