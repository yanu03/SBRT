package kr.tracom.brt.domain.MO0700;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;

@Service
public class MO0700Service extends ServiceSupport{

	@Autowired
	private MO0700Mapper mo0700Mapper;

	public List MO0700G0R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return mo0700Mapper.MO0700G0R0(param);
	}
	
	public List MO0700G1R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return mo0700Mapper.MO0700G1R0(param);
	}
	
	public List MO0700SHI1() throws Exception{
		Map param = getSimpleDataMap("dma_search");		
		return mo0700Mapper.MO0700SHI1(param);
	}	
}
