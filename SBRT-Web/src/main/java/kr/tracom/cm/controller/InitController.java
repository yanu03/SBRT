package kr.tracom.cm.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.cm.domain.Login.LoginService;
import kr.tracom.cm.domain.Member.MemberService;
import kr.tracom.cm.support.ControllerSupport;
import kr.tracom.util.CommonUtil;
import kr.tracom.util.Constants;
import kr.tracom.util.PageURIUtil;
import kr.tracom.util.Result;
import kr.tracom.util.SsoCrypto;
import kr.tracom.util.UserInfo;

import kr.tracom.util.Constants;

@Controller
public class InitController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InitController.class);
	
	@Autowired
	private MemberService service;

	@Autowired
	private UserInfo userInfo;

	@Autowired
	private LoginService loginService;

	public InitController() {
	}

	/**
	 * 다국어 처리 Root Url 처리
	 * 
	 * @date 2017.12.22
	 * @author Inswave
	 * @example websquare 진입 후 세션과 설정 값에 따른 화면 xml 분기를 위한 controller. 고려 대상은 websquare.jsp와 I18N.jsp. 화면 페이지의 정보는 properties파일에서 일괄 관리.
	 * @todo 차후 interceptor에서 일괄 처리 가능한지 체크 해야 함.
	 */
	@RequestMapping("/I18N")
	public String indexMultiLang(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("movePage", getLoginPage(request.getParameter("w2xPath")));
		return "websquare/I18N";
	}

	/**
	 * 
	 * 기본 Root Url 처리
	 * 
	 * @date 2017.12.22
	 * @author Inswave
	 * @todo url의 경로가 /(root)인 경우 웹스퀘어 엔진에서 하위 컨텐츠 로딩 부분의 특이사항이 발견되어 redirect로 처리.수정 및 개선 필요.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String IndexBase(HttpServletRequest request, Model model) throws Exception {
		String ssoCryptoId = request.getParameter("ID");
		HttpSession session = request.getSession();
		//session.setAttribute("SSO_ID", id);
		LOGGER.debug("IndexBase() in w2xPath ="+request.getParameter("w2xPath"));
		if(CommonUtil.notEmpty(ssoCryptoId)) {
			SsoCrypto.getCookie(request);
			String id = SsoCrypto.decrypt(ssoCryptoId, CommonUtil.getIpAddress(request));
			Map loginParam = new HashMap();
			loginParam.put("USER_ID", id);
			loginParam.put("SYSTEM_BIT", Constants.SYSTEM_SBRT);
			Map memberMap = null;
			String status = null;
			memberMap = loginService.selectMemberInfoByOnlyId(loginParam);
			status = (String) memberMap.get("LOGIN");

			// 로그인 성공
			if (status.equals("success")) {
				int systemBit = Integer.parseInt((String)memberMap.get("SYSTEM_BIT"));
				int loginSystemBit = (int)loginParam.get("SYSTEM_BIT");
				if(systemBit==Constants.SYSTEM_ALL) {
					session.setAttribute(Constants.SSN_CUR_SYSTEM, loginSystemBit);
				}
				else if(systemBit==loginSystemBit) {
					session.setAttribute(Constants.SSN_CUR_SYSTEM, loginSystemBit);
				}
				
				
				// 로그인한 ID가 시스템 관리자인지 여부를 체크한다.
				// 시스템 관리자 ID는 websquareConfig.properties 파일의 system.admin.id 속성에 정의하면 된다.
				// 시스템 관자자 ID가 여러 개일 경우 콤마(",") 구분해서 작성할 수 있다.
				boolean isAdmin = loginService.isAdmin((String) memberMap.get("USER_ID"));
				session.setAttribute(Constants.SSN_IS_ADMIN, isAdmin);
				
				
				
				// 클라이언트(UI)에 전달하는 IS_ADMIN 정보는 관리자인지의 여부에 따라 화면 제어가 필요한 로직 처리를 위해서만 사용한다.
				// 서버 서비스에서의 로직 처리는 보안을 위해서 클라이언트에서 전달하는 IS_ADMIN 정보가 아닌
				// 서버 서비스에서 관리하는 UserInfo.getIsAdmin()에서 관리자 여부를 받아와서 판단해야 한다.

				// 메뉴 정보 가져오기
				//memberMap.put("SYSTEM_BIT",session.getAttribute(Constants.SSN_CUR_SYSTEM));
				//memberMap.put("SSN_USER_ID",session.getAttribute(Constants.SSN_USER_ID));
				//List sessionMList = commonService.selectMenuList(memberMap);
				//session.setAttribute("MENU_LIST", (List) sessionMList);

				session.setAttribute(Constants.SSN_DELETED, "false");
				session.setAttribute(Constants.SSN_USER_ID, (String) memberMap.get("USER_ID"));
				session.setAttribute(Constants.SSN_USER_NM, (String) memberMap.get("USER_NM"));
				session.setAttribute(Constants.SSN_SYSTEM_BIT, memberMap.get("SYSTEM_BIT"));
				
				userInfo.setUserInfo(session);
			}
		}
		else {
			userInfo.setUserInfo(session);
		}
		model.addAttribute("movePage", getLoginPage(request.getParameter("w2xPath")));
		return "websquare/websquare";
	}
	
	/**
	 * 
	 * WebSquare Url 처리한다.
	 * 
	 * @date 2017.12.22
	 * @author Inswave
	 * @todo url의 경로가 /(root)인 경우 웹스퀘어 엔진에서 하위 컨텐츠 로딩 부분의 특이사항이 발견되어 redirect로 처리.수정 및 개선 필요.
	 */
	@RequestMapping(value = "/ws", method = RequestMethod.GET)
	public String IndexWebSquare(HttpServletRequest request, Model model) throws Exception {
		return "websquare/websquare";
	}
	
	/**
	 * SPA IFrame에서 호출하는 Blank 페이지를 반환하다.
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/blank.xml", method = RequestMethod.GET)
	public String callBlankPage(HttpServletRequest request, Model model) throws Exception {
		return "websquare/blank";
	}

	@RequestMapping(value = "/Clip", method = RequestMethod.GET)
	public String Clip(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("movePage", getLoginPage(request.getParameter("w2xPath")));
		return "/ClipReport/Clip";
	}
	
	@RequestMapping(value = "/report_server", method =  {RequestMethod.POST,RequestMethod.GET})
	public String report_server(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("movePage", getLoginPage(request.getParameter("w2xPath")));
		return "/ClipReport/report_server";
	}
	
	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public String report(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("movePage", getLoginPage(request.getParameter("w2xPath")));
		return "/ClipReport/report";
	}
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("movePage", getLoginPage(request.getParameter("w2xPath")));
		return "/dashboard/dashboard";
	}
	
	/**
	 * 로그인 페이지 Url을 반환한다.
	 * 
	 * @param w2xPath w2xPath 파라미터
	 * @return 로그인 페이지 Url
	 */
	private String getLoginPage(String w2xPath) {
		String movePage = w2xPath;
		
		// session이 없을 경우 login 화면으로 이동.
		if (!userInfo.isLogined()) {
			// session이 있고 w2xPath가 없을 경우 index화면으로 이동.
			LOGGER.debug("getLoginPage() in PageURIUtil.getLoginPage() before");
			movePage = PageURIUtil.getLoginPage();
		} else {
			LOGGER.debug("getLoginPage() in movePage ="+movePage);
			if (movePage == null) {
				// DB 설정조회 초기 page 구성
				//movePage = PageURIUtil.getIndexPageURI(userInfo.getMainLayoutCode());

				// DB에 값이 저장되어 있지 않은 경우 기본 index화면으로 이동
				LOGGER.debug("getLoginPage() in movePage before");
				if (movePage == null) {
					movePage = PageURIUtil.getIndexPageURI();
					LOGGER.debug("getLoginPage() in PageURIUtil.getIndexPageURI() after movePage ="+movePage);
				}
			}
		}
		return movePage;
	}
	
	
	/*@RequestMapping(value = "/directLogin", method = RequestMethod.GET)
	public String void directLogin(HttpServletRequest request, Model model) throws Exception {
		HttpSession session = request.getSession();
		Map memberMap = null;
		String status = null;
		Map loginParam = null;

		// loginParam은 param(USER_ID/PW)의 값을 꺼내는 용도
		loginParam = getSimpleDataMap("dma_loginCheck");
		
		SsoCrypto.getCookie(request);
		memberMap = loginService.selectMemberInfoByOnlyId(loginParam);
		status = (String) memberMap.get("LOGIN");

		// 로그인 성공
		if (status.equals("success")) {
			session.setAttribute(Constants.SSN_DELETED, "false");
			session.setAttribute(Constants.SSN_USER_ID, (String) memberMap.get("USER_ID"));
			session.setAttribute(Constants.SSN_USER_NM, (String) memberMap.get("USER_NM"));
			session.setAttribute(Constants.SSN_SYSTEM_BIT, memberMap.get("SYSTEM_BIT"));
			
			int systemBit = Integer.parseInt((String)memberMap.get("SYSTEM_BIT"));
			int loginSystemBit = Integer.parseInt((String) loginParam.get("SYSTEM_BIT"));
			if(systemBit==Constants.SYSTEM_ALL) {
				session.setAttribute(Constants.SSN_CUR_SYSTEM, loginSystemBit);
			}
			else if(systemBit==loginSystemBit) {
				session.setAttribute(Constants.SSN_CUR_SYSTEM, systemBit);
			}
			else {
				if(systemBit==Constants.SYSTEM_BIMS) {
					result.setMsg(Result.STATUS_ERROR, "차량운행관리 시스템 접근 권한이 없습니다. 시스템 관리자에게 문의하시기 바랍니다.");
				}
				else {
					result.setMsg(Result.STATUS_ERROR, "통합운영관리 시스템 접근 권한이 없습니다. 시스템 관리자에게 문의하시기 바랍니다.");
				}
			}
			
			// 로그인한 ID가 시스템 관리자인지 여부를 체크한다.
			// 시스템 관리자 ID는 websquareConfig.properties 파일의 system.admin.id 속성에 정의하면 된다.
			// 시스템 관자자 ID가 여러 개일 경우 콤마(",") 구분해서 작성할 수 있다.
			boolean isAdmin = loginService.isAdmin((String) memberMap.get("USER_ID"));
			session.setAttribute(Constants.SSN_IS_ADMIN, isAdmin);
			
			
			
			// 클라이언트(UI)에 전달하는 IS_ADMIN 정보는 관리자인지의 여부에 따라 화면 제어가 필요한 로직 처리를 위해서만 사용한다.
			// 서버 서비스에서의 로직 처리는 보안을 위해서 클라이언트에서 전달하는 IS_ADMIN 정보가 아닌
			// 서버 서비스에서 관리하는 UserInfo.getIsAdmin()에서 관리자 여부를 받아와서 판단해야 한다.

			// 메뉴 정보 가져오기
			//memberMap.put("SYSTEM_BIT",session.getAttribute(Constants.SSN_CUR_SYSTEM));
			//memberMap.put("SSN_USER_ID",session.getAttribute(Constants.SSN_USER_ID));
			//List sessionMList = commonService.selectMenuList(memberMap);
			//session.setAttribute("MENU_LIST", (List) sessionMList);

			user.setUserInfo(session);
			
			//로그인 이력 저장.
			memberMap.put("IP", CommonUtil.getIpAddress(request));
			loginService.insertLoginHis(memberMap);

			result.setMsg(Result.STATUS_SUCESS, "로그인 성공");
		} else if (status.equals("error")) {
			result.setMsg(Result.STATUS_ERROR, "로그인 실패(패스워드 불일치)");
		} else {
			result.setMsg(Result.STATUS_ERROR, "사용자 정보가 존재하지 않습니다.");
		}
	
	}*/
}
