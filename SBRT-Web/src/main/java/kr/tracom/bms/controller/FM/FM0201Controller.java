package kr.tracom.bms.controller.FM;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.FM0201.FM0201Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class FM0201Controller extends ControllerSupport {

	@Autowired
	private FM0201Service fm0201Service;

	@RequestMapping("/fm/FM0201G0R0")
	public @ResponseBody Map<String, Object> FM0201G0R0() throws Exception {
		result.setData("dlt_BMS_NODE_MST", fm0201Service.FM0201G0R0());
		return result.getResult();
	}
	
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
	}	
	
}
