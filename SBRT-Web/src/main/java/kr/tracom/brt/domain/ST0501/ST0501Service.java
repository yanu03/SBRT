package kr.tracom.brt.domain.ST0501;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.brt.domain.ST0501.ST0501Mapper;
import kr.tracom.cm.support.ServiceSupport;

@Service
public class ST0501Service extends ServiceSupport {

	@Autowired
	private ST0501Mapper st0501Mapper;
	
	public List ST0501G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return st0501Mapper.ST0501G0R0(map);
	}
	
	public List ST0501G0R1() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		return st0501Mapper.ST0501G0R1(map);
	}
	
	public List ST0501G1R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String temp[] = map.get("VHC_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
		map.put("VHC_ID", temp);
		return st0501Mapper.ST0501G1R0(map);
	}
	
	public List ST0501G2R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String temp[] = map.get("VHC_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
		map.put("VHC_ID", temp);
		return st0501Mapper.ST0501G2R0(map);
	}
	
	public List ST0501SHI0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return st0501Mapper.ST0501SHI0(map);
	}
}
