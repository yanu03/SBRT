package kr.tracom.brt.domain.AL0105;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AL0105Mapper {

	public List AL0105G1R0(Map param);
	
	public List AL0105G2R0(Map param);
	
	public int AL0105G2I0(Map param);
	
	public int AL0105G2U0(Map param);
	
	public int AL0105G2D0(Map param);
}
