package kr.tracom.brt.domain.AL0102;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AL0102Mapper {
	
	public List<Map> AL0102G0R0(Map param);
	
	public List AL0102SHI0();
	
	public int AL0102G0U0(Map param);
	
	public int AL0102G0D0(Map param);
	
	public int AL0102G0I0(Map param);
	
}