package kr.tracom.bms.controller.PI;

import java.util.Map;
import kr.tracom.bms.domain.PI1001.PI1001Service;
import kr.tracom.cm.support.ControllerSupport;
import kr.tracom.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Scope("request")
public class PI1001Controller extends ControllerSupport {

	@Autowired
	private PI1001Service PI1001Service;

	@RequestMapping({ "/pi/PI1001G0R0" })
	@ResponseBody
	public Map<String, Object> PI1001G0R0() throws Exception {
		this.result.setData("dlt_BMS_NOTICE_MST", this.PI1001Service.PI1001G0R0());
		return this.result.getResult();
	}

	@RequestMapping({ "/pi/PI1001G1R0" })
	@ResponseBody
	public Map<String, Object> PI1001G1R0() throws Exception {
		this.result.setData("dlt_BMS_VHC_MST", this.PI1001Service.PI1001G1R0());
		return this.result.getResult();
	}

	@RequestMapping({ "/pi/PI1001SHI0" })
	@ResponseBody
	public Map<String, Object> PI1001SHI0() throws Exception {
		this.result.setData("dlt_searchitem", this.PI1001Service.PI1001SHI0());
		return this.result.getResult();
	}
	
	@RequestMapping({ "/pi/PI1001SHI1" })
	@ResponseBody
	public Map<String, Object> PI1001SHI1() throws Exception {
		this.result.setData("dlt_searchitem", this.PI1001Service.PI1001SHI1());
		return this.result.getResult();
	}

	@RequestMapping({ "/pi/PI1001G2R0" })
	@ResponseBody
	public Map<String, Object> PI1001G2R0() throws Exception {
		this.result.setData("dlt_BMS_DVC_INFO", this.PI1001Service.PI1001G2R0());
		return this.result.getResult();
	}

	@RequestMapping("/pi/PI1001G0S0" )
	public @ResponseBody Map<String, Object> PI1001G0S0() throws Exception {
		Map map = PI1001Service.PI1001G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}

	@RequestMapping({ "/pi/PI1001G0K0" })
	@ResponseBody
	public Map<String, Object> PI1001G0K0() throws Exception {
		this.result.setData("dma_SEQ_BMS_NOTICE_MST_0", this.PI1001Service.PI1001G0K0());
		return this.result.getResult();
	}

	@RequestMapping({ "/pi/PI1001G1S0" })
	@ResponseBody
	public Map<String, Object> PI1001G1S0() throws Exception {
		this.result.setData("result", this.PI1001Service.PI1001G1S0());
		return this.result.getResultSave();
	}

	@RequestMapping({ "/pi/PI1001G2S0" })
	@ResponseBody
	public Map<String, Object> PI1001G2S0() throws Exception {
		this.result.setData("result", this.PI1001Service.PI1001G2S0());
		return this.result.getResultSave();
	}

	@RequestMapping({ "/pi/PI1001P0R0" })
	@ResponseBody
	public Map<String, Object> PI1001P0R0() throws Exception {
		this.result.setData("dlt_BMS_VHC_MST", this.PI1001Service.PI1001P0R0());
		return this.result.getResult();
	}

	@RequestMapping({ "/pi/PI1001P1R0" })
	@ResponseBody
	public Map<String, Object> PI1001P1R0() throws Exception {
		this.result.setData("dlt_BMS_STTN_MST", this.PI1001Service.PI1001P1R0());
		return this.result.getResult();
	}
	
	@RequestMapping({ "/pi/PI1001P2R0" })
	@ResponseBody
	public Map<String, Object> PI1001P2R0() throws Exception {
		this.result.setData("dlt_BMS_DVC_INFO", this.PI1001Service.PI1001P2R0());
		return this.result.getResult();
	}
	
	@RequestMapping({ "/pi/PI1001P3R0" })
	@ResponseBody
	public Map<String, Object> PI1001P3R0() throws Exception {
		this.result.setData("dlt_BMS_NOTICE_SCNRO_MST", this.PI1001Service.PI1001P3R0());
		return this.result.getResult();
	}
}