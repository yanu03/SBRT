package kr.tracom.bms.controller.PI;

import java.util.Map;
import kr.tracom.bms.domain.PI1002.PI1002Service;
import kr.tracom.cm.support.ControllerSupport;
import kr.tracom.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Scope("request")
public class PI1002Controller extends ControllerSupport {

	@Autowired
	private PI1002Service PI1002Service;
	
	@RequestMapping({ "/pi/PI1002G0R0" })
	@ResponseBody
	public Map<String, Object> PI1002G0R0() throws Exception {
		this.result.setData("dlt_BMS_NOTICE_MST", this.PI1002Service.PI1002G0R0());
		return this.result.getResult();
	}

	@RequestMapping({ "/pi/PI1002G1R0" })
	@ResponseBody
	public Map<String, Object> PI1002G1R0() throws Exception {
		this.result.setData("dlt_BMS_STTN_MST", this.PI1002Service.PI1002G1R0());
		return this.result.getResult();
	}

	@RequestMapping({ "/pi/PI1002SHI0" })
	@ResponseBody
	public Map<String, Object> PI1002SHI0() throws Exception {
		this.result.setData("dlt_searchitem", this.PI1002Service.PI1002SHI0());
		return this.result.getResult();
	}

	@RequestMapping({ "/pi/PI1002G2R0" })
	@ResponseBody
	public Map<String, Object> PI1002G2R0() throws Exception {
		this.result.setData("dlt_BMS_DVC_INFO", this.PI1002Service.PI1002G2R0());
		return this.result.getResult();
	}

	@RequestMapping("/pi/PI1002G0S0" )
	public @ResponseBody Map<String, Object> PI1002G0S0() throws Exception {
		Map map = PI1002Service.PI1002G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}

	@RequestMapping({ "/pi/PI1002G0K0" })
	@ResponseBody
	public Map<String, Object> PI1002G0K0() throws Exception {
		this.result.setData("dma_SEQ_BMS_NOTICE_MST_0", this.PI1002Service.PI1002G0K0());
		return this.result.getResult();
	}

	@RequestMapping({ "/pi/PI1002G1S0" })
	@ResponseBody
	public Map<String, Object> PI1002G1S0() throws Exception {
		this.result.setData("dma_result", this.PI1002Service.PI1002G1S0());
		return this.result.getResultSave();
	}

	@RequestMapping({ "/pi/PI1002G2S0" })
	@ResponseBody
	public Map<String, Object> PI1002G2S0() throws Exception {
		this.result.setData("result", this.PI1002Service.PI1002G2S0());
		return this.result.getResultSave();
	}

	@RequestMapping({ "/pi/PI1002P0R0" })
	@ResponseBody
	public Map<String, Object> PI1002P0R0() throws Exception {
		this.result.setData("dlt_BMS_VHC_MST", this.PI1002Service.PI1002P0R0());
		return this.result.getResult();
	}

	@RequestMapping({ "/pi/PI1002P1R0" })
	@ResponseBody
	public Map<String, Object> PI1002P1R0() throws Exception {
		this.result.setData("dlt_BMS_STTN_MST", this.PI1002Service.PI1002P1R0());
		return this.result.getResult();
	}
	
	@RequestMapping({ "/pi/PI1002P2R0" })
	@ResponseBody
	public Map<String, Object> PI1002P2R0() throws Exception {
		this.result.setData("dlt_BMS_DVC_INFO", this.PI1002Service.PI1002P2R0());
		return this.result.getResult();
	}
}