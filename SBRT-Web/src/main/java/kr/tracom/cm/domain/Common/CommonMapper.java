package kr.tracom.cm.domain.Common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonMapper {

	// 메뉴 조회 (로그인 사용자에게 권한이 있는 메뉴만 조회함)
	public List<HashMap<String, Object>> selectMenuList(Map param);
	
	// 사용자별 프로그램 권한 리스트 조회 (로그인 사용자에게 권한이 있는 프로그램 권한만 조회함)
	public List selectProgramAuthorityList(Map param);

	public List selectCommonSearchItem();

	// 공통코드 및 코드 그룹 조회
	public List selectCommonCo(Map param);

	public List selectCommonDtl();

	public List selectCommonDtlList(Map param);
	
	public List selectCommonDtlImg(Map param);

	// 공통코드 그룹 C, U, D
	public int deleteCommonCo(Map param);

	public int insertCommonCo(Map param);

	public int updateCommonCo(Map param);

	// 공통코드 C, U, D
	public int deleteCommonDtl(Map param);

	public int insertCommonDtl(Map param);

	public int updateCommonDtl(Map param);
	
	public int insertCommonDtlImgPath(Map param);
	
	public int updateCommonDtlImgPath(Map param);

	// 공통코드
	public List<Map> selectCodeList(Map param);
	
	// 공통코드
	public List<Map> selectCodeList2(Map param);
	
	//시스템코드
	public List<Map> selectSystemList(Map param);


	/**
	 * 그룹코드로 세부코드 정보 한번에 삭제하기
	 * 
	 * @date 2016. 12. 05.
	 * @param
	 * @returns
	 * @author tracom
	 */
	public int deleteCommonDtlAll(Map param);
	
	/** 210826 공통코드 컬럼값 얻어오기 COL, CO_CD, DL_CD_NM 필요**/
	public String selectDlCdCol(Map param);

}
