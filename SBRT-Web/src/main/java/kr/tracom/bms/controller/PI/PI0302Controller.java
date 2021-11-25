package kr.tracom.bms.controller.PI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.PI0302.PI0302Service;
import kr.tracom.cm.support.ControllerSupport;


@Controller
@Scope("request")
public class PI0302Controller extends ControllerSupport {

	@Autowired
	private PI0302Service PI0302Service;

	@RequestMapping("/pi/PI0302G0R0")
	public @ResponseBody Map<String, Object> PI0302G0R0() throws Exception {
		result.setData("dlt_BMS_NEWS_INFO", PI0302Service.PI0302G0R0());
		return result.getResult();
	}	
	
	@RequestMapping("/pi/PI0302SHI0")
	public @ResponseBody Map<String, Object> PI0302SHI0() throws Exception {
		result.setData("dlt_searchitem", PI0302Service.PI0302SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0302G0S0")
	public @ResponseBody Map<String, Object> PI0302G0S0() throws Exception {
		Map map = PI0302Service.PI0302G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}	
	
	@RequestMapping("/pi/PI0302G1R0")
	public @ResponseBody Map<String, Object> PI0302G1R0() throws Exception {
		result.setData("dlt_BMS_LIVING_LOG", PI0302Service.PI0302G1R0());
		return result.getResult();
	}	
}
