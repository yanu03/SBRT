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
import kr.tracom.cm.support.ControllerSupport;


@Controller
@Scope("request")
public class MO0301Controller extends ControllerSupport {
	
	@Autowired
	private MO0301Service MO0301Service;
	
	@RequestMapping("/mo/MO0301G0R0")
	public @ResponseBody Map<String, Object> MO0301G0R0() throws Exception {
		result.setData("dlt_BRT_INCDNT_SCNRO_MST", MO0301Service.MO0301G0R0());
		return result.getResult();
	}	
	
	@RequestMapping("/mo/MO0301SHI0")
	public @ResponseBody Map<String, Object> MO0301SHI0() throws Exception {
		result.setData("dlt_searchitem", MO0301Service.MO0301SHI0());
		return result.getResult();
	}	
	
	@RequestMapping("/mo/MO0301G0S0")
	public @ResponseBody Map<String, Object> MO0301G0S0() throws Exception{
		Map map = MO0301Service.MO0301G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/mo/MO0301G0K0")
	public @ResponseBody Map<String, Object> MO0301G0K0() throws Exception{
		result.setData("dma_SEQ_BRT_INCDNT_SCNRO_MST_0", MO0301Service.MO0301G0K0());
		return result.getResult();
	}
	
}
