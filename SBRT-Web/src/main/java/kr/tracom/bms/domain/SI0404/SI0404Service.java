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
import kr.tracom.cm.domain.Rout.RoutMapper;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.CommonUtil;
import kr.tracom.util.Constants;
import kr.tracom.util.DataInterface;
import kr.tracom.util.Result;

@Service
public class SI0404Service extends ServiceSupport {

	@Autowired
	private SI0404Mapper si0404Mapper;

	@Autowired
	private CommonMapper commonMapper;
	
	@Autowired
	private RoutMapper routMapper;	
	
	public Map SI0404G1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		
		List<Map<String, Object>> soundList = null;
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_ROUT_NODE_CMPSTN");
		try {
			
			//기존 노선노드테이블, 노선링크테이블 삭제
			if(param.size()>0) {
				si0404Mapper.SI0404G1DA0(map);
				si0404Mapper.SI0404G1DA1(map);
			}
			
			for (int i = 0; i < param.size(); i++) { //노드 생성
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				String rowStatus2 = "";
				if(i<param.size()-1) {
					rowStatus2 = (String) param.get(i+1).get("rowStatus");
				}
				if (rowStatus.equals("C")) {
					
					if(data.get("NODE_ID")==null||((String)data.get("NODE_ID")).isEmpty()) {
						Map key = si0404Mapper.SI0404G1K0();
						data.put("NODE_ID",key.get("SEQ"));
					}
					
					if((i<param.size()-1)&&rowStatus2.equals("C"))
					{
						
						if(data.get("LINK_ID")==null||((String)data.get("LINK_ID")).isEmpty()) {//라우트와누드 구성에 링크 ID를 넣기 위해 미리 생성함
							Map key = si0404Mapper.SI0404G1K1();
							data.put("LINK_ID",key.get("SEQ"));
						}
					}
					data.put("NODE_SN",(i+1));
					data.put("WAY_DIV",map.get("WAY_DIV"));
					iCnt += si0404Mapper.SI0404G1I0(data);
					
				} 
			}
			int sttnCnt = 0;
			double routLen = 0;
			for (int i = 0; i < param.size(); i++) { //링크 생성
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				String rowStatus2 = "";
				if(i<param.size()-1) {
					rowStatus2 = (String) param.get(i+1).get("rowStatus");
				}
				if (rowStatus.equals("C")) {
					if(Constants.NODE_TYPE_BUSSTOP.equals((String) data.get("NODE_TYPE"))){
						sttnCnt++;
					}
					
					if((i<param.size()-1)&&rowStatus2.equals("C")) {
							data.put("LINK_SN",(i+1));
							data.put("ST_NODE_ID",param.get(i).get("NODE_ID"));
							data.put("ED_NODE_ID",param.get(i+1).get("NODE_ID"));
							String linkNm = param.get(i).get("NODE_NM") + "-" + param.get(i+1).get("NODE_NM");
							data.put("LINK_NM",linkNm);
							
							double len = DataInterface.getDistanceBetween(Double.parseDouble((String)param.get(i).get("GPS_X")), Double.parseDouble((String)param.get(i).get("GPS_Y")), 
									Double.parseDouble((String)param.get(i+1).get("GPS_X")), Double.parseDouble((String)param.get(i+1).get("GPS_Y")));
							
							data.put("LEN",CommonUtil.pointRound(len,3));
							routLen += len;
							si0404Mapper.SI0404G1I1(data);
					}
				}
			}
			if(param.size()>0) {
				Map routMap = new HashMap();
				double routStrtLen = DataInterface.getDistanceBetween(Double.parseDouble((String)param.get(0).get("GPS_X")), Double.parseDouble((String)param.get(0).get("GPS_Y")), 
						Double.parseDouble((String)param.get(param.size()-1).get("GPS_X")), Double.parseDouble((String)param.get(param.size()-1).get("GPS_Y")));
						
				routMap.put("ROUT_ID", (String)param.get(0).get("ROUT_ID"));
				
				routMap.put("ROUT_LEN", CommonUtil.pointRound(routLen,3));
				routMap.put("ROUT_STRT_LEN", CommonUtil.pointRound(routStrtLen,3));
				routMap.put("STTN_CNT", sttnCnt);
				routMapper.updateRout(routMap);
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
				|| Constants.NODE_TYPE_NORMAL.equals(map.get("NODE_TYPE"))){
			//일반 노드 처리
			param.put("CO_CD", Constants.INTG_URL);
			param.put("DL_CD", Constants.URL_CODE_SEJONG_ROUT);
			List<Map<String, Object>> list = commonMapper.selectCommonDtlList(param);
			String baseUrl = (String) list.get(0).get("REMARK");
			String json = DataInterface.interface_URL("POST", baseUrl + map.get("INT_ROUT_ID")); 
			nodeList = DataInterface.parseJsonRouteNode(json);
		}
		
		if(map.get("NODE_TYPE") == null || ((String)map.get("NODE_TYPE")).isEmpty() 
				|| Constants.NODE_TYPE_BUSSTOP.equals(map.get("NODE_TYPE"))){
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
					tmp.put("NODE_ID",DataInterface.getTagValue("nodeid", eElement).substring(3));
					tmp.put("NODE_NM",DataInterface.getTagValue("nodenm", eElement));
					tmp.put("STTN_NO",DataInterface.getTagValue("nodeno", eElement));
					tmp.put("GPS_Y",DataInterface.getTagValue("gpslati", eElement));
					tmp.put("GPS_X",DataInterface.getTagValue("gpslong", eElement));
					tmp.put("NODE_TYPE",Constants.NODE_TYPE_BUSSTOP);
					
					staList.add(tmp);
				}
			}
			//첫번째 정류장과 마지막 정류장이 같은 경우 (순환노선)
			//첫번째 정류장 삭제
			if(staList.size() > 0) {
				if(staList.get(0).get("STTN_ID").equals(staList.get(staList.size()-1).get("STTN_ID"))) {
					staList.remove(0);
				}
			}
		}

		if(nodeList!=null && nodeList.size() > 0 && staList!=null && staList.size() > 0) {
			returnList = DataInterface.generalNode2(staList,nodeList);
			DataInterface.insertNodeToNode(returnList, staList);
			
			map.put("NODE_TYPE",Constants.NODE_TYPE_CROSS);
			List<Map<String, Object>> crsList = routMapper.selectNodeListByRout(map);
			if(crsList!=null && crsList.size() > 0) {
				returnList = DataInterface.generalNode2(crsList,returnList);
				DataInterface.insertNodeToNode(returnList, crsList);
			}
			map.put("NODE_TYPE",Constants.NODE_TYPE_SOUND);
			List<Map<String, Object>> sndList = routMapper.selectNodeListByRout(map);
			if(sndList!=null && sndList.size() > 0) {
				DataInterface.insertNodeToNode(returnList, sndList);
			}
		}else if(nodeList!=null && nodeList.size() > 0) {
			map.put("NODE_TYPE",Constants.NODE_TYPE_CROSS);
			List<Map<String, Object>> crsList = routMapper.selectNodeListByRout(map);
			if(crsList!=null && crsList.size() > 0) {
				returnList = DataInterface.generalNode2(crsList,nodeList);
				DataInterface.insertNodeToNode(returnList, crsList);
				
				map.put("NODE_TYPE",Constants.NODE_TYPE_SOUND);
				List<Map<String, Object>> sndList = routMapper.selectNodeListByRout(map);
				if(sndList!=null && sndList.size() > 0) {
					DataInterface.insertNodeToNode(returnList, sndList);
				}
			}
			else {
				returnList = nodeList;
			}
		}else if(staList!=null && staList.size() > 0) {
			returnList = staList;
		}
		return returnList;
	}
	
}
