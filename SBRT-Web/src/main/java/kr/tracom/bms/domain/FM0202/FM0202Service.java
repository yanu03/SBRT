package kr.tracom.bms.domain.FM0202;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;

@Service
public class FM0202Service extends ServiceSupport{
	
	@Autowired
	private FM0202Mapper fm0202Mapper;
	
	public List<Map> fm0202G0R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return fm0202Mapper.fm0202G0R0(param);
	}
	
	public List<Map> fm0202G1R0() throws Exception{
		Map param = getSimpleDataMap("dma_sub_search");
		return fm0202Mapper.fm0202G1R0(param);
	}
	
	public List<Map> fm0202SHI0() throws Exception{
		return fm0202Mapper.fm0202SHI0();
	}
	
	public List fm0202SHI1() throws Exception {
		return fm0202Mapper.fm0202SHI1();
	}
	
	public List fm0202SHI2() throws Exception {
		return fm0202Mapper.fm0202SHI2();
	}
	
	public List fm0202SHI3() throws Exception {
		Map param = getSimpleDataMap("dma_search");		
		return fm0202Mapper.fm0202SHI3(param);
	}	
}
