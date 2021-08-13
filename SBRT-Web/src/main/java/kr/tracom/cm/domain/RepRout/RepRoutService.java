package kr.tracom.cm.domain.RepRout;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;

@Service
public class RepRoutService extends ServiceSupport {

	@Autowired
	private RepRoutMapper reproutMapper;

	public List<Map<String, Object>> selectRepRoutList() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return reproutMapper.selectRepRoutList(map);
	}
	
	public List<Map<String, Object>> selectRepRoutItem() throws Exception {
		return reproutMapper.selectRepRoutItem();
	}
	
}
