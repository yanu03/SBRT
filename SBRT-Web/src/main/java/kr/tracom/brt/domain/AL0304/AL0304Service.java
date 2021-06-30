package kr.tracom.brt.domain.AL0304;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;


@Service
public class AL0304Service extends ServiceSupport {

	@Autowired
	private AL0304Mapper al0304Mapper;
	
	
	public List AL0304G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return al0304Mapper.AL0304G0R0(map);
	}
	
	public List AL0304G1R0() throws Exception {
		Map param = getSimpleDataMap("dma_param_AL0304G1");
		return al0304Mapper.AL0304G1R0(param);
	}
	
	public List AL0304P0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return al0304Mapper.AL0304P0R0(map);
	}
	
	public List AL0304P0R1() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_param_AL0304P0R1");
		return al0304Mapper.AL0304P1R0(map);
	}		
}
