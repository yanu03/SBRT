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
import kr.tracom.cm.domain.Vhc.VhcService;
import kr.tracom.cm.support.ControllerSupport;
import kr.tracom.util.Result;
import kr.tracom.util.UserInfo;

@Controller
@Scope("request")
public class VhcController extends ControllerSupport {

	@Autowired
	private VhcService vhcService;
	
	@RequestMapping("/vhc/selectVhcList")
	public @ResponseBody Map<String, Object> selectVhcList() throws Exception {
		result.setData("dlt_BMS_VHC_MST", vhcService.selectVhcList());
		return result.getResult();
	}
	
	@RequestMapping("/vhc/selectAllocVhcList")
	public @ResponseBody Map<String, Object> selectAllocVhcList() throws Exception {
		result.setData("dlt_BMS_VHC_MST", vhcService.selectAllocVhcList());
		return result.getResult();
	}
	
	@RequestMapping("/vhc/selectCurOperVhcList")
	public @ResponseBody Map<String, Object> selectCurOperVhcList() throws Exception {
		result.setData("dlt_BMS_VHC_MST", vhcService.selectCurOperVhcList());
		return result.getResult();
	}
	
	@RequestMapping("/vhc/selectVhcItem")
	public @ResponseBody Map<String, Object> selectVhcItem() throws Exception {
		result.setData("dlt_vhcItem", vhcService.selectVhcItem());
		return result.getResult();
	}
	
	@RequestMapping("/vhc/selectVhcBit")
	public @ResponseBody Map<String, Object> selectVhcBit() throws Exception {
		result.setData("dlt_vhcBit", vhcService.selectVhcBit());
		return result.getResult();
	}
}