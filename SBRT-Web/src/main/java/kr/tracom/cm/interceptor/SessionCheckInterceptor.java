package kr.tracom.cm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.tracom.util.Constants;
import kr.tracom.util.UserInfo;

//@Component
public class SessionCheckInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private UserInfo userInfo;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String loginInfo = null;
		HttpSession session = request.getSession();
		String reqUrl = request.getRequestURI();
		String w2xPath = request.getParameter("w2xPath");
		boolean result = true;
		
		try {
			loginInfo = (String) session.getAttribute(Constants.SSN_USER_ID);
			
			if (loginInfo != null) {
				//userInfo.setUserInfo(session);
			} else {				
				if (!isSkipURI(request)) {
					//String ssnDeleted =(String) session.getAttribute(Constants.SSN_DELETED);
					//if(ssnDeleted!=null&&"true".equals(ssnDeleted))return false;
					//session.setAttribute(Constants.SSN_DELETED, "true");
					if ((w2xPath != null) || (reqUrl.indexOf(".xml") > -1)) {
						// 웹스퀘어 화면 호출 시 세션이 종료된 경우, 로그인 페이지로 Redirect 처리한다.
						result = false;
						response.setContentType("text/xml");
						response.setCharacterEncoding("UTF-8");
						response.getWriter().write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
						response.getWriter().write("<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:ev=\"http://www.w3.org/2001/xml-events\" ");
						response.getWriter().write("xmlns:w2=\"http://www.inswave.com/websquare\" xmlns:xf=\"http://www.w3.org/2002/xforms\">");
						response.getWriter().write("<head>");
						response.getWriter().write("<w2:buildDate/>");
						response.getWriter().write("<xf:model><xf:instance><data xmlns=\"\"/></xf:instance></xf:model>");
						response.getWriter().write("<script type=\"javascript\" lazy=\"false\"><![CDATA[ ");
						response.getWriter().write("scwin.onpageload = function() { com.alert(\"Session이 종료 되었습니다. 로그인 화면으로 이동하겠습니다.\", \"com.goHome\", true); };");
						response.getWriter().write("scwin.onpageunload = function() { };");
						response.getWriter().write("]]></script>");
						response.getWriter().write("</head>");
						response.getWriter().write("<body ev:onpageload=\"scwin.onpageload\" ev:onpageunload=\"scwin.onpageunload\"></body>");
						response.getWriter().write("</html>");
					} else {
						// 서비스 호출 시 세션이 종료된 경우, Session 종료 Alert 후, 로그인 페이지로 Redirect 처리 한다.
						result = false;
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");
						response.getWriter().write("{\"rsMsg\":{\"statusCode\":\"E\", \"errorCode\" : \"E0001\", \"message\":\"Session이 종료 되었습니다.\",\"status\":\"Error\"}}");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	/**
	 * Session 체크 대상에서 예외 URI 구성
	 * 
	 * @date 2016. 8. 29.
	 * @param argument
	 *			파라미터 정보
	 * @returns <boolean> 반환 변수 및 객체
	 * @author tracom
	 * @example 샘플 코드
	 */
	private boolean isSkipURI(HttpServletRequest request) {
		
		String[] skipUrl = { "/", "/I18N", "/ClipReport", "/main/ssoCheck", "/dashboard"};
		boolean result = false;
		String uri = (request.getRequestURI()).replace(request.getContextPath(), "");
		System.out.println("isSkipURI() in uri="+uri);
		for (int i = 0; i < skipUrl.length; i++) {
			if (uri.equals(skipUrl[i])) {
				result = true;
				break;
			}
		}
		String[] skipUrl2 = {"/webjars"};
		for (int i = 0; i < skipUrl2.length; i++) {
			if (uri.contains(skipUrl2[i])) {
				result = true;
				break;
			}
		}
		return result;
	}
}
