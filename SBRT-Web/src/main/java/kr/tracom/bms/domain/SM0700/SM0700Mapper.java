package kr.tracom.bms.domain.SM0700;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SM0700Mapper {

	public List SM0700G0R0(Map param);
	
	public Map SM0700G0K0();
	
	public List SM0700SHI0();
	
	public int SM0700G0I0(Map param);
	
	public int SM0700G0D0(Map param);
	
	public int SM0700G0U0(Map param);
}
