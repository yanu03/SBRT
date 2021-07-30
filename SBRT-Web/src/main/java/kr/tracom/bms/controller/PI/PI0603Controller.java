package kr.tracom.bms.controller.PI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.PI0603.PI0603Service;
import kr.tracom.cm.support.ControllerSupport;


@Controller
@Scope("request")
public class PI0603Controller extends ControllerSupport {

	@Autowired
	private PI0603Service pi0603Service;
	
	@RequestMapping("/pi/PI0603G0R0")
	public @ResponseBody Map<String, Object> PI0603G0R0() throws Exception {
		result.setData("dlt_BIT_VDO_ORGA_INFO", pi0603Service.PI0603G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0603G1R0")
	public @ResponseBody Map<String, Object> PI0603G1R0() throws Exception {
		result.setData("dlt_FCLT_INFO", pi0603Service.PI0603G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0603G1S0")
	public @ResponseBody Map<String, Object> PI0603G1S0() throws Exception {
		Map map = pi0603Service.PI0603G1S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/pi/PI0603G1U0")
	public @ResponseBody Map<String, Object> PI0603G1D0() throws Exception {
		Map map = pi0603Service.PI0603G1U0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/pi/PI0603SHI0")
	public @ResponseBody Map<String, Object> PI0603SHI0() throws Exception {
		result.setData("dlt_searchitem", pi0603Service.PI0603SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0603G2R0")
	public @ResponseBody Map<String, Object> PI0603G2R0() throws Exception {
		result.setData("dlt_BMS_BIT_VDO_RSV_RST_INFO", pi0603Service.PI0603G2R0());
		return result.getResult();
	}	
	
}
