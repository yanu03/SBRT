package kr.tracom.brt.domain.VH0200;

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
public class VH0200Service extends ServiceSupport {

	@Autowired
	private VH0200Mapper vh0200Mapper;
	
	public List VH0200G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return vh0200Mapper.VH0200G0R0(map);
	}
	
	public List VH0200G1R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_sub_search");
		return vh0200Mapper.VH0200G1R0(param);
	}
	
	public List VH0200G1CNT() throws Exception {
		Map param = getSimpleDataMap("dma_sub_search");
		return vh0200Mapper.VH0200G1CNT(param);
	}
	
	public List VH0200G2CNT() throws Exception {
		Map param = getSimpleDataMap("dma_sub_search");
		return vh0200Mapper.VH0200G2CNT(param);
	}
	
	public List VH0200G2R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_sub_search");
		return vh0200Mapper.VH0200G2R0(param);
	}
	
}
