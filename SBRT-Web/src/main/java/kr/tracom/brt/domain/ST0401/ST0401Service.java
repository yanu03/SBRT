package kr.tracom.brt.domain.ST0401;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.brt.domain.ST0401.ST0401Mapper;
import kr.tracom.cm.support.ServiceSupport;

@Service
public class ST0401Service extends ServiceSupport {

	@Autowired
	private ST0401Mapper st0302Mapper;
	
	public List ST0401G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		
		return st0302Mapper.ST0401G0R0(map);
	}
	
	public List ST0401G1R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		
		return st0302Mapper.ST0401G1R0(map);
	}
}
