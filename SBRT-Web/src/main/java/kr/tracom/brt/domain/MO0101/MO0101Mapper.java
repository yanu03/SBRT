package kr.tracom.brt.domain.MO0101;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MO0101Mapper {
	
	public List MO0101G0R0(Map param);
	
	public List MO0101G1R0(Map param);
	
	public List MO0101SHI0();
	
	public List MO0101SHI1(Map param);
	
	public List MO0101G2R0(Map param);
	
	public List selectCategory(Map param);
	
	public List selectSigOper(Map param);
	
	public List selectSigPeriod();
	
	public List MO0101P0R0();
	
	public List MO0101G3R0(Map param);
	
	public List MO0101G4R0(Map param);
	
	public List MO0101G5R0(Map param);
	
	public List MO0101G6R0(Map param);
	
	public List MO0101G7R0(Map param);
	
	public List selectCommuMap();
	
	public List selectSigPhaseInfo(Map param);
	
	//교차로의 현시번호 가져오기
	public int selectCurPhaseNo(Map param);
}
