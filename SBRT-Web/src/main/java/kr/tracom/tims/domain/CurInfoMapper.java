package kr.tracom.tims.domain;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CurInfoMapper {
	
	int insertCurOperInfo(Map<String, Object> paramMap); //현재운행정보 insert	
	Map<String, Object> selectCurOperInfo(Map<String, Object> paramMap); //현재운행정보 가져오기
	
	Map<String, Object> selectCurOperInfoByVhcNo(Map<String, Object> paramMap);
	
	//돌발정보 insert
	int insertIncidentInfo(Map<String, Object> paramMap);
	
	
	int insertOrUpdateSigOperEventInfo(Map<String, Object> paramMap);
	
	//현재운행계획정보 갱신
	void refreshCurOperAllocPLRoutInfo();
	void refreshCurOperAllocPLNodeInfo();
	
	String getBusId(Map<String, Object> paramMap);
    Map<String, Object> getRoutMst(Map<String, Object> paramMap);
    String getSttnLinkId(Map<String, Object> paramMap);
    
    Map<String, Object> selectCurAllocPlInfo(Map<String, Object> paramMap);
    Map<String, Object> selectCurAllocPlInfoByVhcId(Map<String, Object> paramMap);
    Map<String, Object> selectCurAllocPlInfoByOperVhcId(Map<String, Object> paramMap);
    int minAllocNoCurAllocPlInfo(Map<String, Object> params);

	String selectCurNearAllocPlInfo(Map<String, Object> paramMap);
	String selectCurNearAllocPlInfo2(Map<String, Object> paramMap);
    int insertCurAllocPlInfo(Map<String, Object> paramMap);
    int updateOperVhcIdCurAllocPlInfo(Map<String, Object> paramMap);
    
    Map<String, Object> selectCurOperAllocPlRouteInfo(Map<String, Object> paramMap);
    
    Map<String, Object> getEventCode(Map<String, Object> paramMap);
    String selectCurNearAllocPlInfo3(Map<String, Object> params);
    
    public List selectCorDtlInfo(String repRoutId);
    
    public List selectIntgNodeList(String routId);

}
