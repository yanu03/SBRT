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
	

	@RequestMapping("/operPlan/selectOperAllocPlanNode")
	public @ResponseBody Map<String, Object> selectOperAllocPlanNode() throws Exception {
		result.setData("dlt_BRT_OPER_ALLOC_PL_NODE_INFO", operPlanService.selectOperAllocPlanNode());
		return result.getResult();
	}
	
	@RequestMapping("/operPlan/selectOperAllocRealNode")
	public @ResponseBody Map<String, Object> selectOperAllocRealNode() throws Exception {
		result.setData("dlt_BRT_OPER_ALLOC_RL_NODE_INFO", operPlanService.selectOperAllocRealNode());
		return result.getResult();	
	}
	@RequestMapping("/operPlan/selectOperPlanRout2")
	public @ResponseBody Map<String, Object> selectOperPlanRout2() throws Exception {
		result.setData("dlt_BRT_OPER_PL_ROUT_INFO_ASC", operPlanService.selectOperPlanRoutAsc());
		result.setData("dlt_BRT_OPER_PL_ROUT_INFO_DESC", operPlanService.selectOperPlanRoutDesc());
		return result.getResult();
	}
	
	@RequestMapping("/operPlan/selectCourseList")
	public @ResponseBody Map<String, Object> selectCourseList() throws Exception {
		result.setData("dlt_BRT_COR_MST", operPlanService.selectCourseList());
		return result.getResult();
	}
	
	@RequestMapping("/operPlan/selectOperAllocPlanCourseList")
	public @ResponseBody Map<String, Object> selectOperAllocPlanCourseList() throws Exception {
		result.setData("dlt_BRT_OPER_ALLOC_PL_COR_DTL_INFO", operPlanService.selectOperAllocPlanCourseList());
		return result.getResult();
	}
	
	@RequestMapping("/operPlan/selectCourseDtlList")
	public @ResponseBody Map<String, Object> selectCourseDtlList() throws Exception {
		result.setData("dlt_BRT_COR_DTL_INFO", operPlanService.selectCourseDtlList());
		return result.getResult();
	}
	
	@RequestMapping("/operPlan/selectTargetCourseDtlList")
	public @ResponseBody Map<String, Object> dlt_BRT_TARGET_COR_DTL_INFO() throws Exception {
		result.setData("dlt_BRT_TARGET_COR_DTL_INFO", operPlanService.selectTargetCourseDtlList());
		return result.getResult();
	}
}