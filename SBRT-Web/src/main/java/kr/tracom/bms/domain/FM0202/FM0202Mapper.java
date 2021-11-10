package kr.tracom.bms.domain.FM0202;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FM0202Mapper {
	
	public List<Map> fm0202G0R0(Map param);
	
	public List<Map> fm0202G1R0(Map param);
	
	public List<Map> fm0202SHI0();
	
	public List fm0202SHI1();
	
	public List fm0202SHI2();
	
	public List fm0202SHI3(Map param);	
	
}
