package kr.tracom.brt.controller.ST;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.ST0601.ST0601Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class ST0601Controller extends ControllerSupport {

	@Autowired
	private ST0601Service st0601Service;
	
	@RequestMapping("/st/ST0601G0R0")
	public @ResponseBody Map<String, Object> ST0601G0R0() throws Exception {
		result.setData("dlt_BMS_GRG_MST", st0601Service.ST0601G0R0());
		return result.getResult();
	}
	
	
}
