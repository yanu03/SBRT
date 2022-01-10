package kr.tracom.brt.domain.ST0701;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.brt.domain.ST0701.ST0701Mapper;
import kr.tracom.cm.support.ServiceSupport;

@Service
public class ST0701Service extends ServiceSupport {

	@Autowired
	private ST0701Mapper st0701Mapper;
	
	public List ST0701G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return st0701Mapper.ST0701G0R0(map);
	}
	
	
	public List ST0701G1R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String temp[] = map.get("CPLNT_TYPE").toString().replace("[","").replace("]","").replace(" ","").split(",");
		map.put("CPLNT_TYPE", temp);
		return st0701Mapper.ST0701G1R0(map);
	}
	
	public List ST0701G2R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String temp[] = map.get("CPLNT_TYPE").toString().replace("[","").replace("]","").replace(" ","").split(",");
		map.put("CPLNT_TYPE", temp);
		return st0701Mapper.ST0701G2R0(map);
	}
	
	public List ST0701SHI0() throws Exception {
		return st0701Mapper.ST0701SHI0();
	}
	
	public List ST0701SHI1() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		return st0701Mapper.ST0701SHI1(map);
	}
}
