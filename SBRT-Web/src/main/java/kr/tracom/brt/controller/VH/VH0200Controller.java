package kr.tracom.brt.controller.VH;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.AL0103.AL0103Service;
import kr.tracom.brt.domain.AL0203.AL0203Service;
import kr.tracom.brt.domain.VH0200.VH0200Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class VH0200Controller extends ControllerSupport{
	
	@Autowired
	private VH0200Service vh0200Service;
	
	@RequestMapping("/vh/VH0200G0R0")
	public @ResponseBody Map<String, Object> VH0200G0R0() throws Exception {
		result.setData("dlt_BRT_CUR_CHG_OPER_INFO_HIS", vh0200Service.VH0200G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/vh/VH0200G1R0")
	public @ResponseBody Map<String, Object> VH0200G1R0() throws Exception {
		result.setData("dlt_BRT_CUR_CHG_OPER_DTL_INFO_HIS", vh0200Service.VH0200G1R0());
		result.setData("dlt_BRT_CUR_OPER_ALLOC_PL_NODE_INFO_HIS_CNT", vh0200Service.VH0200G1CNT());
		return result.getResult();
	}
	
	@RequestMapping("/vh/VH0200G2R0")
	public @ResponseBody Map<String, Object> VH0200G2R0() throws Exception {
		result.setData("dlt_BRT_CUR_CHG_OPER_DTL_INFO_HIS", vh0200Service.VH0200G2R0());
		return result.getResult();
	}
	/*
	@RequestMapping("/al/AL0203SHI0")
	public @ResponseBody Map<String, Object> AL0203SHI0() throws Exception {
		result.setData("dlt_searchitem", al0203Service.AL0203SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/al/AL0203G1R0")
	public @ResponseBody Map<String, Object> AL0203G1R0() throws Exception {
		result.setData("dlt_BRT_OPER_ALLOC_PL_NODE_INFO", al0203Service.AL0203G1R0());
		result.setData("dlt_BRT_OPER_ALLOC_PL_NODE_CNT", al0203Service.AL0203G1CNT());
		return result.getResult();
	}
	
	@RequestMapping("/al/AL0203G0P1")
	public @ResponseBody Map<String, Object> AL0203G0P1() throws Exception {
		result.setData("dlt_BRT_OPER_ALLOC_PL_NODE_CNT", al0203Service.AL0203G1CNT());
		return result.getResult();
	}*/
	/*
	@RequestMapping("/al/AL0103G0S0")
	public @ResponseBody Map<String, Object> AL0103G0S0() throws Exception {
		Map map = al0103Service.AL0103G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}	
	
	@RequestMapping("/al/AL0103G1S0")
	public @ResponseBody Map<String, Object> SI0401G1S0() throws Exception {
		Map map = al0103Service.AL0103G1S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}	
	
	@RequestMapping("/si/AL0103P0R0")
	public @ResponseBody Map<String, Object> AL0103P0R0() throws Exception {
		result.setData("dlt_BMS_ROUT_MST", al0103Service.AL0103P0R0());
		return result.getResult();
	}
	
	@RequestMapping("/al/AL0103P01R0")
	public @ResponseBody Map<String, Object> AL0103P01R0() throws Exception {
		result.setData("dlt_BMS_ROUT_MST", al0103Service.AL0103P01R0());
		return result.getResult();
	}
	
	@RequestMapping("/al/AL0103P1SH")
	public @ResponseBody Map<String, Object> AL0103P1SH() throws Exception {
		result.setData("dlt_searchitem", al0103Service.AL0103P1SH());
		return result.getResult();
	}
*/
	
}
