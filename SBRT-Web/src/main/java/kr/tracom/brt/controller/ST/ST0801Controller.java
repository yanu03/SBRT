package kr.tracom.brt.controller.ST;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.PI0503.PI0503Service;
import kr.tracom.brt.domain.ST0200.ST0200Service;
import kr.tracom.brt.domain.ST0800.ST0800Service;
import kr.tracom.brt.domain.ST0801.ST0801Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class ST0801Controller extends ControllerSupport {

	@Autowired
	private ST0801Service st0801Service;
	
	@RequestMapping("/st/ST0801G0R0")
	public @ResponseBody Map<String, Object> ST0801G0R0() throws Exception {
		result.setData("dlt_BMS_ROUT_MST", st0801Service.ST0801G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0801G1R0")
	public @ResponseBody Map<String, Object> ST0801G1R0() throws Exception {
		result.setData("dlt_BRT_SGN_STAT", st0801Service.ST0801G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0801G1R1")
	public @ResponseBody Map<String, Object> ST0801G1R1() throws Exception {
		result.setData("dlt_BRT_SGN_STAT_PIVOT_2", st0801Service.ST0801G1R1());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0801G2R0")
	public @ResponseBody Map<String, Object> ST0801G2R0() throws Exception {
		result.setData("dlt_BRT_SGN_STAT_PIVOT", st0801Service.ST0801G2R0());
		return result.getResult();
	}
	
	/*@RequestMapping("/st/ST0200G1R1")
	public @ResponseBody Map<String, Object> ST0200G1R1() throws Exception {
		result.setData("dlt_BRT_ROUT_AVER_OPER_SP_STAT", st0200Service.ST0200G1R1());
		return result.getResult();
	}
	
	@RequestMapping("/st/ST0200G1R2")
	public @ResponseBody Map<String, Object> ST0200G1R2() throws Exception {
		result.setData("dlt_BRT_ROUT_AVER_OPER_SP_STAT", st0200Service.ST0200G1R2());
		return result.getResult();
	}*/
	
	/*@RequestMapping("/pi/PI0503G0R0")
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
