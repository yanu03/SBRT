package kr.tracom.bms.domain.PI0202;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.util.DateUtil;

@Service
public class PI0202Service extends ServiceSupport{

	@Autowired
	private PI0202Mapper PI0202Mapper;
		
	public List<Map> PI0202G0R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return PI0202Mapper.PI0202G0R0(param);
	}

	public List<Map> PI0202G1R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return PI0202Mapper.PI0202G1R0(param);
	}

	public List<Map> PI0202G2R0() throws Exception{
		Map param = getSimpleDataMap("dma_search3");
		return PI0202Mapper.PI0202G2R0(param);
	}
}
