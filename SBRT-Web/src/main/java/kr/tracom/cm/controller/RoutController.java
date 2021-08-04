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

import kr.tracom.cm.domain.Rout.RoutService;
import kr.tracom.cm.support.ControllerSupport;
import kr.tracom.util.Result;
import kr.tracom.util.UserInfo;

@Controller
@Scope("request")
public class RoutController extends ControllerSupport {

	@Autowired
	private RoutService routService;

	@RequestMapping("/rout/selectRoutItem")
	public @ResponseBody Map<String, Object> selectRoutItem() throws Exception {
		result.setData("dlt_searchitem", routService.selectRoutItem());
		return result.getResult();
	}

	@RequestMapping("/rout/selectRoutList")
	public @ResponseBody Map<String, Object> selectRoutList() throws Exception {
		result.setData("dlt_BMS_ROUT_MST", routService.selectRoutList());
		return result.getResult();
	}
	
	@RequestMapping("/rout/selectNodeRoutList2")
	public @ResponseBody Map<String, Object> selectNodeRoutList2() throws Exception {
		result.setData("dlt_BMS_ROUT_NODE_CMPSTN", routService.selectNodeRoutList2());
		return result.getResult();
	}
	
	@RequestMapping("/rout/selectNodeRoutList")
	public @ResponseBody Map<String, Object> selectNodeRoutList() throws Exception {
		result.setData("dlt_BMS_ROUT_NODE_CMPSTN", routService.selectNodeRoutList());
		return result.getResult();
	}
}