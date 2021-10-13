package kr.tracom.bms.controller.PI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.PI0501.PI0501Service;
import kr.tracom.cm.support.ControllerSupport;


@Controller
@Scope("request")
public class PI0501Controller extends ControllerSupport {

	@Autowired
	private PI0501Service pi0501Service;
	
	@RequestMapping("/pi/PI0501G0R0")
	public @ResponseBody Map<String, Object> PI0501G0R0() throws Exception {
		result.setData("dlt_VDO_INFO", pi0501Service.PI0501G0R0());
		return result.getResult();
	}	

	@RequestMapping("/pi/PI0501G0K0")
	public @ResponseBody Map<String, Object> PI0501G0K0() throws Exception {
		result.setData("dma_SEQ_BMS_VDO_INFO_0", pi0501Service.PI0501G0K0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0501G0S0")
	public @ResponseBody Map<String, Object> PI0501G0S0() throws Exception {
		Map map = pi0501Service.PI0501G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/pi/PI0501SHI0")
	public @ResponseBody Map<String, Object> PI0501SHI0() throws Exception {
		result.setData("dlt_searchitem", pi0501Service.PI0501SHI0());
		return result.getResult();
	}
	
}
