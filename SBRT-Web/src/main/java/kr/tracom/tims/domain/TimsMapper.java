package kr.tracom.tims.domain;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TimsMapper {
	
	//이벤트 이력 insert
	int insertEventHistory(Map<String, Object> paramMap);
	
	//현재운행정보 insert
	int insertCurOperInfo(Map<String, Object> paramMap);
	
	//차량정보 가져오기
	Map<String, Object> selectVhcInfo(Map<String, Object> paramMap);
	
	//노선이름 가져오기
	String selectRoutName(String routId);

	
}
