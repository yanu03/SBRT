package kr.tracom.brt.domain.MO0403;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MO0403Mapper {

	public List MO0403G0R0(Map param);
	
	public List MO0403SHI0();
	
	public List MO0403SHI1();
	
	public List MO0403G1R0();
}
