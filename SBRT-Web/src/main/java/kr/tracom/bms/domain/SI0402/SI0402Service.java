package kr.tracom.bms.domain.SI0402;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.cm.domain.Rout.RoutMapper;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.CommonUtil;
import kr.tracom.util.Constants;
import kr.tracom.util.DataInterface;
import kr.tracom.util.Result;

@Service
public class SI0402Service extends ServiceSupport {

	@Autowired
	private SI0402Mapper si0402Mapper;
	
	@Autowired
	private RoutMapper routMapper;
	
	public List SI0402G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return si0402Mapper.SI0402G0R0(map);
	}

	public Map SI0402G0K0() throws Exception {
		return si0402Mapper.SI0402G0K0(); 
	}
	
	public List SI0402SHI0() throws Exception {
		return si0402Mapper.SI0402SHI0();
	}	
	
	public Map SI0402G1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;
		boolean isLinkChange = false; //링크가 추가되거나, 변경되었는지 체크
		
		Map<String, Object> map = getSimpleDataMap("dma_sub_search");
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_ROUT_NODE_CMPSTN");
		
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				
				//링크가 추가되거나, 변경되었는지 체크
				if(CommonUtil.notEmpty(data.get("NODE_SN"))&&CommonUtil.notEmpty(data.get("OLD_NODE_SN"))&&
						(data.get("NODE_SN").equals(data.get("OLD_NODE_SN"))==false)) {
					isLinkChange = true;
				}
				
				String rowStatus = (String) data.get("rowStatus");
				String nodeType = (String) data.get("NODE_TYPE");
				
				if (rowStatus.equals("C")) {
					Map key = null;
					if(CommonUtil.empty(data.get("NODE_ID"))){
						key = si0402Mapper.SI0402G1K0();
					}
					if(Constants.NODE_TYPE_VERTEX.equals(nodeType)==false
						&&Constants.NODE_TYPE_SOUND.equals(nodeType)==false
						&&Constants.NODE_TYPE_GARAGE.equals(nodeType)==false
					) {
						if(i<param.size()-1)
						{
							Map linkKeyMap = si0402Mapper.SI0402G1K1();
							data.put("LINK_ID",linkKeyMap.get("SEQ"));	
						}
						data.put("LINK_NODE_YN","Y");
					}
					
					if(key!=null)data.put("NODE_ID",key.get("SEQ"));
					if(CommonUtil.empty(data.get("OLD_NODE_SN"))||"0".equals(data.get("OLD_NODE_SN"))){
						iCnt += si0402Mapper.SI0402G1I0(data);
					}
					else {
						 iCnt += si0402Mapper.SI0402G1U0(data);
					}
					
					if(Constants.NODE_TYPE_BUSSTOP.equals(nodeType)&&Constants.NODE_TYPE_CROSS.equals(nodeType)
						&&(CommonUtil.notEmpty(data.get("STTN_ID"))||CommonUtil.notEmpty(data.get("CRS_ID")))){
						if(Constants.NODE_TYPE_BUSSTOP.equals(nodeType)&&CommonUtil.notEmpty(data.get("STTN_ID"))){
							data.put("TYPE","STTN_ID");	
							//data.put("WAY_DIV",map.get("WAY_DIV"));	
							routMapper.updateSttn(data);
						
						}
						else if(Constants.NODE_TYPE_CROSS.equals(nodeType)&&CommonUtil.notEmpty(data.get("CRS_ID"))){
							data.put("TYPE","CRS_ID");
							routMapper.updateCrs(data);
						}
						
						routMapper.updateRoutNodeToAnotherRoute(data);
						routMapper.updateMainRoutNodeToAnotherRoute(data);
					}
				} else if (rowStatus.equals("U")) {
					if(Constants.NODE_TYPE_VERTEX.equals(nodeType)==false
						&&Constants.NODE_TYPE_SOUND.equals(nodeType)==false
						&&Constants.NODE_TYPE_GARAGE.equals(nodeType)==false
					) {
						if(CommonUtil.empty(data.get("LINK_ID"))&&i<param.size()-1)
						{
							Map linkKeyMap = si0402Mapper.SI0402G1K1();
							data.put("LINK_ID",linkKeyMap.get("SEQ"));	
						}
						data.put("LINK_NODE_YN","Y");
					}
					
					if(Constants.NODE_TYPE_BUSSTOP.equals(nodeType)&&Constants.NODE_TYPE_CROSS.equals(nodeType)
						&&(CommonUtil.notEmpty(data.get("STTN_ID"))||CommonUtil.notEmpty(data.get("CRS_ID")))){
						
						if(Constants.NODE_TYPE_BUSSTOP.equals(nodeType)&&CommonUtil.notEmpty(data.get("STTN_ID"))){
							data.put("TYPE","STTN_ID");
							//data.put("WAY_DIV",map.get("WAY_DIV"));	
							routMapper.updateSttn(data);
						}
						else if(Constants.NODE_TYPE_CROSS.equals(nodeType)&&CommonUtil.notEmpty(data.get("CRS_ID"))){
							data.put("TYPE","CRS_ID");
							routMapper.updateCrs(data);
						}
						
						routMapper.updateRoutNodeToAnotherRoute(data);
						routMapper.updateMainRoutNodeToAnotherRoute(data);
						
						if(CommonUtil.notEmpty(data.get("OLD_NODE_ID"))){ //일반노드에서 정류소/교차로 노드로 변경시 NODE_ID를 변경해야함
							Map delParam = new HashMap();
							delParam.put("ROUT_ID", data.get("ROUT_ID"));
							delParam.put("NODE_ID", data.get("OLD_NODE_ID"));
							delParam.put("OLD_NODE_SN", data.get("OLD_NODE_SN"));
							si0402Mapper.SI0402G1D0(delParam);
							uCnt += si0402Mapper.SI0402G1I0(data);
						}
						else {
							uCnt += si0402Mapper.SI0402G1U0(data);
						}
					}
					else {
						uCnt += si0402Mapper.SI0402G1U0(data);
					}
					
				} else if (rowStatus.equals("D")) {
					dCnt += si0402Mapper.SI0402G1D0(data);
					
					//정류소,교차로 테이블의 NODE_ID 초기화
					//data.put("NODE_ID", "");
					//routMapper.updateSttn(data);
					//routMapper.updateCrs(data);
				}
			}
			
			//링크가 변경되었을때 링크를 재 구성함
			if((isLinkChange==true)&&(param.size()>0)) {
				int sttnCnt = 0;
				double routLen = 0;
				si0402Mapper.SI0402G1DA1(map);
				List<Map<String, Object>> routNodeList = si0402Mapper.SI0402G1R1(map);
				if(routNodeList.size()>0) {
					for (int i = 0; i < routNodeList.size()-1; i++) {
						Map data = routNodeList.get(i);
						String nodeType = (String) data.get("NODE_TYPE");
						
						Map data2 = routNodeList.get(i+1);
						if(Constants.NODE_TYPE_BUSSTOP.equals(nodeType)){
							sttnCnt++;
						}
						data.put("LINK_SN",(i+1));
						data.put("ST_NODE_ID",data.get("NODE_ID"));
						data.put("ED_NODE_ID",data2.get("NODE_ID"));
						String linkNm = data.get("NODE_NM") + "-" + data2.get("NODE_NM");
						data.put("LINK_NM",linkNm);
						double len = DataInterface.getDistanceBetween(CommonUtil.decimalToDouble(data.get("GPS_X")), CommonUtil.decimalToDouble(data.get("GPS_Y")), 
								CommonUtil.decimalToDouble(data2.get("GPS_X")), CommonUtil.decimalToDouble(data2.get("GPS_Y")));
						
						if(i==0) {
							data.put("ACCRU_LEN",0);
							si0402Mapper.updateLengthRoutNodeCmpstn(data);
						}
						data.put("LEN",CommonUtil.pointRound(len,3));
						routLen += len;
						si0402Mapper.SI0402G1I1(data); //링크 insert
						
						data2.put("ACCRU_LEN",(int)routLen);
						si0402Mapper.updateLengthRoutNodeCmpstn(data2);
					}
					Map routMap = new HashMap();
					double routStrtLen = DataInterface.getDistanceBetween(CommonUtil.decimalToDouble(routNodeList.get(0).get("GPS_X")), CommonUtil.decimalToDouble(routNodeList.get(0).get("GPS_Y")), 
							CommonUtil.decimalToDouble(routNodeList.get(routNodeList.size()-1).get("GPS_X")), CommonUtil.decimalToDouble(routNodeList.get(routNodeList.size()-1).get("GPS_Y")));
							
					routMap.put("ROUT_ID", (String)routNodeList.get(0).get("ROUT_ID"));
					
					routMap.put("ROUT_LEN", CommonUtil.pointRound(routLen,3));
					routMap.put("ROUT_STRT_LEN", CommonUtil.pointRound(routStrtLen,3));
					routMap.put("STTN_CNT", sttnCnt);
					routMapper.updateRout(routMap);
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
	
	public List SI0402G1R0() throws Exception {
		// TODO Auto-generated method stub
		Map param = getSimpleDataMap("dma_sub_search");
		return si0402Mapper.SI0402G1R0(param);
	}
	
	public List SI0402P0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return si0402Mapper.SI0402P0R0(map);
	}
	
	public List SI0402P1R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return si0402Mapper.SI0402P1R0(map);
	}
	
	public List SI0402P2R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return si0402Mapper.SI0402P2R0(map);
	}
		
	public Map SI0402P2S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_STTN_MST");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += si0402Mapper.SI0402P2I0(data);
				} else if (rowStatus.equals("U")) {
					uCnt += si0402Mapper.SI0402P2U0(data);
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
	
	public Map SI0402P2K0() throws Exception {
		return si0402Mapper.SI0402P2K0(); 
	}
	
	public List SI0402P3R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return si0402Mapper.SI0402P3R0(map);
	}
	
	public Map SI0402P3S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_BMS_CRS_MST");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("C")) {
					iCnt += si0402Mapper.SI0402P3I0(data);
				} else if (rowStatus.equals("U")) {
					uCnt += si0402Mapper.SI0402P3U0(data);
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
	
	public Map SI0402P3K0() throws Exception {
		return si0402Mapper.SI0402P3K0(); 
	}
	
	public List SI0402P5R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return si0402Mapper.SI0402P5R0(map);
	}	
}
