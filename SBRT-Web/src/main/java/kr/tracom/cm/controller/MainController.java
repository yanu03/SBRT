package kr.tracom.cm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.cm.domain.Common.CommonService;
import kr.tracom.cm.domain.Main.MainService;
import kr.tracom.cm.support.ControllerSupport;
import kr.tracom.util.CommonUtil;
import kr.tracom.util.Constants;
import kr.tracom.util.Result;
import kr.tracom.util.UserInfo;

@Controller
@Scope("request")
public class MainController extends ControllerSupport {

	@Autowired
	private UserInfo user;

	@Autowired
	private CommonService commonService;

	@Autowired
	private MainService mainService;

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
	public @ResponseBody Map<String, Object> systemChange(HttpServletRequest request) {
		HttpSession session = request.getSession();

		Map map = null;
		Result result = new Result();
		try {

			map = (Map) getSimpleDataMap("dma_systemChange");
			
			int changeSystem = Integer.parseInt((String)map.get("SYSTEM_BIT"));
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
	
	@RequestMapping("/bm/bmsMainG0")
	public @ResponseBody Map<String, Object> bmsMainG0() throws Exception {
		result.setData("dlt_BRT_CUR_OPER_INFO", mainService.bmsMainG0());
		return result.getResult();
	}
	
	@RequestMapping("/bm/bmsMainG1")
	public @ResponseBody Map<String, Object> bmsMainG1() throws Exception {
		result.setData("dlt_BMS_NEWS_INFO", mainService.bmsMainG1());
		return result.getResult();
	}
	
	@RequestMapping("/bm/bmsMainF0")
	public @ResponseBody Map<String, Object> bmsMainF0() throws Exception {
		result.setData("dma_BMS_WEAT_INFO", mainService.bmsMainF0());
		return result.getResult();
	}
	
	@RequestMapping("/bm/bmsMainF1")
	public @ResponseBody Map<String, Object> bmsMainF1() throws Exception {
		result.setData("dma_BMS_WEAT_INFO", mainService.bmsMainF1());
		return result.getResult();
	}
	
	@RequestMapping("/br/brtMainG1")
	public @ResponseBody Map<String, Object> brtMainG1() throws Exception {
		result.setData("dlt_BRT_DSPTCH_LOG", mainService.brtMainG1());
		return result.getResult();
	}
	
	@RequestMapping("/br/brtMainF0")
	public @ResponseBody Map<String, Object> brtMainF0() throws Exception {
		result.setData("dma_BRT_INCDNT_CNT", mainService.brtMainF0());
		result.setData("dma_BRT_INCDNT_RES_CNT", mainService.brtMainF1());
		result.setData("dma_BRT_SPEEDING_CNT", mainService.brtMainF2());
		result.setData("dma_BRT_NOSTOP_CNT", mainService.brtMainF3());
		return result.getResult();
	}
	
	/*@RequestMapping("/br/brtMainF1")
	public @ResponseBody Map<String, Object> brtMainF1() throws Exception {
		result.setData("dma_BRT_INCDNT_HIS", mainService.brtMainF1());
		return result.getResult();
	}*/
}
