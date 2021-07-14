package kr.tracom.bms.controller.PI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.PI0601.PI0601Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class PI0601Controller extends ControllerSupport {

	@Autowired
	private PI0601Service pi0601Service;
	
	@RequestMapping("/pi/PI0601G0R0")
	public @ResponseBody Map<String, Object> PI0601G0R0() throws Exception {
		result.setData("dlt_BIT_VDO_INFO", pi0601Service.PI0601G0R0());
		return result.getResult();
	}	

	@RequestMapping("/pi/PI0601G0K0")
	public @ResponseBody Map<String, Object> PI0601G0K0() throws Exception {
		result.setData("dma_SEQ_BMS_BIT_VDO_INFO_0", pi0601Service.PI0601G0K0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0601G0S0")
	public @ResponseBody Map<String, Object> PI0601G0S0() throws Exception {
		Map map = pi0601Service.PI0601G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/pi/PI0601SHI0")
	public @ResponseBody Map<String, Object> PI0601SHI0() throws Exception {
		result.setData("dlt_searchitem", pi0601Service.PI0601SHI0());
		return result.getResult();
	}	
	
}
