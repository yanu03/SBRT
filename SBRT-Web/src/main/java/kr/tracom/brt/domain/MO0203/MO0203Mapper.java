package kr.tracom.brt.domain.MO0203;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MO0203Mapper {
	
	public List<Map> MO0203G0R0(Map param);
	
	public List<Map> MO0203SHI0();
	
	public List MO0203SHI1();
	
	public List MO0203SHI2();	
	
	public List MO0203G2R0(Map param);
	
	public List MO0203P0R0(Map param);
	
	public List MO0203P0R1();
}
