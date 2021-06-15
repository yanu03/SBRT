package kr.tracom.bms.domain.PI0202;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PI0202Mapper {
	public List<Map> PI0202G0R0(Map param);
	public List<Map> PI0202G1R0(Map param);
	public List<Map> PI0202G2R0(Map param);
}