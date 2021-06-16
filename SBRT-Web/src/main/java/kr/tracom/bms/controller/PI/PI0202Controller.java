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
	private PI0202Service PI0202Service;

	@RequestMapping("/pi/PI0202G0R0")
	public @ResponseBody Map<String, Object> PI0202G0R0() throws Exception {
		result.setData("dlt_BMS_ROUT_MST", PI0202Service.PI0202G0R0());
		return result.getResult();
	}
	@RequestMapping("/pi/PI0202G1R0")
	public @ResponseBody Map<String, Object> PI0202G1R0() throws Exception {
		result.setData("dlt_BMS_VOC_INFO", PI0202Service.PI0202G1R0());
		return result.getResult();
	}
	@RequestMapping("/pi/PI0202G2R0")
	public @ResponseBody Map<String, Object> PI0202G2R0() throws Exception {
		result.setData("dlt_BMS_VOC_ORGA_LIST", PI0202Service.PI0202G2R0());
		return result.getResult();
	}	
}
