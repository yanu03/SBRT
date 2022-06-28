package kr.tracom.brt.domain.MO0205;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;

@Service
public class MO0205Service extends ServiceSupport{
	
	@Autowired
	private MO0205Mapper MO0205Mapper;
	
	public List<Map> MO0205G0R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return MO0205Mapper.MO0205G0R0(param);
	}
	
	public List<Map> MO0205G1R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return MO0205Mapper.MO0205G1R0(param);
	}
	
	public List<Map> MO0205G2R0() throws Exception{
		Map param = getSimpleDataMap("dma_sub_search");
		return MO0205Mapper.MO0205G2R0(param);
	}
	
	public List MO0205G0R1() throws Exception {
		Map param = getSimpleDataMap("dma_sub_search");
		return MO0205Mapper.MO0205G0R1(param);
	}
	
	public List<Map> MO0205SHI0() throws Exception{
		return MO0205Mapper.MO0205SHI0();
	}
	
	public List MO0205SHI1() throws Exception {
		return MO0205Mapper.MO0205SHI1();
	}
	
	public List MO0205SHI2() throws Exception {
		return MO0205Mapper.MO0205SHI2();
	}
	
	public List MO0205SHI3() throws Exception {
		Map param = getSimpleDataMap("dma_search");		
		return MO0205Mapper.MO0205SHI3(param);
	}	
}
