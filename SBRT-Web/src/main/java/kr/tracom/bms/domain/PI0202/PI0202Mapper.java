package kr.tracom.bms.domain.PI0202;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PI0202Mapper {
	public List PI0202SHI0();
	
	public List<Map> PI0202G1R0(Map param);
	
	public int PI0202G1I0(Map param);
	
	public int PI0202G1D0(Map param);
	
	public int PI0202G1U0(Map param);
	
	public Map PI0202G1K0();
	
	public List<Map> PI0202P0R0(Map param);
}