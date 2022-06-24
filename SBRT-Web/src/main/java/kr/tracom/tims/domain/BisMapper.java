package kr.tracom.tims.domain;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BisMapper {
	
	//ParamKind코드값 select
	Map selectDlCdParamKindInfo(Map<String, Object> paramMap);
	
	//ParamDiv코드값 select
	Map selectDlCdParamDivInfo(Map<String, Object> paramMap);
	
	//스크린도어 현정보 insert
	//int insertFacilityStatus(Map<String, Object> paramMap);
	int insertFacilityParam(Map<String, Object> paramMap);
	
	
	//태그리스 현정보 insert
	int insertBluemobileFacilityStatus(Map<String, Object> paramMap);
	

}
