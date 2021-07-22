package kr.tracom.bms.domain.VD0300;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VD0300Mapper {
	
	public List VD0300G0R0(Map param);
	
	public List VD0300SHI0();
	
	public List VD0300SHI1();
	
	public List VD0300G1R0(Map param);
	
	public List VD0300G2R0(Map param);
	
	public List VD0300G3R0(Map param);
	
	public int VD0300G2I0(Map param);
	
	public int VD0300G2U0(Map param);
	
	public int VD0300G2D0(Map param);
	
}
