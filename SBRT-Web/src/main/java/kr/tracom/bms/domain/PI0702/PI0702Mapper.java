package kr.tracom.bms.domain.PI0702;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PI0702Mapper {

	public List PI0702G0R0(Map param);
	
	public List PI0702SHI0();
	
	public Map PI0702G1K0();
	
	public List PI0702G1R0(Map param);
	
	public int PI0702G1I0(Map param);
	
	public int PI0702G1U0(Map param);
	
	public int PI0702G1D0(Map param);
	
	public List PI0702G2R0(Map param);
	
	public List PI0702G1R1(); 
	
	
	//차량별 장치정보 
	List<Map<String, Object>> selectDvcCd(String vhcId);
	//노선정보
	Map<String, Object> selectRouteInfo(String vhcId);
	
}
