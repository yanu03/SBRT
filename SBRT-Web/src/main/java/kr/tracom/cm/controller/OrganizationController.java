package kr.tracom.cm.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.cm.domain.Organization.OrganizationService;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class OrganizationController extends ControllerSupport {

	@Autowired
	private OrganizationService service;

	/**
	 * selectOrganizaionSearchItem - 조직관리 아이템 리스트를 조회한다.
	 * 
	 * @date 2017.12.22
	 * @returns mv dlt_organizaionSearchItem ( 조직관리 아이템 리스트 )
	 * @author Inswave
	 * @throws Exception 
	 * @example
	 */
	@RequestMapping("/organization/selectOrganizaionSearchItem")
	public @ResponseBody Map<String, Object> selectOrganizaionSearchItem() throws Exception {
		result.setData("dlt_organizationSearchItem", service.selectOrganizaionSearchItem());
		return result.getResult();
	}

	/**
	 * searchOrganization - 조회조건에 따른 조직기본관리 리스트를 조회한다.
	 * 
	 * @date 2017.12.22
	 * @param {} dma_search { TYPE:"기관명 또는 기관코드 또는 상위소속명 또는 직책자", CONTENTS:"검색어", USE_YN:"사용여부" }
	 * @returns mv dlt_organizationBasic ( 조직관리 리스트 )
	 * @author Inswave
	 * @throws Exception 
	 * @example
	 */
	@RequestMapping("/organization/searchOrganization")
	public @ResponseBody Map<String, Object> searchOrganization() throws Exception {
		result.setData("dlt_organizationBasic", service.selectOrganization());
		return result.getResult();
	}

	/**
	 * updateOrganizationBasic
	 * 
	 * @date 2017.12.22
	 * @param {} dlt_organizationBasic ( 조직기본관리 상태인( C,U,D ) 리스트 )
	 * @returns mv dlt_result ( 입력,수정,삭제된 건수 및 상태 ), dlt_organizationBasic ( 조직기본관리 리스트 )
	 * @author Inswave
	 * @throws Exception 
	 * @example
	 */
	@RequestMapping("/organization/updateOrganizationBasic")
	public @ResponseBody Map<String, Object> updateOrganizationBasic() throws Exception {
		Map hash = service.saveOrganizaionBasicList();
		result.setData("dma_result", hash);
		result.setData("dlt_organizationBasic", service.selectOrganization());
		return result.getResultSave();
	}

	/**
	 * 조직 데이터를 조회한다.
	 * 
	 * @param param 클라이언트에서 전달한 데이터 맵 객체
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/organization/selectOrganBasicList")
	public @ResponseBody Map<String, Object> getOrganBasicList() throws Exception {
		result.setData("data", service.selectOrganizaionBasicList());
		return result.getResult();
	}
	
	@RequestMapping("/organization/selectOrganizaionKey")
	public @ResponseBody Map<String, Object> selectAuthorityKey() throws Exception {
		result.setData("dma_SEQ_BMS_ORG_MST_0", service.selectOrganizaionKey());
		return result.getResult();
	}
}
