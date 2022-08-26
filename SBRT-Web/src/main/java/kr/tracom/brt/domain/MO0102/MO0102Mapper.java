package kr.tracom.brt.domain.MO0102;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MO0102Mapper {
	
	public List MO0102G0R0(Map param);
	
	public List MO0102G1R0(Map param);
	
	public List MO0102SHI0();
	
	public List MO0102SHI1(Map param);
	
	public List MO0102G2R0(Map param);
	
	public List selectCategory(Map param);
	
	public List selectSigOper(Map param);
	
	public List selectSigPeriod();
	
	public List MO0102P0R0();
	
	public List MO0102G3R0(Map param);
	
	public List MO0102G4R0(Map param);
	
	public List MO0102G5R0(Map param);
	
	public List MO0102G6R0(Map param);
	
	public List MO0102G7R0(Map param);
	
	public List selectCommuMap();
	
	public List selectSigPhaseInfo(Map param);
	
	public List selectGrgRdsInfo();
	//교차로의 현시번호 가져오기
	public int selectCurPhaseNo(Map param);
	
	public List<Map<String, Object>> selectNodeDispListByRepRout(Map param);
	
	public List<Map<String, Object>> selectRepRoutItem();
}
