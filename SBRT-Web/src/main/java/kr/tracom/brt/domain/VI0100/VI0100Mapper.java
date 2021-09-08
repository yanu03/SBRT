package kr.tracom.brt.domain.VI0100;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VI0100Mapper {
	
	public List VI0100G0R0(Map param);
	
	public List VI0100G1R0(Map param);
	
	public List VI0100P0R0(Map param);
	
	public int VI0100G1I0(Map param);
	
	public int VI0100G1U0(Map param);
	
	public int VI0100G1D0(Map param);
	
	public List VI0100SHI0();
}
