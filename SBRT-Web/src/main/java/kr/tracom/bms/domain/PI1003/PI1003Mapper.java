package kr.tracom.bms.domain.PI1003;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PI1003Mapper {
	
	public List<Map> PI1003G0R0(Map param);
	
	public List PI1003SHI0();
	
	public int PI1003G0U0(Map param);
	
	public int PI1003G0I0(Map param);
	
	public int PI1003G0D0(Map param);
	
	public Map PI1003G0K0();
	
	
	
}