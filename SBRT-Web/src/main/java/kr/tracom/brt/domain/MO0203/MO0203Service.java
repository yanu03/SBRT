package kr.tracom.brt.domain.MO0203;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;

@Service
public class MO0203Service extends ServiceSupport{
	
	@Autowired
	private MO0203Mapper mo0203Mapper;
	
	public List<Map> MO0203G0R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return mo0203Mapper.MO0203G0R0(param);
	}
	
	public List<Map> MO0203SHI0() throws Exception{
		return mo0203Mapper.MO0203SHI0();
	}
	
	public List MO0203SHI1() throws Exception {
		return mo0203Mapper.MO0203SHI1();
	}
	
	public List MO0203SHI2() throws Exception {
		return mo0203Mapper.MO0203SHI2();
	}	
	
	public List MO0203G2R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_sub_search");
		return mo0203Mapper.MO0203G2R0(param);
	}	
	
	/*public List<Map> MO0203SCK() throws Exception{
		Map param = getSimpleDataMap("dma_sub_search");
		return mo0203Mapper.MO0203SCK(param);
	}*/
	
}
