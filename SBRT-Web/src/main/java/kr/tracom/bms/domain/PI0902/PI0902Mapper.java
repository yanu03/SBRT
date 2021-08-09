package kr.tracom.bms.domain.PI0902;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PI0902Mapper {

	public List PI0902G0R0(Map param);
	
	public List PI0902SHI0();
	
	public Map PI0902G1K0();
	
	public List PI0902G1R0(Map param);
	
	public int PI0902G1I0(Map param);
	
	public int PI0902G1U0(Map param);
	
	public int PI0902G1D0(Map param);
	
	public List PI0902G2R0(Map param);
	
	public List PI0902G1R1();
	
}
