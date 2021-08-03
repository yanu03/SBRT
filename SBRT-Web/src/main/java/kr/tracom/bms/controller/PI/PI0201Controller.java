package kr.tracom.bms.controller.PI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.PI0201.PI0201Service;
import kr.tracom.cm.support.ControllerSupport;


@Controller
@Scope("request")
public class PI0201Controller extends ControllerSupport {

	@Autowired
	private PI0201Service PI0201Service;

	@RequestMapping("/pi/PI0201G0R0")
	public @ResponseBody Map<String, Object> PI0201G0R0() throws Exception {
		result.setData("dlt_BMS_VOC_INFO", PI0201Service.PI0201G0R0());
		return result.getResult();
	}	
	
	@RequestMapping("/pi/PI0201G0S0")
	public @ResponseBody Map<String, Object> PI0201G0S0() throws Exception{
		Map map = PI0201Service.PI0201G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/pi/PI0201G0K0")
	public @ResponseBody Map<String, Object> PI0201G0K0() throws Exception{
		result.setData("dma_SEQ_BMS_VOC_INFO_0", PI0201Service.PI0201G0K0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0201SHI0")
	public @ResponseBody Map<String, Object> PI0201SHI0() throws Exception {
		result.setData("dlt_searchitem", PI0201Service.PI0201SHI0());
		return result.getResult();
	}	
}
