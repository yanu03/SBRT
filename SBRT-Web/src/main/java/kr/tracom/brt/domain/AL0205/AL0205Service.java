package kr.tracom.brt.domain.AL0205;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.brt.domain.AL0103.AL0103Mapper;
import kr.tracom.cm.domain.OperPlan.OperPlanService;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class AL0205Service extends ServiceSupport {

	@Autowired
	OperPlanService operPlanService;
	
	@Autowired
	private AL0205Mapper al0205Mapper;
	
	public List AL0205G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return al0205Mapper.AL0205G0R0(map);
	}
	
	public List AL0205G0R1() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return al0205Mapper.AL0205G0R1(map);
	}
	
	public List AL0205SHI0() throws Exception {
		return al0205Mapper.AL0205SHI0();
	}
	
	public List AL0205SHI1() throws Exception{
		Map param = getSimpleDataMap("dma_search");		
		return al0205Mapper.AL0205SHI1(param);
	}
	
	public List AL0205G1R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_sub_search");
		return al0205Mapper.AL0205G1R0(param);
	}
	
	public List AL0205G1CNT() throws Exception {
		Map param = getSimpleDataMap("dma_sub_search");
		return al0205Mapper.AL0205G1CNT(param);
	}
	
	
	
}
