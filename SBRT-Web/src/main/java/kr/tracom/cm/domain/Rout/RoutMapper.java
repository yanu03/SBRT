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
	public int updateRoutNodeToAnotherRoute(Map param); //BMS_ROUT_NODE_CMPSTN���� �ٸ� route id�� ������ ��ġ ���� ����
	public int updateMainRoutNodeToAnotherRoute(Map param); //BRT_MAIN_ROUT_NODE_INFO���� �ٸ� route id�� ������ ��ġ ���� ����
	public List<Map<String, Object>> selectRoutList(Map param);
	public List<Map<String, Object>> selectRoutItem();
	public List<Map<String, Object>> selectNodeListByRouts(Map param);
	public List<Map<String, Object>> selectNodeListByRout(Map param);
	public List<Map<String, Object>> selectSttnList(Map param);
	public List<Map<String, Object>> selectSttnItem();
}
