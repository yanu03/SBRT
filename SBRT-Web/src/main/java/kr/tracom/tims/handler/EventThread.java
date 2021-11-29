package kr.tracom.tims.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import kr.tracom.beans.BeanUtil;
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
import kr.tracom.platform.service.kafka.model.KafkaMessage;
import kr.tracom.tims.OperDtUtil;
import kr.tracom.tims.domain.CurInfoMapper;
import kr.tracom.tims.domain.HistoryMapper;
import kr.tracom.tims.domain.TimsMapper;
import kr.tracom.ws.WsClient;

public class EventThread extends Thread{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Queue<KafkaMessage> kafkaQ = new LinkedList<>();
	private String sessionId;	
	
	private boolean bRunning = true;
	
	//@Autowired
    TimsMapper timsMapper;
    
    //@Autowired
    HistoryMapper historyMapper;
    
    //@Autowired
    CurInfoMapper curInfoMapper;
    
    //@Autowired
    CommonMapper commonMapper;
    
    //@Autowired
	WsClient webSocketClient;
	
	
	
	public EventThread(String sessionId) {
		this.sessionId = sessionId;
		
	    timsMapper = (TimsMapper) BeanUtil.getBean(TimsMapper.class);
	    historyMapper = (HistoryMapper) BeanUtil.getBean(HistoryMapper.class);
	    curInfoMapper = (CurInfoMapper) BeanUtil.getBean(CurInfoMapper.class);
	    commonMapper = (CommonMapper) BeanUtil.getBean(CommonMapper.class);
		webSocketClient = (WsClient) BeanUtil.getBean(WsClient.class);
		
	}
	
	
	public void stop(boolean bStop) {
		
		bRunning = false;
	}
	
	
	@Override
	public void run() {
		
		while(bRunning) {
			
			//logger.info("HandleThread Running...kafkaQ.size:{}", getKafkaSize());
			
			try {
				KafkaMessage msg = getKafkaMessage();
				
				if(msg != null) {
					
					//logger.info("===================== START >> sessionId:{}", sessionId);
					
					Map<String, Object> map = null;
					
					String sessionId = msg.getSessionId();
		        	TimsMessage timsMessage = msg.getTimsMessage();
		        	
		        	map = handle(timsMessage, sessionId);
		        	
		        	//웹소켓 전송이 필요한 경우
		            if(map != null) {    		
		        		webSocketClient.sendMessage(map);
		            }
		            
		            //logger.info("===================== END >> sessionId:{}", sessionId);
				}
				
				Thread.sleep(1);
				
			} catch (InterruptedException e) {
				logger.error("", e);
			}
		}
		
	}
	
	
	public void addKafkaMessage(KafkaMessage kafkaMessage) {		
		kafkaQ.add(kafkaMessage);		
	}
	
	
	public KafkaMessage getKafkaMessage() {
		return kafkaQ.poll();
	}
	
	public int getKafkaSize() {
		return kafkaQ.size();
	}
	
	
	
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
                	
                	logger.debug("Received BrtAtCode.BUS_INFO >> {}", atMessage);
                	
                    
                	AtBusInfo busInfo = (AtBusInfo)atMessage.getAttrData();
                	
                	//insert to BRT_CUR_OPER_INFO
                	Map<String, Object> busInfoMap = busInfo.toMap();

                	try {
                		insertCurOperInfo(busInfoMap);
                	} catch (DuplicateKeyException e) {
                		//logger.error("", e);
                	} catch (Exception e) {
                		logger.error("", e);
					}
                	
                	
                	//모니터링용 웹소켓 데이터
                	paramMap = new HashMap<>();
                	paramMap.put("MNG_ID", sessionId);

                	vhcInfo = timsMapper.selectVhcInfo(paramMap);
                	//Map<String, Object> dataMap =busInfo.toMap();
                	
                	wsDataMap = new HashMap<>();
                	wsDataMap.put("ATTR_ID", attrId);
                	wsDataMap.put("ROUT_ID", busInfoMap.get("ROUT_ID"));
                	wsDataMap.put("ROUT_NM", busInfoMap.get("ROUT_NM"));
                	wsDataMap.put("VHC_NO", busInfoMap.get("BUS_NO"));
                	
