package kr.tracom.bms.domain.PI1001;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public abstract interface PI1001Mapper {
	public abstract List<Map> PI1001G0R0(Map paramMap);

	public abstract Map PI1001G0K0();

	public abstract List PI1001SHI0();
	
	public abstract List PI1001SHI1();

	public abstract List<Map> PI1001G1R0(Map paramMap);

	public abstract List<Map> PI1001G2R0(Map paramMap);

	public abstract List<Map> PI1001P0R0(Map paramMap);

	public abstract List<Map> PI1001P1R0(Map paramMap);
	
	public abstract List<Map> PI1001P2R0(Map paramMap);
	
	public abstract List<Map> PI1001P3R0(Map paramMap);

	public abstract int PI1001G0I0(Map paramMap);

	public abstract int PI1001G0D0(Map paramMap);

	public abstract int PI1001G0U0(Map paramMap);

	public abstract int PI1001G1I0(Map paramMap);

	public abstract int PI1001G1D0(Map paramMap);

	public abstract int PI1001G1U0(Map paramMap);

	public abstract int PI1001G2I0(Map paramMap);

	public abstract int PI1001G2D0(Map paramMap);

	public abstract int PI1001G2U0(Map paramMap);
}