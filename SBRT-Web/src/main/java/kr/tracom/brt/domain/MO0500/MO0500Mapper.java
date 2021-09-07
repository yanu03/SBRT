package kr.tracom.brt.domain.MO0500;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MO0500Mapper {

	public List MO0500G0R0(Map param);
	
	public List MO0500SHI0();
	
	public int MO0500G0U0(Map param);
	
	public int MO0500G0I0(Map param);
	
	public int MO0500G0D0(Map param);
	/*
	public Map MO0500G0K0();
	
	public List MO0500P0R0(Map param);
	
	public int MO0500P0I0(Map param);
	
	public int MO0500P0D0(Map param);
	
	public int MO0500P0U0(Map param);
	
	public int MO0500P0DA0(Map param);*/
	
}
