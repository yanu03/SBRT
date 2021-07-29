package kr.tracom.bms.domain.PI0204;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PI0204Mapper {
	public List<Map> PI0204G0R0(Map param);
	
	public int PI0204G0U0(Map param);
	public int PI0204G0D0(Map param);
	
	//public Map PI0201G0K0();
}