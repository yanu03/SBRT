package kr.tracom.brt.domain.ST0603;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ST0603Mapper {

	public List ST0603G0R0(Map param);
	
	public List ST0603G1R0(Map param);
	
	public List ST0603G1R1(Map param);
	
	public List ST0603G2R0(Map param);
}
