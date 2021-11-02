package kr.tracom.bms.controller.SI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.SI0405.SI0405Service;
import kr.tracom.bms.domain.SI0702.SI0702Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class SI0702Controller extends ControllerSupport {
	@Autowired
	private SI0702Service si0702Service;
	
	@RequestMapping("/si/SI0702G0R0")
	public @ResponseBody Map<String, Object> SI0702G0R0() throws Exception {
		result.setData("dlt_BMS_EMER_MEMBER_INFO", si0702Service.SI0702G0R0());
		return result.getResult();
	}

	@RequestMapping("/si/SI0702SHI0")
	public @ResponseBody Map<String, Object> SI0702SHI0() throws Exception {
		result.setData("dlt_searchitem", si0702Service.SI0702SHI0());
		return result.getResult();
	}	

	@RequestMapping("/si/SI0702G0K0")
	public @ResponseBody Map<String, Object> SI0405G0K0() throws Exception {
		result.setData("dlt_SEQ_BMS_EMER_MEMBER_INFO_0", si0702Service.SI0702G0K0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0702P0R0")
	public @ResponseBody Map<String, Object> SI0600P0R0() throws Exception {
		result.setData("dlt_ORG_INFO", si0702Service.SI0702P0R0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0702G0S0")
	public @ResponseBody Map<String, Object> SI0702G0S0() throws Exception {
		Map map = si0702Service.SI0702G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
}
