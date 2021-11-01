package kr.tracom.brt.controller.ST;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.ST0401.ST0401Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class ST0401Controller extends ControllerSupport {

	@Autowired
	private ST0401Service st0302Service;
	
	@RequestMapping("/st/ST0401G0R0")
	public @ResponseBody Map<String, Object> ST0401G0R0() throws Exception {
		result.setData("dlt_BRT_OPER_VIOLT_STAT", st0302Service.ST0401G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0401G1R0")
	public @ResponseBody Map<String, Object> ST0401G1R0() throws Exception {
		result.setData("dlt_BRT_OPER_VIOLT_STAT2", st0302Service.ST0401G1R0());
		return result.getResult();
	}
	
}
