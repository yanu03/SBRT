package kr.tracom.bms.domain.VD0301;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;

@Service
public class VD0301Service extends ServiceSupport{
	
	@Autowired
	private VD0301Mapper vd0301Mapper;
	
	public List VD0301G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return vd0301Mapper.VD0301G0R0(map);
	}
	
	public List VD0301SHI0() throws Exception {
		return vd0301Mapper.VD0301SHI0();
	}		
}
