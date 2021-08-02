package kr.tracom.tims.domain;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TimsMapper {
	
	//이벤트 이력 insert
	int insertEventHistory(Map<String, Object> paramMap);
	
}
