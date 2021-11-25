package kr.tracom.bms.domain.PI0100;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PI0100Mapper {
	public List<Map> PI0100G0R0(Map param);
	public Map PI0100G0K0();
	public List PI0100SHI0();
	public int PI0100G0I0(Map param);
	public int PI0100G0D0(Map param);
	public int PI0100G0U0(Map param);
}