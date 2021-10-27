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

import kr.tracom.cm.domain.Drv.DrvService;
import kr.tracom.cm.domain.Rout.RoutService;
import kr.tracom.cm.domain.Vhc.VhcService;
import kr.tracom.cm.support.ControllerSupport;
import kr.tracom.util.Result;
import kr.tracom.util.UserInfo;

@Controller
@Scope("request")
public class DrvController extends ControllerSupport {

	@Autowired
	private DrvService drvService;
	
	@RequestMapping("/drv/selectDrvList")
	public @ResponseBody Map<String, Object> selectDrvList() throws Exception {
		result.setData("dlt_BMS_DRV_MST", drvService.selectDrvList());
		return result.getResult();
	}

}