package kr.tracom.bms.domain.PI0206;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PI0206Mapper {

	public List PI0206G0R0(Map param);
	
	public List PI0206SHI0();
	
	public Map PI0206G1K0();
	
	public List PI0206G1R0(Map param);
	
	public int PI0206G1I0(Map param);
	
	public int PI0206G1U0(Map param);
	
	public int PI0206G1D0(Map param);
	
	public List PI0206G2R0(Map param);
	
	public List PI0206G1R1(); 
	
	
	//차량별 장치정보 
	List<Map<String, Object>> selectDvcCd(String vhcId);
	//노선정보
	Map<String, Object> selectRouteInfo(String vhcId);
	
}
