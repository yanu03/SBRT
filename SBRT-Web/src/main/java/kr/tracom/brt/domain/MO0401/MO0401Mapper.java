package kr.tracom.brt.domain.MO0401;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MO0401Mapper {
	
	public List<Map> MO0401G0R0(Map param);
	
	public List MO0401SHI0();
	
	public Map MO0401G0K0();
	
	public int MO0401G0U0(Map param);
	
	public int MO0401G0I0(Map param);
	
	public int MO0401G0D0(Map param);
	
}