package kr.tracom.brt.domain.VH0400;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VH0400Mapper {
	
	public List<Map> VH0400G0R0(Map param);
	public List<Map> VH0400EVT(Map param);
	
}
