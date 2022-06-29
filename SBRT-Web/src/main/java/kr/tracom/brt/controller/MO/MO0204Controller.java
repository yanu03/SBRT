package kr.tracom.brt.controller.MO;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.MO0204.MO0204Service;
import kr.tracom.cm.support.ControllerSupport;


@Controller
@Scope("request")
public class MO0204Controller extends ControllerSupport {

	@Autowired
	private MO0204Service MO0204Service;

	@RequestMapping("/mo/MO0204G0R0")
	public @ResponseBody Map<String, Object> MO0204G0R0() throws Exception {
		result.setData("dlt_BRT_CUR_OPER_INFO", MO0204Service.MO0204G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0204G1R0")
	public @ResponseBody Map<String, Object> MO0204G1R0() throws Exception {
		result.setData("dlt_BMS_DVC_INFO", MO0204Service.MO0204G1R0());
		result.setData("dlt_BMS_DVC_INFO2", MO0204Service.MO0204G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0204G1R1")
	public @ResponseBody Map<String, Object> MO0204G1R1() throws Exception {
		result.setData("dlt_PARAM_KIND", MO0204Service.MO0204G1R1());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0204SHI1")
	public @ResponseBody Map<String, Object> MO0204SHI0() throws Exception {
		result.setData("dlt_searchitem2", MO0204Service.MO0204SHI1());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0204G2R0")
	public @ResponseBody Map<String, Object> MO0204G2R0() throws Exception {
		result.setData("dlt_BMS_ROUT_LINK_CMPSTN", MO0204Service.MO0204G2R0());
		return result.getResult();
	}
	
}
