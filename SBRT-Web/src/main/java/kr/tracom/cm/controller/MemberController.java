package kr.tracom.cm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.cm.domain.Member.MemberService;
import kr.tracom.cm.support.ControllerSupport;
import kr.tracom.util.Result;

@Controller
@Scope("request")
public class MemberController extends ControllerSupport{
	
	@Autowired
	private MemberService service;

	/**
	 * searchMemberBasicOrganization - 조회조건에 따른 인사기본관리 리스트를 조회한다.
	 * 
	 * @date 2017.12.22
	 * @returns mv dlt_memberOrganization ( 인사관리 소속 )
	 * @author tracom
	 * @example
	 */
	@RequestMapping("/member/searchMemberBasicOrganization")
	public @ResponseBody Map<String, Object> searchMemberBasicOrganization() throws Exception {
		result.setData("dlt_memberOrganization", service.selectMemberBasicOrganization());
		return result.getResult();
	}

	/**
	 * selectMemberSearchItem - 인사기본관리 아이템 리스트를 조회한다.
	 * 
	 * @date 2017.12.22
	 * @param {} 없음
	 * @returns mv dlt_memberSearchItem ( 인사기본관리 아이템 리스트 )
	 * @author tracom
	 * @example
	 */
	@RequestMapping("/member/selectMemberSearchItem")
	public @ResponseBody Map<String, Object> selectMemberSearchItem() throws Exception {
		result.setData("dlt_memberSearchItem", service.selectMemberSearchItem());
		return result.getResult();
	}

	/**
	 * searchMemberBasic - 조회조건에 따른 인사기본관리 리스트를 조회한다.
	 * 
	 * @date 2017.12.22
	 * @param {} dma_search { TYPE:"사원명 또는 사원코드 또는 직위 또는 소속", CONTENTS:"검색어", USE_YN:"사용여부" }
	 * @returns mv dlt_memberBasic ( 인사기본관리 리스트 )
	 * @author tracom
	 * @example
	 */
	@RequestMapping("/member/searchMemberBasic")
	public @ResponseBody Map<String, Object> searchMemberBasic() throws Exception {
		result.setData("dlt_memberBasic", service.selectMemberBasic());
		return result.getResult();
	}

	/**
	 * updateMemberBasic - 인사관리 리스트를 등록 수정 삭제 한다.
	 * 
	 * @date 2017.12.22
	 * @param {} dlt_memberBasic ( 인사기본관리 상태인( C,U,D ) 리스트 )
	 * @returns mv dlt_result ( 입력,수정,삭제된 건수 및 상태 ), dlt_memberBasic ( 인사기본관리 리스트 )
	 * @author tracom
	 * @example
	 */
	@RequestMapping("/member/updateMemberBasic")
	public @ResponseBody Map<String, Object> updateMemberBasic() throws Exception {
		Map hash = service.saveMemberBasicList();
		result.setData("dma_result", hash);
		result.setData("dlt_memberBasic", service.selectMemberBasic());
		return result.getResultSave();
	}
	
	/**
	 * 개인 기본 데이터를 조회한다.
	 * 
	 * @param param 클라이언트에서 전달한 데이터 맵 객체
	 * @return
	 */
	@RequestMapping(value = "/member/selectMemberOragn")
	public @ResponseBody Map<String, Object> selectMemberOragn() throws Exception {
		result.setData("data", service.selectMemberOragn());
		return result.getResult();
	}

	@RequestMapping(value = "/member/saveMemberInfo")
	public @ResponseBody Map<String, Object> saveMemberInfo() throws Exception {
		Map hash = new HashMap();
		hash.put("basic", service.saveMemberBasic());
		result.setData("data", hash);
		return result.getResult();
	}
	
	/**
	 * 우편번호 데이터를 조회한다.
	 * 
	 * @param param 클라이언트에서 전달한 데이터 맵 객체
	 * @return
	 */
	@RequestMapping(value = "/member/selectZipCodeList")
	public @ResponseBody Map<String, Object> selectZipCodeList() throws Exception {
		result.setData("data", service.selectZipCodeList());

		return result.getResult();
	}
}
