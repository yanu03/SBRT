package kr.tracom.brt.domain.AL0600;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.brt.domain.AL0103.AL0103Mapper;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class AL0600Service extends ServiceSupport {

	@Autowired
	private AL0600Mapper al0600Mapper;
	
	public List AL0600G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return al0600Mapper.AL0600G0R0(map);
	}
	
	
	public List AL0600SHI0() throws Exception {
		return al0600Mapper.AL0600SHI0();
	}
	
	public List AL0600G1R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_sub_search");
		return al0600Mapper.AL0600G1R0(param);
	}
	
	public List AL0600G1CNT() throws Exception {
		Map param = getSimpleDataMap("dma_sub_search");
		return al0600Mapper.AL0600G1CNT(param);
	}
	
		
	
}
