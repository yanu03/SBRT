package kr.tracom.bms.domain.PI0901;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PI0901Mapper {

	public List PI0901G0R0(Map param);
	
	public List PI0901SHI0();
	
	public Map PI0901G0K0();
	
	public int PI0901G0I0(Map param);
	
	public int PI0901G0D0(Map param);
	
	public int PI0901G0U0(Map param);
}
