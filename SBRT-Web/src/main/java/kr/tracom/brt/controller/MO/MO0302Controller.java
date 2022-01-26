package kr.tracom.brt.controller.MO;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.MO0302.MO0302Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class MO0302Controller extends ControllerSupport {
	
	@Autowired
	private MO0302Service mo0302Service;
	
	@RequestMapping("/mo/MO0302G0R0")
	public @ResponseBody Map<String, Object> MO0302G0R0() throws Exception {
		result.setData("dlt_BRT_INCDNT_HIS", mo0302Service.MO0302G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0302SHI0")
	public @ResponseBody Map<String, Object> MO0302SHI0() throws Exception {
		result.setData("dlt_grgSearchItem", mo0302Service.MO0302SHI0());
		return result.getResult();
	}
}
