package kr.tracom.cm.domain.Emer;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;

@Service
public class EmerService extends ServiceSupport{
	
	@Autowired
	private EmerMapper emerMapper;

	public List<Map<String, Object>> selectEmerList() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return emerMapper.selectEmerList(map);
	}
	
	public List<Map<String, Object>> selectEmerItem() throws Exception {
		return emerMapper.selectEmerItem();
	}	
	
	public List selectTreeEmerList() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return emerMapper.selectTreeEmerList(map);		
	}
}
