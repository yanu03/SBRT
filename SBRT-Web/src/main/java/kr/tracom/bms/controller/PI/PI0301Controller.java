package kr.tracom.bms.controller.PI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.PI0301.PI0301Service;
import kr.tracom.cm.support.ControllerSupport;


@Controller
@Scope("request")
public class PI0301Controller extends ControllerSupport {

	@Autowired
	private PI0301Service PI0301Service;

	@RequestMapping("/pi/PI0301G0R0")
	public @ResponseBody Map<String, Object> PI0301G0R0() throws Exception {
		result.setData("dlt_BMS_NEWS_CFG_INFO", PI0301Service.PI0301G0R0());
		return result.getResult();
	}	
	
	@RequestMapping("/pi/PI0301G0S0")
	public @ResponseBody Map<String, Object> PI0301G0S0() throws Exception{
		result.setData("result", PI0301Service.PI0301G0S0());
		return result.getResultSave();
	}
	
	@RequestMapping("/pi/PI0301G0K0")
	public @ResponseBody Map<String, Object> PI0301G0K0() throws Exception{
		result.setData("dma_SEQ_BMS_NEWS_CFG_INFO_0", PI0301Service.PI0301G0K0());
		return result.getResult();
	}
	
	
	@RequestMapping("/pi/PI0301SHI0")
	public @ResponseBody Map<String, Object> SI0200SHI0() throws Exception {
		result.setData("dlt_searchItem", PI0301Service.PI0301SHI0());
		return result.getResult();
	}
}
