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
		return result.getResult();
	}
	
	@RequestMapping("/vh/VH0200G1CNT")
	public @ResponseBody Map<String, Object> VH0200G1CNT() throws Exception {
		result.setData("dlt_BRT_CUR_OPER_ALLOC_PL_NODE_INFO_HIS_CNT", vh0200Service.VH0200G1CNT());
		return result.getResult();
	}
	
	@RequestMapping("/vh/VH0200G2R0")
	public @ResponseBody Map<String, Object> VH0200G2R0() throws Exception {
		result.setData("dlt_BRT_CUR_CHG_OPER_DTL_INFO_HIS", vh0200Service.VH0200G2R0());
		return result.getResult();
	}
	
}
