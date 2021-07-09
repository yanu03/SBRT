package kr.tracom.bms.domain.PI0502;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PI0502Mapper {

	public List PI0502G0R0(Map param);
	
	public Map PI0502G0K0();
	
	public List PI0502SHI0();
	
	public List PI0502G1R0();
	
	public List PI0502G2R0(Map param);
	
	public int PI0502G0I0(Map param);
	
	public int PI0502G0D0(Map param);
	
	public int PI0502G0U0(Map param);

	public int PI0502G2I0(Map param);
	
	public int PI0502G2U0(Map param);
	
	public int PI0502G2D0(Map param);
	
	
}
