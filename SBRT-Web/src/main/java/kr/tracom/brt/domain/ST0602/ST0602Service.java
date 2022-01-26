package kr.tracom.brt.domain.ST0602;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;

@Service
public class ST0602Service extends ServiceSupport {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceSupport.class);

	@Autowired
	private ST0602Mapper st0602Mapper;
	
	public List ST0602G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return st0602Mapper.ST0602G0R0(map);
	}
	
	public List ST0602G1R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		return st0602Mapper.ST0602G1R0(map);
	}
	
	public List ST0602G1R1() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String tabDivG1 = (String) map.get("TAB_DIV_G1");
		
		if(tabDivG1.equals("BUS")){
			String temp[] = map.get("VHC_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
			map.put("VHC_ID", temp);
		}else if(tabDivG1.equals("DRV")) {
			String temp[] = map.get("DRV_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
			map.put("DRV_ID", temp);
		}
	
		return st0602Mapper.ST0602G1R1(map);
	}
	
	public List ST0602G2R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String tabDivG1 = (String) map.get("TAB_DIV_G1");
		
		if(tabDivG1.equals("BUS")){
			String temp[] = map.get("VHC_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
			map.put("VHC_ID", temp);
		}else if(tabDivG1.equals("DRV")) {
			String temp[] = map.get("DRV_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
			map.put("DRV_ID", temp);
		}
	
		return st0602Mapper.ST0602G2R0(map);
	}
	
}
