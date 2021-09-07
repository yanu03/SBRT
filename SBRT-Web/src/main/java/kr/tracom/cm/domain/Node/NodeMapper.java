package kr.tracom.cm.domain.Node;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NodeMapper {
	
	public List<Map<String, Object>> selectNodeList(Map param);
	
	public List<Map<String, Object>> selectNodeItem();
	
}
