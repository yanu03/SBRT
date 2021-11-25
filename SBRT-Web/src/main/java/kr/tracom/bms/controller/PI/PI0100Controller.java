package kr.tracom.bms.controller.PI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.PI0100.PI0100Service;
import kr.tracom.cm.support.ControllerSupport;


@Controller
@Scope("request")
public class PI0100Controller extends ControllerSupport {

	@Autowired
	private PI0100Service pI0100Service;

	@RequestMapping("/pi/PI0100G0R0")
	public @ResponseBody Map<String, Object> PI0100G0R0() throws Exception {
		result.setData("dlt_BMS_USER_NEWS_CFG_INFO", pI0100Service.PI0100G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0100SHI0")
	public @ResponseBody Map<String, Object> PI0100SHI0() throws Exception {
		result.setData("dlt_searchitem", pI0100Service.PI0100SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0100G0S0")
	public @ResponseBody Map<String, Object> PI0100G0S0() throws Exception{
		Map map = pI0100Service.PI0100G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/pi/PI0100G0K0")
	public @ResponseBody Map<String, Object> PI0100G0K0() throws Exception{
		Map map = pI0100Service.PI0100G0K0();
		result.setData("dma_SEQ_BMS_USER_NEWS_CFG_INFO_0", map);
		return result.getResult();
	}	
	
}
