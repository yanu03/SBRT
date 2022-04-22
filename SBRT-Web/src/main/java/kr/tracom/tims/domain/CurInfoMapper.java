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
	
	Map<String, Object> getBusId(Map<String, Object> paramMap);
    Map<String, Object> getRoutMst(Map<String, Object> paramMap);
    Map<String, Object> getCurAllocPlInfo(Map<String, Object> paramMap);
    Map<String, Object> getCurAllocPlInfoByOperVhcId(Map<String, Object> paramMap);
    Map<String, Object> getCurNearAllocPlInfo(Map<String, Object> paramMap);
    int insertCurAllocPlInfo(Map<String, Object> paramMap);
    int updateOperVhcIdCurAllocPlInfo(Map<String, Object> paramMap);
	
}
