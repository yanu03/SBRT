package kr.tracom.cm.domain.Member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

	@Autowired
	private MemberMapper memberMapper;

	/**
	 * 인사기본관리(소속)
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */

	public List<Map> selectMemberBasicOrganization() {
		return memberMapper.selectMemberBasicOrganization();
	}

	/**
	 * 인사기본관리(개인기본정보)
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */

	public List<Map> selectMemberBasic(Map param) {
		return memberMapper.selectMemberBasic(param);
	}

	/**
	 * 인사기본관리(사용자 USER_ID, USER_NM)
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */

	public List<Map> selectMemberSearchItem() {
		return memberMapper.selectMemberSearchItem();
	}

	/**
	 * 여러 건의 인사기본관리(개인기본정보) 데이터를 변경(등록, 수정, 삭제)한다.
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */

	public Map saveMemberBasicList(List param) {

		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;

		for (int i = 0; i < param.size(); i++) {

			Map data = (Map) param.get(i);
			String rowStatus = (String) data.get("rowStatus");
			if (rowStatus.equals("C")) {
				iCnt += memberMapper.insertMemberBasic(data);
			} else if (rowStatus.equals("U")) {
				uCnt += memberMapper.updateMemberBasic(data);
			} else if (rowStatus.equals("D")) {
				memberMapper.deleteMemberFamily(data);
				memberMapper.deleteMemberProject(data);
				dCnt += memberMapper.deleteMemberBasic(data);
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
	 * 개인 정보를 저장한다.
	 * 
	 * @param param
	 * @return
	 */

	public Map saveMemberBasic(Map param) {
		
		int uCnt = 0;
		Map result = new HashMap();
		
		String rowStatus = (String) param.get("rowStatus");
		uCnt += memberMapper.updateMemberBasic(param);
		
		result.put("UCNT", String.valueOf(uCnt));
		return result;
	}

	/**
	 * 가족 정보를 저장한다.
	 * 
	 * @param param
	 * @return
	 */

	public Map saveMemberFamily(List param) {
		
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;
		Map result = new HashMap();
		
		for (int i = 0; i < param.size(); i++) {
			Map data = (Map) param.get(i);
			String rowStatus = (String) data.get("rowStatus");
			System.out.println("패밀리 :: " + rowStatus);
			System.out.println(data);
			if (rowStatus.equals("C")) {
				int SEQ = memberMapper.selectMemberFamilyMaxSeq(data);
				data.put("SEQ", SEQ + 1);
				iCnt += memberMapper.insertMemberFamily(data);
			} else if (rowStatus.equals("U")) {
				uCnt += memberMapper.updateMemberFamily(data);
			} else if (rowStatus.equals("D") || rowStatus.equals("E")) {
				dCnt += memberMapper.deleteMemberFamily(data);
			}
		}
		result.put("ICNT", String.valueOf(iCnt));
		result.put("UCNT", String.valueOf(uCnt));
		result.put("DCNT", String.valueOf(dCnt));
		return result;
	}

	/**
	 * 프로젝트 정보를 저장한다.
	 * 
	 * @param param
	 * @return
	 */

	public Map saveMemberProject(List param) {
		
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;
		Map result = new HashMap();
		
		for (int i = 0; i < param.size(); i++) {
			Map data = (Map) param.get(i);
			String rowStatus = (String) data.get("rowStatus");
			System.out.println("프로젝트 :: " + rowStatus);
			System.out.println(data);
			if (rowStatus.equals("C")) {
				int SEQ = memberMapper.selectMemberProjectMaxSeq(data);
				data.put("SEQ", SEQ + 1);
				iCnt += memberMapper.insertMemberProject(data);
			} else if (rowStatus.equals("U")) {
				uCnt += memberMapper.updateMemberProject(data);
			} else if (rowStatus.equals("D") || rowStatus.equals("E")) {
				dCnt += memberMapper.deleteMemberProject(data);
			}
		}
		result.put("ICNT", String.valueOf(iCnt));
		result.put("UCNT", String.valueOf(uCnt));
		result.put("DCNT", String.valueOf(dCnt));
		return result;
	}

	/**
	 * 개인 기본 정보 데이터 정보를 조회한다.
	 * 
	 * @param param
	 * @return
	 */

	public List<Map> selectMemberOragn(Map param) {
		// TODO Auto-generated method stub
		return memberMapper.selectMemberOragn(param);
	}

	/**
	 * 로그인 정보를 조회한다.
	 * 
	 * @param param
	 * @return
	 */
	public List<Map> getLoginInfo(Map param) {
		return memberMapper.getLoginInfo(param);
	}

	/**
	 * 개인별 가족 데이터를 조회한다.
	 * 
	 * @param param
	 * @return
	 */
	public List<Map> selectMemberFamilyList(Map param) {
		return memberMapper.selectMemberFamilyList(param);
	}

	/**
	 * 개인별 가족 최대 순번을 조회한다.
	 * 
	 * @param param
	 * @return
	 */
	public int selectMemberFamilyMaxSeq(Map param) {
		return memberMapper.selectMemberFamilyMaxSeq(param);
	}

	/**
	 * 개인별 프로젝트 데이터를 조회한다.
	 * 
	 * @param param
	 * @return
	 */
	public List<Map> selectMemberProjectList(Map param) {
		return memberMapper.selectMemberProjectList(param);
	}

	/**
	 * 개인별 프로젝트 최대 순번을 조회한다.
	 * 
	 * @param param
	 * @return
	 */
	public int selectMemberProjectMaxSeq(Map param) {
		return memberMapper.selectMemberProjectMaxSeq(param);
	}

	/**
	 * 우편번호를 검색한다.
	 * 
	 * @param param
	 */

	public List<Map> selectZipCodeList(Map param) {
		System.out.println("IMPL");
		System.out.println(param);
		return memberMapper.selectZipCodeList(param);
	}


}
