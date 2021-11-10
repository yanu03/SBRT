package kr.tracom.bms.controller.VD;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.VD0202.VD0202Service;
import kr.tracom.cm.support.ControllerSupport;


@Controller
@Scope("request")
public class VD0202Controller extends ControllerSupport {

	@Autowired
	private VD0202Service VD0202Service;

	@RequestMapping("/vd/VD0202G0R0")
	public @ResponseBody Map<String, Object> VD0202G0R0() throws Exception {
		result.setData("dlt_BRT_CUR_OPER_INFO", VD0202Service.VD0202G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/vd/VD0202G1R0")
	public @ResponseBody Map<String, Object> VD0202G1R0() throws Exception {
		result.setData("dlt_BMS_DVC_INFO", VD0202Service.VD0202G1R0());
		result.setData("dlt_BMS_DVC_INFO2", VD0202Service.VD0202G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/vd/VD0202SHI1")
	public @ResponseBody Map<String, Object> VD0202SHI0() throws Exception {
		result.setData("dlt_searchitem2", VD0202Service.VD0202SHI1());
		return result.getResult();
	}
	
	@RequestMapping("/vd/VD0202G2R0")
	public @ResponseBody Map<String, Object> VD0202G2R0() throws Exception {
		result.setData("dlt_BMS_ROUT_LINK_CMPSTN", VD0202Service.VD0202G2R0());
		return result.getResult();
	}
	
}
