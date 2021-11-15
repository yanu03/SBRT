package kr.tracom.bms.controller.FM;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.FM0100.FM0100Service;
import kr.tracom.bms.domain.VD0100.VD0100Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class FM0100Controller extends ControllerSupport {

	@Autowired
	private FM0100Service fm0100Service;
	
	@RequestMapping("/fm/FM0100G0R0")
	public @ResponseBody Map<String, Object> FM0100G0R0() throws Exception {
		result.setData("dlt_BMS_NODE_MST", fm0100Service.FM0100G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/fm/FM0100G1R0")
	public @ResponseBody Map<String, Object> FM0100G1R0() throws Exception {
		result.setData("dlt_BMS_FCLT_INFO", fm0100Service.FM0100G1R0());
		return result.getResult();
	}
	
	
	@RequestMapping("/fm/FM0100SHI1")
	public @ResponseBody Map<String, Object> FM0100SHI1() throws Exception {
		result.setData("dlt_searchitem2", fm0100Service.FM0100SHI1());
		return result.getResult();
	}
	
	@RequestMapping("/fm/FM0100SHI2")
	public @ResponseBody Map<String, Object> FM0100SHI2() throws Exception {
		result.setData("dlt_searchitem3", fm0100Service.FM0100SHI2());
		return result.getResult();
	}
	
	@RequestMapping("/fm/FM0100G1K0")
	public @ResponseBody Map<String, Object> FM0100G1K0() throws Exception {
		result.setData("dma_SEQ_BMS_FCLT_INFO_0", fm0100Service.FM0100G1K0());
		return result.getResult();
	}
	
	@RequestMapping("/fm/FM0100G1S0")
	public @ResponseBody Map<String, Object> FM0100G1S0() throws Exception {
		Map map = fm0100Service.FM0100G1S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}	
	
}
