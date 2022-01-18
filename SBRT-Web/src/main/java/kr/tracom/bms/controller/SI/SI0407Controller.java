package kr.tracom.bms.controller.SI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.SI0407.SI0407Service;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class SI0407Controller extends ControllerSupport {
	
	@Autowired
	private SI0407Service si0407Service;
	
	@RequestMapping("/si/SI0407G0R0")
	public @ResponseBody Map<String, Object> SI0407G0R0() throws Exception {
		result.setData("dlt_BMS_MOCK_NODE", si0407Service.SI0407G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0407G0S0")
	public @ResponseBody Map<String, Object> SI0407G0S0() throws Exception {
		Map map = si0407Service.SI0407G0S0();
		result.setData("dma_result", map);
		return result.getResultSave();		
	}
	
	//표준노드 검색 항목
	@RequestMapping("/si/SI0407SHI0")
	public @ResponseBody Map<String, Object> SI0407SHI0() throws Exception {
		result.setData("dlt_searchitem", si0407Service.SI0407SHI0());
		return result.getResult();
	}	
	
	@RequestMapping("/si/SI0407G1R0")
	public @ResponseBody Map<String, Object> SI0407G1R0() throws Exception {
		result.setData("dlt_BMS_MOCK_LINK", si0407Service.SI0407G1R0());
		return result.getResult();
	}
	
	@RequestMapping("/si/SI0407G1S0")
	public @ResponseBody Map<String, Object> SI0407G1S0() throws Exception {
		Map map = si0407Service.SI0407G1S0();
		result.setData("dma_result", map);
		return result.getResultSave();		
	}

	//표준 링크 검색 항목
	@RequestMapping("/si/SI0407SHI1")
	public @ResponseBody Map<String, Object> SI0407SHI1() throws Exception {
		result.setData("dlt_searchitem2", si0407Service.SI0407SHI1());
		return result.getResult();
	}
	
	//표준 링크 시작,종료 노드 매핑
	@RequestMapping("/si/SI0407SHI2")
	public @ResponseBody Map<String, Object> SI0407SHI2() throws Exception {
		result.setData("dlt_searchitem3", si0407Service.SI0407SHI2());
		return result.getResult();
	}

}
