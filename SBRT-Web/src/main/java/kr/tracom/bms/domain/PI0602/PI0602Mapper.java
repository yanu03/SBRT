package kr.tracom.bms.domain.PI0602;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PI0602Mapper {

	public List PI0602G0R0(Map param);
	
	public Map PI0602G0K0();
	
	public List PI0602SHI0();
	
	public List PI0602G1R0();
	
	public List PI0602G2R0(Map param);
	
	public int PI0602G0I0(Map param);
	
	public int PI0602G0D0(Map param);
	
	public int PI0602G0U0(Map param);

	public int PI0602G2I0(Map param);
	
	public int PI0602G2U0(Map param);
	
	public int PI0602G2D0(Map param);
	
}
