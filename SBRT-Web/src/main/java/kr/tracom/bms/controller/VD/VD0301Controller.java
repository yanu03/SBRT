package kr.tracom.bms.controller.VD;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.VD0301.VD0301Service;
import kr.tracom.cm.support.ControllerSupport;


@Controller
@Scope("request")
public class VD0301Controller extends ControllerSupport {
	
	@Autowired
	private VD0301Service vd0301Service;
	
	@RequestMapping("/vd/VD0301G0R0")
	public @ResponseBody Map<String, Object> VD0301G0R0() throws Exception {
		result.setData("dlt_BMS_DVC_INFO", vd0301Service.VD0301G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/vd/VD0301SHI0")
	public @ResponseBody Map<String, Object> VD0301SHI0() throws Exception {
		result.setData("dlt_searchItem", vd0301Service.VD0301SHI0());
		return result.getResult();
	}	
}
