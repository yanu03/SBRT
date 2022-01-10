package kr.tracom.brt.domain.ST0604;

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
public class ST0604Service extends ServiceSupport {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceSupport.class);

	@Autowired
	private ST0604Mapper st0604Mapper;
	
	public List ST0604G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return st0604Mapper.ST0604G0R0(map);
	}
	
	public List ST0604G1R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		return st0604Mapper.ST0604G1R0(map);
	}
	
	public List ST0604G1R1() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		
		String temp[] = map.get("NODE_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
		map.put("NODE_ID", temp);
		
		return st0604Mapper.ST0604G1R1(map);
	
	}
	
	public List ST0604G2R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		
		String temp[] = map.get("NODE_ID").toString().replace("[","").replace("]","").replace(" ","").split(",");
		map.put("NODE_ID", temp);
	
		return st0604Mapper.ST0604G2R0(map);
	}
	
}
