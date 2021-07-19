package kr.tracom.bms.domain.PI0503;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PI0503Mapper {

	public List PI0503G0R0(Map param);
	
	public List PI0503SHI0();
	
	public List PI0503G1R0();
	
	public int PI0503G1I0(Map param);
	
	public int PI0503G1D0(Map param);
	
	/*public int PI0503G1U0(Map param);*/
}
