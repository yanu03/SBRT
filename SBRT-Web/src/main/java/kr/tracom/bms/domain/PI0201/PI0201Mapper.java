package kr.tracom.bms.domain.PI0201;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PI0201Mapper {
	public List<Map> PI0201G0R0(Map param);
	public Map PI0201G0K0();
	
	public int PI0201G0I0(Map param);
	public int PI0201G0D0(Map param);
	public int PI0201G0U0(Map param);
	public List PI0201SHI0();
}