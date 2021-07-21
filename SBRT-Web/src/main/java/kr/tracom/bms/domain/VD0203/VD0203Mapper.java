package kr.tracom.bms.domain.VD0203;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VD0203Mapper {
	
	public List VD0203G0R0(Map param);
	public List VD0203SHI0();
	/*public List<Map> VD0201G0R0(Map param);
	public List<Map> VD0201G1R0(Map param);*/
}