package kr.tracom.bms.domain.SI0300;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SI0300Mapper {
	
	public List SI0300G0R0(Map param);
	
	public List SI0300P0R0(Map param);
	
	public List SI0300SHI0();
	
	public Map SI0300G0K0();
	
	public int SI0300G0I0(Map param);
	
	public int SI0300G0D0(Map param);
	
	public int SI0300G0U0(Map param);
}
