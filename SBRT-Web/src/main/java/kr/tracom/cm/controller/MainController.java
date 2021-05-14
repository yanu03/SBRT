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
			defInfo.put(Constants.SSN_USER_ID, user.getUserId());
			defInfo.put(Constants.SSN_USER_NM, user.getUserName());

			if (user.getIsAdmin()) {
				defInfo.put(Constants.SSN_IS_ADMIN, "Y"); 
			} else {  
				defInfo.put(Constants.SSN_IS_ADMIN, "N");
			}
			
			int curSystem = (int)user.getCurSystem();
			defInfo.put(Constants.SSN_CUR_SYSTEM, curSystem);
			
			memberParam.put(Constants.SSN_SYSTEM_BIT, curSystem);
			
			
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
			int SystemBit = Integer.parseInt((String)session.getAttribute(Constants.SSN_SYSTEM_BIT));
	
			if(SystemBit==Constants.SYSTEM_ALL) {
				user.setCurSystem(changeSystem);
				session.setAttribute(Constants.SSN_CUR_SYSTEM,changeSystem);
			}
		} catch (Exception ex) {// DB커넥션 없음
			ex.printStackTrace();
			result.setMsg(Result.STATUS_ERROR, "처리도중 시스템 오류가 발생하였습니다.", ex);
		}

		
		result.setMsg(Result.STATUS_SUCESS, "성공");
		return result.getResult();
	}
}
