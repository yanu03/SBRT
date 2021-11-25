package kr.tracom.brt.domain.AL0600;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AL0600Mapper {
	
	public List AL0600G0R0(Map param);
	
	public List AL0600SHI0();
	
	public List AL0600G1R0(Map param);
	
	public List AL0600G1CNT(Map param);
}
