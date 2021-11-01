package kr.tracom.cm.domain.Main;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;


@Service
public class MainService extends ServiceSupport {

	@Autowired
	private MainMapper mainMapper;

	public List<Map> bmsMainG0() throws Exception{
		return mainMapper.bmsMainG0();
	}
	
	public List<Map> bmsMainG1() throws Exception{
		return mainMapper.bmsMainG1();
	}
	
	public Map bmsMainF0() throws Exception{
		return mainMapper.bmsMainF0();
	}
	
	public Map bmsMainF1() throws Exception{
		return mainMapper.bmsMainF1();
	}
	
	public List<Map> brtMainG1() throws Exception{
		return mainMapper.brtMainG1();
	}
	
	public List<Map> brtMainG2() throws Exception{
		return mainMapper.brtMainG2();
	}
	
	public Map brtMainF0() throws Exception{
		return mainMapper.brtMainF0();
	}
	public Map brtMainF1() throws Exception{
		return mainMapper.brtMainF1();
	}
	public Map brtMainF2() throws Exception{
		return mainMapper.brtMainF2();
	}
	public Map brtMainF3() throws Exception{
		return mainMapper.brtMainF3();
	}
	
}
