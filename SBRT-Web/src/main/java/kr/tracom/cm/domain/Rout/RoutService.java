package kr.tracom.cm.domain.Rout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;


@Service
public class RoutService extends ServiceSupport {

	@Autowired
	private RoutMapper routMapper;

	public List<Map<String, Object>> selectRoutList() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return routMapper.selectRoutList(map);
	}
	
	public List<Map<String, Object>> selectRoutListByRepRout() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return routMapper.selectRoutListByRepRout(map);
	}
	
	public List<Map<String, Object>> selectRoutItem() throws Exception {
		Map<String, Object> map = null;
		try {
			map = getSimpleDataMap("dma_search");
		}
		catch(Exception e) {
				
		}
		if(map==null) map = new HashMap<String, Object>();
		return routMapper.selectRoutItem(map);
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
	
	public List<Map<String, Object>> selectMainNodeListByRout() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		return routMapper.selectMainNodeListByRout(map);
	}

	public List<Map<String, Object>> selectSttnList() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return routMapper.selectSttnList(map);
	}
	
	public List<Map<String, Object>> selectSttnItem() throws Exception {
		return routMapper.selectSttnItem();
	}
	
	public List<Map<String, Object>> selectSttnCrsList() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return routMapper.selectSttnCrsList(map);
	}
}
