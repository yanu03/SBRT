package kr.tracom.brt.domain.AL0204;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AL0204Mapper {
	
	public List AL0204G0R0(Map param);
	
	public List AL0204G1R0(Map param);
	
	public List AL0204G1CNT(Map param);
	
	public int AL0204G0U0(Map param);

}
