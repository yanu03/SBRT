package kr.tracom.cm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.cm.domain.Common.CommonService;
import kr.tracom.cm.support.ControllerSupport;
import kr.tracom.util.Result;
import kr.tracom.util.UserInfo;

@Controller
@Scope("request")
public class CommonController extends ControllerSupport {

	@Autowired
	private CommonService commonService;

	@Autowired
	private UserInfo user;

	/**
	 * selectCommonSearchItem - 공통코드 아이템 리스트를 조회한다.
	 * 
	 * @date 2017.12.22
	 * @param {} 없음
	 * @returns mv dlt_commonSearchItem ( 공통코드 아이템 리스트 )
	 * @author tracom
	 * @example
	 */
	@RequestMapping("/common/selectCommonSearchItem")
	public @ResponseBody Map<String, Object> selectCommonSearchItem() throws Exception {
		result.setData("dlt_commonSearchItem", commonService.selectCommonSearchItem());
		return result.getResult();
	}

	/**
	 * CommonDtl - 모든 공통코드를 조회한다.
	 * 
	 * @date 2017.12.22
	 * @param {} 없음
	 * @returns mv List : 공통코드 전체 리스트
	 * @author tracom
	 * @example
	 */
	@RequestMapping("/common/selectCommonDtl")
	public @ResponseBody Map<String, Object> selectCommonDtl() throws Exception {
		result.setData("dlt_commonDtl", commonService.selectCommonDtlAll());
		return result.getResult();
	}
	
	/**
	 * selectMenuList - 조회조건에 따른 메뉴 리스트를 조회한다.
	 * 
	 * @date 2017.12.22
	 * @param {} dma_userInfo { SSN_USER_ID :"사용자 ID" }
	 * @returns mv List ( 사용자의 메뉴 리스트 )
	 * @author tracom
	 * @example
	 */
	/*@RequestMapping("/common/selectMenuList")
	public @ResponseBody Map<String, Object> selectMenuList() throws Exception {
		result.setData("dlt_menu", commonService.selectMenuList(user.getUserInfo()));
		return result.getResult();
	}*/

	/**
	 * CommonDtlList - 조회조건에 따른 공통코드 리스트를 조회한다.
	 * 
	 * @date 2017.12.22
	 * @param {} dma_commonCO { CO_CD : "공통그룹 코드" }
	 * @returns mv dlt_commonDtl ( 공통코드 리스트 );
	 * @author tracom
	 * @example
	 */
	@RequestMapping("/common/selectCommonDtlList")
	public @ResponseBody Map<String, Object> selectCommonDtlList() throws Exception {
		Result result = new Result();
		result.setData("dlt_commonDtl", commonService.selectCommonDtlList());
		return result.getResult();
	}
	
	@RequestMapping("/common/selectCommonCo")
	public @ResponseBody Map<String, Object> selectCommonCo() throws Exception {
		result.setData("dlt_commonCo", commonService.selectCommonCo());
		return result.getResult();
	}

	/**
	 * updateCommonCoAll - 공통그룹 리스트 및 하위 코드정보를 등록 수정 삭제 한다.
	 * 
	 * @date 2017.12.22
	 * @param {} dlt_commonCo ( 공통코드그룹 상태인( C,U,D ) 리스트 ), dlt_commonDtl ( 공통코드 상태인( C,U,D ) 리스트 )
	 * @returns mv dlt_result (입력,수정,삭제된 건수 및 상태)
	 * @author tracom
	 * @example
	 */
	@RequestMapping("/common/updateCommonCoAll")
	public @ResponseBody Map<String, Object> updateCommonCoAll() throws Exception {
		Map hash = commonService.saveCodeCoListAll();

		result.setData("dma_result_All", hash);
		return result.getResultSave();
	}

	/**
	 * updateCommonCo - 공통그룹 리스트를 등록 수정 삭제 한다.
	 * 
	 * @date 2017.12.22
	 * @param {} dlt_commonCo ( 공통코드그룹 상태인( C,U,D ) 리스트 ), dma_search ( 조회조건 )
	 * @returns mv dlt_result (입력,수정,삭제된 건수 및 상태), dlt_commonCo ( 공통코드 그룹 리스트 )
	 * @author tracom
	 * @example
	 */
	@RequestMapping("/common/updateCommonCo")
	public @ResponseBody Map<String, Object> updateCommonCo() throws Exception {
		Map hash = commonService.saveCodeCoList();
		result.setData("dma_result", hash);
		result.setData("dlt_commonCo", commonService.selectCommonCo());
		return result.getResultSave();
	}

