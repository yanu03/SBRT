package kr.tracom.bms.domain.VD0202;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VD0202Mapper {

	public List<Map> VD0202G0R0(Map param);
	public List<Map> VD0202G1R0(Map param);
	public List<Map> VD0202SHI1(Map param);
	public List VD0202G2R0(Map param);		
	
}
