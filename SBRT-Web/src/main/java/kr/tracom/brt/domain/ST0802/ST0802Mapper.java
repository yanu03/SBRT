package kr.tracom.brt.domain.ST0802;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ST0802Mapper {

	public List ST0802G0R0(Map param);
	
	public List ST0802G1R0(Map param);
	
	public List ST0802G1R1(Map param);
	
	public List ST0802G2R0(Map param);
}
