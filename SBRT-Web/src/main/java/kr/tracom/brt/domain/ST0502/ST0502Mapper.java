package kr.tracom.brt.domain.ST0502;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ST0502Mapper{

	public List ST0502G0R0(Map param);
	
	public List ST0502G0R1(Map param);
	
	public List ST0502G1R0(Map param);
	
	public List ST0502G2R0(Map param);
	
	public List ST0502SHI0(Map param);

}
