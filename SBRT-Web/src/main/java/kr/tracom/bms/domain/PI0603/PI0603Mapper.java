package kr.tracom.bms.domain.PI0603;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PI0603Mapper {

	public List PI0603G0R0(Map param);
	
	public List PI0603SHI0();
	
	public List PI0603G1R0(Map param);
	
	public List PI0603G2R0(Map param);
	
	public int PI0603G1I0(Map param);

	public int PI0603G1I1(Map param);
	
	public int PI0603G1U0(Map param);
	
}
