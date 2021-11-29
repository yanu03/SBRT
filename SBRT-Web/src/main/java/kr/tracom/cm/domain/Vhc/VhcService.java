package kr.tracom.cm.domain.Vhc;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;


@Service
public class VhcService extends ServiceSupport {

	@Autowired
	private VhcMapper vhcMapper;

	public List<Map<String, Object>> selectVhcList() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return vhcMapper.selectVhcList(map);
	}
	
	public List<Map<String, Object>> selectAllocVhcList() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return vhcMapper.selectAllocVhcList(map);
	}
	
	public List<Map<String, Object>> selectCurOperVhcList() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return vhcMapper.selectCurOperVhcList(map);
	}
	
	public List<Map<String, Object>> selectVhcItem() throws Exception {
		return vhcMapper.selectVhcItem();
	}
	
	/*public List<Map<String, Object>> selectRoutList() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return routMapper.selectRoutList(map);
	}
	
	public List<Map<String, Object>> selectRoutItem() throws Exception {
		return routMapper.selectRoutItem();
	}
	
	public List<Map<String, Object>> selectNodeListByRouts() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String temp[] = map.get("ROUT_IDS").toString().replace("[","").replace("]","").replace(" ","").split(",");
		map.put("ROUT_IDS", temp);
		return routMapper.selectNodeListByRouts(map);
	}
	
	public List<Map<String, Object>> selectNodeListByRout() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		return routMapper.selectNodeListByRout(map);
	}
	
	public List<Map<String, Object>> selectNodeListByRepRout() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return routMapper.selectNodeListByRepRout(map);
	}

	public List<Map<String, Object>> selectSttnList() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return routMapper.selectSttnList(map);
	}
	
	public List<Map<String, Object>> selectSttnItem() throws Exception {
		return routMapper.selectSttnItem();
	}*/
}
