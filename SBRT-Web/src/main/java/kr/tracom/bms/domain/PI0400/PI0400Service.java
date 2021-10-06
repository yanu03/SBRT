package kr.tracom.bms.domain.PI0400;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.util.DateUtil;

@Service
public class PI0400Service extends ServiceSupport{

	@Autowired
	private PI0400Mapper PI0400Mapper;
		
	public List<Map> PI0400G0R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return PI0400Mapper.PI0400G0R0(param);
	}
	public List<Map> PI0400G1R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return PI0400Mapper.PI0400G1R0(param);
	}
	public List<Map> PI0400G2R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return PI0400Mapper.PI0400G2R0(param);
	}
}
