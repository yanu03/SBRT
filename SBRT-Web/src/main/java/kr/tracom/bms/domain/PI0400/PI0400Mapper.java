package kr.tracom.bms.domain.PI0400;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PI0400Mapper {
	public List<Map> PI0400G0R0(Map param);
	public List<Map> PI0400G1R0(Map param);
	public List<Map> PI0400G2R0(Map param);
}