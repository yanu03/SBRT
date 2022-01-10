package kr.tracom.brt.domain.ST0604;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ST0604Mapper {

	public List ST0604G0R0(Map param);
	
	public List ST0604G1R0(Map param);
	
	public List ST0604G1R1(Map param);
	
	public List ST0604G2R0(Map param);
}
