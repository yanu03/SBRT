package kr.tracom.bms.domain.SI0403;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SI0403Mapper {
	
	public List SI0403G0R0(Map param);
	
	public Map SI0403G0K0();
	
	public List SI0403SHI0();
	
	public List SI0403G1R0(Map param);
	
	public List SI0403P0R0(Map param);
	
	public List SI0403P1R0(Map param);
	
	public Map SI0403P1K0();
	
	public int SI0403P1I0(Map param);
	
	public int SI0403P1U0(Map param);
	
	public int SI0403P1D0(Map param);
	
	public List SI0403P2R0(Map param);
	
	public List SI0403P3R0(Map param);
	
	public int SI0403G0I0(Map param);
	
	public int SI0403G0D0(Map param);
	
	public int SI0403G0U0(Map param);

	public int SI0403G1I0(Map param);
	
	public int SI0403G1D0(Map param);
	
}
