package kr.tracom.brt.domain.ST0602;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ST0602Mapper {

	public List ST0602G0R0(Map param);
	
	public List ST0602G1R0(Map param);
	
	public List ST0602G1R1(Map param);
	
	public List ST0602G2R0(Map param);
}
