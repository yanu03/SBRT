package kr.tracom.bms.domain.SM0800;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SM0800Mapper {
	
	public List SM0800G0R0(Map param);
	
	public List SM0800SHI0();
	
	public int SM0800G0I0(Map param);
	
	public int SM0800G0U0(Map param);
	
	public int SM0800G0D0(Map param);
	
	public Map SM0800G0K0();
	// 이메일 조회
	//public List<Map> selectEmail(Map param);
	/*public List<Map> SM0601G0R0(Map param);
	// 수신자 조회
	public List<Map> SM0601G1R0(String[] param);
	// 팝업 명부 선택
	public List<Map> SM0601P0R0(Map param);
		
	// Email C, U, D
	public int SM0601G0I0(Map param);

	public int SM0601G0D0(Map param);

	public int SM0601G0U0(Map param);*/
}