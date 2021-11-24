package kr.tracom.brt.domain.AL0203;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AL0203Mapper {
	
	public List AL0203G0R0(Map param);
	
	public List AL0203SHI0();
	
	public List AL0203G1R0(Map param);
	
	public List AL0203G1CNT(Map param);
	
	public int AL0203P0I0(Map param);
	
	public int AL0203G0U0(Map param);
	
	public List AL0203P0R0(Map param);

}
