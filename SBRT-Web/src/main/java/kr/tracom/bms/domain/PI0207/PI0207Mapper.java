package kr.tracom.bms.domain.PI0207;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PI0207Mapper {

	public List PI0207G0R0(Map param);
	
	public Map PI0207G0K0();
	
	public List PI0207SHI0();
	
	public List PI0207G1R0();
	
	public List PI0207G2R0(Map param);
	
	public int PI0207G0I0(Map param);
	
	public int PI0207G0D0(Map param);
	
	public int PI0207G0U0(Map param);

	public int PI0207G2I0(Map param);
	
	public int PI0207G2U0(Map param);
	
	public int PI0207G2D0(Map param);
	
	
}
