package kr.tracom.brt.domain.MO0204;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MO0204Mapper {

	public List<Map> MO0204G0R0(Map param);
	public List<Map> MO0204G1R0(Map param);
	public List<Map> MO0204G1R1(Map param);
	public List<Map> MO0204SHI1(Map param);
	public List MO0204G2R0(Map param);		
	
}
