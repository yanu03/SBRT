package kr.tracom.bms.domain.SI0407;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SI0407Mapper {
	
	public List SI0407G0R0(Map param);
	
	public List SI0407SHI0();
	
	public int SI0407G0I0(Map param);
	
	public int SI0407G0D0(Map param);
	
	public int SI0407G0U0(Map param);	
	
}
