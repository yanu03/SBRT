package kr.tracom.cm.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.cm.domain.Find.FindService;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class FindController extends ControllerSupport {

	@Autowired
	private FindService findService;
	
	@RequestMapping("/find/findUser")
	public @ResponseBody Map<String, Object> findUser() throws Exception {
		result.setData("dlt_BMS_USER_MST", findService.findUser());
		return result.getResult();
	}
	
}
