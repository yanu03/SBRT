package kr.tracom.tims.domain;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TimsMapper {
	
	//차량정보 가져오기
	Map<String, Object> selectVhcInfo(Map<String, Object> paramMap);
	
	//노선이름 가져오기
	String selectRoutName(String routId);

	
}
