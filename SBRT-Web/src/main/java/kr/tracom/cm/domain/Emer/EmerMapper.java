package kr.tracom.cm.domain.Emer;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmerMapper {

	public List<Map<String, Object>> selectEmerList(Map param);
	
	public List<Map<String, Object>> selectEmerItem();
	
	public List selectTreeEmerList(Map param);
}
