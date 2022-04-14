package kr.tracom.brt.domain.VH0100;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;

@Service
public class VH0100Service extends ServiceSupport{
	
	@Autowired
	private VH0100Mapper vh0100Mapper;
	
	public List<Map> VH0100G0R0() throws Exception{
		Map param = getSimpleDataMap("dma_search");
		return vh0100Mapper.VH0100G0R0(param);
	}
	
	public List<Map> VH0100SHI0() throws Exception{
		return vh0100Mapper.VH0100SHI0();
	}
	
	public List<Map> selectDsptchDivItem() throws Exception{
		return vh0100Mapper.selectDsptchDivItem();
	}
}
