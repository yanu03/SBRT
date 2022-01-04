package kr.tracom.brt.domain.MO0303;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MO0303Mapper {

	public List MO0303G0R0(Map param);
	
	public Map MO0303G0K0();
	
	public List MO0303SHI0();
	
	public List MO0303G1R0(Map param);
	
	public Map MO0303G1K0();
	
	public int MO0303G0I0(Map param);
	
	public int MO0303G0D0(Map param);
	
	public int MO0303G0U0(Map param);

	public int MO0303G1I0(Map param);
	
	public int MO0303G1U0(Map param);
	
	public int MO0303G1D0(Map param);
	
	public List MO0303P2R0(Map param);
	
	public List MO0303SHI1();
	
	public List MO0303P3R1(Map param);
	
	public List allocVhcList(Map param);
	
	public List sttnList(Map param);
}
