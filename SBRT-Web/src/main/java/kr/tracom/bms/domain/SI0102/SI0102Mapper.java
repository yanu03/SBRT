package kr.tracom.bms.domain.SI0102;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SI0102Mapper {

	public List SI0102G0R0(Map param);
	
	public Map SI0102G0K0();
	
	public List SI0102SHI0();
	
	public List SI0102G1R0(Map param);
	
	public List SI0102P0R0(Map param);
	
	public int SI0102G0I0(Map param);
	
	public int SI0102G0D0(Map param);
	
	public int SI0102G0U0(Map param);

	public int SI0102G1I0(Map param);
	
	public int SI0102G1D0(Map param);
	
	public int SI0102G1D1(Map param);
	
}
