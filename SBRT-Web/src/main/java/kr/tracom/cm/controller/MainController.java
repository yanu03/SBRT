package kr.tracom.cm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.cm.domain.Common.CommonService;
import kr.tracom.util.Constants;
import kr.tracom.util.Result;
import kr.tracom.util.UserInfo;

@Controller
public class MainController {

	@Autowired
	private UserInfo user;

	@Autowired
	private CommonService commonService;


	@Value("${main.setting.code.DB}")
	private String dbCode;

	@Value("${main.setting.code.LS}")
	private String lsCode;

	@RequestMapping("/main/init")
	public @ResponseBody Map<String, Object> getInitMainInfo(HttpServletRequest request) {
		Result result = new Result();
		Map memberParam = null;
		Map setInfo = null;
		List menuList = null;
		Map defInfo = null;
		HttpSession session = request.getSession();
		
		try {
			memberParam = user.getUserInfoByBase();
			defInfo = new HashMap();
			defInfo.put("USER_ID", user.getUserId());
			defInfo.put("USER_NM", user.getUserName());

			if (user.getIsAdmin()) {
				defInfo.put("IS_ADMIN", "Y"); 
			} else {  
				defInfo.put("IS_ADMIN", "N");
			}
			
			int curSystem = (int)session.getAttribute("CUR_SYSTEM");
			defInfo.put("CUR_SYSTEM", curSystem);
			
			memberParam.put("SYSTEM_BIT", curSystem);
			
			
			result.setData("dlt_menu", commonService.selectMenuList(memberParam));
			result.setData("dma_defInfo", defInfo);
			result.setData("dlt_programAuthority", commonService.selectProgramAuthorityList(memberParam));
			result.setMsg(result.STATUS_SUCESS, "메뉴정보가 조회 되었습니다.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setMsg(result.STATUS_ERROR, null, ex);
		}

		return result.getResult();
	}

	/**
	 * 로그인된 사용자의 메인 설정 정보를 가져온다.
	 * 
	 * @date 2017.12.22
	 * @author InswaveSystems
	 * @example 샘플 코드
	 * @todo 추가해야 할 작업
	 */
	@RequestMapping("/main/selectBmMainSetting")
	public @ResponseBody Map<String, Object> selectBmMainSetting() {
		Result result = new Result();

		try {
			result.setData("dma_setting", commonService.selectBmMainSetting(user.getUserInfo()));
			result.setMsg(result.STATUS_SUCESS, "정상적으로 조회가 완료되었습니다.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setMsg(result.STATUS_ERROR, null, ex);
		}

		return result.getResult();
	}

	
	/**
	 * ReleaseUpdate - 메인화면의 단축키 리스트를 조회 한다.
	 * 
	 * @date 2018.03.21
	 * @param {} 
	 * @returns 
	 */
	@RequestMapping("/main/selectShortcutList")
	public @ResponseBody Map<String, Object> selectShortcutList(@RequestBody Map<String, Object> param) {
		Result result = new Result();
		Map dbParam = null;
		String programCode = null;
		try {
			dbParam = (Map) param.get("dma_shortcut");
			programCode = (String) dbParam.get("PROG_CD");
			if (programCode == null) {
				throw new NullPointerException("프로그램 코드가 누락되었습니다.");
			}
			result.setData("dlt_shortcutList", commonService.selectShortcutList(programCode));
		} catch(Exception ex) {
			result.setMsg(result.STATUS_ERROR, "단축키 조회 도중 오류가 발생하였습니다.", ex);
		}
		return result.getResult();
	}

	@RequestMapping("/main/updateShortcutList")
	public @ResponseBody Map<String, Object> updateShortcutList(@RequestBody Map<String, Object> param) {
		Result result = new Result();
		try {
			Map hash = commonService.updateShortcutList((List) param.get("dlt_updataShortcutList"));
			result.setData("dma_shortcutMap", hash);
			result.setMsg(result.STATUS_SUCESS, "단축키 정보가 업데이트 되었습니다. 입력 : " + (String) hash.get("ICNT") + "건, 수정 : " + (String) hash.get("UCNT") + "건, 삭제 : "
					+ (String) hash.get("DCNT") + "건");
		} catch(Exception ex) {
			result.setMsg(result.STATUS_ERROR, "단축키 업데이트 도중 오류가 발생하였습니다.", ex);
		}
		return result.getResult();
	}

	/**
	 * systemChange - 시스템 변환
	 * 
	 * @date 2021.04.27
	  * @param dma_systemChange { SYSTEM_BIT:"시스템 비트"}
	 * @author baek
	 * @example
	 */
	@RequestMapping(value = "/main/systemChange")
	public @ResponseBody Map<String, Object> login(@RequestBody Map<String, Object> param, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();

		Map map = null;
		Result result = new Result();
		try {

			map = (Map) param.get("dma_systemChange");

			int changeSystem = (int)map.get("SYSTEM_BIT");
			int SystemBit = (int)session.getAttribute("SYSTEM_BIT");
	
			if(SystemBit==Constants.SYSTEM_ALL) {
				session.setAttribute("CUR_SYSTEM",changeSystem);
			}
		} catch (Exception ex) {// DB커넥션 없음
			ex.printStackTrace();
			result.setMsg(Result.STATUS_ERROR, "처리도중 시스템 오류가 발생하였습니다.", ex);
		}

		
		result.setMsg(Result.STATUS_SUCESS, "성공");
		return result.getResult();
	}
}
