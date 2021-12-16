package kr.tracom.bms.domain.PI1002;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public abstract interface PI1002Mapper {
	
	public abstract List<Map> PI1002G0R0(Map paramMap);

	public abstract Map PI1002G0K0();

	public abstract List PI1002SHI0();

	public abstract List<Map> PI1002G1R0(Map paramMap);

	public abstract List<Map> PI1002G2R0(Map paramMap);

	public abstract List<Map> PI1002P0R0(Map paramMap);

	public abstract List<Map> PI1002P1R0(Map paramMap);
	
	public abstract List<Map> PI1002P2R0(Map paramMap);

	public abstract int PI1002G0I0(Map paramMap);

	public abstract int PI1002G0D0(Map paramMap);

	public abstract int PI1002G0U0(Map paramMap);

	public abstract int PI1002G1I0(Map paramMap);

	public abstract int PI1002G1D0(Map paramMap);

	public abstract int PI1002G1U0(Map paramMap);

	public abstract int PI1002G2I0(Map paramMap);

	public abstract int PI1002G2D0(Map paramMap);

	public abstract int PI1002G2U0(Map paramMap);
}