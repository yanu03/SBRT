package kr.tracom.bms.domain.SI0405;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SI0405Mapper {
	
	public List SI0405G0R0(Map param);
	
	public List SI0405SHI0();
	
	public Map SI0405G0K0();
	
	public int SI0405G0I0(Map param);
	
	public int SI0405G0U0(Map param);
	
	public int SI0405G0D0(Map param);
}
