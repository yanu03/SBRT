package kr.tracom.bms.domain.VD0301;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VD0301Mapper {

	public List VD0301G0R0(Map param);
	
	public List VD0301SHI0();
}
