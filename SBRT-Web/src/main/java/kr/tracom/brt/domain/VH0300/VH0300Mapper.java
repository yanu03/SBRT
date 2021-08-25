package kr.tracom.brt.domain.VH0300;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VH0300Mapper {
	
	public List VH0300G0R0(Map param);
	
	public List VH0300G1R0(Map param);
	
	public int VH0300G1I0(Map param);
	
	public int VH0300G1D0(Map param);
	
	public List VH0300SHI0();
}
