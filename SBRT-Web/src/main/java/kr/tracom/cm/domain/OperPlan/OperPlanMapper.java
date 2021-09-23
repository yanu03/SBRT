package kr.tracom.cm.domain.OperPlan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperPlanMapper {
	public int insertSimpleOperPlan(Map param);
	public List<Map<String, Object>> selectSttnPeakInfoOfOperPlan(Map param);
	public Map<String, Object> selectOperPlanMst(Map param);
}
