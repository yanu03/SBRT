package kr.tracom.brt.domain.AL0304;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AL0304Mapper {
	
	public List AL0304G0R0(Map param);
	
	public List AL0304G1R0(Map param);
		
	public List AL0304P0R0(Map param);
	
	public List AL0304P1R0(Map param);
}
