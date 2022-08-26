package kr.tracom.brt.controller.MO;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.MO0102.MO0102Service;
import kr.tracom.cm.support.ControllerSupport;

@Scope("request")
@Controller
public class MO0102Controller extends ControllerSupport {

	
	@Autowired
	private MO0102Service mo0102Service;
	
	@RequestMapping("/mo2/MO0102G0R0")
	public @ResponseBody Map<String, Object> MO0102G0R0() throws Exception {
		result.setData("dlt_BRT_CUR_OPER_INFO_MO0102", mo0102Service.MO0102G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo2/MO0102G1R0")
	public @ResponseBody Map<String, Object> MO0102G1R0() throws Exception {
		result.setData("dlt_BMS_ROUT_NODE_CMPSTN2_MO0102", mo0102Service.MO0102G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo2/MO0102SHI0")
	public @ResponseBody Map<String, Object> MO0102SHI0() throws Exception {
		result.setData("dlt_searchitem_MO0102", mo0102Service.MO0102SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/mo2/MO0102SHI1")
	public @ResponseBody Map<String, Object> MO0102SHI1() throws Exception {
		result.setData("dlt_searchitem2_MO0102", mo0102Service.MO0102SHI1());
		return result.getResult();
	}
	
	@RequestMapping("/mo2/MO0102G2R0")
	public @ResponseBody Map<String, Object> MO0102G2R0() throws Exception {
		result.setData("dlt_BMS_ROUT_NODE_CMPSTN3_MO0102", mo0102Service.MO0102G2R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo2/selectCategory")
	public @ResponseBody Map<String, Object> selectCategory() throws Exception {
		result.setData("dlt_BMS_NODE_MST_MO0102_MO0102", mo0102Service.selectCategory());
		return result.getResult();
	}
	
	@RequestMapping("/mo2/selectSigOper")
	public @ResponseBody Map<String, Object> selectSigOper() throws Exception {
		result.setData("dlt_BMS_ROUT_NODE_CMPSTN4_MO0102", mo0102Service.selectSigOper());
		return result.getResult();
	}
	
	@RequestMapping("/mo2/selectSigPeriod")
	public @ResponseBody Map<String, Object> selectSigPeriod() throws Exception {
		result.setData("dlt_SIG_PERIOD_MO0102", mo0102Service.selectSigPeriod());
		return result.getResult();
	}		
	
	@RequestMapping("/mo2/MO0102SCK")
	public @ResponseBody Map<String, Object> MO0102SCK() throws Exception {		
		
		result.setData("", mo0102Service.MO0102SCK());
		return result.getResult();
	}	
	
	@RequestMapping("/mo2/MO0102P0R0")
	public @ResponseBody Map<String, Object> MO0102P0R0() throws Exception {
		result.setData("dlt_BRT_OPER_INST_MSG_MST_MO0102", mo0102Service.MO0102P0R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo2/MO0102SCK1")
	public @ResponseBody Map<String, Object> MO0102SCK1() throws Exception {		
		
		result.setData("", mo0102Service.MO0102SCK1());
		return result.getResult();
	}
	
	@RequestMapping("/mo2/MO0102G3R0")
	public @ResponseBody Map<String, Object> MO0102G3R0() throws Exception {
		result.setData("dlt_BRT_OPER_EVENT_HIS_MO0102", mo0102Service.MO0102G3R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo2/MO0102G4R0")
	public @ResponseBody Map<String, Object> MO0102G4R0() throws Exception {
		result.setData("dlt_BRT_DSPTCH_LOG_MO0102", mo0102Service.MO0102G4R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo2/MO0102G5R0")
	public @ResponseBody Map<String, Object> MO0102G5R0() throws Exception {
		result.setData("dlt_FCLT_COND_LOG_MO0102", mo0102Service.MO0102G5R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo2/MO0102G6R0")
	public @ResponseBody Map<String, Object> MO0102G6R0() throws Exception {
		result.setData("dlt_FCLT_COND_LOG_MO0102", mo0102Service.MO0102G6R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo2/MO0102G7R0")
	public @ResponseBody Map<String, Object> MO0102G7R0() throws Exception {
		result.setData("dlt_BMS_CRS_SIGOPER_EVENT_HIS_MO0102", mo0102Service.MO0102G7R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo2/selectCommuMap")
	public @ResponseBody Map<String, Object> selectCommuMap() throws Exception {
		result.setData("dlt_BMS_LINK_MST_MO0102", mo0102Service.selectCommuMap());
		return result.getResult();
	}
	
	@RequestMapping("/mo2/MO0102SCK2")
	public @ResponseBody Map<String, Object> MO0102SCK2() throws Exception {		
		result.setData("", mo0102Service.MO0102SCK2());
		return result.getResult();
	}	
	
	@RequestMapping("/mo2/selectSigPhaseInfo")
	public @ResponseBody Map<String, Object> selectSigPhaseInfo() throws Exception {
		result.setData("dlt_BMS_CRS_SIGOPER_PHASE_INFO_HIS_MO0102", mo0102Service.selectSigPhaseInfo());
		return result.getResult();
	}
	
	@RequestMapping("/mo2/selectGrgRdsInfo")
	public @ResponseBody Map<String, Object> selectGrgRdsInfo() throws Exception {		
		result.setData("dlt_BMS_GRG_RDS_INFO_MO0102", mo0102Service.selectGrgRdsInfo());
		return result.getResult();
	}	
	
	//모니터링 파라미터 분리를 위해 공통코드에서 빼오고 따로 사용
	@RequestMapping("/mo2/selectNodeDispListByRepRout")
	public @ResponseBody Map<String, Object> selectNodeDispListByRepRout() throws Exception {
		result.setData("dlt_BMS_ROUT_NODE_DISP_VW_MO0102", mo0102Service.selectNodeDispListByRepRout());
		return result.getResult();
	}
	
	//모니터링 파라미터 분리를 위해 공통코드에서 빼오고 따로 사용
	@RequestMapping("/mo2/selectRepRoutItem")
	public @ResponseBody Map<String, Object> selectRepRoutItem() throws Exception {
		result.setData("dlt_repRoutItem_MO0102", mo0102Service.selectRepRoutItem());
		return result.getResult();
	}
	
}
