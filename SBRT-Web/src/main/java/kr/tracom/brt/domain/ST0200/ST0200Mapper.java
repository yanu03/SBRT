package kr.tracom.brt.domain.ST0200;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ST0200Mapper {

	public List ST0200G0R0(Map param);
	
	public List ST0200G1R0(Map param);
	public List ST0200G1R1(Map param);
	public List ST0200G1R2(Map param);
	
}
