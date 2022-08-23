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
		result.setData("dlt_BRT_CUR_OPER_INFO_MO0101", mo0101Service.MO0101G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0101G1R0")
	public @ResponseBody Map<String, Object> MO0101G1R0() throws Exception {
		result.setData("dlt_BMS_ROUT_NODE_CMPSTN2_MO0101", mo0101Service.MO0101G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0101SHI0")
	public @ResponseBody Map<String, Object> MO0101SHI0() throws Exception {
		result.setData("dlt_searchitem_MO0101", mo0101Service.MO0101SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0101SHI1")
	public @ResponseBody Map<String, Object> MO0101SHI1() throws Exception {
		result.setData("dlt_searchitem2_MO0101", mo0101Service.MO0101SHI1());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0101G2R0")
	public @ResponseBody Map<String, Object> MO0101G2R0() throws Exception {
		result.setData("dlt_BMS_ROUT_NODE_CMPSTN3_MO0101", mo0101Service.MO0101G2R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/selectCategory")
	public @ResponseBody Map<String, Object> selectCategory() throws Exception {
		result.setData("dlt_BMS_NODE_MST_MO0101_MO0101", mo0101Service.selectCategory());
		return result.getResult();
	}
	
	@RequestMapping("/mo/selectSigOper")
	public @ResponseBody Map<String, Object> selectSigOper() throws Exception {
		result.setData("dlt_BMS_ROUT_NODE_CMPSTN4_MO0101", mo0101Service.selectSigOper());
		return result.getResult();
	}
	
	@RequestMapping("/mo/selectSigPeriod")
	public @ResponseBody Map<String, Object> selectSigPeriod() throws Exception {
		result.setData("dlt_SIG_PERIOD_MO0101", mo0101Service.selectSigPeriod());
		return result.getResult();
	}		
	
	@RequestMapping("/mo/MO0101SCK")
	public @ResponseBody Map<String, Object> MO0101SCK() throws Exception {		
		
		result.setData("", mo0101Service.MO0101SCK());
		return result.getResult();
	}	
	
	@RequestMapping("/mo/MO0101P0R0")
	public @ResponseBody Map<String, Object> MO0101P0R0() throws Exception {
		result.setData("dlt_BRT_OPER_INST_MSG_MST_MO0101", mo0101Service.MO0101P0R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0101SCK1")
	public @ResponseBody Map<String, Object> MO0101SCK1() throws Exception {		
		
		result.setData("", mo0101Service.MO0101SCK1());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0101G3R0")
	public @ResponseBody Map<String, Object> MO0101G3R0() throws Exception {
		result.setData("dlt_BRT_OPER_EVENT_HIS_MO0101", mo0101Service.MO0101G3R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0101G4R0")
	public @ResponseBody Map<String, Object> MO0101G4R0() throws Exception {
		result.setData("dlt_BRT_DSPTCH_LOG_MO0101", mo0101Service.MO0101G4R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0101G5R0")
	public @ResponseBody Map<String, Object> MO0101G5R0() throws Exception {
		result.setData("dlt_FCLT_COND_LOG_MO0101", mo0101Service.MO0101G5R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0101G6R0")
	public @ResponseBody Map<String, Object> MO0101G6R0() throws Exception {
		result.setData("dlt_FCLT_COND_LOG_MO0101", mo0101Service.MO0101G6R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0101G7R0")
	public @ResponseBody Map<String, Object> MO0101G7R0() throws Exception {
		result.setData("dlt_BMS_CRS_SIGOPER_EVENT_HIS_MO0101", mo0101Service.MO0101G7R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/selectCommuMap")
	public @ResponseBody Map<String, Object> selectCommuMap() throws Exception {
		result.setData("dlt_BMS_LINK_MST_MO0101", mo0101Service.selectCommuMap());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0101SCK2")
	public @ResponseBody Map<String, Object> MO0101SCK2() throws Exception {		
		result.setData("", mo0101Service.MO0101SCK2());
		return result.getResult();
	}	
	
	@RequestMapping("/mo/selectSigPhaseInfo")
	public @ResponseBody Map<String, Object> selectSigPhaseInfo() throws Exception {
		result.setData("dlt_BMS_CRS_SIGOPER_PHASE_INFO_HIS_MO0101", mo0101Service.selectSigPhaseInfo());
		return result.getResult();
	}
	
	@RequestMapping("/mo/selectGrgRdsInfo")
	public @ResponseBody Map<String, Object> selectGrgRdsInfo() throws Exception {		
		result.setData("dlt_BMS_GRG_RDS_INFO_MO0101", mo0101Service.selectGrgRdsInfo());
		return result.getResult();
	}	
	
	//모니터링 파라미터 분리를 위해 공통코드에서 빼오고 따로 사용
	@RequestMapping("/mo/selectNodeDispListByRepRout")
	public @ResponseBody Map<String, Object> selectNodeDispListByRepRout() throws Exception {
		result.setData("dlt_BMS_ROUT_NODE_DISP_VW_MO0101", mo0101Service.selectNodeDispListByRepRout());
		return result.getResult();
	}
	
	//모니터링 파라미터 분리를 위해 공통코드에서 빼오고 따로 사용
	@RequestMapping("/mo/selectRepRoutItem")
	public @ResponseBody Map<String, Object> selectRepRoutItem() throws Exception {
		result.setData("dlt_repRoutItem_MO0101", mo0101Service.selectRepRoutItem());
		return result.getResult();
	}
}
