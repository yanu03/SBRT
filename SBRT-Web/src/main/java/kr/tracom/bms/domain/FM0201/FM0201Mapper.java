package kr.tracom.bms.domain.FM0201;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FM0201Mapper {
	
	public List<Map> FM0201G0R0(Map param);
	
	public List<Map> FM0201G1R0(Map param);
	
	public List<Map> FM0201SHI0();
	
	public List FM0201SHI1();
	
	public List FM0201SHI2();
	
	public List FM0201SHI3(Map param);	
	
}
