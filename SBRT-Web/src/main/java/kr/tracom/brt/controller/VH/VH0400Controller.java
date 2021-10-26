package kr.tracom.brt.controller.VH;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.VH0400.VH0400Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class VH0400Controller extends ControllerSupport {

	@Autowired
	private VH0400Service vh0400Service;

	@RequestMapping("/vh/VH0400G0R0")
	public @ResponseBody Map<String, Object> VH0400G0R0() throws Exception {
		result.setData("dlt_BRT_OPER_EVENT_HIS", vh0400Service.VH0400G0R0());
		return result.getResult();
	}
	
}
