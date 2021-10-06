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

import kr.tracom.cm.domain.OperPlan.OperPlanService;
import kr.tracom.cm.support.ControllerSupport;
import kr.tracom.util.Result;
import kr.tracom.util.UserInfo;

@Controller
@Scope("request")
public class OperPlanController extends ControllerSupport {

	@Autowired
	private OperPlanService operPlanService;
	
	@RequestMapping("/operPlan/selectOperPlanRoutList")
	public @ResponseBody Map<String, Object> selectOperPlanRoutList() throws Exception {
		result.setData("dlt_BRT_OPER_PL_ROUT_INFO", operPlanService.selectOperPlanRoutList());
		return result.getResult();
	}

	@RequestMapping("/operPlan/selectOperPlanRout")
	public @ResponseBody Map<String, Object> selectOperPlanRout() throws Exception {
		result.setData("dlt_BRT_OPER_PL_ROUT_INFO", operPlanService.selectOperPlanRout());
		return result.getResult();
	}
}