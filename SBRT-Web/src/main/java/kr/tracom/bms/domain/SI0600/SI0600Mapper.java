package kr.tracom.bms.domain.SI0600;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SI0600Mapper {

	public List SI0600T0R0(Map param);
		
	public Map SI0600T0K0();
	
	public List SI0600SHI0();
	
	public int SI0600T0I0(Map param);
	
	public int SI0600T0D0(Map param);
	
	public int SI0600T0U0(Map param);
	
	public List SI0600G0R0(Map param);
	
	public List SI0600P0R0(Map param);
	
	public int SI0600G2I0(Map param);
	
	public int SI0600G2D0(Map param);
}
