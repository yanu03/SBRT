package kr.tracom.brt.domain.AL0104;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AL0104Mapper {

	public List AL0104G1R0();
	
	public List AL0104G2R0(Map param);
	
	public int AL0104G2I0(Map param);
	
	public int AL0104G2U0(Map param);
	
	public int AL0104G2D0(Map param);
}
