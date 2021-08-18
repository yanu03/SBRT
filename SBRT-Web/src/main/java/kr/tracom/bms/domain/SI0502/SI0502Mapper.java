package kr.tracom.bms.domain.SI0502;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SI0502Mapper {
	
	public List SI0502G0R0(Map param);
	
	public List SI0502SHI0();
	
	public List SI0502G1R0(Map param);
	
	public Map SI0502G1K0();
	
	public int SI0502G1I0(Map param);
	
	public int SI0502G1U0(Map param);
	
	public int SI0502G1D0(Map param);
}
