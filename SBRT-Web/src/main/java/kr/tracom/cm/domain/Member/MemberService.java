package kr.tracom.cm.domain.Member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.Sha256;

@Service
public class MemberService extends ServiceSupport{

	@Autowired
	private MemberMapper memberMapper;

	/**
	 * 인사기본관리(소속)
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */

	public List<Map> selectMemberBasicOrganization() throws Exception{
		return memberMapper.selectMemberBasicOrganization();
	}

	/**
	 * 인사기본관리(개인기본정보)
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */

	public List<Map> selectMemberBasic() throws Exception {
		Map param = getSimpleDataMap("dma_search");
		return memberMapper.selectMemberBasic(param);
	}

	/**
	 * 인사기본관리(사용자 USER_ID, USER_NM)
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */

	public List<Map> selectMemberSearchItem() throws Exception {
		return memberMapper.selectMemberSearchItem();
	}

	/**
	 * 여러 건의 인사기본관리(개인기본정보) 데이터를 변경(등록, 수정, 삭제)한다.
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */

	public Map saveMemberBasicList() throws Exception {

		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;
		List param = getSimpleList("dlt_memberBasic");
		
		for (int i = 0; i < param.size(); i++) {
			
			Map data = (Map) param.get(i);
			String rowStatus = (String) data.get("rowStatus");
			if (rowStatus.equals("C")) {
				String cryptoPs = Sha256.getHashTwo((String) data.get("NEW_USER_PS"), "".getBytes(),(String) data.get("USER_ID"));
				data.put("USER_PS",cryptoPs);
				iCnt += memberMapper.insertMemberBasic(data);
			} else if (rowStatus.equals("U")) {
				String cryptoPs = Sha256.getHashTwo((String) data.get("NEW_USER_PS"), "".getBytes(),(String) data.get("USER_ID"));
				data.put("USER_PS",cryptoPs);
				uCnt += memberMapper.updateMemberBasic(data);
			} else if (rowStatus.equals("D")) {
				dCnt += memberMapper.deleteMemberBasic(data);
			}
		}
		Map result = saveResult(iCnt,uCnt,dCnt);

		return result;

	}
	
	/**
	 * 개인 정보를 저장한다.
	 * 
	 * @param param
	 * @return
	 * @throws Exception 
	 */

	public Map saveMemberBasic() throws Exception {
		
		int uCnt = 0;
		Map param = getSimpleDataMap("basic");
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
	 * 개인 기본 정보 데이터 정보를 조회한다.
	 * 
	 * @param param
	 * @return
	 * @throws Exception 
	 */

	public List<Map> selectMemberOragn() throws Exception {
		Map param = getSimpleDataMap("dma_memberBasic");

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

	public List<Map> selectZipCodeList() throws Exception {
		Map param = getSimpleDataMap("param");
		return memberMapper.selectZipCodeList(param);
	}


}
