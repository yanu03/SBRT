package kr.tracom.cm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.cm.domain.Common.CommonService;
import kr.tracom.cm.domain.Login.LoginService;
import kr.tracom.cm.support.ControllerSupport;
import kr.tracom.util.Result;
import kr.tracom.util.UserInfo;
import kr.tracom.util.CommonUtil;
import kr.tracom.util.Constants;
import kr.tracom.util.SsoCrypto;

@Controller
@Scope("request")
public class LoginController extends ControllerSupport {

	@Autowired
	private LoginService loginService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private UserInfo user;
	
	@Value("${system.bims.url}")
	private String bimsUrl;

	/**
	 * logout session 삭제 성공 : redirect로 기본 페이지 이동. session 삭제 오류 : 기존 화면으로 오류 메세지 전송
	 * 
	 * @date 2017.12.22
	 * @returns modelAndView
	 * @author Inswave
	 * @example
	 */
	@RequestMapping(value = "/main/logout")
	public @ResponseBody Map<String, Object> logout() throws Exception {
		HttpSession session = request.getSession();
		try {
			Map param = new HashMap<>();
			
			param.put(Constants.SSN_USER_ID, session.getAttribute(Constants.SSN_USER_ID));
			param.put(Constants.SSN_USER_NM, session.getAttribute(Constants.SSN_USER_NM));
			param.put("IP", CommonUtil.getIpAddress(request));
			
			loginService.insertLogoutHis(param);
			result.setMsg(Result.STATUS_SUCESS, "정상적으로 로그아웃 되었습니다.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setMsg(Result.STATUS_ERROR, "로그아웃 도중 오류가 발생하였습니다.", ex);
		} finally {
			request.getSession().invalidate();
			user.init();
		}
		return result.getResult();
	}

	/**
	 * login - 요청받은 ID, 비밀번호를 회원DB와 비교한다.
	 * 
	 * @date 2017.12.22
	 * @param dma_loginCheck { USER_ID:"사용자ID", PASSWORD:"비밀번호" }
	 * @returns mv dma_resLoginCheck { USER_ID:"사용자ID", USER_NM:"사용자명", LOGIN:"상태" }
	 * @author Inswave
	 * @throws Exception 
	 * @example
	 */
	@RequestMapping(value = "/main/login")
	public @ResponseBody Map<String, Object> login() throws Exception {
		HttpSession session = request.getSession();
		Map memberMap = null;
		String status = null;
		Map loginParam = null;

		// loginParam은 param(USER_ID/PW)의 값을 꺼내는 용도
		loginParam = getSimpleDataMap("dma_loginCheck");

		memberMap = loginService.selectMemberInfoForLogin(loginParam);
		status = (String) memberMap.get("LOGIN");

		// 로그인 성공
		if (status.equals("success")) {
			int systemBit = Integer.parseInt((String)memberMap.get("SYSTEM_BIT"));
			int loginSystemBit = Integer.parseInt((String) loginParam.get("SYSTEM_BIT"));
			
			SsoCrypto.init();
			String ip =  CommonUtil.getIpAddress(request);
			SsoCrypto.setCookie(request,response);
			
			if(systemBit==Constants.SYSTEM_ALL) {
				session.setAttribute(Constants.SSN_CUR_SYSTEM, loginSystemBit);
				if(loginSystemBit==Constants.SYSTEM_BIMS) {
					String ssoId = SsoCrypto.encrypt((String) loginParam.get("USER_ID"), ip);
					Map resultData = new HashMap();
					resultData.put("USER_ID", ssoId);
					resultData.put("BIMS_URL", bimsUrl);
					result.setData("dma_result", resultData);
					result.setMsg(Result.STATUS_SUCESS, "로그인 성공");
					return result.getResult();
				}
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
				return result.getResult();
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
		return result.getResult();
	}
	
	@RequestMapping(value = "/main/ssoCheck")
	public @ResponseBody Map<String, Object> loginDirect() throws Exception {
		HttpSession session = request.getSession();
		String ssoCryptoId = (String) session.getAttribute("SSO_ID");

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
					session.setAttribute(Constants.SSN_CUR_SYSTEM, systemBit);
				}
				else {
					if(systemBit==1) {
						result.setMsg(Result.STATUS_ERROR, "차량운행관리 시스템 접근 권한이 없습니다. 시스템 관리자에게 문의하시기 바랍니다.");
					}
					else {
						result.setMsg(Result.STATUS_ERROR, "통합운영관리 시스템 접근 권한이 없습니다. 시스템 관리자에게 문의하시기 바랍니다.");
					}
					return result.getResult();
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
				
				user.setUserInfo(session);
			}
			
			result.setMsg(Result.STATUS_SSO);
		}
		else {
			result.setMsg(Result.STATUS_SUCESS);
		}
			
		
		return result.getResult();
	}
	
	/**
	 * 로그인한 사용자의 비밀번호를 변경한다.
	 * 
	 * @date 2018.11.29
	 * @param dma_password { PASSWORD: "현재 비밀번호", NEW_PASSWORD: "새로운 비밀번호", RETRY_PASSWORD: "새로운 비밀번호(재입력)" }
	 * @returns mv dlt_result { FOCUS:"포커스를 이동할 컬럼 ID" }
	 * @author tracom
	 * @example
	 */
	@RequestMapping("/main/updatePassword")
	public @ResponseBody Map<String, Object> updatePassword() throws Exception {
		Result result = new Result();
		
		Map passwordMap = getSimpleDataMap("dma_password");
		boolean checkCurrPassword = false;
		
		// 시스템 관리자인 경우에는 현재 비밀번호 체크를 하지 않고 비밀번호를 변경한다.
		if (user.getIsAdmin()) {
			checkCurrPassword = true;
			
		// 일반 사용자인 경우에는 현재 비밀번호를 체크하고 비밀번호를 변경한다.
		} else {
			Map memberMap = loginService.selectMemberInfoForLogin(passwordMap);
			String status = (String) memberMap.get("LOGIN");
		   
			// 현재 비밀번호 정상 입력 여부 확인
			if (status.equals("success")) {
				checkCurrPassword = true;
			} else {
				Map resultMap = new HashMap<String, Object>();
				// TODO : FOCUS 정보가 정상적으로 Response에 담기지 않음
				resultMap.put("FOCUS", "PASSWORD");
				result.setData("dma_result", resultMap);
				result.setMsg(result.STATUS_ERROR, "현재 비밀번호를 잘못 입력하셨습니다.");
				return result.getResult();
			}
		}
		
		String newPassword = (String) passwordMap.get("NEW_PASSWORD");
		String retryPassword = (String) passwordMap.get("RETRY_PASSWORD");
		
		if (newPassword.equals(retryPassword)) {
			loginService.updatePassword(passwordMap);
			result.setMsg(result.STATUS_SUCESS, "비밀번호 변경에 성공했습니다.");
		} else {
			Map resultMap = new HashMap<String, Object>();
			resultMap.put("FOCUS", "NEW_PASSWORD");
			result.setData("dma_result", resultMap);
			result.setMsg(result.STATUS_ERROR, "신규 비밀번호와 신규 비밀번호(재입력) 항목의 비밀번호가 다르게 입력 되었습니다.");
		}
		
		return result.getResult();
	}
}
