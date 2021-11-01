package kr.tracom.brt.domain.ST0201;

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
public class ST0201Service extends ServiceSupport {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceSupport.class);

	@Autowired
	private ST0201Mapper st02010Mapper;
	
	public List ST0201G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return st02010Mapper.ST0201G0R0(map);
	}
	
	public List ST0201SHI1() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		return st02010Mapper.ST0201SHI1(map);
	}
	
	public Map ST0201G1R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search2");
		return st02010Mapper.ST0201G1R0(map);
	}
	
	public Map ST0201G1R1() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search2");
		return st02010Mapper.ST0201G1R1(map);
	}
	
	public List ST0201G2R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		Map<String, Object> map1 = getSimpleDataMap("dma_sub_search3");
		map.putAll(map1);
		return st02010Mapper.ST0201G2R0(map);
	}
	
	
}
