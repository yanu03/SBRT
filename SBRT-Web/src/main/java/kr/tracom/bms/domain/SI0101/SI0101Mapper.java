package kr.tracom.bms.domain.SI0101;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SI0101Mapper {

	public List SI0101G0R0(Map param);
	
	public Map SI0101G0K0();
	
	public List SI0101SHI0();
	
	public int SI0101G0I0(Map param);
	
	public int SI0101G0D0(Map param);
	
	public int SI0101G0U0(Map param);
	
	public List SI0101P0R0(Map param);
	
	public int SI0101P0I0(Map param);
	
	public int SI0101P0D0(Map param);
	
	public int SI0101P0U0(Map param);
	
	public int SI0101P0DA0(Map param);
}
