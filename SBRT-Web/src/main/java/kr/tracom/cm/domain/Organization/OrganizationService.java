package kr.tracom.cm.domain.Organization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;

@Service
public class OrganizationService extends ServiceSupport {

	@Autowired
	private OrganizationMapper organizationMapper;

	/**
	 * 조직기본관리(기본정보)
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 * @throws Exception 
	 */

	public List<Map> selectOrganization() throws Exception {
		Map param = getSimpleDataMap("dma_search");
		return organizationMapper.selectOrganizaion(param);
	}

	/**
	 * 조직기본관리 조회(검색어)
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */

	public List<Map> selectOrganizaionSearchItem() throws Exception {
		return organizationMapper.selectOrganizaionSearchItem();
	}

	/**
	 * 조직선택 조회(검색어)
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 * @throws Exception 
	 */

	public List<Map> selectOrganizaionBasicList() throws Exception {
		Map param = (Map) getSimpleDataMap("dlt_organizationBasic");
		return organizationMapper.selectOrganizaionBasicList(param);
	}

	/**
	 * 여러 건의 조직기본관리(개인기본정보) 데이터를 변경(등록, 수정, 삭제)한다.
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 * @throws Exception 
	 */

	public Map saveOrganizaionBasicList() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;
		List param = getSimpleList("dlt_organizationBasic");
		for (int i = 0; i < param.size(); i++) {

			Map data = (Map) param.get(i);
			String rowStatus = (String) data.get("rowStatus");
			if (rowStatus.equals("C")) {
				iCnt += organizationMapper.insertOrganizaionBasic(data);
			} else if (rowStatus.equals("U")) {
				uCnt += organizationMapper.updateOrganizaionBasic(data);
			} else if (rowStatus.equals("D")) {
				dCnt += organizationMapper.deleteOrganizaionBasic(data);
			}
		}
		Map result = saveResult(iCnt, uCnt, dCnt);
		return result;
	}
	
	public Map selectOrganizaionKey() throws Exception {
		return organizationMapper.selectOrganizaionKey();
	}
}
