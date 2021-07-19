package kr.tracom.bms.domain.SI0701;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SI0701Mapper {
	// 메뉴 조회 (로그인 사용자에게 권한이 있는 메뉴만 조회함)
	public List<HashMap<String, Object>> selectMenuList(Map param);
	
	// 사용자별 프로그램 권한 리스트 조회 (로그인 사용자에게 권한이 있는 프로그램 권한만 조회함)
	public List selectProgramAuthorityList(Map param);

	public List selectSI0701SearchItem();

	// 공통코드 및 코드 그룹 조회
	public List selectSI0701Co(Map param);

	public List selectSI0701Dtl();

	public List selectSI0701DtlList(Map param);

	// 공통코드 그룹 C, U, D
	public int deleteSI0701Co(Map param);

	public int insertSI0701Co(Map param);

	public int updateSI0701Co(Map param);

	// 공통코드 C, U, D
	public int deleteSI0701Dtl(Map param);

	public int insertSI0701Dtl(Map param);

	public int updateSI0701Dtl(Map param);

	// 공통코드
	public List<Map> selectCodeList(Map param);
	
	//시스템코드
	public List<Map> selectSystemList(Map param);


	/**
	 * 그룹코드로 세부코드 정보 한번에 삭제하기
	 * 
	 * @date 2016. 12. 05.
	 * @param
	 * @returns
	 * @author InswaveSystems
	 */
	public int deleteSI0701DtlAll(Map param);

}
