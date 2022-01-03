package kr.tracom.brt.domain.ST0603;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.brt.domain.ST0603.ST0603Mapper;
import kr.tracom.cm.domain.Rout.RoutMapper;
import kr.tracom.cm.support.ServiceSupport;

@Service
public class ST0603Service extends ServiceSupport {

	@Autowired
	private ST0603Mapper st0603Mapper;
	
	@Autowired
	private RoutMapper routMapper;
	public List ST0603G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return st0603Mapper.ST0603G0R0(map);
	}
	
	public List ST0603G1R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String temp[] = map.get("ROUT_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
		map.put("ROUT_ID", temp);
		return st0603Mapper.ST0603G1R0(map);
	}
	
	public List ST0603G2R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String temp[] = map.get("ROUT_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
		map.put("ROUT_ID", temp);
		return st0603Mapper.ST0603G2R0(map);
	}
	
	public List ST0603SHI0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return st0603Mapper.ST0603SHI0(map);
	}
}
