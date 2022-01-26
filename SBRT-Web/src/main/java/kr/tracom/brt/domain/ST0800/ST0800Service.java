package kr.tracom.brt.domain.ST0800;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;

@Service
public class ST0800Service extends ServiceSupport {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceSupport.class);

	@Autowired
	private ST0800Mapper st0800Mapper;
	
	public List ST0800G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return st0800Mapper.ST0800G0R0(map);
	}
	
	public List ST0800G1R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		return st0800Mapper.ST0800G1R0(map);
	}
	
	public List ST0800G1R1() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String temp[] = map.get("NODE_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
		map.put("NODE_ID", temp);
		return st0800Mapper.ST0800G1R1(map);
	}
	
	public List ST0800G2R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String temp[] = map.get("NODE_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
		map.put("NODE_ID", temp);
		return st0800Mapper.ST0800G2R0(map);
	}
	
}
