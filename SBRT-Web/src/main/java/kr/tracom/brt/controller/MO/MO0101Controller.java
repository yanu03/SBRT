package kr.tracom.brt.controller.MO;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.MO0101.MO0101Service;
import kr.tracom.cm.support.ControllerSupport;

@Scope("request")
@Controller
public class MO0101Controller extends ControllerSupport {
	
	@Autowired
	private MO0101Service mo0101Service;
	
	@RequestMapping("/mo/MO0101G0R0")
	public @ResponseBody Map<String, Object> MO0101G0R0() throws Exception {
		result.setData("dlt_BRT_CUR_OPER_INFO", mo0101Service.MO0101G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0101G1R0")
	public @ResponseBody Map<String, Object> MO0101G1R0() throws Exception {
		result.setData("dlt_BMS_ROUT_NODE_CMPSTN2", mo0101Service.MO0101G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0101SHI0")
	public @ResponseBody Map<String, Object> MO0101SHI0() throws Exception {
		result.setData("dlt_searchitem", mo0101Service.MO0101SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0101SHI1")
	public @ResponseBody Map<String, Object> MO0101SHI1() throws Exception {
		result.setData("dlt_searchitem2", mo0101Service.MO0101SHI1());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0101G2R0")
	public @ResponseBody Map<String, Object> MO0101G2R0() throws Exception {
		result.setData("dlt_BMS_ROUT_NODE_CMPSTN3", mo0101Service.MO0101G2R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/selectCategory")
	public @ResponseBody Map<String, Object> selectCategory() throws Exception {
		result.setData("dlt_BMS_NODE_MST", mo0101Service.selectCategory());
		return result.getResult();
	}
	
	@RequestMapping("/mo/selectSigOper")
	public @ResponseBody Map<String, Object> selectSigOper() throws Exception {
		result.setData("dlt_BMS_ROUT_NODE_CMPSTN4", mo0101Service.selectSigOper());
		return result.getResult();
	}
	
	@RequestMapping("/mo/selectSigPeriod")
	public @ResponseBody Map<String, Object> selectSigPeriod() throws Exception {
		result.setData("dlt_SIG_PERIOD", mo0101Service.selectSigPeriod());
		return result.getResult();
	}		
	
	@RequestMapping("/mo/MO0101SCK")
	public @ResponseBody Map<String, Object> MO0101SCK() throws Exception {		
		
		result.setData("", mo0101Service.MO0101SCK());
		return result.getResult();
	}	
	
	@RequestMapping("/mo/MO0101P0R0")
	public @ResponseBody Map<String, Object> MO0101P0R0() throws Exception {
		result.setData("dlt_BRT_OPER_INST_MSG_MST", mo0101Service.MO0101P0R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0101SCK1")
	public @ResponseBody Map<String, Object> MO0101SCK1() throws Exception {		
		
		result.setData("", mo0101Service.MO0101SCK1());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0101G3R0")
	public @ResponseBody Map<String, Object> MO0101G3R0() throws Exception {
		result.setData("dlt_BRT_OPER_EVENT_HIS", mo0101Service.MO0101G3R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0101G4R0")
	public @ResponseBody Map<String, Object> MO0101G4R0() throws Exception {
		result.setData("dlt_BRT_DSPTCH_LOG", mo0101Service.MO0101G4R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0101G5R0")
	public @ResponseBody Map<String, Object> MO0101G5R0() throws Exception {
		result.setData("dlt_FCLT_COND_LOG", mo0101Service.MO0101G5R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0101G6R0")
	public @ResponseBody Map<String, Object> MO0101G6R0() throws Exception {
		result.setData("dlt_FCLT_COND_LOG", mo0101Service.MO0101G6R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0101G7R0")
	public @ResponseBody Map<String, Object> MO0101G7R0() throws Exception {
		result.setData("dlt_BMS_CRS_SIGOPER_EVENT_HIS", mo0101Service.MO0101G7R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/selectCommuMap")
	public @ResponseBody Map<String, Object> selectCommuMap() throws Exception {
		result.setData("dlt_BMS_LINK_MST", mo0101Service.selectCommuMap());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0101SCK2")
	public @ResponseBody Map<String, Object> MO0101SCK2() throws Exception {		
		result.setData("", mo0101Service.MO0101SCK2());
		return result.getResult();
	}	
	
}
