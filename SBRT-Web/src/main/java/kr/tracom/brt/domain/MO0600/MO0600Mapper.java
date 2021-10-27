package kr.tracom.brt.domain.MO0600;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MO0600Mapper {
	
	public List MO0600G0R0(Map param);
	
	public List MO0600G1R0(Map param);
	
	public List MO0600SHI1(Map param);
	
}
