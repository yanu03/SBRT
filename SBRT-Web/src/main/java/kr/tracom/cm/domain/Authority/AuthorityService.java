package kr.tracom.cm.domain.Authority;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {

	@Autowired
	private AuthorityMapper authorityMapper;

	/**
	 * 권한관리 조회
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */
	public List selectAuthorityList(Map param) {
		return authorityMapper.selectAuthorityList(param);
	}

	/**
	 * 권한별 등록사원 조회
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */
	public List selectAuthorityMemberList(Map param) {
		return authorityMapper.selectAuthorityMemberList(param);
	}

	/**
	 * 아직 권한부여가 되지 않은 등록사원 조회
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */
	public List<Map> excludeSelectAuthorityMemberList(Map param) {
		return authorityMapper.excludeSelectAuthorityMemberList(param);
	}

	/**
	 * 권한관리 조회(검색어)
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */
	public List<Map> selectAuthoritySearchItem() {
		return authorityMapper.selectAuthoritySearchItem();
	}

	/**
	 * 여러 건의 메뉴 권한 데이터를 변경(등록, 수정, 삭제)한다.
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */
	public Map saveAuthority(List param) {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;

		for (int i = 0; i < param.size(); i++) {
			Map data = (Map) param.get(i);
			String rowStatus = (String) data.get("rowStatus");
			if (rowStatus.equals("C")) {
				iCnt += authorityMapper.insertAuthority(data);
			} else if (rowStatus.equals("U")) {
				uCnt += authorityMapper.updateAuthority(data);
			} else if (rowStatus.equals("D")) {
				dCnt += authorityMapper.deleteAuthority(data);
			}
		}
		Map result = new HashMap();
		result.put("STATUS", "S");
		result.put("ICNT", String.valueOf(iCnt));
		result.put("UCNT", String.valueOf(uCnt));
		result.put("DCNT", String.valueOf(dCnt));
		return result;
	}

	/**
	 * 여러 건의 권한별 사원 데이터를 변경(등록, 수정, 삭제)한다.
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */
	public Map saveAuthorityMember(List param) {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;

		for (int i = 0; i < param.size(); i++) {
			Map data = (Map) param.get(i);
			String rowStatus = (String) data.get("rowStatus");
			if (rowStatus.equals("C")) {
				iCnt += authorityMapper.insertAuthorityMember(data);
			} else if (rowStatus.equals("D")) {
				dCnt += authorityMapper.deleteAuthorityMember(data);
			}
		}
		Map result = new HashMap();
		result.put("STATUS", "S");
		result.put("ICNT", String.valueOf(iCnt));
		result.put("UCNT", String.valueOf(uCnt));
		result.put("DCNT", String.valueOf(dCnt));
		return result;
	}

	/**
	 * 권한정보 삭제시 하위의 권한별 사원 데이터를 변경(등록, 수정, 삭제)한다.
	 * 
	 * @param param Client 전달한 데이터 리스트 객체
	 */
	public Map saveAuthorityAll(List paramAuth, List paramAuthMember) {

		int iCnt_grp = 0; // 등록한 그룹코드 건수
		int iCnt_code = 0; // 등록한 세부코드 건수
		int uCnt_grp = 0; // 수정한 그룹코드 건수
		int uCnt_code = 0; // 수정한 세부코드 건수
		int dCnt_grp = 0; // 삭제한 그룹코드 건수
		int dCnt_code = 0; // 삭제한 세부코드 건수

		for (int i = 0; i < paramAuth.size(); i++) {
			Map dataAuth = (Map) paramAuth.get(i);
			String rowStatusAuth = (String) dataAuth.get("rowStatus");
			if (rowStatusAuth.equals("C")) {
				iCnt_grp += authorityMapper.insertAuthority(dataAuth);

				for (int j = 0; j < paramAuthMember.size(); j++) {
					Map dataAuthMember = (Map) paramAuthMember.get(j);
					String rowStatusAuthMember = (String) dataAuthMember.get("rowStatus");
					if (rowStatusAuthMember.equals("C")) {
						iCnt_code += authorityMapper.insertAuthorityMember(dataAuthMember);
					}
				}
			} else if (rowStatusAuth.equals("U")) {
				for (int j = 0; j < paramAuthMember.size(); j++) {
					Map dataAuthMember = (Map) paramAuthMember.get(j);
					String rowStatusAuthMember = (String) dataAuthMember.get("rowStatus");
					if (rowStatusAuthMember.equals("C")) {
						iCnt_code += authorityMapper.insertAuthorityMember(dataAuthMember);
					} else if (rowStatusAuthMember.equals("D")) {
						dCnt_code += authorityMapper.deleteAuthorityMember(dataAuthMember);
					}
				}
				uCnt_grp += authorityMapper.updateAuthority(dataAuth);
				// 상위 코드가 삭제이면 하위코드는 모두 삭제
			} else if (rowStatusAuth.equals("D")) {
				authorityMapper.deleteAuthorityMemberAll(dataAuth); // 하위 코드 정보는 전체 삭제
				dCnt_grp += authorityMapper.deleteAuthority(dataAuth);
			}

		}
		Map result = new HashMap();
		result.put("STATUS", "S");
		result.put("ICNT_AUTH", String.valueOf(iCnt_grp));
		result.put("ICNT_MEM", String.valueOf(iCnt_code));
		result.put("UCNT_AUTH", String.valueOf(uCnt_grp));
		result.put("UCNT_MEM", String.valueOf(uCnt_code));
		result.put("DCNT_AUTH", String.valueOf(dCnt_grp));
		result.put("DCNT_MEM", String.valueOf(dCnt_code));
		return result;
	}
}
