package kr.tracom.bms.domain.SI0404;

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
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Constants;
import kr.tracom.util.DataInterface;
import kr.tracom.util.Result;

@Service
public class SI0404Service extends ServiceSupport {

	@Autowired
	private SI0404Mapper si0404Mapper;

	@Autowired
	private CommonMapper commonMapper;

	
	public List SI0404G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return si0404Mapper.SI0404G0R0(map);
	}

	public Map SI0404G0K0() throws Exception {
		return si0404Mapper.SI0404G0K0(); 
	}
	
	public List SI0404SHI0() throws Exception {
		return si0404Mapper.SI0404SHI0();
	}	
	
	public Map SI0404G1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_ROUT_NODE_CMPSTN");
		try {
			
			if(param.size()>0)si0404Mapper.SI0404G1DA0(param.get(0));
			
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += si0404Mapper.SI0404G1I0(data);
				} else if (rowStatus.equals("U")) {
					uCnt += si0404Mapper.SI0404G1U0(data);
				} else if (rowStatus.equals("D")) {
					dCnt += si0404Mapper.SI0404G1D0(data);
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
	
	public List SI0404G1R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_sub_search");
		return si0404Mapper.SI0404G1R0(param);
	}
	

	
	public List SI0404P0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");		
		Map<String, Object> param = new HashMap<>();
		List<Map<String, Object>> returnList = null;
		List<Map<String, Object>> nodeList = null;
		List<Map<String, Object>> staList = null;
		
		
		
		if(map.get("NODE_TYPE") == null || ((String)map.get("NODE_TYPE")).isEmpty() 
				|| Constants.NODE_TYPE_3.equals(map.get("NODE_TYPE"))){
			//일반 노드 처리
			param.put("CO_CD", Constants.INTG_URL);
			param.put("DL_CD", Constants.URL_CODE_SEJONG_ROUT);
			List<Map<String, Object>> list = commonMapper.selectCommonDtlList(param);
			String baseUrl = (String) list.get(0).get("REMARK");
			String json = DataInterface.interface_URL("POST", baseUrl + map.get("INT_ROUT_ID")); 
			nodeList = DataInterface.parseJsonRouteNode(json);
		}
		
		if(map.get("NODE_TYPE") == null || ((String)map.get("NODE_TYPE")).isEmpty() 
				|| Constants.NODE_TYPE_2.equals(map.get("NODE_TYPE"))){
			//공공데이터 정류소 처리
			String routId = "SJB" +  map.get("INT_ROUT_ID");
			
			param.put("CO_CD", Constants.INTG_URL);
			param.put("DL_CD", Constants.URL_CODE_OPENAPI_ROUT_STA );
			List<Map<String, Object>>  list = commonMapper.selectCommonDtlList(param);
			String baseUrl = baseUrl = (String) list.get(0).get("REMARK");
			
			param.put("CO_CD", Constants.API_KEY);
			param.put("DL_CD", Constants.KEY_CODE_OPENAPI_ROUT );
			list = commonMapper.selectCommonDtlList(param);
			String apiKey = (String) list.get(0).get("REMARK");
			
			String url = baseUrl + "serviceKey=" + apiKey + "&cityCode=12&routeId="+ routId;
			
			staList = new ArrayList<>();
			NodeList tempList = DataInterface.interface_XML(url);
			
		
			for(int i = 0; i < tempList.getLength(); i++) { //노선별 정류장에 걸리는 for문
				Map<String, Object> tmp = new HashMap<String, Object>();
				Node child = tempList.item(i);
	
				//한 노선의 정류장 parse
				if(child.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element)child;
					tmp.put("STTN_ID",DataInterface.getTagValue("nodeid", eElement).substring(3));
					tmp.put("NODE_NM",DataInterface.getTagValue("nodenm", eElement));
					tmp.put("STTN_NO",DataInterface.getTagValue("nodeno", eElement));
					tmp.put("GPS_Y",DataInterface.getTagValue("gpslati", eElement));
					tmp.put("GPS_X",DataInterface.getTagValue("gpslong", eElement));
					
					staList.add(tmp);
				}
			}
		}
		
		if(nodeList!=null && nodeList.size() > 0 && staList!=null && staList.size() > 0) {
			returnList = DataInterface.insertSttn(nodeList, staList);
		}else if(nodeList!=null && nodeList.size() > 0) {
			returnList = nodeList;
		}else if(staList!=null && staList.size() > 0) {
			returnList = staList;
		}
		return returnList;
	}
	
}
