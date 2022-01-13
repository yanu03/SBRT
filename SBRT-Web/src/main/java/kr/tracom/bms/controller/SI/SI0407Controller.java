package kr.tracom.bms.controller.SI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.SI0407.SI0407Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class SI0407Controller extends ControllerSupport {
	
	@Autowired
	private SI0407Service si0407Service;
	
	@RequestMapping("/si/SI0407G0R0")
	public @ResponseBody Map<String, Object> SI0407P0R0() throws Exception {
		result.setData("dlt_BMS_MOCK_LINK", si0407Service.SI0407G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0407G0S0")
	public @ResponseBody Map<String, Object> SI0407P0S0() throws Exception {
		Map map = si0407Service.SI0407G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();		
	}

	@RequestMapping("/si/SI0407SHI0")
	public @ResponseBody Map<String, Object> SI0407SHI0() throws Exception {
		result.setData("dlt_searchitem", si0407Service.SI0407SHI0());
		return result.getResult();
	}

}
