package kr.tracom.cm.domain.Main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainMapper {
	
	public List<Map> bmsMainG0();
	
	public List<Map> bmsMainG1();
	
	public Map bmsMainF0();
	
	public Map bmsMainF1();
	
	public List<Map> brtMainG1();
	
	public List<Map> brtMainG2();
	
	public Map brtMainF0();
	
	public Map brtMainF1();
	
	public Map brtMainF2();
	
	public Map brtMainF3();
}
