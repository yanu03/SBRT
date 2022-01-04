package kr.tracom.brt.controller.MO;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.MO0303.MO0303Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class MO0303Controller extends ControllerSupport{

	@Autowired
	private MO0303Service mo0303Service;
	
	@RequestMapping("/mo/MO0303G0R0")
	public @ResponseBody Map<String, Object> MO0303G0R0() throws Exception {
		result.setData("dlt_BRT_INCDNT_INFO", mo0303Service.MO0303G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0303G0K0")
	public @ResponseBody Map<String, Object> MO0303G0K0() throws Exception {
		result.setData("dma_SEQ_BRT_INCDNT_INFO_0", mo0303Service.MO0303G0K0());
		return result.getResult();
	}	
	
	@RequestMapping("/mo/MO0303SHI0")
	public @ResponseBody Map<String, Object> MO0303G0R2() throws Exception {
		result.setData("dlt_searchitem", mo0303Service.MO0303SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0303G1R0")
	public @ResponseBody Map<String, Object> MO0303G1R0() throws Exception {
		result.setData("dlt_BRT_INCDNT_RES_INFO", mo0303Service.MO0303G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0303G1K0")
	public @ResponseBody Map<String, Object> MO0303G1K0() throws Exception {
		result.setData("dma_SEQ_BRT_INCDNT_RES_INFO_0", mo0303Service.MO0303G1K0());
		return result.getResult();
	}	
	
	@RequestMapping("/mo/MO0303G0S0")
	public @ResponseBody Map<String, Object> MO0303G0S0() throws Exception {
		Map map = mo0303Service.MO0303G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/mo/MO0303G1S0")
	public @ResponseBody Map<String, Object> MO0303G2S0() throws Exception {
		Map map = mo0303Service.MO0303G1S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/mo/MO0303P2R0")
	public @ResponseBody Map<String, Object> MO0303P2R0() throws Exception {
		result.setData("dlt_BRT_INCDNT_SCNRO_MST", mo0303Service.MO0303P2R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0303SHI1")
	public @ResponseBody Map<String, Object> MO0303SHI1() throws Exception {
		result.setData("dlt_searchitem", mo0303Service.MO0303SHI1());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0303P3R1")
	public @ResponseBody Map<String, Object> MO0303P3R1() throws Exception {
		result.setData("dlt_BRT_CUR_OPER_INFO", mo0303Service.MO0303P3R1());
		result.setData("dlt_SEND_MESSAGE_LIST", mo0303Service.MO0303P3R1());
		return result.getResult();
	}
	
	@RequestMapping("/mo/allocVhcList")
	public @ResponseBody Map<String, Object> allocVhcList() throws Exception {
		result.setData("dlt_BRT_CUR_ALLOC_PL_INFO", mo0303Service.allocVhcList());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0303SCK0")
	public @ResponseBody Map<String, Object> MO0303SCK0() throws Exception {		
		result.setData("", mo0303Service.MO0303SCK0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0303SCK1")
	public @ResponseBody Map<String, Object> MO0303SCK1() throws Exception {		
		result.setData("", mo0303Service.MO0303SCK1());
		return result.getResult();
	}	
	
	@RequestMapping("/mo/sttnList")
	public @ResponseBody Map<String, Object> sttnList() throws Exception {		
		result.setData("dlt_BMS_STTN_MST", mo0303Service.sttnList());
		return result.getResult();
	}	
}
