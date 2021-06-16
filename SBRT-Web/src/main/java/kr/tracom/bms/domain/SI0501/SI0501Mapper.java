package kr.tracom.bms.domain.SI0501;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SI0501Mapper {
	
	public List SI0501G0R0(Map param);
	
	public Map SI0501G0K0();
	
	public List SI0501SHI0();
	
	public List SI0501G1R0(Map param);
	
	public List SI0501P0R0(Map param);
	
	public int SI0501G0I0(Map param);
	
	public int SI0501G0D0(Map param);
	
	public int SI0501G0U0(Map param);
	
	public int SI0501G1I0(Map param);
	
	public int SI0501G1D0(Map param);
}
