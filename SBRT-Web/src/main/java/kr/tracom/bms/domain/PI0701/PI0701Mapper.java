package kr.tracom.bms.domain.PI0701;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PI0701Mapper {
	
	public List PI0701G0R0(Map param);
	
	public List PI0701SHI0();
	
	public List PI0701SHI1();
	
	/*public List PI0701P0R0(Map param);
	
	public int PI0701G0I0(Map param);
	
	public int PI0701G0D0(Map param);
	
	public int PI0701G0U0(Map param);*/
}
