package kr.tracom.brt.controller.AL;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.PI0201.PI0201Service;
import kr.tracom.bms.domain.PI0205.PI0205Service;
import kr.tracom.brt.domain.AL0102.AL0102Service;
import kr.tracom.cm.support.ControllerSupport;


@Controller
@Scope("request")
public class AL0102Controller extends ControllerSupport {

	@Autowired
	private AL0102Service AL0102Service;
	
	@RequestMapping("/al/AL0102G0R0")
	public @ResponseBody Map<String, Object> AL0102G0R0() throws Exception {
		result.setData("dlt_BRT_HOLI_MST", AL0102Service.AL0102G0R0());
		return result.getResult();
	}	
	
	@RequestMapping("/al/AL0102SHI0")
	public @ResponseBody Map<String, Object> PI0205SHI0() throws Exception {
		result.setData("dlt_searchitem", AL0102Service.AL0102SHI0());
		return result.getResult();
	}	
	
	@RequestMapping("/al/AL0102G0S0")
	public @ResponseBody Map<String, Object> AL0102G0S0() throws Exception{
		Map map = AL0102Service.AL0102G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
}
