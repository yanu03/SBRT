package kr.tracom.cm.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.cm.domain.RepRout.RepRoutService;
import kr.tracom.cm.support.ControllerSupport;


@Controller
@Scope("request")
public class RepRoutController extends ControllerSupport {

	@Autowired
	private RepRoutService reproutService;

	@RequestMapping("/repRout/selectRepRoutItem")
	public @ResponseBody Map<String, Object> selectRepRoutItem() throws Exception {
		result.setData("dlt_repRoutItem", reproutService.selectRepRoutItem());
		return result.getResult();
	}

	@RequestMapping("/repRout/selectRepRoutList")
	public @ResponseBody Map<String, Object> selectRepRoutList() throws Exception {
		result.setData("dlt_BMS_REP_ROUT_MST", reproutService.selectRepRoutList());
		return result.getResult();
	}
	
	@RequestMapping("/repRout/selectRepRoutListByNode")
	public @ResponseBody Map<String, Object> selectRepRoutListByNode() throws Exception {
		result.setData("dlt_BMS_REP_ROUT_MST", reproutService.selectRepRoutListByNode());
		return result.getResult();
	}
	
}
