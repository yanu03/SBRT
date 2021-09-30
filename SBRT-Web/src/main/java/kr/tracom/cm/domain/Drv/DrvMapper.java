package kr.tracom.cm.domain.Drv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DrvMapper {
	
	public List<Map<String, Object>> selectDrvList(Map param);
	
	/*public int updateSttn(Map param);
	public int updateCrs(Map param);
	public int updateRout(Map param);
	public int updateRoutNodeToAnotherRoute(Map param); //BMS_ROUT_NODE_CMPSTN에서 다른 route id의 정류소 위치 정보 갱신
	public int updateMainRoutNodeToAnotherRoute(Map param); //BRT_MAIN_ROUT_NODE_INFO에서 다른 route id의 정류소 위치 정보 갱신
	public List<Map<String, Object>> selectRoutList(Map param);
	public List<Map<String, Object>> selectRoutItem();
	public List<Map<String, Object>> selectNodeListByRouts(Map param);
	public List<Map<String, Object>> selectNodeListByRout(Map param);
	public List<Map<String, Object>> selectNodeListByRepRout(Map param);
	public List<Map<String, Object>> selectSttnList(Map param);
	public List<Map<String, Object>> selectSttnItem();*/
}
