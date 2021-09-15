package kr.tracom.bms.domain.FM0201;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;

@Service
public class FM0201Service extends ServiceSupport{
	
	@Autowired
	private FM0201Mapper fm0201Mapper;
	
	public List<Map> FM0201G0R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return fm0201Mapper.FM0201G0R0(param);
	}
	
	public List<Map> FM0201G1R0() throws Exception{
		Map param = getSimpleDataMap("dma_sub_search");
		return fm0201Mapper.FM0201G1R0(param);
	}
	
	public List<Map> FM0201SHI0() throws Exception{
		return fm0201Mapper.FM0201SHI0();
	}
	
	public List FM0201SHI1() throws Exception {
		return fm0201Mapper.FM0201SHI1();
	}
	
	public List FM0201SHI2() throws Exception {
		return fm0201Mapper.FM0201SHI2();
	}
	
	public List FM0201SHI3() throws Exception {
		Map param = getSimpleDataMap("dma_search");		
		return fm0201Mapper.FM0201SHI3(param);
	}	
}
