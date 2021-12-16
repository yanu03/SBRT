package kr.tracom.bms.controller.PI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.PI0201.PI0201Service;
import kr.tracom.bms.domain.PI0205.PI0205Service;
import kr.tracom.bms.domain.PI1003.PI1003Service;
import kr.tracom.brt.domain.AL0102.AL0102Service;
import kr.tracom.brt.domain.MO0301.MO0301Service;
import kr.tracom.cm.support.ControllerSupport;


@Controller
@Scope("request")
public class PI1003Controller extends ControllerSupport {
	
	@Autowired
	private PI1003Service PI1003Service;
	
	@RequestMapping("/pi/PI1003G0R0")
	public @ResponseBody Map<String, Object> MO0301G0R0() throws Exception {
		result.setData("dlt_BMS_NOTICE_SCNRO_MST", PI1003Service.PI1003G0R0());
		return result.getResult();
	}	
	
	@RequestMapping("/pi/PI1003SHI0")
	public @ResponseBody Map<String, Object> MO0301SHI0() throws Exception {
		result.setData("dlt_searchitem", PI1003Service.PI1003SHI0());
		return result.getResult();
	}	
	
	@RequestMapping("/pi/PI1003G0S0")
	public @ResponseBody Map<String, Object> MO0301G0S0() throws Exception{
		Map map = PI1003Service.PI1003G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/pi/PI1003G0K0")
	public @ResponseBody Map<String, Object> MO0301G0K0() throws Exception{
		result.setData("dma_SEQ_BMS_NOTICE_SCNRO_MST_0", PI1003Service.PI1003G0K0());
		return result.getResult();
	}
	
}