	/**
	 * CommonDtlUpdate - 공통그룹코드 리스트를 등록 수정 삭제 한다.
	 * 
	 * @date 2017.12.22
	 * @param {} dlt_commonDtl ( 공통코드 상태인( C,U,D ) 리스트 ), dma_commonCO ( 조회조건 )
	 * @returns mv dma_result ( 입력,수정,삭제된 건수 및 상태 ), dlt_commonDtl ( 공통코드 리스트 )
	 * @author tracom
	 * @example
	 */
	@RequestMapping("/common/selectCommonDtlUpdate")
	public @ResponseBody Map<String, Object> selectCommonDtlUpdate() throws Exception {
		Map hash = commonService.saveCodeDtlList();
		result.setData("dma_result", hash);
		result.setData("dlt_commonDtl", commonService.selectCommonDtlList());
		return result.getResultSave();
	}

	/**
	 * GetCodeList - 공통코드 조회 dma_commonDtl : {CO_CD:"02,01", DATA_PREFIX:"dlt_code"} <String> CO_CD : 코드값,코드값 <String> DATA_PREFIX :
	 * "Data객체의 ID prefix 없을 경우 dlt_commonDtl_"
	 * 
	 * @date 2017.12.22
	 * @param param {dma_commonDtl : {CO_CD:"02,01", DATA_PREFIX:"dlt_code"}}
	 * @author tracom
	 * @example
	 */
	@RequestMapping("/common/selectCodeList")
	public @ResponseBody Map<String, Object> selectCodeList() throws Exception {

		Map commonDtl = getSimpleDataMap("dma_commonDtl");
		String dataIdPrefix = dataIdPrefix = (String) commonDtl.get("DATA_PREFIX");
		if (dataIdPrefix == null) {
			dataIdPrefix = "dlt_commonDtl_";
		}
		
		List codeList = commonService.selectCodeList();
		
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
	 * GetCodeList - 공통코드 조회 dma_commonDtl : {CO_CD:"02,01", DATA_PREFIX:"dlt_code"} <String> CO_CD : 코드값,코드값 <String> DATA_PREFIX :
	 * "Data객체의 ID prefix 없을 경우 dlt_commonDtl_"
	 * 
	 * @date 2017.12.22
	 * @param param {dma_commonDtl : {CO_CD:"02,01", DATA_PREFIX:"dlt_code"}}
	 * @author tracom
	 * @example
	 */
	@RequestMapping("/common/selectCodeList2")
	public @ResponseBody Map<String, Object> selectCodeList2() throws Exception {

		Map commonDtl = getSimpleDataMap("dma_commonDtl");
		String dataIdPrefix = dataIdPrefix = (String) commonDtl.get("DATA_PREFIX");

		String prefix = (String) commonDtl.get("DL_CD");
		
		if (dataIdPrefix == null) {
			dataIdPrefix = "dlt_commonDtl_";
		}
		
		List codeList = commonService.selectCodeList2();
		
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
				result.setData(prefix+dataIdPrefix + preCode, codeCoList);
			}
		}

		return result.getResult();
	}
	
	/**
	 * GetCodeList - 시스템코드 조회 dma_commonDtl : {CO_CD:"02,01", DATA_PREFIX:"dlt_code"} <String> CO_CD : 코드값,코드값 <String> DATA_PREFIX :
	 * "Data객체의 ID prefix 없을 경우 dlt_systemCode_"
	 * 
	 * @date 2021.04.28
	 * @param param {dma_commonDtl : {CO_CD:"02,01", DATA_PREFIX:"dlt_code"}}
	 * @author tracom
	 * @example
	 */
	@RequestMapping("/common/selectSystemList")
	public @ResponseBody Map<String, Object> selectSystemList() throws Exception {
		
		Map commonDtl = getSimpleDataMap("dma_systemCode");
		String dataIdPrefix = dataIdPrefix = (String) commonDtl.get("DATA_PREFIX");
		if (dataIdPrefix == null) {
			dataIdPrefix = "dlt_systemCode_";
		}
		
		List systemList = commonService.selectSystemList();

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