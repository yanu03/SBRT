package kr.tracom.brt.domain.ST0603;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.tracom.bms.domain.PI0503.PI0503Mapper;
import kr.tracom.bms.ftp.FTPHandler;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Constants;
import kr.tracom.util.Result;

@Service
public class ST0603Service extends ServiceSupport {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceSupport.class);

	@Autowired
	private ST0603Mapper st0603Mapper;
	
	public List ST0603G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return st0603Mapper.ST0603G0R0(map);
	}
	
	public List ST0603G1R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		return st0603Mapper.ST0603G1R0(map);
	}
	
	public List ST0603G1R1() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String tabDivG1 = (String) map.get("TAB_DIV_G1");
		
		if(tabDivG1.equals("BUS")){
			String temp[] = map.get("VHC_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
			map.put("VHC_ID", temp);
		}else if(tabDivG1.equals("DRV")) {
			String temp[] = map.get("DRV_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
			map.put("DRV_ID", temp);
		}else if(tabDivG1.equals("STTN")) {
			String temp[] = map.get("NODE_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
			map.put("NODE_ID", temp);
		}
	
		return st0603Mapper.ST0603G1R1(map);
	}
	
	public List ST0603G2R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String tabDivG1 = (String) map.get("TAB_DIV_G1");
		
		if(tabDivG1.equals("BUS")){
			String temp[] = map.get("VHC_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
			map.put("VHC_ID", temp);
		}else if(tabDivG1.equals("DRV")) {
			String temp[] = map.get("DRV_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
			map.put("DRV_ID", temp);
		}else if(tabDivG1.equals("STTN")) {
			String temp[] = map.get("NODE_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
			map.put("NODE_ID", temp);
		}
	
		return st0603Mapper.ST0603G2R0(map);
	}
	
}
