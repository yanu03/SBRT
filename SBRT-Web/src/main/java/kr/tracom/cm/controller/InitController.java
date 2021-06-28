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


import kr.tracom.cm.domain.Member.MemberService;
import kr.tracom.cm.support.ControllerSupport;
import kr.tracom.util.Constants;
import kr.tracom.util.PageURIUtil;
import kr.tracom.util.Result;
import kr.tracom.util.UserInfo;

import kr.tracom.util.Constants;

@Controller
public class InitController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InitController.class);
	
	@Autowired
	private MemberService service;

	@Autowired
	private UserInfo userInfo;

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
		LOGGER.debug("IndexBase() in w2xPath ="+request.getParameter("w2xPath"));
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
}
