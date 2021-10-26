package kr.tracom.brt.domain.MO0700;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MO0700Mapper {
	
	public List MO0700G0R0(Map param);
	
	public List MO0700G1R0(Map param);
	
	public List MO0700SHI1(Map param);
	
}
