package kr.tracom.brt.domain.ST0602;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.brt.domain.ST0602.ST0602Mapper;
import kr.tracom.cm.support.ServiceSupport;

@Service
public class ST0602Service extends ServiceSupport {

	@Autowired
	private ST0602Mapper st0602Mapper;
	
	public List ST0602G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return st0602Mapper.ST0602G0R0(map);
	}
	
	public List ST0602G1R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String temp[] = map.get("ROUT_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
		map.put("ROUT_ID", temp);
		return st0602Mapper.ST0602G1R0(map);
	}
	
	public List ST0602G2R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String temp[] = map.get("ROUT_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
		map.put("ROUT_ID", temp);
		return st0602Mapper.ST0602G2R0(map);
	}
	
	public List ST0602SHI0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return st0602Mapper.ST0602SHI0(map);
	}
}
