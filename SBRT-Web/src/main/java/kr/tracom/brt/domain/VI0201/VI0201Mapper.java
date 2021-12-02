package kr.tracom.brt.domain.VI0201;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VI0201Mapper {

	public List VI0201G0R0(Map param);
	
	public List VI0201SHI0();
	
	public Map VI0201G0K0();
	
	public int VI0201G0U0(Map param);
	
	public int VI0201G0I0(Map param);
	
	public int VI0201G0D0(Map param);
	
}
