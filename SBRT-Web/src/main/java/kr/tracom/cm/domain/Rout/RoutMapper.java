package kr.tracom.cm.domain.Rout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoutMapper {
	public int updateSttn(Map param);
	public int updateCrs(Map param);
	public int updateRout(Map param);
	public List<Map> selectRoutList(Map param);
	public List<Map> selectRoutItem();
	public List<Map> selectNodeListByRouts(Map param);
	public List<Map> selectNodeListByRout(Map param);
}
