package kr.tracom.brt.domain.ST0803;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;

@Service
public class ST0803Service extends ServiceSupport {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceSupport.class);

	@Autowired
	private ST0803Mapper st0803Mapper;
	
	public List ST0803G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return st0803Mapper.ST0803G0R0(map);
	}
	
	public List ST0803G1R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		return st0803Mapper.ST0803G1R0(map);
	}
	
	public List ST0803G1R1() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String temp[] = map.get("NODE_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
		map.put("NODE_ID", temp);
		return st0803Mapper.ST0803G1R1(map);
	}
	
	public List ST0803G2R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String temp[] = map.get("NODE_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
		map.put("NODE_ID", temp);
		return st0803Mapper.ST0803G2R0(map);
	}
	
}
