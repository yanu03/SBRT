package kr.tracom.bms.domain.SI0702;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SI0702Mapper {
	
	public List SI0702G0R0(Map param);
	
	public List SI0702SHI0();
	
	public Map SI0702G0K0();
	
	public int SI0702G0I0(Map param);
	
	public List SI0702P0R0(Map param);
	
	public int SI0702G0U0(Map param);
	
	public int SI0702G0D0(Map param);
}
