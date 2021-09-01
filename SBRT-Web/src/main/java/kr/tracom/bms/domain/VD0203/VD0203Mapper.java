package kr.tracom.bms.domain.VD0203;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VD0203Mapper {
	
	public List VD0203G0R0(Map param);
	
	public List VD0203G1R0(Map param);
	
	public List VD0203SHI0();
	
	public int VD0203G0I0(Map param);
	
	public int VD0203G0I1(Map param);
	
	public int VD0203G0D0(Map param);
	
	public int VD0203G0D1(Map param);
	
	public int VD0203G0U0(Map param);


}