package kr.tracom.bms.domain.SI0200;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SI0200Mapper {

	public List SI0200G0R0(Map param);
	
	public List SI0200SHI0();
	
	public List SI0200P0R0(Map param);
	
	public Map SI0200G0K0();
	
	public int SI0200G0I0(Map param);
	
	public int SI0200G0D0(Map param);
	
	public int SI0200G0U0(Map param);
	
	public List SI0200SHI1();
	
}
