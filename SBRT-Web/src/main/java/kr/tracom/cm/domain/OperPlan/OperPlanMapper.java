package kr.tracom.cm.domain.OperPlan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperPlanMapper {
	public int insertSimpleOperPlan(Map param);
	public List<Map<String, Object>> selectSttnPeakInfoOfOperPlan(Map param);
	public Map<String, Object> selectOperPlanMst(Map param);
	
	/** 운행계획 생성 **/
	//대표노선의 운행계획 리스트
	public List<Map<String, Object>> selectPlList(Map<String, Object> params);
	
    //노선의 노드목록
	public List<Map<String, Object>> selectNodeList(Map<String, Object> params);

    //운행순번에 따른 기점 출발시각, 종점 도착 시각
	public Map<String, Object> selectRoutStEdTm(Map<String, Object> params);

    //최소 정차시간 가져오기
	public String selectMinStopTm();

    //최대 정차시간 가져오기
	public String selectMaxStopTm();

    //노선의 첨두시 가져오기
	public Map<String, Object> selectPeakTm(Map<String, Object> params);

    //대표노선 가져오기
	public String selectRepRout(String routId);

    //다음노드 아이디
	public List<Map<String, Object>> selectAllNextNodeInfo(String routId);

    //진입현시 정보
	public List<Map<String, Object>> selectAllCrsInfo(String routId);

    //정류장별 필요 정차시간 가져오기
	public List<Map<String, Object>> selectAllStopTm(String routId);

    //남은 현시 시간 가져오기
	public int selectPhaseRemainTm(Map<String, Object> params);
	
	//요일구분 가져오기
	String selectDayDiv(String operDt);
	
	//노선 출도착 시간 가져오기
	Map<String, Object> selectArrvDprtTm(Map<String, Object> params);

    //운행배차계획 생성
	public int insertOperAllocPlNodeInfo(Map<String, Object> params);
	public int insertOperAllocPlNodeInfoList(Map<String, Object> params);
	public int updateOperAllocPlNodeInfoList(Map<String, Object> params);
	public void deleteOperPl(Map<String, Object> params);

	public List<Map<String, Object>> selectOperPlanRout(Map<String, Object> params);
	
	public List<Map<String, Object>> selectCourseList(Map<String, Object> params);
	
	public List<Map<String, Object>> selectOperAllocPlanCourseList(Map<String, Object> params);
	
	public List<Map<String, Object>> selectCourseDtlList(Map<String, Object> params);
	
    //변경운행계획 생성
    int insertChgOperInfo(Map<String, Object> params);
    int insertChgOperDtlInfo(Map<String, Object> params);
    
    
    public List<Map<String, Object>> selectOperAllocPlanNode(Map<String, Object> params);
    
    public List<Map<String, Object>> selectOperAllocRealNode(Map<String, Object> params);

	public List selectOperAllocRealNodeCnt(Map param);
    
    //노드 운행계획 생성
	public int makeOperPl(Map<String, Object> params);
	
	public int makeOperPl2(Map<String, Object> params);
	
}
