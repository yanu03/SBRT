package kr.tracom.brt.domain.ST0801;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ST0801Mapper {

	public List ST0801G0R0(Map param);
	
	public List ST0801G1R0(Map param);
	
	public List ST0801G1R1(Map param);
	
	public List ST0801G2R0(Map param);
	/*public List ST0200G1R1(Map param);
	public List ST0200G1R2(Map param);*/
	
	/*public List PI0503G0R0(Map param);
	
	public List PI0503SHI0();
	
	public List PI0503G1R0();
	
	public List PI0503G2R0(Map param);
	
	public int PI0503G1I0(Map param);
	
	public int PI0503G1I1(Map param); //예약결과 정보 insert
	
	public int PI0503G1U0(Map param);
	
	/*public int PI0503G1D0(Map param);
	
	
	//영상 playlist 생성
	List<Map<String, Object>> makePlayList(String value);*/
}
