package kr.tracom.bms.controller.SI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.SI0403.SI0403Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class SI0403Controller extends ControllerSupport {
	
	@Autowired
	private SI0403Service SI0403Service;
	
	
	@RequestMapping("/si/SI0403G1R0")
	public @ResponseBody Map<String, Object> SI0403G1R0() throws Exception {
		result.setData("dlt_BMS_ROUT_LINK_CMPSTN", SI0403Service.SI0403G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0403G1S0")
	public @ResponseBody Map<String, Object> SI0403G1S0() throws Exception {
		Map map = SI0403Service.SI0403G1S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}	
}
