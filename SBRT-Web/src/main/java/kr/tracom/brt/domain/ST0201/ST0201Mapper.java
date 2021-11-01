package kr.tracom.brt.domain.ST0201;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ST0201Mapper {

	public List ST0201G0R0(Map param);
	
	public List ST0201SHI1(Map param);
	
	public Map ST0201G1R0(Map param);
	
	public Map ST0201G1R1(Map param);
	
	public List ST0201G2R0(Map param);
	
}
