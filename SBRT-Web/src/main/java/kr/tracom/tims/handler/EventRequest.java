package kr.tracom.tims.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.tracom.cm.domain.Common.CommonMapper;
import kr.tracom.platform.attribute.BrtAtCode;
import kr.tracom.platform.attribute.brt.AtBusArrivalInfo;
import kr.tracom.platform.attribute.brt.AtBusArrivalInfoItem;
import kr.tracom.platform.attribute.brt.AtBusInfo;
import kr.tracom.platform.attribute.brt.AtBusOperEvent;
import kr.tracom.platform.attribute.brt.AtDispatch;
import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.attribute.AtMessage;
import kr.tracom.platform.net.protocol.payload.PlEventRequest;
import kr.tracom.tims.domain.CurInfoMapper;
import kr.tracom.tims.domain.HistoryMapper;
import kr.tracom.tims.domain.TimsMapper;

@Component
public class EventRequest {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    TimsMapper timsMapper;
    
    @Autowired
    HistoryMapper historyMapper;
    
    @Autowired
    CurInfoMapper curInfoMapper;
    
    @Autowired
    CommonMapper commonMapper;
    
    
    public Map<String, Object> handle(TimsMessage timsMessage, String sessionId){
    	
    	//웹소켓 전송이 필요한 경우 데이터 세팅
    	Map<String, Object> wsDataMap = null;
    	
    	//쿼리용 파라미터 맵
    	Map<String, Object> paramMap = null;
    	
    	Map<String, Object> vhcInfo = null;
    	
        PlEventRequest request = (PlEventRequest) timsMessage.getPayload();
        
        //logger.info("================ handle Tims Message");

        for(int i=0; i<request.getAttrCount(); i++){
            AtMessage atMessage = request.getAttrList().get(i);
            short attrId = atMessage.getAttrId();

            switch(attrId){
                case BrtAtCode.BUS_INFO: //정주기 버스 정보
                    
                	AtBusInfo busInfo = (AtBusInfo)atMessage.getAttrData();
                	
                	//insert to BRT_CUR_OPER_INFO
                	Map<String, Object> busInfoMap = busInfo.toMap();

                	try {
                		insertCurOperInfo(busInfoMap);
                	} catch (Exception e) {
						//e.printStackTrace();
					}
                	
                	
                	//모니터링용 웹소켓 데이터
                	paramMap = new HashMap<>();
                	paramMap.put("MNG_ID", sessionId);

                	vhcInfo = timsMapper.selectVhcInfo(paramMap);
                	//Map<String, Object> dataMap =busInfo.toMap();
                	
                	wsDataMap = new HashMap<>();
                	wsDataMap.put("ATTR_ID", attrId);
                	wsDataMap.put("VHC_ID", vhcInfo.get("VHC_ID"));
                	wsDataMap.put("DVC_ID", vhcInfo.get("DVC_ID"));
                	wsDataMap.put("GPS_X", busInfoMap.get("LONGITUDE"));
                	wsDataMap.put("GPS_Y", busInfoMap.get("LATITUDE"));
                	wsDataMap.put("NEXT_NODE_ID", busInfoMap.get("NEXT_NODE_ID"));
                	wsDataMap.put("NEXT_NODE_NM", busInfoMap.get("NEXT_NODE_NM"));
                	wsDataMap.put("NEXT_NODE_TYPE", busInfoMap.get("NEXT_NODE_TYPE"));
                	
                	
                	
                    break;
                    
                    
            	case BrtAtCode.BUS_ARRIVAL_INFO: //차량 도착정보
                	
                	AtBusArrivalInfo busArrivalInfo = (AtBusArrivalInfo)atMessage.getAttrData();            	
                	
                	
                	/*모니터링용 데이터 생성*/
                	wsDataMap = new HashMap<>();
                	wsDataMap.put("ATTR_ID", attrId);
                	wsDataMap.put("STTN_ID", busArrivalInfo.getStopId());
                	
                	
                	List<AtBusArrivalInfoItem> arrivalInfoList = busArrivalInfo.getList();
                	List<HashMap <String, Object>> arrivalInfoMapList = new ArrayList<>();           	
                	
                	
                	for(AtBusArrivalInfoItem arrivalInfoItem : arrivalInfoList) {
                		HashMap<String, Object> arrivalInfoMap = new HashMap<>();
                		
                		String routId = arrivalInfoItem.getRoutId();
                		int loc = arrivalInfoItem.getLocation();
                		long remainSec = arrivalInfoItem.getTime();
                		
                		String routNm = timsMapper.selectRoutName(routId);
                		
                		arrivalInfoMap.put("ROUT_ID", routId);
                		arrivalInfoMap.put("ROUT_NM", routNm);
                		arrivalInfoMap.put("VHC_TYPE", "VT" + arrivalInfoItem.getBusType());                		
                		arrivalInfoMap.put("REMAIN_STTN", loc);
                		arrivalInfoMap.put("REMAIN_TM", remainSec);
                		
                		arrivalInfoMapList.add(arrivalInfoMap);
                	}
                	
                	
                	wsDataMap.put("LIST", arrivalInfoMapList);
                	                                
                	
                	break;                    
                    
                    
                case BrtAtCode.BUS_OPER_EVENT: //운행 이벤트 정보
                	//이벤트 이력정보에 insert
                	
                	 String eventNm = "";
                	 String eventData = "";
                	 AtBusOperEvent busEvent = (AtBusOperEvent)atMessage.getAttrData();            	
                	 
                	 byte eventCode = busEvent.getEventCode();
                	 Map<String, Object> busEventMap = busEvent.toMap();                        	 
                 		

                	 try {
                		 //현재운행정보도 업데이트
                		 insertCurOperInfo(busEventMap);
                		 
                		 historyMapper.insertEventHistory(busEventMap); //이력 insert                		 
                		 
                	 } catch (Exception e) {
                		 //e.printStackTrace();
                	 }

                     switch(eventCode){
                     /** 운행 이벤트 **/
                     case 0x01: //정류장 도착
                     case 0x03: //기점 도착
                     case 0x05: //종점 도착
                     case 0x02: //정류장 출발
                     case 0x04: //기점 출발
                     case 0x06: //종점 출발
                     case 0x07: //노드 통과
                         //통플에서 정류장통과시에도 노드 통과 이벤트를 준다?
                         //brtMapper.insertLinkSpeed(busEventMap);
                     case 0x08: //음성 출력
                     /**특정 이벤트 **/
                     case 0x11: //문 열림
                     case 0x12: //문 닫힘
                    	 
                    	 paramMap = new HashMap<>();
                    	 paramMap.put("COL", "DL_CD_NM");
                    	 paramMap.put("CO_CD", "OPER_EVT_TYPE");
                    	 paramMap.put("COL3", "NUM_VAL4");
                    	 paramMap.put("COL_VAL3", (int)eventCode);
                    	 
                    	 eventNm = commonMapper.selectDlCdCol(paramMap);
                    	 
                    	 //eventDesc = timsMapper.selectNodeInfo(paramMap);
                    	 
                         break;

                     /** 운행위반 이벤트 **/
                     case 0x21: //무정차 주행
                     case 0x22: //과속 주행
                     case 0x23: //급가속
                     case 0x24: //급감속
                     case 0x25: //급출발
                     case 0x26: //급정지
                     case 0x27: //개문주행
                     case 0x28: //노선이탈
                         logger.info("운행위반 발생!! [IMP ID : " + busEvent.getImpId() + "]");
                         
                         eventNm = "운행위반";
                         
                         paramMap = new HashMap<>();
                    	 paramMap.put("COL", "DL_CD_NM");
                    	 paramMap.put("CO_CD", "VIOLT_TYPE");
                    	 paramMap.put("COL3", "NUM_VAL4");
                    	 paramMap.put("COL_VAL3", (int)eventCode);
                    	 
                         try {
                        	 eventData = commonMapper.selectDlCdCol(paramMap);
                        	 
                    		 historyMapper.insertOperVioltHistory(busEventMap); //운행위반이력 insert                    		 
                    	 } catch (Exception e) {
                    		 e.printStackTrace();
                    	 }
                         
                         
                         break;

                     /** 돌발 **/
                     case 0x31: //차량 고장
                     case 0x32: //차량 사고
                     case 0x33: //차내 폭력 사고
                     case 0x34: //강도
                     case 0x35: //테러
                         logger.info("돌발 발생!! [IMP ID : " + busEvent.getImpId() + "]");
                         
                         eventNm = "돌발";
                         
                         
                         paramMap = new HashMap<>();
                    	 paramMap.put("COL", "DL_CD_NM");
                    	 paramMap.put("CO_CD", "INCDNT_TYPE");
                    	 paramMap.put("COL3", "NUM_VAL4");
                    	 paramMap.put("COL_VAL3", (int)eventCode);
                         
                         try {
                        	 eventData = commonMapper.selectDlCdCol(paramMap);
                        	 
                    		 curInfoMapper.insertIncidentInfo(busEventMap); //돌발정보 insert                    		 
                    	 } catch (Exception e) {
                    		 e.printStackTrace();
                    	 }
                         
                         break;


                     }

                     
                     
                 	//모니터링용 웹소켓 데이터
                 	paramMap = new HashMap<>();
                 	paramMap.put("MNG_ID", sessionId);

                 	vhcInfo = timsMapper.selectVhcInfo(paramMap);
                 	//Map<String, Object> dataMap =busInfo.toMap();
                 	
                 	wsDataMap = new HashMap<>();
                 	wsDataMap.put("ATTR_ID", attrId);
                 	wsDataMap.put("VHC_ID", vhcInfo.get("VHC_ID"));
                 	wsDataMap.put("DVC_ID", vhcInfo.get("DVC_ID"));
                 	wsDataMap.put("GPS_X", busEventMap.get("LONGITUDE"));
                 	wsDataMap.put("GPS_Y", busEventMap.get("LATITUDE"));
                 	wsDataMap.put("NEXT_NODE_ID", busEventMap.get("NEXT_NODE_ID"));
                 	wsDataMap.put("NEXT_NODE_NM", busEventMap.get("NEXT_NODE_NM"));
                 	wsDataMap.put("NEXT_NODE_TYPE", busEventMap.get("NEXT_NODE_TYPE"));
                 	wsDataMap.put("EVT_NM", eventNm);
                 	wsDataMap.put("EVT_DATA", eventData);
                	
                    
                    break;                    
                    
                   
                case BrtAtCode.DISPATCH:
                	
                	AtDispatch dispatch = (AtDispatch)atMessage.getAttrData();            
                	
                	try {
                		String udpDtm = dispatch.getUpdateTm().toString();
                		int msgType = (int)dispatch.getMessageType();
                		int msgLv = (int)dispatch.getMessageLevel();
                		
                		//차량정보 가져오기
                		paramMap = new HashMap<>();
                		paramMap.put("MNG_ID", sessionId);
                		
                		vhcInfo = timsMapper.selectVhcInfo(paramMap);
                		String vhcId = String.valueOf(vhcInfo.get("VHC_ID"));
                		
                		
                		//디스패치 이력 생성
                		//버스의 현재 정보 가져오기 //BRT_CUR_OPER_INFO               		
                		paramMap.put("UPD_DTM", udpDtm);
                		paramMap.put("VHC_ID", vhcId);
                		
                		Map<String, Object> curInfo = curInfoMapper.selectCurOperInfo(paramMap);
                		
                		//디스패치 이력 넣기      
                		//디스패치 구분코드 가져오기
                		paramMap = new HashMap<>();
                		paramMap.put("CO_CD", "DISPATCH_DIV");
                		paramMap.put("COL", "DL_CD");
                		paramMap.put("COL3", "TXT_VAL1");
                		paramMap.put("COL_VAL3", msgType);
                		String dpDiv = commonMapper.selectDlCdCol(paramMap);
                		
                		paramMap.put("CO_CD", "DISPATCH_KIND");
                		paramMap.put("COL", "DL_CD");
                		paramMap.put("COL3", "TXT_VAL1");
                		paramMap.put("COL_VAL3", msgLv);
                		String dpLv = commonMapper.selectDlCdCol(paramMap);
                		
                		
                		HashMap<String, Object> dispatchLog = new HashMap<String, Object>(curInfo);
                		dispatchLog.put("SEND_DATE", udpDtm);
                		dispatchLog.put("DSPTCH_DIV", dpDiv);
                		dispatchLog.put("DSPTCH_KIND", dpLv);
                		dispatchLog.put("DSPTCH_CONTS", dispatch.getMessage());
                		
                		historyMapper.insertDispatchHistory(dispatchLog);
                		
                		
                		//웹소켓용 데이터 생성
	                	
	                	//디스패치 메시지 넣기
	                	wsDataMap = new HashMap<>();
	                	
	                	wsDataMap.put("ATTR_ID", attrId);
	                	wsDataMap.put("VHC_ID", vhcId);
	                	wsDataMap.put("DSPTCH_DIV", dpDiv);
	                	wsDataMap.put("DSPTCH_KIND", dpLv);
	                	wsDataMap.put("MESSAGE", dispatch.getMessage());
	                	
	                	
                	} catch (Exception e) {
						e.printStackTrace();
					}
                	
                	break;
                    
                    
                default:
                	break;
            }
        }
        
        
        return wsDataMap;
    }
    
    
    Map<String, Object> getNextNodeInfo(Map<String, Object> curOperInfo) {
    	
    	//다음노드(교차로 or 정류소)
    	Map<String, Object> nextNodeInfo = timsMapper.selectNextSttnCrsInfo(curOperInfo);
    	
		
		return nextNodeInfo;
    	
    }
    
    private int insertCurOperInfo(Map<String, Object> curOperInfo) throws Exception {
    	
    	//다음노드(교차로 or 정류소)
    	Map<String, Object> nextNodeInfo = timsMapper.selectNextSttnCrsInfo(curOperInfo);
    	
		String nextNodeId = nextNodeInfo.get("NODE_ID").toString();
		String nextNodeNm = nextNodeInfo.get("NODE_NM").toString();
		String nextNodeType = nextNodeInfo.get("NODE_TYPE").toString();
		
		curOperInfo.put("NEXT_NODE_ID", nextNodeId);
		curOperInfo.put("NEXT_NODE_NM", nextNodeNm); 
		curOperInfo.put("NEXT_NODE_TYPE", nextNodeType);
    	
    	return curInfoMapper.insertCurOperInfo(curOperInfo);
    }
    
    
}
