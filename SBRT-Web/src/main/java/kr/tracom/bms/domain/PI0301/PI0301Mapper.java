package kr.tracom.bms.domain.PI0301;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PI0301Mapper {
	public List<Map> PI0301G0R0(Map param);
	public Map PI0301G0K0();
	
	public int PI0301G0I0(Map param);
	public int PI0301G0D0(Map param);
	public int PI0301G0U0(Map param);
	
	public List PI0301SHI0();

}