package kr.tracom.bms.domain.SI0101;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SI0101Mapper {

	public List SI0101G0R0();
	
	public List SI0101G0R1();
	
	public int SI0101G0I0(Map param);
	
	public int SI0101G0D0(Map param);
	
	public int SI0101G0U0(Map param);
}
