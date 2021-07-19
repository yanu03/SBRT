package kr.tracom.brt.domain.AL0301;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AL0301Mapper {

	public List AL0301G0R0(Map param);
	
	public List AL0301C0R0(Map param);
	
}
