package kr.tracom.brt.controller.MO;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.FM0201.FM0201Service;
import kr.tracom.brt.domain.MO0203.MO0203Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class MO0203Controller extends ControllerSupport {

	@Autowired
	private MO0203Service mo0203Service;
	
	@RequestMapping("/mo/MO0203G0R0")
	public @ResponseBody Map<String, Object> MO0203G0R0() throws Exception {
		result.setData("dlt_BMS_NODE_MST", mo0203Service.MO0203G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0203SHI0")
	public @ResponseBody Map<String, Object> MO0203SHI0() throws Exception {
		result.setData("dlt_searchitem", mo0203Service.MO0203SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0203SHI1")
	public @ResponseBody Map<String, Object> MO0203SHI1() throws Exception {
		result.setData("dlt_searchitem2", mo0203Service.MO0203SHI1());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0203SHI2")
	public @ResponseBody Map<String, Object> MO0203SHI2() throws Exception {
		result.setData("dlt_searchitem3", mo0203Service.MO0203SHI2());
		return result.getResult();
	}	
	
	@RequestMapping("/mo/MO0203G2R0")
	public @ResponseBody Map<String, Object> MO0203G2R0() throws Exception {
		result.setData("dlt_BMS_ROUT_LINK_CMPSTN", mo0203Service.MO0203G2R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0203SCK")
	public @ResponseBody Map<String, Object> MO0203SCK() throws Exception {		
		
		result.setData("dlt_BMS_ETA_INFO", mo0203Service.MO0203SCK());
		return result.getResult();
	}
	
}
