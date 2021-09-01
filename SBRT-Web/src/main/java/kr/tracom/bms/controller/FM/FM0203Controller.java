package kr.tracom.bms.controller.FM;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.FM0203.FM0203Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class FM0203Controller extends ControllerSupport {

	@Autowired
	private FM0203Service fm0203Service;
	
	@RequestMapping("/fm/FM0203G0R0")
	public @ResponseBody Map<String, Object> FM0203G0R0() throws Exception {
		result.setData("dlt_BMS_FCLT_INFO", fm0203Service.FM0203G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/fm/FM0203G1R0")
	public @ResponseBody Map<String, Object> FM0203G1R0() throws Exception {
		result.setData("dlt_BMS_FCLT_UPD_LOG", fm0203Service.FM0203G1R0());
		return result.getResult();
	}
	
	 
	@RequestMapping("/fm/FM0203G0S0")
	public @ResponseBody Map<String, Object> FM0203G0S0() throws Exception {
		Map map = fm0203Service.FM0203G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/fm/FM0203G0D0")
	public @ResponseBody Map<String, Object> FM0203G0D0() throws Exception {
		Map map = fm0203Service.FM0203G0D0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/fm/FM0203SHI0")
	public @ResponseBody Map<String, Object> FM0203SHI0() throws Exception {
		result.setData("dlt_searchitem", fm0203Service.FM0203SHI0());
		return result.getResult();
	}

	
	
	
	
}
