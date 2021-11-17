package kr.tracom.brt.domain.ST0301;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ST0301Mapper {

	public List ST0301G0R0(Map param);
	
	public List ST0301G1R0(Map param);
	
	public List ST0301G2R0(Map param);
	
	public List ST0301SHI0(Map param);
}
