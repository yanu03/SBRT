package kr.tracom.brt.domain.ST0402;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ST0402Mapper {

	public List ST0402G0R0(Map param);
	
	public List ST0402G1R0(Map param);
	
	public List ST0402G2R0(Map param);
	
	public List ST0402SHI0(Map param);

}
