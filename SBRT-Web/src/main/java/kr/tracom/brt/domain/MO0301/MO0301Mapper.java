package kr.tracom.brt.domain.MO0301;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MO0301Mapper {
	
	public List<Map> MO0301G0R0(Map param);
	
	public List MO0301SHI0();
	
	public int MO0301G0U0(Map param);
	
	public int MO0301G0I0(Map param);
	
	public int MO0301G0D0(Map param);
	
	public Map MO0301G0K0();
	
	
	
}