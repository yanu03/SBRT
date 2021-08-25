package kr.tracom.bms.controller.PI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.PI0206.PI0206Service;
import kr.tracom.bms.domain.PI0702.PI0702Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class PI0206Controller extends ControllerSupport{

	@Autowired
	private PI0206Service pi0206Service;
	
	@RequestMapping("/pi/PI0206G0R0")
	public @ResponseBody Map<String, Object> PI0206G0R0() throws Exception {
		result.setData("dlt_BRT_COR_MST", pi0206Service.PI0206G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0206G1R0")
	public @ResponseBody Map<String, Object> PI0206G1R0() throws Exception {
		result.setData("dlt_VHC_MST", pi0206Service.PI0206G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0206G1K0")
	public @ResponseBody Map<String, Object> PI0701G1K0() throws Exception {
		result.setData("dma_SEQ_BMS_VOC_RSV_INFO_0", pi0206Service.PI0206G1K0());
		return result.getResult();
	}	
	
	@RequestMapping("/pi/PI0206G1R1")
	public @ResponseBody Map<String, Object> PI0206G1R1() throws Exception {
		result.setData("dlt_VHC_MST_MNG_LIST", pi0206Service.PI0206G1R1());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0206G1S0")
	public @ResponseBody Map<String, Object> PI0206G1S0() throws Exception {
		Map map = pi0206Service.PI0206G1S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/pi/PI0206G1U0")
	public @ResponseBody Map<String, Object> PI0206G1U0() throws Exception {
		Map map = pi0206Service.PI0206G1U0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/pi/PI0206SHI0")
	public @ResponseBody Map<String, Object> PI0206SHI0() throws Exception {
		result.setData("dlt_searchitem", pi0206Service.PI0206SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0206G2R0")
	public @ResponseBody Map<String, Object> PI0206G2R0() throws Exception {
		result.setData("dlt_BMS_VOC_RSV_RST_INFO", pi0206Service.PI0206G2R0());
		return result.getResult();
	}
	
}
