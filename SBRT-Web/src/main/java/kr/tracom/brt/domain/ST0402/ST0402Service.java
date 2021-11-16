package kr.tracom.brt.domain.ST0402;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.brt.domain.ST0402.ST0402Mapper;
import kr.tracom.cm.support.ServiceSupport;

@Service
public class ST0402Service extends ServiceSupport {

	@Autowired
	private ST0402Mapper st0402Mapper;
	
	public List ST0402G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return st0402Mapper.ST0402G0R0(map);
	}
	
	public List ST0402G1R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String temp[] = map.get("ROUT_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
		map.put("ROUT_ID", temp);
		return st0402Mapper.ST0402G1R0(map);
	}
	
	public List ST0402G2R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String temp[] = map.get("ROUT_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
		map.put("ROUT_ID", temp);
		return st0402Mapper.ST0402G2R0(map);
	}
	
	public List ST0402SHI0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return st0402Mapper.ST0402SHI0(map);
	}
}
