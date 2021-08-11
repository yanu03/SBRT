package kr.tracom.brt.domain.AL0101;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AL0101Mapper {
	
	public List AL0101G0R0(Map param);
	
	public Map AL0101G0K0();
	
	public List AL0101SHI0();
	
	public List AL0101G1R0(Map param);
	
	public List AL0101P0R0(Map param);
	
	public int AL0101G0I0(Map param);
	
	public int AL0101G0D0(Map param);
	
	public int AL0101G0U0(Map param);
	
	public int AL0101G1I0(Map param);
	
	public int AL0101G1D0(Map param);
}
