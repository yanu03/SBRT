package kr.tracom.brt.domain.AL0103;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AL0103Mapper {
	
	public List AL0103G0R0(Map param);
	
	public Map AL0103G0K0();
	
	public List AL0103SHI0();
	
	public List AL0103G1R0(Map param);
	
	public List AL0103P0R0(Map param);
	
	public int AL0103G0I0(Map param);
	
	public int AL0103G0D0(Map param);
	
	public int AL0103G0U0(Map param);
	
	public int AL0103G1I0(Map param);
	
	public int AL0103G1D0(Map param);
	
	public int AL0103G1U0(Map param);
	
	public List AL0103P01R0(Map param);
	
	public List AL0103P1SH(Map param);
}
