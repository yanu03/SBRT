package kr.tracom.cm.domain.Login;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {

	// 사용자 정보 조회 (로그인 체크용도로 사용 )
	public Map selectMemberInfoForLogin(Map param);

	// 사용자의 비밀번호를 업데이트한다.
	public int updatePassword(Map param);
}