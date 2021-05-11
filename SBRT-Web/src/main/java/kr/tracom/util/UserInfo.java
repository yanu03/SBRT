package kr.tracom.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserInfo implements Serializable {

	// USER_ID
	private String userId;

	// USER_NM
	private String userName;
	
	// 시스템 관리자 여부
	private boolean isAdmin;
	
	// 사용자 시스템 권한
	private int systemBit;
	
	// 선택된 현재 시스템
	private int curSystem;

	public String getUserId() {
		return userId;
	}

	private void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	private void setUserName(String userName) {
		this.userName = userName;
	}
	
	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public int getSystemBit() {
		return systemBit;
	}

	public void setSystemBit(int systemBit) {
		this.systemBit = systemBit;
	}

	public int getCurSystem() {
		return curSystem;
	}

	public void setCurSystem(int curSystem) {
		this.curSystem = curSystem;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Map<String, Object> getUserInfo() {
		Map<String, Object> userInfo = new HashMap<String, Object>();
		userInfo.put(Constants.SSN_USER_ID, this.getUserId());
		userInfo.put(Constants.SSN_USER_NM, this.getUserName());
		userInfo.put(Constants.SSN_SYSTEM_BIT, this.getUserId());
		userInfo.put(Constants.SSN_CUR_SYSTEM, this.getUserName());
		return userInfo;
	}

	public Map<String, Object> getUserInfoWithoutUserID() {
		Map<String, Object> userInfo = new HashMap<String, Object>();
		userInfo.put(Constants.SSN_USER_NM, this.getUserName());
		return userInfo;
	}

	/**
	 * Map객체에 사원번호만 담아서 return한다.
	 * 
	 * @date 2016.08.22
	 * @returns <Map> USER_ID가 담긴 map
	 * @author InswaveSystems
	 * @example 샘플 코드
	 * @todo 추가해야 할 작업
	 */
	public Map getUserInfoByBase() {
		Map<String, Object> userInfo = new HashMap<String, Object>();
		userInfo.put(Constants.SSN_USER_ID, this.getUserId());
		return userInfo;
	}

	/**
	 * session 값을 참조하여 dataSetting
	 * 
	 * @date 2016.08.19
	 * @param session 사용자 정보가 담긴 session객체
	 * @author InswaveSystems
	 * @example 샘플 코드
	 * @todo 추가해야 할 작업
	 */
	public void setUserInfo(HttpSession session) {
		this.setUserId((String) session.getAttribute(Constants.SSN_USER_ID));
		this.setUserName((String) session.getAttribute(Constants.SSN_USER_NM));
		this.setIsAdmin((boolean) session.getAttribute(Constants.SSN_IS_ADMIN));
		this.setSystemBit(Integer.parseInt((String)session.getAttribute(Constants.SSN_SYSTEM_BIT)));
		this.setCurSystem((int)session.getAttribute(Constants.SSN_CUR_SYSTEM));
	}



	/**
	 * Map값을 참조하여 dataSetting
	 * 
	 * @date 2016. 8. 19.
	 * @param memberInfo 사용자 정보가 담긴 map객체
	 * @author InswaveSystems
	 * @example 샘플 코드
	 * @todo 추가해야 할 작업
	 */
	public void setUserInfo(Map memberInfo) {
		this.setUserId((String) memberInfo.get(Constants.SSN_USER_ID));
		this.setUserName((String) memberInfo.get(Constants.SSN_USER_NM));

	}

	/**
	 * data 초기화
	 * 
	 * @date 2016. 8. 19.
	 * @returns <void> 반환 변수 및 객체
	 * @author InswaveSystems
	 * @example 샘플 코드
	 * @todo 추가해야 할 작업
	 */
	public void init() {
		this.setUserId(null);
		this.setUserName(null);
	}

	/**
	 * 사용자(로그인) 정보가 있는 경우
	 * 
	 * @date 2016. 8. 19.
	 * @returns <Boolean> 반환 변수 및 객체
	 * @author InswaveSystems
	 * @example 샘플 코드
	 * @todo 추가해야 할 작업
	 */
	public Boolean isLogined() {
		String userId = this.getUserId();
		if (userId == null || userId.equals("")) {
			return false;
		}
		return true;
	}
}
