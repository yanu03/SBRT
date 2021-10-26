package kr.tracom.brt.controller.MO;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.MO0700.MO0700Service;
import kr.tracom.cm.support.ControllerSupport;

@Scope("request")
@Controller
public class MO0700Controller extends ControllerSupport {

	@Autowired
	private MO0700Service mo0700Service;

	@RequestMapping("/mo/MO0700G0R0")
	public @ResponseBody Map<String, Object> MO0700G0R0() throws Exception {
		result.setData("dlt_BRT_CUR_OPER_INFO", mo0700Service.MO0700G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0700G1R0")
	public @ResponseBody Map<String, Object> MO0700G1R0() throws Exception {
		result.setData("dlt_BRT_OPER_VIOLT_HIS", mo0700Service.MO0700G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0700SHI1")
	public @ResponseBody Map<String, Object> MO0700SHI1() throws Exception {
		result.setData("dlt_searchitem2", mo0700Service.MO0700SHI1());
		return result.getResult();
	}
	
}
