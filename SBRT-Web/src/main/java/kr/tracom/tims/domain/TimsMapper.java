package kr.tracom.tims.domain;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TimsMapper {
	
	//차량정보 가져오기
	Map<String, Object> selectVhcInfo(Map<String, Object> paramMap);
	
	//노선이름 가져오기
	String selectRoutName(String routId);
	
	//노드정보 가져오기
	Map<String, Object> selectNodeInfo(Map<String, Object> paramMap);	

	//현재노드의 다음노드(교차로 or 정류장) 가져오기
	Map<String, Object> selectNextSttnCrsInfo(Map<String, Object> paramMap);	
	
	//링크순번으로 노드정보 가져오기
	Map<String, Object> selectNodeByLinkSn(Map<String, Object> paramMap);
	
}
