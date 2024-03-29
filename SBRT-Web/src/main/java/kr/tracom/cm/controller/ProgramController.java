package kr.tracom.cm.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.cm.domain.Program.ProgramService;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class ProgramController extends ControllerSupport {

	@Autowired
	private ProgramService programService;

	/**
	 * searchProgram - 조회조건에 따른 프로그램관리 리스트를 조회한다.
	 * 
	 * @date 2017.12.22
	 * @param {} dma_search { TYPE:"프로그램명 또는 프로그램코드 또는 부모프로그램명 또는 프로그램레벨", CONTENTS:"검색어", USE_YN:"사용여부" }
	 * @throws Exception 
	 * @returns mv dlt_program ( 프로그램관리 리스트 ) author tracom
	 * @example
	 */
	@RequestMapping("/program/searchProgram")
	public @ResponseBody Map<String, Object> searchProgram() throws Exception {
		result.setData("dlt_program", programService.selectProgram());
		return result.getResult();
	}
	
	/**
	 * searchUserAuthProgram - 조회조건에 따른 프로그램관리 리스트를 조회한다.
	 * 
	 * @date 2022.01.13
	 * @param {} dma_program { USER_ID:"사용자아이디"}
	 * @throws Exception 
	 * @returns mv dlt_userAuthProgram ( 프로그램관리 리스트 ) author ko
	 * @example
	 */
	@RequestMapping("/program/searchUserAuthProgram")
	public @ResponseBody Map<String, Object> searchUserAuthProgram() throws Exception {
		result.setData("dlt_userAuthProgram", programService.searchUserAuthProgram());
		return result.getResult();
	}
	
	/**
	 * searchOrgAuthProgram - 조회조건에 따른 프로그램관리 리스트를 조회한다.
	 * 
	 * @date 2022.01.13
	 * @param {} dma_program { ORG_ID:"조직아이디"}
	 * @throws Exception 
	 * @returns mv dlt_orgAuthProgram ( 프로그램관리 리스트 ) author ko
	 * @example
	 */
	@RequestMapping("/program/searchOrgAuthProgram")
	public @ResponseBody Map<String, Object> searchOrgAuthProgram() throws Exception {
		result.setData("dlt_orgAuthProgram", programService.searchOrgAuthProgram());
		return result.getResult();
	}

	/**
	 * updateProgram - 프로그램관리 리스트를 등록 수정 삭제 한다.
	 * 
	 * @date 2017.12.22
	 * @param {} dlt_program ( 프로그램관리 상태인( C,U,D ) 리스트 ), dma_search { TYPE:"프로그램명 또는 프로그램코드 또는 부모프로그램명 또는 프로그램레벨", CONTENTS:"검색어" }
	 * @throws Exception 
	 * @returns mv dlt_result ( 입력,수정,삭제된 건수 및 상태 ), dlt_program ( 프로그램관리 리스트 ) author tracom
	 * @example
	 */
	@RequestMapping("/program/updateProgram")
	public @ResponseBody Map<String, Object> updateProgram() throws Exception {

		Map hash = programService.saveProgram();
		result.setData("dma_result", hash);
		result.setData("dlt_program", programService.selectProgram());
		return result.getResultSave();
	}

	/**
	 * saveUserAuthProgram - 사용자별 프로그램 권한 등록 수정 삭제 한다.
	 * 
	 * @date 2022.01.13
	 * @param {} dlt_userAuthProgram ( 프로그램관리 상태인( C,U,D ) 리스트 )
	 * @throws Exception 
	 * @returns mv dlt_result ( 입력,수정,삭제된 건수 및 상태 )author ko
	 * @example
	 */
	@RequestMapping("/program/saveUserAuthProgram")
	public @ResponseBody Map<String, Object> saveUserAuthProgram() throws Exception {
		Map map = programService.saveUserAuthProgram();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	/**
	 * saveOrgAuthProgram - 조직별 프로그램 권한 등록 수정 삭제 한다.
	 * 
	 * @date 2022.01.13
	 * @param {} dlt_orgAuthProgram ( 프로그램관리 상태인( C,U,D ) 리스트 )
	 * @throws Exception 
	 * @returns mv dlt_result ( 입력,수정,삭제된 건수 및 상태 )author ko
	 * @example
	 */
	@RequestMapping("/program/saveOrgAuthProgram")
	public @ResponseBody Map<String, Object> saveOrgAuthProgram() throws Exception {
		Map map = programService.saveOrgAuthProgram();
		result.setData("dma_result", map);
		return result.getResultSave();
	}
	
	/**
	 * updateProgram - 프로그램관리 리스트를 등록 수정 삭제 한다.
	 * 
	 * @date 2017.12.22
	 * @param {} dlt_program ( 상태인( C,U,D ) 프로그램 리스트 ), dlt_programAuthority ( 상태인( C,U,D ) 프로그램별 권한 리스트 )
	 * @throws Exception 
	 * @returns mv dlt_result ( 입력,수정,삭제된 건수 및 상태 ), dlt_program ( 프로그램관리 리스트 ) author tracom
	 * @example
	 */
	@RequestMapping("/program/saveProgramAll")
	public @ResponseBody Map<String, Object> saveProgramAll() throws Exception {
		Map hash = programService.saveProgramAll();

		result.setData("dma_result_All", hash);

		return result.getResultSave();
	}

	/**
	 * searchProgramAuthority - 조회조건에 따른 프로그램별 접근 프로그램 리스트를 조회한다.
	 * 
	 * @date 2017.12.22
	 * @param {} dma_program ( 프로그램관리 리스트 )
	 * @throws Exception 
	 * @returns mv dlt_programAuthority ( 프로그램별 권한 리스트 ) author tracom
	 * @example
	 */
	@RequestMapping("/program/searchProgramAuthority")
	public @ResponseBody Map<String, Object> searchProgramAuthority() throws Exception {
		result.setData("dlt_programAuthority", programService.selectProgramAuthority());
		return result.getResult();
	}

	/**
	 * updateProgramAuthority - 프로그램별 접근 프로그램 리스트를 등록 수정 삭제 한다.
	 * 
	 * @date 2017.12.22
	 * @param {} dlt_programAuthority ( 프로그램관리 상태인( C,U,D ) 리스트 ), dma_program { MENU_CD:"프로그램코드" }
	 * @throws Exception 
	 * @returns mv dlt_result ( 입력,수정,삭제된 건수 및 상태 ), dlt_programAuthority ( 프로그램별 접근 프로그램 리스트 ) author tracom
	 * @example
	 */
	@RequestMapping("/program/updateProgramAuthority")
	public @ResponseBody Map<String, Object> updateProgramAuthority() throws Exception { 
		Map hash = programService.saveProgramAuthority();
		result.setData("dma_result", hash);
		result.setData("dlt_programAuthority", programService.selectProgramAuthority());
		return result.getResultSave();
	}

	/**
	 * excludeSelectProgramAuthority - 조회조건에 따른 프로그램별 접근 프로그램 등록 리스트를 조회한다.
	 * 
	 * @date 2017.12.22
	 * @param {} dma_program { TYPE:"권한명 또는 권한코드", CONTENTS:"검색어", MENU_CD:"프로그램코드" }
	 * @throws Exception 
	 * @returns mv dlt_programAuthority ( 프로그램별 접근 프로그램 리스트 ) author tracom
	 * @example
	 */
	@RequestMapping("/program/excludeSelectProgramAuthority")
	public @ResponseBody Map<String, Object> excludeSelectProgramAuthority() throws Exception {
		result.setData("dlt_programAuthority", programService.excludeSelectProgramAuthority());
		return result.getResult();
	}
}
