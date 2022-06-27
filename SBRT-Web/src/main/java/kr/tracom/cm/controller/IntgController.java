package kr.tracom.cm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.cm.domain.Intg.IntgService;
import kr.tracom.cm.support.ControllerSupport;
import kr.tracom.util.Result;
import kr.tracom.util.UserInfo;

@Controller
@Scope("request")
public class IntgController extends ControllerSupport {

	@Autowired
	private IntgService intgService;
	
	@RequestMapping("/intg/selectIntgList")
	public @ResponseBody Map<String, Object> selectIntgList() throws Exception {
		intgService.insertIntgList();
		result.setData("dlt_BMS_INTG_AIRCON_INFO", intgService.selectIntgList());
		return result.getResult();
	}
	
	
}