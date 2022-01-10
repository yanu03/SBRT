package kr.tracom.brt.domain.ST0701;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ST0701Mapper{

	public List ST0701G0R0(Map param);
	
	
	public List ST0701G1R0(Map param);
	
	public List ST0701G2R0(Map param);
	
	public List ST0701SHI0();
	
	public List ST0701SHI1(Map param);

}
