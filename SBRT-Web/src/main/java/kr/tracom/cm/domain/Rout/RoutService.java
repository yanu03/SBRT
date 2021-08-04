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

	public List<Map> selectRoutList() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return routMapper.selectRoutList(map);
	}
	
	public List<Map> selectRoutItem() throws Exception {
		return routMapper.selectRoutItem();
	}
	
	public List<Map> selectNodeListByRouts() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String temp[] = map.get("ROUTE_IDS").toString().replace("[","").replace("]","").split(",");
		map.put("ROUTE_IDS", temp);
		return routMapper.selectNodeListByRouts(map);
	}
	
	public List<Map> selectNodeListByRout() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		return routMapper.selectNodeListByRout(map);
	}

}
