package kr.tracom.bms.domain.SI0101;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SI0101Mapper {

	public List selectGarage();
	
	public int insertGarage(Map param);
	
	public int deleteGarage(Map param);
	
	public int updateGarage(Map param);
}
