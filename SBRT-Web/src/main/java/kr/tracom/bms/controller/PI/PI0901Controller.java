package kr.tracom.bms.controller.PI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.PI0901.PI0901Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class PI0901Controller extends ControllerSupport {

	@Autowired
	private PI0901Service pi0901Service;
	
	@RequestMapping("/pi/PI0901G0R0")
	public @ResponseBody Map<String, Object> PI0901G0R0() throws Exception {
		result.setData("dlt_BMS_ED_INFO", pi0901Service.PI0901G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0901G0K0")
	public @ResponseBody Map<String, Object> PI0901G0K0() throws Exception {
		result.setData("dma_SEQ_BMS_ED_INFO_0", pi0901Service.PI0901G0K0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0901G0S0")
	public @ResponseBody Map<String, Object> PI0901G0S0() throws Exception {
		Map map = pi0901Service.PI0901G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/pi/PI0901SHI0")
	public @ResponseBody Map<String, Object> PI0901SHI0() throws Exception {
		result.setData("dlt_searchitem", pi0901Service.PI0901SHI0());
		return result.getResult();
	}
}
