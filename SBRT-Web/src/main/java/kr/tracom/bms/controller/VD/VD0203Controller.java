package kr.tracom.bms.controller.VD;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.VD0100.VD0100Service;
import kr.tracom.bms.domain.VD0203.VD0203Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class VD0203Controller extends ControllerSupport {

	@Autowired
	private VD0203Service vd0203Service;
	
	@RequestMapping("/vd/VD0203G0R0")
	public @ResponseBody Map<String, Object> VD0203G0R0() throws Exception {
		result.setData("dlt_DVC_INFO", vd0203Service.VD0203G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/vd/VD0203SHI0")
	public @ResponseBody Map<String, Object> VD0203SHI0() throws Exception {
		result.setData("dlt_searchitem", vd0203Service.VD0203SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/vd/VD0203G0S0")
	public @ResponseBody Map<String, Object> VD0203G0S0() throws Exception {
		Map map = vd0203Service.VD0203G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/vd/VD0203G0D0")
	public @ResponseBody Map<String, Object> VD0203G0D0() throws Exception {
		Map map = vd0203Service.VD0203G0D0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}

	@RequestMapping("/vd/VD0203G1R0")
	public @ResponseBody Map<String, Object> VD0203G1R0() throws Exception {
		result.setData("dlt_UPD_RSV_INFO", vd0203Service.VD0203G1R0());
		return result.getResult();
	}
	
	
	
}
