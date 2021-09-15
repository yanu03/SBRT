package kr.tracom.brt.domain.MO0101;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;

@Service
public class MO0101Service extends ServiceSupport{

	@Autowired
	private MO0101Mapper mo0101Mapper;
	
	public List MO0101G0R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return mo0101Mapper.MO0101G0R0(param);
	}

	public List MO0101G1R0() throws Exception{
		Map param = getSimpleDataMap("dma_sub_search");
		return mo0101Mapper.MO0101G1R0(param);
	}
	
	public List MO0101SHI0() throws Exception{
		return mo0101Mapper.MO0101SHI0();
	}
	
	public List MO0101SHI1() throws Exception{
		Map param = getSimpleDataMap("dma_sub_search");		
		return mo0101Mapper.MO0101SHI1(param);
	}
	
	public List MO0101G2R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_sub_search");
		return mo0101Mapper.MO0101G2R0(param);
	}	
	
}
