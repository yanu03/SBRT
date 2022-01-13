package kr.tracom.cm.domain.Node;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;


@Service
public class NodeService extends ServiceSupport {
	
	@Autowired
	private NodeMapper nodeMapper;
	
	public List<Map<String, Object>> selectNodeList() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return nodeMapper.selectNodeList(map);
	}
	
	public List<Map<String, Object>> selectNodeItem() throws Exception {
		return nodeMapper.selectNodeItem();
	}
	
	public List<Map<String, Object>> selectMockNodeList() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return nodeMapper.selectMockNodeList(map);
	}	
}
