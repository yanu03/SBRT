package kr.tracom.brt.domain.MO0302;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;

@Service
public class MO0302Service extends ServiceSupport{
	@Autowired
	private MO0302Mapper mo0302Mapper;
	
	public List MO0302G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return mo0302Mapper.MO0302G0R0(map);
	}
	
	public List MO0302SHI0() throws Exception {
		return mo0302Mapper.MO0302SHI0();
	}	
	
	
	
}
