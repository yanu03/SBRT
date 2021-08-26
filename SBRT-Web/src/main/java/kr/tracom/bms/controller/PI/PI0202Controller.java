package kr.tracom.bms.controller.PI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.PI0202.PI0202Service;
import kr.tracom.cm.support.ControllerSupport;


@Controller
@Scope("request")
public class PI0202Controller extends ControllerSupport {

	@Autowired
	private PI0202Service pi0202Service;

	@RequestMapping("/pi/PI0202G1R0")
	public @ResponseBody Map<String, Object> PI0202G1R0() throws Exception {
		result.setData("dlt_BMS_ROUT_NODE_CMPSTN", pi0202Service.PI0202G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0202G1S0")
	public @ResponseBody Map<String, Object> PI0202G1S0() throws Exception {
		Map map = pi0202Service.PI0202G1S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	@RequestMapping("/pi/PI0202P0R0")
	public @ResponseBody Map<String, Object> PI0202P0R0() throws Exception {
		result.setData("dlt_BMS_VOC_ORGA_INFO", pi0202Service.PI0202P0R0());
		return result.getResult();
	}	
	
	@RequestMapping("/pi/PI0202SHI0")
	public @ResponseBody Map<String, Object> PI0202SHI0() throws Exception {
		result.setData("dlt_searchitem", pi0202Service.PI0202SHI0());
		return result.getResult();
	}

}
