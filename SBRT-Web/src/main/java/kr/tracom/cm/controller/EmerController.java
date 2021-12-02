package kr.tracom.cm.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.cm.domain.Emer.EmerService;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class EmerController extends ControllerSupport{
		
	@Autowired
	private EmerService emerService;

	@RequestMapping("/emer/selectEmerItem")
	public @ResponseBody Map<String, Object> selectEmerItem() throws Exception {
		result.setData("dlt_searchitem", emerService.selectEmerItem());
		return result.getResult();
	}	
	
	@RequestMapping("/emer/selectEmerList")
	public @ResponseBody Map<String, Object> selectEmerList() throws Exception {
		result.setData("dlt_BMS_EMER_CLSFCTN_MST", emerService.selectEmerList());
		return result.getResult();
	}
	
	@RequestMapping("/emer/selectTreeEmerList")
	public @ResponseBody Map<String, Object> selectTreeEmerList() throws Exception {
		result.setData("dlt_EMER_CLSFCTN_MST", emerService.selectTreeEmerList());
		return result.getResult();
	}
}
