package kr.tracom.brt.controller.VI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.VI0201.VI0201Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class VI0201Controller extends ControllerSupport {
	
	@Autowired
	private VI0201Service vi0201Service;

	@RequestMapping("/vi/VI0201G0R0")
	public @ResponseBody Map<String, Object> VI0201G0R0() throws Exception {
		result.setData("dlt_BRT_CPLNT_HIS", vi0201Service.VI0201G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/vi/VI0201SHI0")
	public @ResponseBody Map<String, Object> VI0201SHI0() throws Exception {
		result.setData("dlt_grgSearchItem", vi0201Service.VI0201SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/vi/VI0201G0S0")
	public @ResponseBody Map<String, Object> VI0201G0S0() throws Exception{
		Map map = vi0201Service.VI0201G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/vi/VI0201G0K0")
	public @ResponseBody Map<String, Object> VI0201G0K0() throws Exception{
		result.setData("dma_SEQ_BRT_CPLNT_HIS_0", vi0201Service.VI0201G0K0());
		return result.getResult();
	}
	
	
}
