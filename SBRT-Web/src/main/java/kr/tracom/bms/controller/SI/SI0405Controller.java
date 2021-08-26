package kr.tracom.bms.controller.SI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.SI0405.SI0405Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class SI0405Controller extends ControllerSupport {
	
	@Autowired
	private SI0405Service SI0405Service;
	
	
	@RequestMapping("/si/SI0405G0R0")
	public @ResponseBody Map<String, Object> SI0405G0R0() throws Exception {
		result.setData("dlt_BMS_REP_ROUT_MST", SI0405Service.SI0405G0R0());
		return result.getResult();
	}

	@RequestMapping("/si/SI0405SHI0")
	public @ResponseBody Map<String, Object> SI0405SHI0() throws Exception {
		result.setData("dlt_searchitem", SI0405Service.SI0405SHI0());
		return result.getResult();
	}	
	
	@RequestMapping("/si/SI0405G0K0")
	public @ResponseBody Map<String, Object> SI0405G0K0() throws Exception {
		result.setData("dma_SEQ_BMS_REP_ROUT_MST_0", SI0405Service.SI0405G0K0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0405G0S0")
	public @ResponseBody Map<String, Object> SI0405G0S0() throws Exception {
		Map map = SI0405Service.SI0405G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	

}
