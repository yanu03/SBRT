package kr.tracom.cm.domain.Find;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FindMapper {

	public List findUser(Map param);
	
}
