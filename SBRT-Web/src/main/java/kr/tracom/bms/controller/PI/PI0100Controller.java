package kr.tracom.bms.controller.PI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.PI0100.PI0100Service;
import kr.tracom.cm.support.ControllerSupport;


@Controller
@Scope("request")
public class PI0100Controller extends ControllerSupport {

	@Autowired
	private PI0100Service pI0100Service;

	@RequestMapping("/pi/PI0100G0R0")
	public @ResponseBody Map<String, Object> PI0100G0R0() throws Exception {
		result.setData("dlt_BMS_NOTICE_MST", pI0100Service.PI0100G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0100G1R0")
	public @ResponseBody Map<String, Object> PI0100G1R0() throws Exception {
		result.setData("dlt_BMS_VHC_MST", pI0100Service.PI0100G1R0());
		return result.getResult();
	}
	@RequestMapping("/pi/PI0100G2R0")
	public @ResponseBody Map<String, Object> PI0100G2R0() throws Exception {
		result.setData("dlt_BMS_STTN_MST", pI0100Service.PI0100G2R0());
		return result.getResult();
	}	
	
	@RequestMapping("/pi/PI0100G0S0")
	public @ResponseBody Map<String, Object> PI0100G0S0() throws Exception{
		result.setData("result", pI0100Service.PI0100G0S0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0100P0R0")
	public @ResponseBody Map<String, Object> PI0100P0R0() throws Exception {
		result.setData("dlt_BMS_VHC_MST", pI0100Service.PI0100P0R0());
		return result.getResult();
	}	
	
	@RequestMapping("/pi/PI0100P1R0")
	public @ResponseBody Map<String, Object> PI0100P1R0() throws Exception {
		result.setData("dlt_BMS_STTN_MST", pI0100Service.PI0100P1R0());
		return result.getResult();
	}		
}
