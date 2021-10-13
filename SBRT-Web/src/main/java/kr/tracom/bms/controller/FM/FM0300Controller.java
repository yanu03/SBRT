package kr.tracom.bms.controller.FM;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.FM0300.FM0300Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class FM0300Controller extends ControllerSupport {

	@Autowired
	private FM0300Service fm0300Service;
	
	@RequestMapping("/fm/FM0300G0R0")
	public @ResponseBody Map<String, Object> FM0300G0R0() throws Exception {
		result.setData("dlt_BMS_STTN_MST", fm0300Service.FM0300G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/fm/FM0300SHI0")
	public @ResponseBody Map<String, Object> FM0300SHI0() throws Exception {
		result.setData("dlt_searchitem", fm0300Service.FM0300SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/fm/FM0300SHI1")
	public @ResponseBody Map<String, Object> FM0300SHI1() throws Exception {
		result.setData("dlt_searchitem2", fm0300Service.FM0300SHI1());
		return result.getResult();
	}
	
	@RequestMapping("/fm/FM0300SHI2")
	public @ResponseBody Map<String, Object> FM0300SHI2() throws Exception {
		result.setData("dlt_searchitem3", fm0300Service.FM0300SHI2());
		return result.getResult();
	}
	
	@RequestMapping("/fm/FM0300G1R0")
	public @ResponseBody Map<String, Object> FM0300G1R0() throws Exception {
		result.setData("dlt_BMS_FCLT_INFO", fm0300Service.FM0300G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/fm/FM0300G2R0")
	public @ResponseBody Map<String, Object> FM0300G2R0() throws Exception {
		result.setData("dlt_BMS_FCLT_HIS", fm0300Service.FM0300G2R0());
		return result.getResult();
	}
	
	@RequestMapping("/fm/FM0300G3R0")
	public @ResponseBody Map<String, Object> FM0300G3R0() throws Exception {
		result.setData("dlt_BMS_FCLT_COND_LOG", fm0300Service.FM0300G3R0());
		return result.getResult();
	}
	
	@RequestMapping("/fm/FM0300G2S0")
	public @ResponseBody Map<String, Object> FM0300G2S0() throws Exception {
		Map map = fm0300Service.FM0300G2S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
		
	
	
}
