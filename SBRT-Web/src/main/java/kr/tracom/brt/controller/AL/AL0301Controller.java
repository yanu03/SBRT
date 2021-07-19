package kr.tracom.brt.controller.AL;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.AL0301.AL0301Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class AL0301Controller extends ControllerSupport {

	@Autowired
	private AL0301Service al0301Service;
	
	@RequestMapping("/AL/AL0301G0R0")
	public @ResponseBody Map<String, Object> AL0301G0R0() throws Exception {
		result.setData("dlt_OPER_PL_ROUT_INFO", al0301Service.AL0301G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/AL/AL0301C0R0")
	public @ResponseBody Map<String, Object> AL0301C0R0() throws Exception {
		result.setData("dlt_OPER_PL_ROUT_INFO_CHART", al0301Service.AL0301C0R0());
		return result.getResult();
	}	
}
