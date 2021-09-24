package kr.tracom.brt.domain.MO0101;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MO0101Mapper {
	
	public List MO0101G0R0(Map param);
	
	public List MO0101G1R0(Map param);
	
	public List MO0101SHI0();
	
	public List MO0101SHI1(Map param);
	
	public List MO0101G2R0(Map param);
	
	public List MO0101G4R0(Map param);
	
}
