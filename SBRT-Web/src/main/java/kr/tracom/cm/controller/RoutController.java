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
		result.setData("dlt_routItem", routService.selectRoutItem());
		return result.getResult();
	}

	@RequestMapping("/rout/selectRoutList")
	public @ResponseBody Map<String, Object> selectRoutList() throws Exception {
		result.setData("dlt_BMS_ROUT_MST", routService.selectRoutList());
		return result.getResult();
	}
	
	@RequestMapping("/rout/selectRoutListByRepRout")
	public @ResponseBody Map<String, Object> selectRoutListByRepRout() throws Exception {
		result.setData("dlt_BMS_ROUT_MST", routService.selectRoutListByRepRout());
		return result.getResult();
	}
	
	@RequestMapping("/rout/selectNodeListByRouts")
	public @ResponseBody Map<String, Object> selectNodeListByRouts() throws Exception {
		result.setData("dlt_BMS_ROUT_NODE_CMPSTN", routService.selectNodeListByRouts());
		return result.getResult();
	}
	
	@RequestMapping("/rout/selectNodeListByRout")
	public @ResponseBody Map<String, Object> selectNodeListByRout() throws Exception {
		result.setData("dlt_BMS_ROUT_NODE_CMPSTN", routService.selectNodeListByRout());
		return result.getResult();
	}
	
	@RequestMapping("/rout/selectMainNodeListByRout")
	public @ResponseBody Map<String, Object> selectMainNodeListByRout() throws Exception {
		result.setData("dlt_BRT_MAIN_ROUT_NODE_INFO", routService.selectMainNodeListByRout());
		return result.getResult();
	}
	
	@RequestMapping("/rout/selectNodeListByRepRout")
	public @ResponseBody Map<String, Object> selectNodeListByRepRout() throws Exception {
		result.setData("dlt_BMS_ROUT_NODE_CMPSTN", routService.selectNodeListByRepRout());
		return result.getResult();
	}
	
	@RequestMapping("/rout/selectSttnList")
	public @ResponseBody Map<String, Object> selectSttnList() throws Exception {
		result.setData("dlt_BMS_STTN_MST", routService.selectSttnList());
		return result.getResult();
	}

	@RequestMapping("/rout/selectSttnItem")
	public @ResponseBody Map<String, Object> selectSttnItem() throws Exception {
		result.setData("dlt_sttnItem", routService.selectSttnItem());
		return result.getResult();
	}
	
	@RequestMapping("/rout/selectSttnCrsList")
	public @ResponseBody Map<String, Object> selectSttnCrsList() throws Exception {
		result.setData("dlt_BMS_NODE_MST", routService.selectSttnCrsList());
		return result.getResult();
	}
	
}