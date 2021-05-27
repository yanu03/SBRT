package kr.tracom.bms.controller.SM;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.SM0601.SM0601Service;
import kr.tracom.cm.support.ControllerSupport;


@Controller
@Scope("request")
public class SM0601Controller extends ControllerSupport {

	@Autowired
	private SM0601Service sm0601Service;

	@RequestMapping("/sm/SM0601G0R0")
	public @ResponseBody Map<String, Object> SM0601G0R0(Map<String, Object> param) throws Exception {
		result.setData("dlt_EMAIL_MST", sm0601Service.SM0601G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/sm/SM0601G1R0")
	public @ResponseBody Map<String, Object> SM0601G1R0(Map<String, Object> param) throws Exception {
		result.setData("dlt_USER_MST", sm0601Service.SM0601G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/sm/SM0601P0R0")
	public @ResponseBody Map<String, Object> SM0601P0R0(Map<String, Object> param) throws Exception {
		result.setData("dlt_USER_MST", sm0601Service.SM0601P0R0());
		return result.getResult();
	}	
	
	@RequestMapping("/sm/SM0601G0S0")
	public @ResponseBody Map<String, Object> SM0601G0S0(Map<String, Object> param) throws Exception{
		result.setData("result", sm0601Service.SM0601G0S0());
		return result.getResult();
	}	
}
