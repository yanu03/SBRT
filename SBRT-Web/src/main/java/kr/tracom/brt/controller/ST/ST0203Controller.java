package kr.tracom.brt.controller.ST;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.ST0203.ST0203Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class ST0203Controller extends ControllerSupport {

	@Autowired
	private ST0203Service st0203Service;
	
	@RequestMapping("/st/ST0203G0R0")
	public @ResponseBody Map<String, Object> ST0203G0R0() throws Exception {
		result.setData("dlt_BMS_ROUT_MST", st0203Service.ST0203G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0203G1R0")
	public @ResponseBody Map<String, Object> ST0203G1R0() throws Exception {
		result.setData("dlt_BRT_ROUT_AVER_OPER_SP_STAT", st0203Service.ST0203G1R0());
		return result.getResult();
	}
	/*
	@RequestMapping("/pi/PI0503G0R0")
	public @ResponseBody Map<String, Object> PI0503G0R0() throws Exception {
		result.setData("dlt_VDO_ORGA_INFO", pi0503Service.PI0503G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0503G1R0")
	public @ResponseBody Map<String, Object> PI0503G1R0() throws Exception {
		result.setData("dlt_DVC_INFO", pi0503Service.PI0503G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0503G2R0")
	public @ResponseBody Map<String, Object> PI0503G2R0() throws Exception {
		result.setData("dlt_BMS_VDO_RSV_RST_INFO", pi0503Service.PI0503G2R0());
		return result.getResult();
	}
	
	@RequestMapping("/pi/PI0503G1S0")
	public @ResponseBody Map<String, Object> PI0503G1S0() throws Exception {
		Map map = pi0503Service.PI0503G1S0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/pi/PI0503G1U0")
	public @ResponseBody Map<String, Object> PI0503G1D0() throws Exception {
		Map map = pi0503Service.PI0503G1U0();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	@RequestMapping("/pi/PI0503SHI0")
	public @ResponseBody Map<String, Object> PI0503SHI0() throws Exception {
		result.setData("dlt_searchitem", pi0503Service.PI0503SHI0());
		return result.getResult();
	}*/
	
}
