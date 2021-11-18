package kr.tracom.brt.domain.AL0302;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AL0302Mapper {
	
	public List AL0302G0R0(Map param);
	
	public List AL0302G1R0(Map param);
	
	public List AL0302SHI1(Map param);
	
	public List AL0302P0R0(Map param);
	
	public List AL0302P1R0(Map param);
	
	public int AL0302G1I0(Map param);
	
	public int AL0302G1I1(Map param);
	
	public int AL0302G1U0(Map param);
	
	public int AL0302G1U1(Map param);
	
	public int AL0302G2U0(Map param);
	
	public int AL0302G2U1(Map param);
	
	public List AL0302G2R0(Map param);
	
	/*
	public List SI0502SHI0();
	
	public Map SI0502G1K0();
	
	public int SI0502G1I0(Map param);
	
	public int SI0502G1D0(Map param);*/
}
