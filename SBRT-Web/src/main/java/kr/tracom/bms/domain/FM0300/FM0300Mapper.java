package kr.tracom.bms.domain.FM0300;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FM0300Mapper {
	
	public List FM0300G0R0(Map param);
	
	public List FM0300SHI0();
	
	public List FM0300SHI1();
	
	public List FM0300SHI2();
	
	public List FM0300G1R0(Map param);
	
	public List FM0300G2R0(Map param);
	
	public List FM0300G3R0(Map param);
	
	public int FM0300G2I0(Map param);
	
	public int FM0300G2U0(Map param);
	
	public int FM0300G2D0(Map param);
	
}
