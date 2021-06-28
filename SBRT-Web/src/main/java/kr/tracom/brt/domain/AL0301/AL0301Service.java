package kr.tracom.brt.domain.AL0301;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;

@Service
public class AL0301Service extends ServiceSupport {

	@Autowired
	private AL0301Mapper al0301Mapper;
	
	
	public List AL0301G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return al0301Mapper.AL0301G0R0(map);
	}
	
	public List AL0301C0R0() throws Exception {
		Map param = getSimpleDataMap("dma_param_AL0301C0");
		return al0301Mapper.AL0301C0R0(param);
	}
	
	
}
