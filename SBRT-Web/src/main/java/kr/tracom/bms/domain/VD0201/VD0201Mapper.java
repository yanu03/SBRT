package kr.tracom.bms.domain.VD0201;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VD0201Mapper {
	public List<Map> VD0201G0R0(Map param);
	public List<Map> VD0201G1R0(Map param);
	public List<Map> VD0201SHI1(Map param);
	public List VD0201G2R0(Map param);	
}