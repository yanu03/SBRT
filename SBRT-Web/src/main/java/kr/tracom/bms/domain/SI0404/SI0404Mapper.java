package kr.tracom.bms.domain.SI0404;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SI0404Mapper {
	
	public List SI0404G0R0(Map param);
	
	
	public List SI0404SHI0();
	
	public List SI0404G1R0(Map param);
	
	public List SI0404P0R0(Map param);
	
	public Map SI0404G1K0();
	public Map SI0404G1K1();
	
	public int SI0404G1I0(Map param);
	
	public int SI0404G1I1(Map param);
	
	public int SI0404G1D0(Map param);
	
	public int SI0404G1DA0(Map param); //노선과노드구성 테이블 삭제
	public int SI0404G1DA1(Map param); //노선과링크구성 테이블 삭제

}
