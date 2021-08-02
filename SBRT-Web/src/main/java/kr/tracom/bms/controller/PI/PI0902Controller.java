package kr.tracom.bms.controller.PI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.PI0902.PI0902Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class PI0902Controller extends ControllerSupport {

	@Autowired
	private PI0902Service pi0902Service;
	
	@RequestMapping("/pi/PI0902G0R0")
	public @ResponseBody Map<String, Object> PI0902G0R0() throws Exception {
		result.setData("dlt_BMS_ED_INFO", pi0902Service.PI0902G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0902G1R0")
	public @ResponseBody Map<String, Object> PI0902G1R0() throws Exception {
		result.setData("dlt_VHC_MST", pi0902Service.PI0902G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0902G1K0")
	public @ResponseBody Map<String, Object> PI0701G1K0() throws Exception {
		result.setData("dma_SEQ_BMS_DESTI_RSV_INFO_0", pi0902Service.PI0902G1K0());
		return result.getResult();
	}	
	
	@RequestMapping("/pi/PI0902G1R1")
	public @ResponseBody Map<String, Object> PI0902G1R1() throws Exception {
		result.setData("dlt_VHC_MST_MNG_LIST", pi0902Service.PI0902G1R1());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0902G1S0")
	public @ResponseBody Map<String, Object> PI0902G1S0() throws Exception {
		Map map = pi0902Service.PI0902G1S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/pi/PI0902G1U0")
	public @ResponseBody Map<String, Object> PI0902G1U0() throws Exception {
		Map map = pi0902Service.PI0902G1U0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/pi/PI0902SHI0")
	public @ResponseBody Map<String, Object> PI0902SHI0() throws Exception {
		result.setData("dlt_searchitem", pi0902Service.PI0902SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0902G2R0")
	public @ResponseBody Map<String, Object> PI0902G2R0() throws Exception {
		result.setData("dlt_BMS_ED_RSV_RST_INFO", pi0902Service.PI0902G2R0());
		return result.getResult();
	}	
	
}
