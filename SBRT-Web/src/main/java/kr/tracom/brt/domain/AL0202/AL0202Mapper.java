package kr.tracom.brt.domain.AL0202;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AL0202Mapper {
	
	public List AL0202G0R0(Map param);
	
	public List AL0202G1R0(Map param);
	
	public List AL0202G1CNT(Map param);
		
	public List AL0202P0R0(Map param);
	
	public List AL0202P1R0(Map param);
}
