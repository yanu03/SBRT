package kr.tracom.brt.domain.ST0601;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.brt.domain.ST0601.ST0601Mapper;
import kr.tracom.cm.support.ServiceSupport;

@Service
public class ST0601Service extends ServiceSupport {

	@Autowired
	private ST0601Mapper st0601Mapper;
	
	public List ST0601G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		
		return st0601Mapper.ST0601G0R0(map);
	}
}
