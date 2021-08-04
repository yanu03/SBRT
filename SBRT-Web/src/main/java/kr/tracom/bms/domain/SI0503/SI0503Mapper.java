package kr.tracom.bms.domain.SI0503;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SI0503Mapper {

	public List SI0503G0R0(Map param);
	
	public Map SI0503G0K0();
	
	public List SI0503SHI0();
	
	public List SI0503P0R0(Map param);
	
	public int SI0503G0I0(Map param);
	
	public int SI0503G0D0(Map param);
	
	public int SI0503G0U0(Map param);
	
	public List SI0503G1R0(Map param);
}
