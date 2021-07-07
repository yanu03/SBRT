package kr.tracom.bms.controller.SI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.SI0701.SI0701Service;
import kr.tracom.cm.support.ControllerSupport;
import kr.tracom.util.Result;
import kr.tracom.util.UserInfo;

@Controller
@Scope("request")
public class SI0701Controller extends ControllerSupport{
	@Autowired
	private SI0701Service si0701Service;

	@Autowired
	private UserInfo user;

	/**
	 * selectSI0701SearchItem - 공통코드 아이템 리스트를 조회한다.
	 * 
	 * @date 2017.12.22
	 * @param {} 없음
	 * @returns mv dlt_si0701SearchItem ( 공통코드 아이템 리스트 )
	 * @author InswaveSystems
	 * @example
	 */
	@RequestMapping("/si0701/selectSI0701SearchItem")
	public @ResponseBody Map<String, Object> selectSI0701SearchItem() throws Exception {
		result.setData("dlt_si0701SearchItem", si0701Service.selectSI0701SearchItem());
		return result.getResult();
	}

	/**
	 * SI0701Dtl - 모든 공통코드를 조회한다.
	 * 
	 * @date 2017.12.22
	 * @param {} 없음
	 * @returns mv List : 공통코드 전체 리스트
	 * @author InswaveSystems
	 * @example
	 */
	@RequestMapping("/si0701/selectSI0701Dtl")
	public @ResponseBody Map<String, Object> selectSI0701Dtl() throws Exception {
		result.setData("dlt_si0701Dtl", si0701Service.selectSI0701DtlAll());
		return result.getResult();
	}
	
	/**
	 * selectMenuList - 조회조건에 따른 메뉴 리스트를 조회한다.
	 * 
	 * @date 2017.12.22
	 * @param {} dma_userInfo { SSN_USER_ID :"사용자 ID" }
	 * @returns mv List ( 사용자의 메뉴 리스트 )
	 * @author InswaveSystems
	 * @example
	 */
	/*@RequestMapping("/si0701/selectMenuList")
	public @ResponseBody Map<String, Object> selectMenuList() throws Exception {
		result.setData("dlt_menu", si0701Service.selectMenuList(user.getUserInfo()));
		return result.getResult();
	}*/

	/**
	 * SI0701DtlList - 조회조건에 따른 공통코드 리스트를 조회한다.
	 * 
	 * @date 2017.12.22
	 * @param {} dma_si0701CO { CO_CD : "공통그룹 코드" }
	 * @returns mv dlt_si0701Dtl ( 공통코드 리스트 );
	 * @author InswaveSystems
	 * @example
	 */
	@RequestMapping("/si0701/selectSI0701DtlList")
	public @ResponseBody Map<String, Object> selectSI0701DtlList() throws Exception {
		Result result = new Result();
		result.setData("dlt_si0701Dtl", si0701Service.selectSI0701DtlList());
		return result.getResult();
	}
	
	@RequestMapping("/si0701/selectSI0701Co")
	public @ResponseBody Map<String, Object> selectSI0701Co() throws Exception {
		result.setData("dlt_si0701Co", si0701Service.selectSI0701Co());
		return result.getResult();
	}

	/**
	 * updateSI0701CoAll - 공통그룹 리스트 및 하위 코드정보를 등록 수정 삭제 한다.
	 * 
	 * @date 2017.12.22
	 * @param {} dlt_si0701Co ( 공통코드그룹 상태인( C,U,D ) 리스트 ), dlt_si0701Dtl ( 공통코드 상태인( C,U,D ) 리스트 )
	 * @returns mv dlt_result (입력,수정,삭제된 건수 및 상태)
	 * @author InswaveSystems
	 * @example
	 */
	@RequestMapping("/si0701/updateSI0701CoAll")
	public @ResponseBody Map<String, Object> updateSI0701CoAll() throws Exception {
		Map hash = si0701Service.saveCodeCoListAll();

		result.setData("dma_result_All", hash);
		return result.getResultSave();
	}

	/**
	 * updateSI0701Co - 공통그룹 리스트를 등록 수정 삭제 한다.
	 * 
	 * @date 2017.12.22
	 * @param {} dlt_si0701Co ( 공통코드그룹 상태인( C,U,D ) 리스트 ), dma_search ( 조회조건 )
	 * @returns mv dlt_result (입력,수정,삭제된 건수 및 상태), dlt_si0701Co ( 공통코드 그룹 리스트 )
	 * @author InswaveSystems
	 * @example
	 */
	@RequestMapping("/si0701/updateSI0701Co")
	public @ResponseBody Map<String, Object> updateSI0701Co() throws Exception {
		Map hash = si0701Service.saveCodeCoList();
		result.setData("dma_result", hash);
		result.setData("dlt_si0701Co", si0701Service.selectSI0701Co());
		return result.getResultSave();
	}