                	wsDataMap.put("VHC_ID", vhcInfo.get("VHC_ID"));
                	wsDataMap.put("DVC_ID", vhcInfo.get("DVC_ID"));
                	wsDataMap.put("GPS_X", busInfoMap.get("LONGITUDE"));
                	wsDataMap.put("GPS_Y", busInfoMap.get("LATITUDE"));
                	wsDataMap.put("PREV_NODE_NM", busInfoMap.get("PREV_NODE_NM")); //이전 정류소/교차로
                	wsDataMap.put("NEXT_NODE_ID", busInfoMap.get("NEXT_NODE_ID")); //다음 정류소/교차로
                	wsDataMap.put("NEXT_NODE_NM", busInfoMap.get("NEXT_NODE_NM"));
                	wsDataMap.put("NEXT_NODE_TYPE", busInfoMap.get("NEXT_NODE_TYPE"));
                	
                    break;
                    
                    
            	case BrtAtCode.BUS_ARRIVAL_INFO: //차량 도착정보
            		
            		logger.info("Received BrtAtCode.BUS_ARRIVAL_INFO >> {}", atMessage);
            		
                	
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
                	
                	logger.info("Received BrtAtCode.BUS_OPER_EVENT >> {}", atMessage);
                	
                	
                	//이벤트 이력정보에 insert
                	
                	 //String eventData = "";
                	 String eventCd = "";
                	 String eventType = "";
                	 AtBusOperEvent busEvent = (AtBusOperEvent)atMessage.getAttrData();            	
                	 
                	 byte eventCode = busEvent.getEventCode();
                	 Map<String, Object> busEventMap = busEvent.toMap();                        	 
                 		

                	 try {
                		 //현재운행정보도 업데이트
                		 insertCurOperInfo(busEventMap);
                	 } catch (DuplicateKeyException e) {
                 		//logger.error("", e);
                 	 }	catch (Exception e) {
                 		logger.error("", e);
                	 }
                	 
                	 
                	 try {
                		//이력 insert            
                		 historyMapper.insertEventHistory(busEventMap);     		 
                	 } catch (DuplicateKeyException e) {
                 		//logger.error("", e);
                 	 }	catch (Exception e) {
                 		logger.error("", e);
                	 }                	 
                	 
                	 

