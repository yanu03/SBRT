package kr.tracom.cm.domain.Menu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;


@Service
public class MenuService extends ServiceSupport {

	@Autowired
	private MenuMapper menuMapper;

	/**
	 * 메뉴관리 조회
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */

	public List<Map> selectMenu() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return menuMapper.selectMenu(param);
	}
	
	public List<Map> searchMenuItem() throws Exception{
		return menuMapper.searchMenuItem();
	}	
	

	/**
	 * 여러 건의 메뉴관리(개인기본정보) 데이터를 변경(등록, 수정, 삭제)한다.
	 * 
	 * @param param Client 전달한 데이터 리스트 객체
	 * @throws Exception 
	 */

	public Map saveMenu() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;

		List param = getSimpleList("dlt_menu");
		for (int i = 0; i < param.size(); i++) {

			Map data = (Map) param.get(i);
			String rowStatus = (String) data.get("rowStatus");
			if (rowStatus.equals("C")) {
				iCnt += menuMapper.insertMenu(data);
			} else if (rowStatus.equals("U")) {
				uCnt += menuMapper.updateMenu(data);
			} else if (rowStatus.equals("D")) {
				dCnt += menuMapper.deleteMenu(data);
			}
		}
		Map result = saveResult(iCnt, uCnt, dCnt);
		return result;
	}
}
