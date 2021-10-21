package kr.tracom.brt.domain.VH0200;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VH0200Mapper {
	
	public List VH0200G0R0(Map param);
	
	public List VH0200G1R0(Map param);
	
	public List VH0200G1CNT(Map param);
	
	public List VH0200G2R0(Map param);
	/*
	public List AL0203SHI0();
	
	public List AL0203G1R0(Map param);
	
	public List AL0203G1CNT(Map param);*/
	/*
	public List AL0103P0R0(Map param);
	
	public int AL0103G0I0(Map param);
	
	public int AL0103G0D0(Map param);
	
	public int AL0103G0U0(Map param);
	
	public int AL0103G1I0(Map param);
	
	public int AL0103G1D0(Map param);
	
	public int AL0103G1U0(Map param);
	
	public List AL0103P01R0(Map param);
	
	public List AL0103P1SH(Map param);*/
}
