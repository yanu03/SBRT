package kr.tracom.brt.domain.AL0205;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AL0205Mapper {
	
	public List AL0205G0R0(Map param);
	
	public List AL0205G0R1(Map param);
	
	public List AL0205SHI0();
	
	public List AL0205SHI1(Map param);
	
	public List AL0205G1R0(Map param);
	
	public List AL0205G1CNT(Map param);
	

}
