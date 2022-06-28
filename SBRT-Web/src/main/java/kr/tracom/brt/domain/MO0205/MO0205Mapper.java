package kr.tracom.brt.domain.MO0205;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MO0205Mapper {
	
	public List<Map> MO0205G0R0(Map param);
	
	public List<Map> MO0205G1R0(Map param);
	
	public List<Map> MO0205G2R0(Map param);
	
	public List<Map> MO0205G0R1(Map param);
	
	public List<Map> MO0205SHI0();
	
	public List MO0205SHI1();
	
	public List MO0205SHI2();
	
	public List MO0205SHI3(Map param);
	
	public List MO0205P0R0(Map param);	
	
}
