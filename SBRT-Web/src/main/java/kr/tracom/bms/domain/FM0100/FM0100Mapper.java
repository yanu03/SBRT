package kr.tracom.bms.domain.FM0100;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FM0100Mapper {
	
	public List FM0100G0R0(Map param);
	
	public List FM0100G1R0(Map param);
	
	public List FM0100SHI1();
	
	public List FM0100SHI2();
	
	public Map FM0100G1K0();
	
	public int FM0100G1I0(Map param);
	
	public int FM0100G1U0(Map param);
	
	public int FM0100G1D0(Map param);
	
}
