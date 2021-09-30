package kr.tracom.cm.domain.Drv;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;


@Service
public class DrvService extends ServiceSupport {

	@Autowired
	private DrvMapper drvMapper;

	public List<Map<String, Object>> selectDrvList() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return drvMapper.selectDrvList(map);
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
