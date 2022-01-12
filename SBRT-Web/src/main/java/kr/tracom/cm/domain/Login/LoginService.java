package kr.tracom.cm.domain.Login;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.Sha256;

@Service
public class LoginService {

	@Autowired
	private LoginMapper loginMapper;;
	
	@Value("${system.admin.id}")
	private String adminId;

	/**
	 * 사용자 정보 조회 (로그인 체크용도로 사용 )
	 */

	public Map selectMemberInfoForLogin(Map param) {
		
		param.put("PASSWORD",Sha256.getHashTwo((String) param.get("PASSWORD"), "".getBytes(),(String) param.get("USER_ID")));
		
		Map memberMap = loginMapper.selectMemberInfoForLogin(param);

		// 사용자가 존재하지 않을 경우
		if (memberMap == null) {
			memberMap = new HashMap();
			memberMap.put("LOGIN", "notexist");

			// 사용자가 존재할 경우
		} else {
			String PASSWORD = (String) memberMap.get("USER_PS");
			String reqPASSWORD = (String) param.get("PASSWORD");

			// 패스워드 일치
			if (PASSWORD!=null&&PASSWORD.equals(reqPASSWORD)) {

				memberMap.put("PASSWORD", "");
				memberMap.put("LOGIN", "success");

			} else { // 패스워드 불일치
				memberMap.put("LOGIN", "error");
			}
		}
		return memberMap;
	}

	/**
	 * 사용자 정보 조회 (ID로만 로그인 처리 사용 )
	 */

	public Map selectMemberInfoByOnlyId(Map param) {
		
		Map memberMap = loginMapper.selectMemberInfoForLogin(param);

		// 사용자가 존재하지 않을 경우
		if (memberMap == null) {
			memberMap = new HashMap();
			memberMap.put("LOGIN", "notexist");

			// 사용자가 존재할 경우
		} else {
			memberMap.put("LOGIN", "success");
		}
		return memberMap;
	}
	
	/**
	 * 해당 사용자 ID가 관리자 ID인지를 검사한다.
	 */

	public boolean isAdmin(String userId) {
		String[] adminIdList = adminId.split(",");
		
		for (String adminId : adminIdList) {
			if (adminId.trim().equals(userId)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 사용자의 비밀번호를 업데이트한다.
	 */

	public int updatePassword(Map param) {
		
		String cryptoPs = Sha256.getHashTwo((String) param.get("NEW_PASSWORD"), "".getBytes(),(String) param.get("USER_ID"));
		param.put("NEW_PASSWORD",cryptoPs);
		
		return loginMapper.updatePassword(param);
	}
	
	/**
	 * 로그인 이력 저장.
	 */
	public void insertLoginHis(Map param) {
		loginMapper.insertLoginHis(param);
	}
	
	/**
	 * 로그아웃 이력 저장.
	 */
	public void insertLogoutHis(Map param) {
		loginMapper.insertLogoutHis(param);
	}

}
