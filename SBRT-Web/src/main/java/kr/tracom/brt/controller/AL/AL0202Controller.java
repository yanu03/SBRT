package kr.tracom.brt.controller.AL;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.AL0301.AL0301Service;
import kr.tracom.brt.domain.AL0202.AL0202Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class AL0202Controller extends ControllerSupport {

	@Autowired
	private AL0202Service al0202Service;
	
	@RequestMapping("/AL/AL0202G0R0")
	public @ResponseBody Map<String, Object> AL0202G0R0() throws Exception {
		result.setData("dlt_BRT_OPER_PL_MST", al0202Service.AL0202G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/AL/AL0202G1R0")
	public @ResponseBody Map<String, Object> AL0301G1R0() throws Exception {
		result.setData("dlt_OPER_ALLOC_PL_ROUT_INFO", al0202Service.AL0202G1R0());
		result.setData("dlt_OPER_ALLOC_PL_ROUT_CNT", al0202Service.AL0202G1CNT());
		return result.getResult();
	}
	
	@RequestMapping("/AL/AL0202P0R0")
	public @ResponseBody Map<String, Object> AL0202P0R0() throws Exception {
		result.setData("dlt_COR_MST", al0202Service.AL0202P0R0());
		return result.getResult();
	}
	
	@RequestMapping("/AL/AL0202P0R1")
	public @ResponseBody Map<String, Object> AL0202P0R1() throws Exception {
		result.setData("dlt_OPER_PL_ROUT_INFO", al0202Service.AL0202P0R1());
		return result.getResult();
	}
	
	@RequestMapping("/AL/selectCorCnt")
	public @ResponseBody Map<String, Object> selectCorCnt() throws Exception {
		result.setData("dlt_COR_CNT", al0202Service.selectCorCnt());
		return result.getResult();
	}
	
	
	@RequestMapping("/al/AL0202G1S0")
	public @ResponseBody Map<String, Object> AL0202G1S0() throws Exception {
		Map map = al0202Service.AL0202G1S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}	
	
	/*@RequestMapping("/AL/AL0202G1CNT")
	public @ResponseBody Map<String, Object> AL0202G1CNT() throws Exception {
		result.setData("dlt_OPER_ALLOC_PL_ROUT_CNT", al0202Service.AL0202G1CNT());
		return result.getResult();
	}*/
	
}
