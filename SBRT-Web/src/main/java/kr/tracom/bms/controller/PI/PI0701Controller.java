package kr.tracom.bms.controller.PI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.PI0701.PI0701Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class PI0701Controller extends ControllerSupport{

	@Autowired
	private PI0701Service pi0701Service;
	
	@RequestMapping("/pi/PI0701G0R0")
	public @ResponseBody Map<String, Object> PI0701G0R0() throws Exception {
		result.setData("dlt_ROUT_MST", pi0701Service.PI0701G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0701G1R0")
	public @ResponseBody Map<String, Object> PI0701G1R0() throws Exception {
		result.setData("dlt_actionSetting", pi0701Service.PI0701G1R0());
		return result.getResult();
	}	

	
	@RequestMapping("/pi/PI0701SHI0")
	public @ResponseBody Map<String, Object> PI0701SHI0() throws Exception {
		result.setData("dlt_searchitem", pi0701Service.PI0701SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0701SHI1")
	public @ResponseBody Map<String, Object> PI0701SHI1() throws Exception {
		result.setData("dlt_searchitem2", pi0701Service.PI0701SHI1());
		return result.getResult();
	}	
	
	@RequestMapping("/pi/PI0701G0S0")
	public @ResponseBody Map<String, Object> PI0701G0S0() throws Exception {
		Map map = pi0701Service.PI0701G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
}
