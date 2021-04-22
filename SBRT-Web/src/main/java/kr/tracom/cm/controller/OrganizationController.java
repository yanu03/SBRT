package kr.tracom.cm.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.cm.domain.Organization.OrganizationService;
import kr.tracom.util.Result;

@Controller
public class OrganizationController {

	@Autowired
	private OrganizationService service;

	/**
	 * selectOrganizaionSearchItem - 조직관리 아이템 리스트를 조회한다.
	 * 
	 * @date 2017.12.22
	 * @returns mv dlt_organizaionSearchItem ( 조직관리 아이템 리스트 )
	 * @author Inswave
	 * @example
	 */
	@RequestMapping("/organization/selectOrganizaionSearchItem")
	public @ResponseBody Map<String, Object> selectOrganizaionSearchItem() {
		Result result = new Result();
		try {
			result.setData("dlt_organizationSearchItem", service.selectOrganizaionSearchItem());
			result.setMsg(result.STATUS_SUCESS, "조직관리 아이템 리스트가 조회 되었습니다.");
		} catch (Exception ex) {
			result.setMsg(result.STATUS_ERROR, "조직관리 아이템 리스트를 가져오는 도중 오류가 발생하였습니다,", ex);
		}
		return result.getResult();
	}

	/**
	 * searchOrganization - 조회조건에 따른 조직기본관리 리스트를 조회한다.
	 * 
	 * @date 2017.12.22
	 * @param {} dma_search { TYPE:"기관명 또는 기관코드 또는 상위소속명 또는 직책자", CONTENTS:"검색어", USE_YN:"사용여부" }
	 * @returns mv dlt_organizationBasic ( 조직관리 리스트 )
	 * @author Inswave
	 * @example
	 */
	@RequestMapping("/organization/searchOrganization")
	public @ResponseBody Map<String, Object> searchOrganization(@RequestBody Map<String, Object> param) {
		Result result = new Result();
		try {
			result.setData("dlt_organizationBasic", service.selectOrganization((Map) param.get("dma_search")));
			result.setMsg(result.STATUS_SUCESS, "조직기본관리 리스트가 조회되었습니다.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setMsg(result.STATUS_ERROR, "조직기본관리 리스트를 가져오는 도중 오류가 발생하였습니다.", ex);
		}
		return result.getResult();
	}

	/**
	 * updateOrganizationBasic
	 * 
	 * @date 2017.12.22
	 * @param {} dlt_organizationBasic ( 조직기본관리 상태인( C,U,D ) 리스트 )
	 * @returns mv dlt_result ( 입력,수정,삭제된 건수 및 상태 ), dlt_organizationBasic ( 조직기본관리 리스트 )
	 * @author Inswave
	 * @example
	 */
	@RequestMapping("/organization/updateOrganizationBasic")
	public @ResponseBody Map<String, Object> updateOrganizationBasic(HttpServletRequest request,@RequestBody Map<String, Object> param) {
		Result result = new Result();
		try {
			HttpSession session = request.getSession();
			String	userId = (String) session.getAttribute("USER_ID");

			Map hash = service.saveOrganizaionBasicList((List) param.get("dlt_organizationBasic"), userId);
			result.setData("dma_result", hash);
			result.setData("dlt_organizationBasic", service.selectOrganization((Map) param.get("dma_search")));
			result.setMsg(result.STATUS_SUCESS, "인사기본관리 정보가 저장 되었습니다. 입력 : " + (String) hash.get("ICNT") + "건, 수정 : " + (String) hash.get("UCNT") + "건, 삭제 : "
					+ (String) hash.get("DCNT") + "건");
		} catch (Exception ex) {
			result.setMsg(result.STATUS_ERROR, "인사기본관리 정보 저장도중 오류가 발생하였습니다.", ex);
		}
		return result.getResult();
	}

	/**
	 * 조직 데이터를 조회한다.
	 * 
	 * @param param 클라이언트에서 전달한 데이터 맵 객체
	 * @return
	 */
	@RequestMapping(value = "/organization/selectOrganBasicList")
	public @ResponseBody Map<String, Object> getOrganBasicList(@RequestBody Map<String, Object> param) {
		Result result = new Result();
		try {
			result.setData("data", service.selectOrganizaionBasicList((Map) param.get("dlt_organizationBasic")));
			result.setMsg(result.STATUS_SUCESS, "조직 정보를 조회하였습니다.");
		} catch (Exception ex) {
			result.setMsg(result.STATUS_ERROR, "조직 정보를 조회하였습니다.", ex);
		}
		return result.getResult();
	}
}
