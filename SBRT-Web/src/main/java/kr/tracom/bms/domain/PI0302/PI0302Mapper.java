package kr.tracom.bms.domain.PI0302;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PI0302Mapper {
	public List<Map> PI0302G0R0(Map param);
	public Map PI0302G0K0();
	
	public int PI0302G0I0(Map param);
	public int PI0302G0D0(Map param);
	public int PI0302G0U0(Map param);

}