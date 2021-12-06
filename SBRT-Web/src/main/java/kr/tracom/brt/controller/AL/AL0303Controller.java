package kr.tracom.brt.controller.AL;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.PI0201.PI0201Service;
import kr.tracom.bms.domain.PI0205.PI0205Service;
import kr.tracom.brt.domain.AL0102.AL0102Service;
import kr.tracom.brt.domain.AL0303.AL0303Service;
import kr.tracom.cm.support.ControllerSupport;


@Controller
@Scope("request")
public class AL0303Controller extends ControllerSupport {

	@Autowired 
	private AL0303Service AL0303Service;
	
	@RequestMapping("/al/AL0303G0R0")
	public @ResponseBody Map<String, Object> AL0303G0R0() throws Exception {
		result.setData("dlt_BRT_RPC_VHC_INFO", AL0303Service.AL0303G0R0());
		return result.getResult();
	}	
	
	@RequestMapping("/al/AL0303SHI0")
	public @ResponseBody Map<String, Object> AL0303SHI0() throws Exception {
		result.setData("dlt_searchitem", AL0303Service.AL0303SHI0());
		return result.getResult();
	}	
	
	@RequestMapping("/al/AL0303SHI1")
	public @ResponseBody Map<String, Object> AL0303SHI1() throws Exception {
		result.setData("dlt_subSearch", AL0303Service.AL0303SHI1());
		return result.getResult();
	}
	
	@RequestMapping("/al/AL0303G0S0")
	public @ResponseBody Map<String, Object> AL0303G0S0() throws Exception{
		Map map = AL0303Service.AL0303G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/al/AL0303P1R0")
	public @ResponseBody Map<String, Object> AL0303P1R0() throws Exception {
		result.setData("dlt_VHC_INFO", AL0303Service.AL0303P1R0());
		return result.getResult();
	}
	
	@RequestMapping("/al/AL0303P2R0")
	public @ResponseBody Map<String, Object> AL0303P2R0() throws Exception {
		result.setData("dlt_BMS_DRV_MST", AL0303Service.AL0303P2R0());
		return result.getResult();
	}
	
	@RequestMapping("/al/AL0303P3R0")
	public @ResponseBody Map<String, Object> AL0303P3R0() throws Exception {
		result.setData("dlt_VHC_INFO", AL0303Service.AL0303P3R0());
		return result.getResult();
	}
	
	@RequestMapping("/al/AL0303P4R0")
	public @ResponseBody Map<String, Object> AL0303P4R0() throws Exception {
		result.setData("dlt_BRT_INCDNT_INFO", AL0303Service.AL0303P4R0());
		return result.getResult();
	}
	
	@RequestMapping("/al/AL0303G0K0")
	public @ResponseBody Map<String, Object> AL0303G0K0() throws Exception {
		result.setData("dma_SEQ_BRT_RPC_VHC_INFO_0", AL0303Service.AL0303G0K0());
		return result.getResult();
	}
	
	/*
	@RequestMapping("/pi/PI0205G0R0")
	public @ResponseBody Map<String, Object> PI0205G0R0() throws Exception {
		result.setData("dlt_BMS_VOC_INFO", PI0205Service.PI0205G0R0());
		return result.getResult();
	}	
	
	@RequestMapping("/pi/PI0205G0K0")
	public @ResponseBody Map<String, Object> PI0205G0K0() throws Exception{
		result.setData("dma_SEQ_BMS_ROUT_MST_0", PI0205Service.PI0205G0K0());
		return result.getResult();
	}
	
	*/
}
