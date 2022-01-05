package kr.tracom.brt.domain.ST0200;

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
public class ST0200Service extends ServiceSupport {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceSupport.class);

	@Autowired
	private ST0200Mapper st0200Mapper;
	
	public List ST0200G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return st0200Mapper.ST0200G0R0(map);
	}
	
	public List ST0200G1R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String temp[] = map.get("ROUT_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
		map.put("ROUT_ID", temp);
		return st0200Mapper.ST0200G1R0(map);
	}
	
	public List ST0200G1R1() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String temp[] = map.get("ROUT_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
		map.put("ROUT_ID", temp);
		return st0200Mapper.ST0200G1R1(map);
	}
	
	public List ST0200G1R2() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		String temp[] = map.get("ROUT_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
		map.put("ROUT_ID", temp);
		return st0200Mapper.ST0200G1R2(map);
	}
	
}
