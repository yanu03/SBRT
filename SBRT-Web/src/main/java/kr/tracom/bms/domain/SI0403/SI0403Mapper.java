package kr.tracom.bms.domain.SI0403;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SI0403Mapper {
	
	public List SI0403G1R0(Map param);
	public int SI0403G1U0(Map param);
	
}
