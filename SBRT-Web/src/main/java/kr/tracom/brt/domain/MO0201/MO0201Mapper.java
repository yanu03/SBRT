package kr.tracom.brt.domain.MO0201;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MO0201Mapper {
	
	public List MO0201G0R0(Map param);
	
	public List MO0201G1R0(Map param);
	
	public List MO0201SHI0();
	
	public List MO0201SHI1(Map param);
	
	public List MO0201G2R0(Map param);
	
	public List MO0201G3R0(Map param);
	
	public List MO0201G4R0(Map param);
	
	public List selectCategory(Map param);
	
	public List selectSigOper(Map param);
	
	public List selectSigPeriod();
	
	public List MO0201P0R0();
	
	public List MO0201G5R0(Map param);
	
	public List MO0201G6R0(Map param);
	
	public List MO0201G7R0(Map param);
	
	public List MO0201G8R0(Map param);
	
	public List selectCommuMap();
	
	//교차로의 현시번호 가져오기
	public int selectCurPhaseNo(Map param);
}
