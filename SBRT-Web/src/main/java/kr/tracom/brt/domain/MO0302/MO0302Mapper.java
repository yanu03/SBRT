package kr.tracom.brt.domain.MO0302;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MO0302Mapper {

	public List MO0302G0R0(Map param);
	
	public List MO0302SHI0();
	
}
