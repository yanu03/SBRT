package kr.tracom.bms.domain.SI0402;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SI0402Mapper {
	
	public List SI0402G0R0(Map param);
	
	public Map SI0402G0K0();
	
	public List SI0402SHI0();
	
	public List SI0402G1R0(Map param);
	
	public List SI0402P0R0(Map param);
	
	public List SI0402P1R0(Map param);
	
	public Map SI0402P1K0();
	
	public int SI0402P1I0(Map param);
	
	public int SI0402P1U0(Map param);
	
	public int SI0402P1D0(Map param);
	
	public List SI0402P2R0(Map param);
	
	public List SI0402P3R0(Map param);
	
	public int SI0402G0I0(Map param);
	
	public int SI0402G0D0(Map param);
	
	public int SI0402G0U0(Map param);

	public int SI0402G1I0(Map param);
	
	public int SI0402G1D0(Map param);
	
}
