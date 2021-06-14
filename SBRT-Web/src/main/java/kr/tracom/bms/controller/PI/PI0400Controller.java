package kr.tracom.bms.controller.PI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.PI0400.PI0400Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class PI0400Controller extends ControllerSupport{

	@Autowired
	private PI0400Service PI0400Service;

	@RequestMapping("/pi/PI0400G0R0")
	public @ResponseBody Map<String, Object> PI0400G0R0() throws Exception {
		result.setData("dlt_BMS_WEAT_INFO", PI0400Service.PI0400G0R0());
		return result.getResult();
	}

	@RequestMapping("/pi/PI0400G1R0")
	public @ResponseBody Map<String, Object> PI0400G1R0() throws Exception {
		result.setData("dlt_BMS_ATMO_INFO", PI0400Service.PI0400G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0400G2R0")
	public @ResponseBody Map<String, Object> PI0400G2R0() throws Exception {
		result.setData("dlt_BMS_LIVING_LOG", PI0400Service.PI0400G2R0());
		return result.getResult();
	}	
}