                     switch(eventCode){
                     /** 운행 이벤트 **/
                     case 0x01: //정류장 도착
                     case 0x02: //정류장 출발
                     case 0x03: //기점 도착
                     case 0x04: //기점 출발
                     case 0x05: //종점 도착
                     case 0x06: //종점 출발
                     case 0x07: //노드 통과
                         //통플에서 정류장통과시에도 노드 통과 이벤트를 준다?
                         //brtMapper.insertLinkSpeed(busEventMap);
                     case 0x08: //음성 출력
                     /**특정 이벤트 **/
                     case 0x11: //문 열림
                     case 0x12: //문 닫힘
                    	 
                    	 paramMap = new HashMap<>();
                    	 
                    	 paramMap.put("COL", "DL_CD");
                    	 paramMap.put("CO_CD", "OPER_EVT_TYPE");
                    	 paramMap.put("COL3", "NUM_VAL4");
                    	 paramMap.put("COL_VAL3", (int)eventCode);
                    	 eventCd = commonMapper.selectDlCdCol(paramMap);
                    	 
                    	 paramMap.put("COL", "DL_CD_NM");
                    	 paramMap.put("CO_CD", "OPER_EVT_TYPE");
                    	 paramMap.put("COL3", "NUM_VAL4");
                    	 paramMap.put("COL_VAL3", (int)eventCode);
                    	 eventType = commonMapper.selectDlCdCol(paramMap);
                    	 
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
                         
                         try {
                        	 paramMap = new HashMap<>();
                        	 paramMap.put("COL", "DL_CD");
                        	 paramMap.put("CO_CD", "VIOLT_TYPE");
                        	 paramMap.put("COL3", "NUM_VAL4");
                        	 paramMap.put("COL_VAL3", (int)eventCode);
                        	 eventCd = commonMapper.selectDlCdCol(paramMap);
                        	 
                        	 paramMap.put("COL", "DL_CD_NM");
                        	 paramMap.put("CO_CD", "VIOLT_TYPE");
                        	 paramMap.put("COL3", "NUM_VAL4");
                        	 paramMap.put("COL_VAL3", (int)eventCode);
                        	 eventType = commonMapper.selectDlCdCol(paramMap);
                        	 
                    		 historyMapper.insertOperVioltHistory(busEventMap); //운행위반이력 insert                    		 
                    	 } catch (Exception e) {
                    		 logger.error("", e);
                    	 }
                         
                         
                         break;

                     /** 돌발 **/ //2021.10.26일자 적용
                     case 0x31: //사고
                     case 0x32: //낙하
                     case 0x33: //고장
                     case 0x34: //기타
                     //case 0x35: //테러 
                         logger.info("돌발 발생!! [IMP ID : " + busEvent.getImpId() + "]");
                         
                         
                         try {
                        	 paramMap = new HashMap<>();
                        	 paramMap.put("COL", "DL_CD");
                        	 paramMap.put("CO_CD", "INCDNT_TYPE");
                        	 paramMap.put("COL3", "NUM_VAL4");
                        	 paramMap.put("COL_VAL3", (int)eventCode);
                        	 eventCd = commonMapper.selectDlCdCol(paramMap);
                        	 
                        	 paramMap.put("COL", "DL_CD_NM");
                        	 paramMap.put("CO_CD", "INCDNT_TYPE");
                        	 paramMap.put("COL3", "NUM_VAL4");
                        	 paramMap.put("COL_VAL3", (int)eventCode);
                        	 eventType = commonMapper.selectDlCdCol(paramMap);
                        	 
                    		 curInfoMapper.insertIncidentInfo(busEventMap); //돌발정보 insert                    		 
                    	 } catch (Exception e) {
                    		 logger.error("", e);
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
        			wsDataMap.put("VHC_NO", busEvent.getBusNo());
        			wsDataMap.put("ROUT_ID", busEvent.getRouteId());
        			wsDataMap.put("ROUT_NM", busEventMap.get("ROUT_NM"));
                 	wsDataMap.put("VHC_ID", vhcInfo.get("VHC_ID"));
                 	wsDataMap.put("DVC_ID", vhcInfo.get("DVC_ID"));
                 	wsDataMap.put("GPS_X", busEventMap.get("LONGITUDE"));
                 	wsDataMap.put("GPS_Y", busEventMap.get("LATITUDE"));
                 	wsDataMap.put("NODE_NM", busEventMap.get("NODE_NM")); //지나온 노드명
                 	wsDataMap.put("NODE_TYPE", busEventMap.get("NODE_TYPE")); //지나온 노드 타입
                	wsDataMap.put("PREV_NODE_NM", busEventMap.get("PREV_NODE_NM")); //이전 정류소/교차로
                 	wsDataMap.put("NEXT_NODE_ID", busEventMap.get("NEXT_NODE_ID")); //다음 정류소/교차로
                 	wsDataMap.put("NEXT_NODE_NM", busEventMap.get("NEXT_NODE_NM"));
                 	wsDataMap.put("NEXT_NODE_TYPE", busEventMap.get("NEXT_NODE_TYPE"));
                 	wsDataMap.put("EVT_CODE", eventCd);
                 	wsDataMap.put("EVT_TYPE", eventType);
                 	wsDataMap.put("SPEED", busEventMap.get("SPEED"));
                 	wsDataMap.put("EVT_DATA", busEventMap.get("EVENT_DATA"));
                	
                	
                    break;                    
                    
                   
                case BrtAtCode.DISPATCH:
                	
                	AtDispatch dispatch = (AtDispatch)atMessage.getAttrData();
                	Map<String, Object> curInfo = null;
                	String routId = "";
                	String routNm = "";
                	String vhcId = "";
                	String vhcNo = "";
                	String dpDiv = "";
                	String dpLv = "";
                	
                	logger.info("디스패치 수신. {}", dispatch);
                	
                	
                	try {
                		String udpDtm = dispatch.getUpdateTm().toString();
                		int msgType = (int)dispatch.getMessageType();
                		int msgLv = (int)dispatch.getMessageLevel();
                		
                		//차량정보 가져오기
                		paramMap = new HashMap<>();
                		paramMap.put("MNG_ID", sessionId);
                		
                		vhcInfo = timsMapper.selectVhcInfo(paramMap);
                		vhcId = String.valueOf(vhcInfo.get("VHC_ID"));
                		vhcNo = String.valueOf(vhcInfo.get("VHC_NO"));
                		
                		//디스패치 이력 생성
                		//버스의 현재 정보 가져오기 //BRT_CUR_OPER_INFO               		
                		paramMap.put("UPD_DTM", udpDtm);
                		paramMap.put("VHC_ID", vhcId);
                		
                		//운행일 생성. 시간에 따라 0시(24시) ~ 02시까지는 이전 날짜로 운행일 설정
                		String operDt = OperDtUtil.convertTimeToOperDt(udpDtm, "yyyy-MM-dd HH:mm:ss");
                		paramMap.put("OPER_DT", operDt); 
                		
                		curInfo = curInfoMapper.selectCurOperInfo(paramMap);
                		
                		if(curInfo != null) {
                		
                			routId = String.valueOf(curInfo.get("ROUT_ID"));
                			routNm = String.valueOf(curInfo.get("ROUT_NM"));
                			
	                		//디스패치 이력 넣기      
	                		//디스패치 구분코드 가져오기
	                		paramMap = new HashMap<>();
	                		paramMap.put("CO_CD", "DISPATCH_DIV");
	                		paramMap.put("COL", "DL_CD");
	                		paramMap.put("COL3", "TXT_VAL1");
	                		paramMap.put("COL_VAL3", msgType);
	                		dpDiv = commonMapper.selectDlCdCol(paramMap);
	                		
	                		paramMap.put("CO_CD", "DISPATCH_KIND");
	                		paramMap.put("COL", "DL_CD");
	                		paramMap.put("COL3", "TXT_VAL1");
	                		paramMap.put("COL_VAL3", msgLv);
	                		dpLv = commonMapper.selectDlCdCol(paramMap);
	                		
	                		
	                		HashMap<String, Object> dispatchLog = new HashMap<String, Object>(curInfo);
	                		dispatchLog.put("OPER_DT", operDt);
	                		dispatchLog.put("SEND_DATE", udpDtm);
	                		dispatchLog.put("DSPTCH_DIV", dpDiv);
	                		dispatchLog.put("DSPTCH_KIND", dpLv);
	                		dispatchLog.put("DSPTCH_CONTS", dispatch.getMessage());
	                		
	                		historyMapper.insertDispatchHistory(dispatchLog);
	                		
                		} else {
                			logger.info("디스패치 무시됨(현재 운행중인 차량정보 없음) : udpDtm:{}, vhcId:{}", udpDtm, vhcId);
                		}
	                	
	                	
                	} catch (DuplicateKeyException e) {
                		//logger.error("", e);
                	} catch (Exception e) {
                		logger.error("", e);
					}
                	
                	
                	if(curInfo != null) {
                		//웹소켓용 데이터 생성
                		
                		//디스패치 메시지 넣기
                		wsDataMap = new HashMap<>();
                		
                		wsDataMap.put("ATTR_ID", attrId);
                		wsDataMap.put("VHC_ID", vhcId);
                		wsDataMap.put("VHC_NO", vhcNo);
                		wsDataMap.put("ROUT_ID", routId);
                		wsDataMap.put("ROUT_NM", routNm);
                		wsDataMap.put("DSPTCH_DIV", dpDiv);
                		wsDataMap.put("DSPTCH_KIND", dpLv);
                		wsDataMap.put("MESSAGE", dispatch.getMessage());
                	}
                	
                	
                	break;
                    
                    
                default:
                	break;
            }
        }
        
        
        return wsDataMap;
    }
    
    
    
    private int insertCurOperInfo(Map<String, Object> curOperInfo) throws Exception {
    	
    	//운행일 생성. 시간에 따라 0시(24시) ~ 02시까지는 이전 날짜로 운행일 설정
    	curOperInfo.put("OPER_DT", OperDtUtil.convertTimeToOperDt(curOperInfo.get("UPD_DTM").toString(), "yyyy-MM-dd HH:mm:ss")); 
    	
    	//다음노드(교차로 or 정류소)
    	Map<String, Object> realNodeInfo = timsMapper.selectNodeByLinkSn(curOperInfo); //통플에서 넘어온 노드순번(실제로는 링크순번) 으로 실제 노드순번 구하기
    	curOperInfo.put("ROUT_NM", realNodeInfo.get("ROUT_NM"));
    	curOperInfo.put("NODE_TYPE", realNodeInfo.get("NODE_TYPE"));
    	curOperInfo.put("NODE_NM", realNodeInfo.get("NODE_NM"));
    	curOperInfo.put("NODE_SN", realNodeInfo.get("NODE_SN"));
    	
    	Map<String, Object> nextNodeInfo = timsMapper.selectNextSttnCrsInfo(curOperInfo);
    	
		curOperInfo.put("PREV_NODE_NM", nextNodeInfo.get("PREV_NODE_NM"));
		curOperInfo.put("NEXT_NODE_ID", nextNodeInfo.get("NEXT_NODE_ID"));
		curOperInfo.put("NEXT_NODE_NM", nextNodeInfo.get("NEXT_NODE_NM")); 
		curOperInfo.put("NEXT_NODE_TYPE", nextNodeInfo.get("NEXT_NODE_TYPE"));
    	
    	return curInfoMapper.insertCurOperInfo(curOperInfo);
    }
    
	

}
