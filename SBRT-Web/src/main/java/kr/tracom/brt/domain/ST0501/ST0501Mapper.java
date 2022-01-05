package kr.tracom.brt.domain.ST0501;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ST0501Mapper{

	public List ST0501G0R0(Map param);
	
	public List ST0501G0R1(Map param);
	
	public List ST0501G1R0(Map param);
	
	public List ST0501G2R0(Map param);
	
	public List ST0501SHI0(Map param);

}
