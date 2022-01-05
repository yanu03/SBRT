package kr.tracom.brt.domain.ST0800;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ST0800Mapper {

	public List ST0800G0R0(Map param);
	
	public List ST0800G1R0(Map param);
	
	public List ST0800G1R1(Map param);
	
	public List ST0800G2R0(Map param);
}
