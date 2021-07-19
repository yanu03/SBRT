package kr.tracom.bms.domain.PI0501;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PI0501Mapper {
	
	public List PI0501G0R0(Map param);
	
	public List PI0501SHI0();
	
	public List PI0501P0R0(Map param);
	
	public Map PI0501G0K0();
	
	public int PI0501G0I0(Map param);
	
	public int PI0501G0D0(Map param);
	
	public int PI0501G0U0(Map param);
}
