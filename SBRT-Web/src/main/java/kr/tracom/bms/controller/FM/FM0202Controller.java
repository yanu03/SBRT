package kr.tracom.bms.controller.FM;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.FM0202.FM0202Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class FM0202Controller extends ControllerSupport {

	@Autowired
	private FM0202Service fm0202Service;
	
	@RequestMapping("/fm/FM0202G0R0")
	public @ResponseBody Map<String, Object> fm0202G0R0() throws Exception {
		result.setData("dlt_BMS_NODE_MST", fm0202Service.fm0202G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/fm/FM0202G1R0")
	public @ResponseBody Map<String, Object> fm0202G1R0() throws Exception {
		result.setData("dlt_BMS_FCLT_INFO", fm0202Service.fm0202G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/fm/FM0202SHI0")
	public @ResponseBody Map<String, Object> fm0202SHI0() throws Exception {
		result.setData("dlt_searchitem", fm0202Service.fm0202SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/fm/FM0202SHI1")
	public @ResponseBody Map<String, Object> fm0202SHI1() throws Exception {
		result.setData("dlt_searchitem2", fm0202Service.fm0202SHI1());
		return result.getResult();
	}
	
	@RequestMapping("/fm/FM0202SHI2")
	public @ResponseBody Map<String, Object> fm0202SHI2() throws Exception {
		result.setData("dlt_searchitem3", fm0202Service.fm0202SHI2());
		return result.getResult();
	}
	
	@RequestMapping("/fm/FM0202SHI3")
	public @ResponseBody Map<String, Object> fm0202SHI3() throws Exception {
		result.setData("dlt_searchitem4", fm0202Service.fm0202SHI3());
		return result.getResult();
	}	
	
}
