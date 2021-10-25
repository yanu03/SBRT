package kr.tracom.brt.domain.MO0600;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;

@Service
public class MO0600Service extends ServiceSupport{

	@Autowired
	private MO0600Mapper mo0600Mapper;

	public List MO0600G0R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return mo0600Mapper.MO0600G0R0(param);
	}
	
	public List MO0600G1R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return mo0600Mapper.MO0600G1R0(param);
	}
	
	public List MO0600SHI1() throws Exception{
		Map param = getSimpleDataMap("dma_search");		
		return mo0600Mapper.MO0600SHI1(param);
	}	
}
