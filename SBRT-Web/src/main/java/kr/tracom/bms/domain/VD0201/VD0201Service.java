package kr.tracom.bms.domain.VD0201;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.util.DateUtil;

@Service
public class VD0201Service extends ServiceSupport{

	@Autowired
	private VD0201Mapper VD0201Mapper;
		
	public List<Map> VD0201G0R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return VD0201Mapper.VD0201G0R0(param);
	}

	public List<Map> VD0201G1R0() throws Exception{
		Map param = getSimpleDataMap("dma_sub_search");
		return VD0201Mapper.VD0201G1R0(param);
	}
	
	public List<Map> VD0201SHI1() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return VD0201Mapper.VD0201SHI1(param);
	}
	
	public List VD0201G2R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_sub_search");
		return VD0201Mapper.VD0201G2R0(param);
	}
	
}
