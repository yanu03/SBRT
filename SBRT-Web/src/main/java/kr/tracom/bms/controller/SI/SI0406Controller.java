package kr.tracom.bms.controller.SI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.SI0406.SI0406Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class SI0406Controller extends ControllerSupport {
	
	@Autowired
	private SI0406Service si0406Service;
	
	
	@RequestMapping("/si/SI0406G1R0")
	public @ResponseBody Map<String, Object> SI0406G1R0() throws Exception {
		result.setData("dlt_BMS_ROUT_NODE_CMPSTN", si0406Service.SI0406G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0406G2R0")
	public @ResponseBody Map<String, Object> SI0406G2R0() throws Exception {
		result.setData("dlt_BMS_MOCK_LINK", si0406Service.SI0406G2R0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0406G1S0")
	public @ResponseBody Map<String, Object> SI0406G1S0() throws Exception {
		Map map = si0406Service.SI0406G1S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}	
	
	@RequestMapping("/si/SI0406P0R0")
	public @ResponseBody Map<String, Object> SI0406P0R0() throws Exception {
		result.setData("dlt_BMS_MOCK_LINK", si0406Service.SI0406P0R0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0406P0S0")
	public @ResponseBody Map<String, Object> SI0406P0S0() throws Exception {
		Map map = si0406Service.SI0406P0S0();
		result.setData("dma_result", map);
		return result.getResultSave();		
	}
	

}
