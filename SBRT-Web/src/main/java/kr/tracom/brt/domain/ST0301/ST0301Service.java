package kr.tracom.brt.domain.ST0301;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.brt.domain.ST0301.ST0301Mapper;
import kr.tracom.cm.support.ServiceSupport;

@Service
public class ST0301Service extends ServiceSupport {

	@Autowired
	private ST0301Mapper st0301Mapper;
	
	public List ST0301G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return st0301Mapper.ST0301G0R0(map);
	}
	
	public List ST0301G1R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String temp[] = map.get("ROUT_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
		map.put("ROUT_ID", temp);
		return st0301Mapper.ST0301G1R0(map);
	}
	
	public List ST0301G2R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String temp[] = map.get("ROUT_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
		map.put("ROUT_ID", temp);
		return st0301Mapper.ST0301G2R0(map);
	}
	
	public List ST0301SHI0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return st0301Mapper.ST0301SHI0(map);
	}
}
