package kr.tracom.brt.controller.VH;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.VH0100.VH0100Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class VH0100Controller extends ControllerSupport {

	@Autowired
	private VH0100Service vh0100Service;

	@RequestMapping("/vh/VH0100G0R0")
	public @ResponseBody Map<String, Object> VH0100G0R0() throws Exception {
		result.setData("dlt_BRT_DSPTCH_LOG", vh0100Service.VH0100G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/vh/VH0100SHI0")
	public @ResponseBody Map<String, Object> VH0100SHI0() throws Exception {
		result.setData("dlt_searchitem", vh0100Service.VH0100SHI0());
		return result.getResult();
	}
	
	/*
	@RequestMapping("/fm/FM0201G1R0")
	public @ResponseBody Map<String, Object> FM0201G1R0() throws Exception {
		result.setData("dlt_BMS_FCLT_INFO", fm0201Service.FM0201G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/fm/FM0201SHI0")
	public @ResponseBody Map<String, Object> FM0201SHI0() throws Exception {
		result.setData("dlt_searchitem", fm0201Service.FM0201SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/fm/FM0201SHI1")
	public @ResponseBody Map<String, Object> FM0201SHI1() throws Exception {
		result.setData("dlt_searchitem2", fm0201Service.FM0201SHI1());
		return result.getResult();
	}
	
	@RequestMapping("/fm/FM0201SHI2")
	public @ResponseBody Map<String, Object> FM0201SHI2() throws Exception {
		result.setData("dlt_searchitem3", fm0201Service.FM0201SHI2());
		return result.getResult();
	}
	
	@RequestMapping("/fm/FM0201SHI3")
	public @ResponseBody Map<String, Object> FM0201SHI3() throws Exception {
		result.setData("dlt_searchitem4", fm0201Service.FM0201SHI3());
		return result.getResult();
	}	*/
	
}
