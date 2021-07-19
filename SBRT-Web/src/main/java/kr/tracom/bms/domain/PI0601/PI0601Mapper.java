package kr.tracom.bms.domain.PI0601;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PI0601Mapper {
	
	public List PI0601G0R0(Map param);
	
	public List PI0601SHI0();
	
	public List PI0601P0R0(Map param);
	
	public Map PI0601G0K0();
	
	public int PI0601G0I0(Map param);
	
	public int PI0601G0D0(Map param);
	
	public int PI0601G0U0(Map param);
}