	/**
	 * SI0701DtlUpdate - 공통그룹코드 리스트를 등록 수정 삭제 한다.
	 * 
	 * @date 2017.12.22
	 * @param {} dlt_si0701Dtl ( 공통코드 상태인( C,U,D ) 리스트 ), dma_si0701CO ( 조회조건 )
	 * @returns mv dma_result ( 입력,수정,삭제된 건수 및 상태 ), dlt_si0701Dtl ( 공통코드 리스트 )
	 * @author InswaveSystems
	 * @example
	 */
	@RequestMapping("/si0701/selectSI0701DtlUpdate")
	public @ResponseBody Map<String, Object> selectSI0701DtlUpdate() throws Exception {
		Map hash = si0701Service.saveCodeDtlList();
		result.setData("dma_result", hash);
		result.setData("dlt_si0701Dtl", si0701Service.selectSI0701DtlList());
		return result.getResultSave();
	}

	/**
	 * GetCodeList - 공통코드 조회 dma_si0701Dtl : {CO_CD:"02,01", DATA_PREFIX:"dlt_code"} <String> CO_CD : 코드값,코드값 <String> DATA_PREFIX :
	 * "Data객체의 ID prefix 없을 경우 dlt_si0701Dtl_"
	 * 
	 * @date 2017.12.22
	 * @param param {dma_si0701Dtl : {CO_CD:"02,01", DATA_PREFIX:"dlt_code"}}
	 * @author InswaveSystems
	 * @example
	 */
	@RequestMapping("/si0701/selectCodeList")
	public @ResponseBody Map<String, Object> selectCodeList() throws Exception {

		Map si0701Dtl = getSimpleDataMap("dma_si0701Dtl");
		String dataIdPrefix = dataIdPrefix = (String) si0701Dtl.get("DATA_PREFIX");
		if (dataIdPrefix == null) {
			dataIdPrefix = "dlt_si0701Dtl_";
		}
		
		List codeList = si0701Service.selectCodeList();
		
		int size = codeList.size();
		String preCode = "";
		List codeCoList = null;
		for (int i = 0; i < size; i++) {
			Map codeMap = (Map) codeList.remove(0);
			String grp_cd = (String) codeMap.get("CO_CD");
			if (!preCode.equals(grp_cd)) {
				if (codeCoList != null) {
					result.setData(dataIdPrefix + preCode, codeCoList);
				}
				preCode = grp_cd;
				codeCoList = new ArrayList();
				codeCoList.add(codeMap);
			} else {
				codeCoList.add(codeMap);
			}

			if (i == size - 1) {
				result.setData(dataIdPrefix + preCode, codeCoList);
			}
		}

		return result.getResult();
	}
	
	/**
	 * GetCodeList - 시스템코드 조회 dma_si0701Dtl : {CO_CD:"02,01", DATA_PREFIX:"dlt_code"} <String> CO_CD : 코드값,코드값 <String> DATA_PREFIX :
	 * "Data객체의 ID prefix 없을 경우 dlt_systemCode_"
	 * 
	 * @date 2021.04.28
	 * @param param {dma_si0701Dtl : {CO_CD:"02,01", DATA_PREFIX:"dlt_code"}}
	 * @author InswaveSystems
	 * @example
	 */
	@RequestMapping("/si0701/selectSystemList")
	public @ResponseBody Map<String, Object> selectSystemList() throws Exception {
		
		Map si0701Dtl = getSimpleDataMap("dma_systemCode");
		String dataIdPrefix = dataIdPrefix = (String) si0701Dtl.get("DATA_PREFIX");
		if (dataIdPrefix == null) {
			dataIdPrefix = "dlt_systemCode_";
		}
		
		List systemList = si0701Service.selectSystemList();

		int size = systemList.size();
		String preCode = "";
		List systemGrpList = null;
		for (int i = 0; i < size; i++) {
			Map codeMap = (Map) systemList.remove(0);
			String grp_cd = (String) codeMap.get("SYSTEM_CD");
			if (!preCode.equals(grp_cd)) {
				if (systemGrpList != null) {
					result.setData(dataIdPrefix + preCode, systemGrpList);
				}
				preCode = grp_cd;
				systemGrpList = new ArrayList();
				systemGrpList.add(codeMap);
			} else {
				systemGrpList.add(codeMap);
			}

			if (i == size - 1) {
				result.setData(dataIdPrefix + preCode, systemGrpList);
			}
		}

		return result.getResult();
	}
}
