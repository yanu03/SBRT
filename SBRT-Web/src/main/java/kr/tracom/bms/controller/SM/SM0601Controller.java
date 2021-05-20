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
	private SM0601Service emailService;

	// Email Search
	@RequestMapping("/sm/SM0601G0R0")
	public @ResponseBody Map<String, Object> SM0601G0R0() throws Exception {
		try {
			result.setData("dlt_EMAIL_MST", emailService.SM0601G0R0());
			result.setMsg(result.STATUS_SUCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg(result.STATUS_ERROR);
		}
		return result.getResult();
	}
	
	// User(수신자) Search
	@RequestMapping("/sm/SM0601G1R0")
	public @ResponseBody Map<String, Object> SM0601G1R0(Map<String, Object> param) throws Exception {
		try {
			result.setData("dlt_USER_MST", emailService.SM0601G1R0());
			result.setMsg(result.STATUS_SUCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg(result.STATUS_ERROR);
		}
		return result.getResult();
	}
	
	// 
	@RequestMapping("/sm/SM0601P0R0")
	public @ResponseBody Map<String, Object> SM0601P0R0(Map<String, Object> param) throws Exception {
		try {
			result.setData("dlt_USER_MST", emailService.SM0601P0R0());
			result.setMsg(result.STATUS_SUCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg(result.STATUS_ERROR);
		}
		return result.getResult();
	}	
	
	// Email Save
	// TODO public @ResponseBody Map<String, Object> SM0601G0S0(@RequestBody Map<String, Object> param){
	// RequestBody 추가하면 에러 발생함 원인 파악 필요.....
	@RequestMapping("/sm/SM0601G0S0")
	public @ResponseBody Map<String, Object> SM0601G0S0(Map<String, Object> param){
		try {
			Map map = emailService.SM0601G0S0();
			result.setData("result", map);
			result.setMsg(result.STATUS_SUCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg(result.STATUS_ERROR);
		}
		return result.getResult();
	}	
}
