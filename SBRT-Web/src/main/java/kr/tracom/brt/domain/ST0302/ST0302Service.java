package kr.tracom.brt.domain.ST0302;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.brt.domain.ST0302.ST0302Mapper;
import kr.tracom.cm.support.ServiceSupport;

@Service
public class ST0302Service extends ServiceSupport {

	@Autowired
	private ST0302Mapper st0302Mapper;
	
	public List ST0302G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		
		return st0302Mapper.ST0302G0R0(map);
	}
	
	public List ST0302G1R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		
		return st0302Mapper.ST0302G1R0(map);
	}
}
