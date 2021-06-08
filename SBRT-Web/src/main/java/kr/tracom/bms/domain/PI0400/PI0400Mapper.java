package kr.tracom.bms.domain.PI0400;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PI0400Mapper {
	public List<Map> PI0400G0R0(Map param);
	public Map PI0400G0K0();
	
	public int PI0400G0I0(Map param);
	public int PI0400G0D0(Map param);
	public int PI0400G0U0(Map param);

}