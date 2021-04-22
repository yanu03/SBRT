package kr.tracom.cm.domain.Organization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {

	@Autowired
	private OrganizationMapper organizationMapper;

	/**
	 * 조직기본관리(기본정보)
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */

	public List<Map> selectOrganization(Map param) {
		return organizationMapper.selectOrganizaion(param);
	}

	/**
	 * 조직기본관리 조회(검색어)
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */

	public List<Map> selectOrganizaionSearchItem() {
		return organizationMapper.selectOrganizaionSearchItem();
	}

	/**
	 * 조직선택 조회(검색어)
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */

	public List<Map> selectOrganizaionBasicList(Map param) {
		// TODO Auto-generated method stub
		return organizationMapper.selectOrganizaionBasicList(param);
	}

	/**
	 * 여러 건의 조직기본관리(개인기본정보) 데이터를 변경(등록, 수정, 삭제)한다.
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */

	public Map saveOrganizaionBasicList(List param, String userId) {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;

		for (int i = 0; i < param.size(); i++) {

			Map data = (Map) param.get(i);
			data.put("USER_ID", userId);
			String rowStatus = (String) data.get("rowStatus");
			if (rowStatus.equals("C")) {
				iCnt += organizationMapper.insertOrganizaionBasic(data);
			} else if (rowStatus.equals("U")) {
				uCnt += organizationMapper.updateOrganizaionBasic(data);
			} else if (rowStatus.equals("D")) {
				dCnt += organizationMapper.deleteOrganizaionBasic(data);
			}
		}
		Map result = new HashMap();
		result.put("STATUS", "S");
		result.put("ICNT", String.valueOf(iCnt));
		result.put("UCNT", String.valueOf(uCnt));
		result.put("DCNT", String.valueOf(dCnt));
		return result;
	}

}
