package kr.tracom.brt.domain.AL0104;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.brt.domain.AL0104.AL0104Mapper;
import kr.tracom.cm.support.ServiceSupport;

@Service
public class AL0104Service extends ServiceSupport{

	@Autowired
	private AL0104Mapper al0104Mapper;	
	
	public List AL0104G1R0() throws Exception {
		return al0104Mapper.AL0104G1R0();
	}
	
	public List AL0104G2R0() throws Exception {
		return al0104Mapper.AL0104G2R0();
	}
}
