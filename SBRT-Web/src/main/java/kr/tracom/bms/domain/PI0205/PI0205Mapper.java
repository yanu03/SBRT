package kr.tracom.bms.domain.PI0205;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PI0205Mapper {
	public List<Map> PI0205G0R0(Map param);
	
	public int PI0205G0I0(Map param);
	public int PI0205G0D0(Map param);
	public int PI0205G0U0(Map param);
	
	public Map PI0205G0K0();
	
	
	public List PI0205SHI0();
}