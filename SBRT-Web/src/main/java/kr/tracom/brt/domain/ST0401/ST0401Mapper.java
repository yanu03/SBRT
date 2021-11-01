package kr.tracom.brt.domain.ST0401;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ST0401Mapper {

	public List ST0401G0R0(Map param);
	
	public List ST0401G1R0(Map param);
}
