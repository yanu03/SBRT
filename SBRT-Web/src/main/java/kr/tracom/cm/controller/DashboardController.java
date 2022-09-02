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
import kr.tracom.cm.domain.Dashboard.DashboardService;
import kr.tracom.cm.support.ControllerSupport;
import kr.tracom.util.Result;
import kr.tracom.util.UserInfo;

@Controller
@Scope("request")
public class DashboardController extends ControllerSupport {

	@Autowired
	private DashboardService dashboardService;
	
	@RequestMapping("/dashboard/selectDashboardList")
	public @ResponseBody Map<String, Object> selectDashboardList() throws Exception {
		result.setData("dlt_BMS_VHC_MST", dashboardService.selectDashboardList());
		return result.getResult();
	}
	
	@RequestMapping("/dashboard/selectAllocDashboardList")
	public @ResponseBody Map<String, Object> selectAllocDashboardList() throws Exception {
		result.setData("dlt_BMS_VHC_MST", dashboardService.selectAllocDashboardList());
		return result.getResult();
	}
	
	@RequestMapping("/dashboard/selectCurOperDashboardList")
	public @ResponseBody Map<String, Object> selectCurOperDashboardList() throws Exception {
		result.setData("dlt_BRT_CUR_OPER_INFO", dashboardService.selectCurOperDashboardList());
		return result.getResult();
	}
	
	@RequestMapping("/dashboard/selectDashboardItem")
	public @ResponseBody Map<String, Object> selectDashboardItem() throws Exception {
		result.setData("dlt_dashboardItem", dashboardService.selectDashboardItem());
		return result.getResult();
	}
	
	@RequestMapping("/dashboard/selectB0Bit")
	public @ResponseBody Map<String, Object> selectB0Bit() throws Exception {		
		
		result.setData("", dashboardService.selectB0Bit());
		return result.getResult();
	}		
	
	//B0제외한 BIT정보
	@RequestMapping("/dashboard/selectDashboardBit")
	public @ResponseBody Map<String, Object> selectDashboardBit() throws Exception {
		result.setData("dlt_dashboardBit", dashboardService.selectDashboardBit());
		return result.getResult();
	}
	
	
	
}