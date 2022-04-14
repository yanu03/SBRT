package kr.tracom.brt.domain.VH0100;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VH0100Mapper {
	
	public List<Map> VH0100G0R0(Map param);
	
	public List<Map> VH0100SHI0();
	
	public List<Map> selectDsptchDivItem();
	
}
