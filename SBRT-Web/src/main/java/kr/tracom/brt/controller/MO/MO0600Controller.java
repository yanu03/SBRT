package kr.tracom.brt.controller.MO;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.MO0600.MO0600Service;
import kr.tracom.cm.support.ControllerSupport;

@Scope("request")
@Controller
public class MO0600Controller extends ControllerSupport{

	@Autowired
	private MO0600Service mo0600Service;

	@RequestMapping("/mo/MO0600G0R0")
	public @ResponseBody Map<String, Object> MO0600G0R0() throws Exception {
		result.setData("dlt_BRT_CUR_OPER_INFO", mo0600Service.MO0600G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0600G1R0")
	public @ResponseBody Map<String, Object> MO0600G1R0() throws Exception {
		result.setData("dlt_BRT_INCDNT_INFO", mo0600Service.MO0600G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0600SHI1")
	public @ResponseBody Map<String, Object> MO0600SHI1() throws Exception {
		result.setData("dlt_searchitem2", mo0600Service.MO0600SHI1());
		return result.getResult();
	}
	
	
}
