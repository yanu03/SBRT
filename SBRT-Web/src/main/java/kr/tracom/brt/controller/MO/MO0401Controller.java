package kr.tracom.brt.controller.MO;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.PI0201.PI0201Service;
import kr.tracom.bms.domain.PI0205.PI0205Service;
import kr.tracom.brt.domain.AL0102.AL0102Service;
import kr.tracom.brt.domain.MO0301.MO0301Service;
import kr.tracom.brt.domain.MO0401.MO0401Service;
import kr.tracom.cm.support.ControllerSupport;


@Controller
@Scope("request")
public class MO0401Controller extends ControllerSupport {
	
	@Autowired
	private MO0401Service MO0401Service;
	
	@RequestMapping("/mo/MO0401G0R0")
	public @ResponseBody Map<String, Object> MO0401G0R0() throws Exception {
		result.setData("dlt_BRT_OPER_INST_MSG_MST", MO0401Service.MO0401G0R0());
		return result.getResult();
	}	
	
	@RequestMapping("/mo/MO0401SHI0")
	public @ResponseBody Map<String, Object> MO0401SHI0() throws Exception {
		result.setData("dlt_searchitem", MO0401Service.MO0401SHI0());
		return result.getResult();
	}	
	
	@RequestMapping("/mo/MO0401G0S0")
	public @ResponseBody Map<String, Object> MO0401G0S0() throws Exception{
		Map map = MO0401Service.MO0401G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/mo/MO0401G0K0")
	public @ResponseBody Map<String, Object> MO0401G0K0() throws Exception{
		result.setData("dma_SEQ_BRT_OPER_INST_MSG_MST_0", MO0401Service.MO0401G0K0());
		return result.getResult();
	}
	
	/*
	@RequestMapping("/pi/PI0205G0R0")
	public @ResponseBody Map<String, Object> PI0205G0R0() throws Exception {
		result.setData("dlt_BMS_VOC_INFO", PI0205Service.PI0205G0R0());
		return result.getResult();
	}	
	
	
	
	*/
}
