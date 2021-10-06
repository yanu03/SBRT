package kr.tracom.tims.domain;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HistoryMapper {
	
	//디스패치 이력 insert
	int insertDispatchHistory(Map<String, Object> paramMap);
	
	//이벤트 이력 insert
	int insertEventHistory(Map<String, Object> paramMap);
	
	//운행위반이력 insert
	int insertOperVioltHistory(Map<String, Object> paramMap);

}
