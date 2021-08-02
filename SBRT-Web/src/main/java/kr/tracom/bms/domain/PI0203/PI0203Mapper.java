package kr.tracom.bms.domain.PI0203;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PI0203Mapper {
	
	public List<Map> PI0203G0R0(Map param);
	public Map PI0203G0K0();
	
	public List PI0203SHI0();
	
	public int PI0203G0I0(Map param);
	public int PI0203G0D0(Map param);
	public int PI0203G0U0(Map param);
	
}
