package kr.tracom.brt.domain.AL0303;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AL0303Mapper {
	
	public List<Map> AL0303G0R0(Map param);
	
	
	public List AL0303SHI0();

	public List AL0303SHI1();
	
	public int AL0303G0I0(Map param);
	public int AL0303G0U0(Map param);
	public int AL0303G0D0(Map param);
	
	public List AL0303P1R0(Map param);
	
	public List AL0303P2R0(Map param);
	
	public List AL0303P3R0(Map param);
	
	public List AL0303P4R0(Map param);
	
	public Map AL0303G0K0();
	
	
	/*
	public List<Map> PI0205G0R0(Map param);
	
	public int PI0205G0I0(Map param);
	public int PI0205G0D0(Map param);
	
	
	public Map PI0205G0K0();
	
	
	*/
}