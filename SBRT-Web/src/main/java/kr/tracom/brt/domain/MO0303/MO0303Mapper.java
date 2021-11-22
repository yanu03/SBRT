package kr.tracom.brt.domain.MO0303;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MO0303Mapper {

	public List MO0303G0R0(Map param);
	
	public Map MO0303G0K0();
	
	public List MO0303SHI0();
	
	public List MO0303G1R0();
	
	public List MO0303G2R0(Map param);
	
	public int MO0303G0I0(Map param);
	
	public int MO0303G0D0(Map param);
	
	public int MO0303G0U0(Map param);

	public int MO0303G2I0(Map param);
	
	public int MO0303G2U0(Map param);
	
	public int MO0303G2D0(Map param);
	
}
