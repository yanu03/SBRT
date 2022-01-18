package kr.tracom.bms.domain.SI0407;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import kr.tracom.cm.domain.Common.CommonMapper;
import kr.tracom.cm.domain.Rout.RoutMapper;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.CommonUtil;
import kr.tracom.util.Constants;
import kr.tracom.util.DataInterface;
import kr.tracom.util.Result;

@Service
public class SI0407Service extends ServiceSupport {

	@Autowired
	private SI0407Mapper si0407Mapper;

	@Autowired
	private CommonMapper commonMapper;
	
	@Autowired
	private RoutMapper routMapper;	
	
	public List SI0407G0R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_search");
		return si0407Mapper.SI0407G0R0(param);		
	}
	
	public List SI0407SHI0() throws Exception {
		return si0407Mapper.SI0407SHI0();
	}	
	
	public Map SI0407G0S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_MOCK_NODE");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += si0407Mapper.SI0407G1I0(data);
				} else if (rowStatus.equals("U")) {
					uCnt += si0407Mapper.SI0407G1U0(data);
				} else if (rowStatus.equals("D")) {
					dCnt += si0407Mapper.SI0407G1D0(data);
				} 
			}			
		} catch(Exception e) {
			if (e instanceof DuplicateKeyException)
			{
				throw new MessageException(Result.ERR_KEY, "중복된 키값의 데이터가 존재합니다.");
			}
			else
			{
				throw e;
			}		
		}
		
		
		Map result = saveResult(iCnt, uCnt, dCnt);
		
		return result;		
	}
	
	public List SI0407G1R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_search2");
		return si0407Mapper.SI0407G1R0(param);		
	}
	
	public List SI0407SHI1() throws Exception {
		return si0407Mapper.SI0407SHI1();
	}
	
	public List SI0407SHI2() throws Exception {
		return si0407Mapper.SI0407SHI2();
	}
	
	public Map SI0407G1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_MOCK_LINK");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += si0407Mapper.SI0407G1I0(data);
				} else if (rowStatus.equals("U")) {
					uCnt += si0407Mapper.SI0407G1U0(data);
				} else if (rowStatus.equals("D")) {
					dCnt += si0407Mapper.SI0407G1D0(data);
				} 
			}			
		} catch(Exception e) {
			if (e instanceof DuplicateKeyException)
			{
				throw new MessageException(Result.ERR_KEY, "중복된 키값의 데이터가 존재합니다.");
			}
			else
			{
				throw e;
			}		
		}

		
		Map result = saveResult(iCnt, uCnt, dCnt);
		
		return result;		
	}
}
