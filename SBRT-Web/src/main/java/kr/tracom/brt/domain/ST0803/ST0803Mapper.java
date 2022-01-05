package kr.tracom.brt.domain.ST0803;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ST0803Mapper {

	public List ST0803G0R0(Map param);
	
	public List ST0803G1R0(Map param);
	
	public List ST0803G1R1(Map param);
	
	public List ST0803G2R0(Map param);
}
