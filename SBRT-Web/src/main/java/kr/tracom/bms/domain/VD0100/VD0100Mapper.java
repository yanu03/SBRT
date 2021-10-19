package kr.tracom.bms.domain.VD0100;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VD0100Mapper {

	public List VD0100G0R0(Map param);
	
	public List VD0100SHI0();
	
	public List VD0100SHI1();
	
	public List VD0100G1R0(Map param);
	
	public Map VD0100G1K0();

	public int VD0100G1I0(Map param);
	
	public int VD0100G1U0(Map param);
	
	public int VD0100G1D0(Map param);
	
	/** PLF 테이블 관련 20211018 jh **/
	public int VD0100G1I2(Map param);
	
	public int VD0100G1U2(Map param);
	
	public int VD0100G1D2(Map param);
	
}
