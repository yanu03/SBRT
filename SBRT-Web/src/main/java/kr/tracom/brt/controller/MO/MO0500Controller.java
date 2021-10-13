package kr.tracom.brt.controller.MO;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.MO0500.MO0500Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class MO0500Controller extends ControllerSupport {
	
	@Autowired
	private MO0500Service mo0500Service;
	
	@RequestMapping("/mo/MO0500G0R0")
	public @ResponseBody Map<String, Object> MO0500G0R0() throws Exception {
		result.setData("dlt_BRT_OPER_APPR_INFO", mo0500Service.MO0500G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0500SHI0")
	public @ResponseBody Map<String, Object> MO0500SHI0() throws Exception {
		result.setData("dlt_grgSearchItem", mo0500Service.MO0500SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0500G0S0")
	public @ResponseBody Map<String, Object> MO0500G0S0() throws Exception{
		Map map = mo0500Service.MO0500G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	/*
	@RequestMapping("/mo/MO0500G0K0")
	public @ResponseBody Map<String, Object> MO0500G0K0() throws Exception{
		result.setData("dma_SEQ_BRT_CPLNT_HIS_0", mo0500Service.MO0500G0K0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0500P0R0")
	public @ResponseBody Map<String, Object> MO0500P0R0() throws Exception {
		result.setData("dlt_BRT_CPLNT_HIS", mo0500Service.MO0500P0R0());
		return result.getResult();
	}
	
	@RequestMapping(value = "/mo/MO0500P0S0")
	public @ResponseBody Map<String, Object> MO0500P0S0() throws Exception {
		result.setData("dma_result", mo0500Service.MO0500P0S0());

		return result.getResultSave();
	}*/
	
}
