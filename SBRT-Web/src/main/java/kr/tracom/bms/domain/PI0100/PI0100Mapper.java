package kr.tracom.bms.domain.PI0100;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PI0100Mapper {
	public List<Map> PI0100G0R0(Map param);
	public Map PI0100G0K0();
	public List PI0100SHI0();
	public List<Map> PI0100G1R0(Map param);
	public List<Map> PI0100G2R0(Map param);
	public List<Map> PI0100P0R0(Map param);
	public List<Map> PI0100P1R0(Map param);
	
	public int PI0100G0I0(Map param);
	public int PI0100G0D0(Map param);
	public int PI0100G0U0(Map param);
	public int PI0100G1I0(Map param);
	public int PI0100G1D0(Map param);
	public int PI0100G1U0(Map param);
	public int PI0100G2I0(Map param);
	public int PI0100G2D0(Map param);
	public int PI0100G2U0(Map param);	
}