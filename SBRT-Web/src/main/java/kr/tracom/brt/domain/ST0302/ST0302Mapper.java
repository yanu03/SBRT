package kr.tracom.brt.domain.ST0302;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ST0302Mapper {

	public List ST0302G0R0(Map param);
	
	public List ST0302G1R0(Map param);
}
