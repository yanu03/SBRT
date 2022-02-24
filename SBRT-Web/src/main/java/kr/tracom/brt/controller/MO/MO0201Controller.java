package kr.tracom.brt.controller.MO;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.MO0201.MO0201Service;
import kr.tracom.cm.support.ControllerSupport;

@Scope("request")
@Controller
public class MO0201Controller extends ControllerSupport {
	
	@Autowired
	private MO0201Service mo0201Service;
	
	@RequestMapping("/mo/MO0201G0R0")
	public @ResponseBody Map<String, Object> MO0201G0R0() throws Exception {
		result.setData("dlt_BRT_CUR_OPER_INFO", mo0201Service.MO0201G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0201G1R0")
	public @ResponseBody Map<String, Object> MO0201G1R0() throws Exception {
		result.setData("dlt_BMS_ROUT_NODE_CMPSTN2", mo0201Service.MO0201G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0201SHI0")
	public @ResponseBody Map<String, Object> MO0201SHI0() throws Exception {
		result.setData("dlt_searchitem", mo0201Service.MO0201SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0201SHI1")
	public @ResponseBody Map<String, Object> MO0201SHI1() throws Exception {
		result.setData("dlt_searchitem2", mo0201Service.MO0201SHI1());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0201G2R0")
	public @ResponseBody Map<String, Object> MO0201G2R0() throws Exception {
		result.setData("dlt_BMS_ROUT_NODE_CMPSTN3", mo0201Service.MO0201G2R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0201G3R0")
	public @ResponseBody Map<String, Object> MO0201G3R0() throws Exception {
		result.setData("dlt_BMS_ROUT_MST", mo0201Service.MO0201G3R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0201G4R0")
	public @ResponseBody Map<String, Object> MO0201G4R0() throws Exception {
		result.setData("dlt_BMS_NODE_MST2", mo0201Service.MO0201G4R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/selectCategory2")
	public @ResponseBody Map<String, Object> selectCategory() throws Exception {
		result.setData("dlt_BMS_NODE_MST", mo0201Service.selectCategory());
		return result.getResult();
	}
	
	@RequestMapping("/mo/selectSigOper2")
	public @ResponseBody Map<String, Object> selectSigOper() throws Exception {
		result.setData("dlt_BMS_ROUT_NODE_CMPSTN4", mo0201Service.selectSigOper());
		return result.getResult();
	}
	
	@RequestMapping("/mo/selectSigPeriod2")
	public @ResponseBody Map<String, Object> selectSigPeriod() throws Exception {
		result.setData("dlt_SIG_PERIOD", mo0201Service.selectSigPeriod());
		return result.getResult();
	}		
	
	@RequestMapping("/mo/MO0201SCK")
	public @ResponseBody Map<String, Object> MO0201SCK() throws Exception {		
		
		result.setData("", mo0201Service.MO0201SCK());
		return result.getResult();
	}	
	
	@RequestMapping("/mo/MO0201P0R0")
	public @ResponseBody Map<String, Object> MO0201P0R0() throws Exception {
		result.setData("dlt_BRT_OPER_INST_MSG_MST", mo0201Service.MO0201P0R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0201SCK1")
	public @ResponseBody Map<String, Object> MO0201SCK1() throws Exception {		
		
		result.setData("", mo0201Service.MO0201SCK1());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0201G5R0")
	public @ResponseBody Map<String, Object> MO0201G5R0() throws Exception {
		result.setData("dlt_FCLT_COND_LOG", mo0201Service.MO0201G5R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0201G6R0")
	public @ResponseBody Map<String, Object> MO0201G6R0() throws Exception {
		result.setData("dlt_FCLT_COND_LOG", mo0201Service.MO0201G6R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0201G7R0")
	public @ResponseBody Map<String, Object> MO0201G7R0() throws Exception {
		result.setData("dlt_FCLT_COND_LOG2", mo0201Service.MO0201G7R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0201G8R0")
	public @ResponseBody Map<String, Object> MO0201G8R0() throws Exception {
		result.setData("dlt_BMS_ROUT_MST2", mo0201Service.MO0201G8R0());
		return result.getResult();
	}	
	
	@RequestMapping("/mo/selectCommuMap2")
	public @ResponseBody Map<String, Object> selectCommuMap() throws Exception {
		result.setData("dlt_BMS_LINK_MST", mo0201Service.selectCommuMap());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0201SCK2")
	public @ResponseBody Map<String, Object> MO0201SCK2() throws Exception {		
		result.setData("", mo0201Service.MO0201SCK2());
		return result.getResult();
	}	
	
}
