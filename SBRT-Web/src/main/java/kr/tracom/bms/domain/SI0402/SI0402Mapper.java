package kr.tracom.bms.domain.SI0402;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SI0402Mapper {
	
	public List SI0402G0R0(Map param);
	
	public Map SI0402G0K0();
	
	public List SI0402SHI0();
	
	public List SI0402G1R0(Map param); //노선과노드구성 정보 조회
	
	public List SI0402G1R1(Map param); //노선과노드구성 정보 조회(링크 구성 정보만)
	
	public int SI0402G1I0(Map param);
	
	public int SI0402G1D0(Map param);
	
	public int SI0402G1U0(Map param);
	
	public int updateLengthRoutNodeCmpstn(Map param);
	
	public Map SI0402G1K0();
	
	public Map SI0402G1K1();
	
	public int SI0402G1DA1(Map param); //노선과링크구성 테이블 삭제
	
	public int SI0402G1I1(Map param);
	
	public List SI0402P0R0(Map param);
	
	public List SI0402P1R0(Map param);
	
	public Map SI0402P1K0();
	
	public List SI0402P2R0(Map param);
	
	public Map SI0402P2K0();
	
	public int SI0402P2I0(Map param);
	
	public int SI0402P2U0(Map param);
	
	public List SI0402P3R0(Map param);

	public Map SI0402P3K0();
	
	public int SI0402P3I0(Map param);
	
	public int SI0402P3U0(Map param);
	
	public List SI0402P5R0(Map param);	
}
