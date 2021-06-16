package kr.tracom.bms.domain.SI0401;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SI0401Mapper {
	
	public List SI0401G0R0(Map param);
	
	public Map SI0401G0K0();
	
	public List SI0401SHI0();
	
	public List SI0401G1R0(Map param);
	
	public List SI0401P0R0(Map param);
	
	public List SI0401P1R0(Map param);
	
	public Map SI0401P1K0();
	
	public int SI0401P1I0(Map param);
	
	public int SI0401P1U0(Map param);
	
	public int SI0401P1D0(Map param);
	
	public List SI0401P2R0(Map param);
	
	public List SI0401P3R0(Map param);
	
	public int SI0401G0I0(Map param);
	
	public int SI0401G0D0(Map param);
	
	public int SI0401G0U0(Map param);

	public int SI0401G1I0(Map param);
	
	public int SI0401G1D0(Map param);
	
}
