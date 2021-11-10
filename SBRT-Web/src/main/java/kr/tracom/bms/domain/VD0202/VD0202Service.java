package kr.tracom.bms.domain.VD0202;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.bms.domain.VD0202.VD0202Mapper;
import kr.tracom.cm.support.ServiceSupport;

@Service
public class VD0202Service extends ServiceSupport{
	
	@Autowired
	private VD0202Mapper VD0202Mapper;
		
	public List<Map> VD0202G0R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return VD0202Mapper.VD0202G0R0(param);
	}

	public List<Map> VD0202G1R0() throws Exception{
		Map param = getSimpleDataMap("dma_sub_search");
		return VD0202Mapper.VD0202G1R0(param);
	}
	
	public List<Map> VD0202SHI1() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return VD0202Mapper.VD0202SHI1(param);
	}
	
	public List VD0202G2R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_sub_search");
		return VD0202Mapper.VD0202G2R0(param);
	}
	
}
