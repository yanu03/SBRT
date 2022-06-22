package kr.tracom.brt.domain.AL0201;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AL0201Mapper {
	
	public List AL0201G0R0(Map param);
	
	public int AL0201G0I0(Map param);
	
	public int AL0201G0D0(Map param);
	
	public int AL0201G0U0(Map param);
	
	public List AL0201G1R0(Map param);
	
	public int AL0201G1I0(Map param);
	
	public int AL0201G1D0(Map param);

	public int AL0201G1U0(Map param);
	
	public int AL0201G1DA0(Map param);
	
}
