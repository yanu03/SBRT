package kr.tracom.bms.controller.SM;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.SM0403.SM0403Service;
import kr.tracom.bms.domain.SM0601.SM0601Service;
import kr.tracom.bms.domain.SM0800.SM0800Service;
import kr.tracom.cm.support.ControllerSupport;


@Controller
@Scope("request")
public class SM0800Controller extends ControllerSupport {

	@Autowired
	private SM0800Service sm0800Service;
	
	@RequestMapping("/sm/SM0800G0R0")
	public @ResponseBody Map<String, Object> SM0800G0R0() throws Exception {
		result.setData("dlt_BMS_API_INFO", sm0800Service.SM0800G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/sm/SM0800SHI0")
	public @ResponseBody Map<String, Object> SM0800SHI0() throws Exception {
		result.setData("dlt_searchItem", sm0800Service.SM0800SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/sm/SM0800G0S0")
	public @ResponseBody Map<String, Object> SM0800G0S0() throws Exception {
		Map map = sm0800Service.SM0800G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/sm/SM0800G0K0")
	public @ResponseBody Map<String, Object> SM0800G0K0() throws Exception {
		result.setData("dma_SEQ_BMS_API_INFO_0", sm0800Service.SM0800G0K0());
		return result.getResult();
	}
}
