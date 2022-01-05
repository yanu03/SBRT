package kr.tracom.brt.domain.ST0502;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.brt.domain.ST0502.ST0502Mapper;
import kr.tracom.cm.support.ServiceSupport;

@Service
public class ST0502Service extends ServiceSupport {

	@Autowired
	private ST0502Mapper st0502Mapper;
	
	public List ST0502G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return st0502Mapper.ST0502G0R0(map);
	}
	
	public List ST0502G0R1() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		return st0502Mapper.ST0502G0R1(map);
	}
	
	public List ST0502G1R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String temp[] = map.get("NODE_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
		map.put("NODE_ID", temp);
		return st0502Mapper.ST0502G1R0(map);
	}
	
	public List ST0502G2R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String temp[] = map.get("NODE_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
		map.put("NODE_ID", temp);
		return st0502Mapper.ST0502G2R0(map);
	}
	
	public List ST0502SHI0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return st0502Mapper.ST0502SHI0(map);
	}
}
