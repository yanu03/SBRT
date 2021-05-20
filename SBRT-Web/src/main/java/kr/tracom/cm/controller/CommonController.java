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
	 * @author InswaveSystems
	 * @example
	 */
	@RequestMapping("/common/selectCommonSearchItem")
	public @ResponseBody Map<String, Object> selectCommonSearchItem() throws Exception {
		result.setData("dlt_commonSearchItem", commonService.selectCommonSearchItem());
		return result.getResult();
	}

	/**
	 * CommonCode - 모든 공통코드를 조회한다.
	 * 
	 * @date 2017.12.22
	 * @param {} 없음
	 * @returns mv List : 공통코드 전체 리스트
	 * @author InswaveSystems
	 * @example
	 */
	@RequestMapping("/common/selectCommonCode")
	public @ResponseBody Map<String, Object> selectCommonCode() throws Exception {
		result.setData("dlt_commonCode", commonService.selectCommonCodeAll());
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
	/*@RequestMapping("/common/selectMenuList")
	public @ResponseBody Map<String, Object> selectMenuList() throws Exception {
		result.setData("dlt_menu", commonService.selectMenuList(user.getUserInfo()));
		return result.getResult();
	}*/

	/**
	 * CommonCodeList - 조회조건에 따른 공통코드 리스트를 조회한다.
	 * 
	 * @date 2017.12.22
	 * @param {} dma_commonGrp { CO_CD : "공통그룹 코드" }
	 * @returns mv dlt_commonCode ( 공통코드 리스트 );
	 * @author InswaveSystems
	 * @example
	 */
	@RequestMapping("/common/selectCommonCodeList")
	public @ResponseBody Map<String, Object> selectCommonCodeList() throws Exception {
		Result result = new Result();
		result.setData("dlt_commonCode", commonService.selectCommonCodeList());
		return result.getResult();
	}
	
	@RequestMapping("/common/selectCommonGroup")
	public @ResponseBody Map<String, Object> selectCommonGroup() throws Exception {
		result.setData("dlt_commonGrp", commonService.selectCommonGroup());
		return result.getResult();
	}

	/**
	 * updateCommonGrpAll - 공통그룹 리스트 및 하위 코드정보를 등록 수정 삭제 한다.
	 * 
	 * @date 2017.12.22
	 * @param {} dlt_commonGrp ( 공통코드그룹 상태인( C,U,D ) 리스트 ), dlt_commonCode ( 공통코드 상태인( C,U,D ) 리스트 )
	 * @returns mv dlt_result (입력,수정,삭제된 건수 및 상태)
	 * @author InswaveSystems
	 * @example
	 */
	@RequestMapping("/common/updateCommonGrpAll")
	public @ResponseBody Map<String, Object> updateCommonGrpAll() throws Exception {
		Map hash = commonService.saveCodeGrpListAll();

		result.setData("dma_result_All", hash);
		return result.getResult();
	}

	/**
	 * updateCommonGrp - 공통그룹 리스트를 등록 수정 삭제 한다.
	 * 
	 * @date 2017.12.22
	 * @param {} dlt_commonGrp ( 공통코드그룹 상태인( C,U,D ) 리스트 ), dma_search ( 조회조건 )
	 * @returns mv dlt_result (입력,수정,삭제된 건수 및 상태), dlt_commonGrp ( 공통코드 그룹 리스트 )
	 * @author InswaveSystems
	 * @example
	 */
	@RequestMapping("/common/updateCommonGrp")
	public @ResponseBody Map<String, Object> updateCommonGrp() throws Exception {
		Map hash = commonService.saveCodeGrpList();
		result.setData("dma_result", hash);
		result.setData("dlt_commonGrp", commonService.selectCommonGroup());
		return result.getResult();
	}

	/**
	 * CommonCodeUpdate - 공통그룹코드 리스트를 등록 수정 삭제 한다.
	 * 
	 * @date 2017.12.22
	 * @param {} dlt_commonCode ( 공통코드 상태인( C,U,D ) 리스트 ), dma_commonGrp ( 조회조건 )
	 * @returns mv dma_result ( 입력,수정,삭제된 건수 및 상태 ), dlt_commonCode ( 공통코드 리스트 )
	 * @author InswaveSystems
	 * @example
	 */
	@RequestMapping("/common/selectCommonCodeUpdate")
	public @ResponseBody Map<String, Object> selectCommonCodeUpdate(@RequestBody Map<String, Object> param) throws Exception {
		Map hash = commonService.saveCodeList();
		result.setData("dma_result", hash);
		result.setData("dlt_commonCode", commonService.selectCommonCodeList());
		return result.getResult();
	}

	/**
	 * GetCodeList - 공통코드 조회 dma_commonCode : {CO_CD:"02,01", DATA_PREFIX:"dlt_code"} <String> CO_CD : 코드값,코드값 <String> DATA_PREFIX :
	 * "Data객체의 ID prefix 없을 경우 dlt_commonCode_"
	 * 
	 * @date 2017.12.22
	 * @param param {dma_commonCode : {CO_CD:"02,01", DATA_PREFIX:"dlt_code"}}
	 * @author InswaveSystems
	 * @example
	 */
	@RequestMapping("/common/selectCodeList")
	public @ResponseBody Map<String, Object> selectCodeList() throws Exception {

		Map commonCode = getSimpleDataMap("dma_commonCode");
		String dataIdPrefix = dataIdPrefix = (String) commonCode.get("DATA_PREFIX");
		if (dataIdPrefix == null) {
			dataIdPrefix = "dlt_commonCode_";
		}
		
		List codeList = commonService.selectCodeList();
		
		int size = codeList.size();
		String preCode = "";
		List codeGrpList = null;
		for (int i = 0; i < size; i++) {
			Map codeMap = (Map) codeList.remove(0);
			String grp_cd = (String) codeMap.get("CO_CD");
			if (!preCode.equals(grp_cd)) {
				if (codeGrpList != null) {
					result.setData(dataIdPrefix + preCode, codeGrpList);
				}
				preCode = grp_cd;
				codeGrpList = new ArrayList();
				codeGrpList.add(codeMap);
			} else {
				codeGrpList.add(codeMap);
			}

			if (i == size - 1) {
				result.setData(dataIdPrefix + preCode, codeGrpList);
			}
		}

		return result.getResult();
	}
	
	/**
	 * GetCodeList - 시스템코드 조회 dma_commonCode : {CO_CD:"02,01", DATA_PREFIX:"dlt_code"} <String> CO_CD : 코드값,코드값 <String> DATA_PREFIX :
	 * "Data객체의 ID prefix 없을 경우 dlt_systemCode_"
	 * 
	 * @date 2021.04.28
	 * @param param {dma_commonCode : {CO_CD:"02,01", DATA_PREFIX:"dlt_code"}}
	 * @author InswaveSystems
	 * @example
	 */
	@RequestMapping("/common/selectSystemList")
	public @ResponseBody Map<String, Object> selectSystemList() throws Exception {
		
		Map commonCode = getSimpleDataMap("dma_systemCode");
		String dataIdPrefix = dataIdPrefix = (String) commonCode.get("DATA_PREFIX");
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