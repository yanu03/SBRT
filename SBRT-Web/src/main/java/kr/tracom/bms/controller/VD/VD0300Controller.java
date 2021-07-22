package kr.tracom.bms.controller.VD;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.VD0300.VD0300Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class VD0300Controller extends ControllerSupport {

	@Autowired
	private VD0300Service vd0300Service;
	
	@RequestMapping("/vd/VD0300G0R0")
	public @ResponseBody Map<String, Object> VD0300G0R0() throws Exception {
		result.setData("dlt_BMS_VHC_MST", vd0300Service.VD0300G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/vd/VD0300SHI0")
	public @ResponseBody Map<String, Object> VD0300SHI0() throws Exception {
		result.setData("dlt_searchitem", vd0300Service.VD0300SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/vd/VD0300SHI1")
	public @ResponseBody Map<String, Object> VD0300SHI1() throws Exception {
		result.setData("dlt_searchitem2", vd0300Service.VD0300SHI1());
		return result.getResult();
	}
	
	@RequestMapping("/vd/VD0300G1R0")
	public @ResponseBody Map<String, Object> VD0300G1R0() throws Exception {
		result.setData("dlt_BMS_DVC_INFO", vd0300Service.VD0300G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/vd/VD0300G2R0")
	public @ResponseBody Map<String, Object> VD0300G2R0() throws Exception {
		result.setData("dlt_BMS_DVC_HIS", vd0300Service.VD0300G2R0());
		return result.getResult();
	}
	
	@RequestMapping("/vd/VD0300G2S0")
	public @ResponseBody Map<String, Object> VD0300G2S0() throws Exception {
		Map map = vd0300Service.VD0300G2S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/vd/VD0300G3R0")
	public @ResponseBody Map<String, Object> VD0300G3R0() throws Exception {
		result.setData("dlt_BMS_DVC_COND_LOG", vd0300Service.VD0300G3R0());
		return result.getResult();
	}
	
}
