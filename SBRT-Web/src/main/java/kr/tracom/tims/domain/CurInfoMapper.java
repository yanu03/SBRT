package kr.tracom.tims.domain;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CurInfoMapper {
	
	int insertCurOperInfo(Map<String, Object> paramMap); //현재운행정보 insert	
	Map<String, Object> selectCurOperInfo(Map<String, Object> paramMap); //현재운행정보 가져오기
	
	//돌발정보 insert
	int insertIncidentInfo(Map<String, Object> paramMap);
	
	//현재운행계획정보 갱신
	void refreshCurOperAllocPLRoutInfo();
	void refreshCurOperAllocPLNodeInfo();
	
}
