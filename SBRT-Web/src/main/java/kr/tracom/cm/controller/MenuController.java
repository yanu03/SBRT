package kr.tracom.cm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.cm.domain.Menu.MenuService;
import kr.tracom.cm.support.ControllerSupport;
import kr.tracom.util.Result;

@Controller
@Scope("request")
public class MenuController extends ControllerSupport{

	@Autowired
	private MenuService menuService;

	/**
	 * searchMenu - 조회조건에 따른 메뉴관리 리스트를 조회한다.
	 * 
	 * @date 2017.12.22
	 * @param {} dma_search { TYPE:"메뉴명 또는 메뉴코드 또는 부모메뉴명 또는 메뉴레벨", CONTENTS:"검색어", USE_YN:"사용여부" }
	 * @returns mv dlt_menu ( 메뉴관리 리스트 ) author tracom
	 * @example
	 */
	@RequestMapping("/menu/searchMenu")
	public @ResponseBody Map<String, Object> searchMenu() throws Exception {
		result.setData("dlt_menu", menuService.selectMenu());
		result.setMsg(result.STATUS_SUCESS, "메뉴 리스트가 조회되었습니다.");
		return result.getResult();
	}
	
	@RequestMapping("/menu/searchMenuItem")
	public @ResponseBody Map<String, Object> searchMenuItem() throws Exception {
		result.setData("dlt_menuSearchItem", menuService.searchMenuItem());
		result.setMsg(result.STATUS_SUCESS, "메뉴 리스트가 조회되었습니다.");
		return result.getResult();
	}


	/**
	 * updateMenu - 메뉴관리 리스트를 등록 수정 삭제 한다.
	 * 
	 * @date 2017.12.22
	 * @param {} dlt_menu ( 메뉴관리 상태인( C,U,D ) 리스트 ), dma_search { TYPE:"메뉴명 또는 메뉴코드 또는 부모메뉴명 또는 메뉴레벨", CONTENTS:"검색어" }
	 * @throws Exception 
	 * @returns mv dlt_result ( 입력,수정,삭제된 건수 및 상태 ), dlt_menu ( 메뉴관리 리스트 ) author tracom
	 * @example
	 */
	@RequestMapping("/menu/updateMenu")
	public @ResponseBody Map<String, Object> updateMenu() throws Exception {
		Map hash = menuService.saveMenu();
		result.setData("dma_result", hash);
		result.setData("dlt_menu", menuService.selectMenu());
		return result.getResult();
	}
}
