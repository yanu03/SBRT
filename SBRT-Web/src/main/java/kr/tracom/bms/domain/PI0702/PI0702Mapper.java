package kr.tracom.bms.domain.PI0702;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PI0702Mapper {

	public List PI0702G0R0(Map param);
	
	public List PI0702SHI0();
	
	public List PI0702G1R0(Map param);
	
	public int PI0702G1I0(Map param);
	
	public int PI0702G1D0(Map param);
	
}
