package kr.tracom.cm.domain.Menu;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuMapper {

	// 메뉴관리 조회
	public List<Map> selectMenu(Map param);
	
	// 메뉴관리 C, U, D
	public int insertMenu(Map param);

	public int deleteMenu(Map param);

	public int updateMenu(Map param);

	public List<Map> searchMenuItem();
}
