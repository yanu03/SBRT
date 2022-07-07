package kr.tracom.tims.handler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import kr.tracom.beans.BeanUtil;
import kr.tracom.cm.domain.Common.CommonMapper;
import kr.tracom.platform.attribute.BrtAtCode;
import kr.tracom.platform.attribute.bis.AtSbrtRouteInfo;
import kr.tracom.platform.attribute.brt.AtBusArrivalInfo;
import kr.tracom.platform.attribute.brt.AtBusArrivalInfoItem;
import kr.tracom.platform.attribute.brt.AtBusInfo;
import kr.tracom.platform.attribute.brt.AtBusOperEvent;
import kr.tracom.platform.attribute.brt.AtDispatch;
import kr.tracom.platform.attribute.common.AtTimeStamp;
import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.TimsMessageBuilder;
import kr.tracom.platform.net.protocol.attribute.AtMessage;
import kr.tracom.platform.net.protocol.payload.PlEventRequest;
import kr.tracom.platform.service.TService;
import kr.tracom.platform.service.config.PlatformConfig;
import kr.tracom.platform.service.kafka.model.KafkaMessage;
import kr.tracom.tims.OperDtUtil;
import kr.tracom.tims.domain.CurInfoMapper;
import kr.tracom.tims.domain.HistoryMapper;
import kr.tracom.tims.domain.TimsMapper;
import kr.tracom.tims.kafka.KafkaProducer;
import kr.tracom.util.CommonUtil;
import kr.tracom.ws.WsClient;

public class EventThread extends Thread {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private ConcurrentLinkedQueue<KafkaMessage> kafkaQ = new ConcurrentLinkedQueue<>();
	private String sessionId;

	private boolean bRunning = true;

	// @Autowired
	TimsMapper timsMapper;

	// @Autowired
	HistoryMapper historyMapper;

	// @Autowired
	CurInfoMapper curInfoMapper;

	// @Autowired
	CommonMapper commonMapper;

	// @Autowired
	WsClient webSocketClient;
	
	// @Autowired
	KafkaProducer kafkaProducer;	
	
	private static Map<String, Object> g_busOperInfoMap = new HashMap<>();
	
	private static Map<String, Object> g_busOperEventMap = new HashMap<>();
	
	private static Map<String, Object> g_busIdMap = new HashMap<>();
	
	private static Map<String, Object> g_routMap = new HashMap<>();
	
	private static Map<String, Object> g_operStsMap  = new HashMap<>();
	
	private static Map<String, Object> g_operEventCodeMap  = new HashMap<>();
	
	
	/*private boolean checkChangeRunTypeBusOperInfo(AtBusInfo busInfo) {
		String impId = busInfo.getImpId();
		if ((impId != null) && (impId.isEmpty() == false)) {
			AtBusInfo oldBusInfo = (AtBusInfo) g_busOperInfoMap.get(impId);
			if (oldBusInfo == null || oldBusInfo.getRunType() != busInfo.getRunType()) {
				if (busInfo != null && oldBusInfo != null) {
					logger.info("checkChangeRunTypeBusInfo busInfo: {}, oldBusInfo: {}", busInfo, oldBusInfo);
				}
                if(g_busOperInfoMap!=null) {
                	g_busOperInfoMap.put(impId, busInfo);
                }
                else{
                	g_busOperInfoMap = new HashMap<>();
                	g_busOperInfoMap.put(impId, busInfo);
                }
				return true;
			}
			return false;
		}
		return false;
	}*/
	
	private boolean checkChangeBusOperInfo(Map<String, Object> busInfo) {
		String impId = (String) busInfo.get("IMP_ID");
		if ((impId != null) && (impId.isEmpty() == false)) {
			Map<String, Object> oldBusOperInfo = (Map<String, Object>) g_busOperInfoMap.get(impId);
			if (oldBusOperInfo == null || oldBusOperInfo.get("OPER_STS") != busInfo.get("OPER_STS")
					|| busInfo.get("EVENT_CD")!=null&&oldBusOperInfo.get("EVENT_CD") != busInfo.get("EVENT_CD")
					|| oldBusOperInfo.get("LATITUDE") != busInfo.get("LATITUDE")
					|| oldBusOperInfo.get("LONGITUDE") != busInfo.get("LONGITUDE")) {
				if (busInfo != null && oldBusOperInfo != null) {
					logger.info("checkChangeBusOperInfo not equal busInfo: {}, oldBusOperInfo: {}", busInfo,
							oldBusOperInfo);
				}
				setBusOperInfo(busInfo);
				/*
				 * if(g_busOperInfoMap!=null) { g_busOperInfoMap.put(impId, busInfo); } else{
				 * g_busOperInfoMap = new HashMap<>(); g_busOperInfoMap.put(impId, busInfo); }
				 */
				return true;
			} else {
				if (busInfo != null && oldBusOperInfo != null) {
					logger.debug("checkChangeBusOperInfo equal busInfo: {}, oldbusInfo: {}", busInfo, oldBusOperInfo);
				}
			}
			return false;
		}
		return false;
	}
	
	private void setBusOperInfo(Map<String, Object> busInfo) {
		String impId = (String) busInfo.get("IMP_ID");
		if ((impId != null) && (impId.isEmpty() == false)) {

			if (g_busOperInfoMap != null) {
				g_busOperInfoMap.put(impId, busInfo);
			} else {
				g_busOperInfoMap = new HashMap<>();
				g_busOperInfoMap.put(impId, busInfo);
			}
		}
	}
	
	private Map<String, Object> getBusOperInfo(Map<String, Object> busInfo) {
		String impId = (String) busInfo.get("IMP_ID");
		if(CommonUtil.empty(g_busOperInfoMap.get(impId)))
			return curInfoMapper.selectCurOperInfo(busInfo);
		else
			return (Map<String, Object>) g_busOperInfoMap.get(impId);
	}
	
	//이벤트의 노드 변경이 있는지 체크
	private boolean checkRoutChangeBusOperEvent(AtBusOperEvent operEvent) {
		String impId = operEvent.getImpId();
		if ((impId != null) && (impId.isEmpty() == false)) {
			AtBusOperEvent oldOperEvent = (AtBusOperEvent) g_busOperEventMap.get(impId);
			if (oldOperEvent != null && (operEvent.getNodeSeq()<10 &&oldOperEvent.getNodeSeq() > operEvent.getNodeSeq())) {
				if (operEvent != null && oldOperEvent != null) {
					logger.info("checkRoutChangeBusOperEvent operEvent: {}, oldOperEvent: {}", operEvent, oldOperEvent);
				}
                if(g_busOperEventMap!=null) {
                	g_busOperEventMap.put(impId, operEvent);
                }
                else{
                	g_busOperEventMap = new HashMap<>();
                	g_busOperEventMap.put(impId, operEvent);
                }
				return true;
			}
            if(g_busOperEventMap!=null) {
            	g_busOperEventMap.put(impId, operEvent);
            }
            else{
            	g_busOperEventMap = new HashMap<>();
            	g_busOperEventMap.put(impId, operEvent);
            }
			return false;
		}
		return false;
	}
	
	private String getBusId(Map<String, Object> paramMap) {
		String  busNo = (String)paramMap.get("BUS_NO");
		logger.debug("getBusId() busNo="+busNo);
		String busId = null;
		if(g_busIdMap==null) {
			g_busIdMap = new HashMap<>();
			busId = curInfoMapper.getBusId(paramMap);
			g_busIdMap.put(busNo, busId);
			return busId;
		}
		
		
		if ((busNo != null) && (busNo.isEmpty() == false)) {
			busId = (String)g_busIdMap.get(busNo); 
			if ((busId != null) && (busId.isEmpty() == false)) {
				return busId;
			}
			else {
				busId = curInfoMapper.getBusId(paramMap);
				g_busIdMap.put(busNo, busId);
				return busId;
			}
		}
		else {
			return null;
		}
	}
	
	private Map<String, Object> getRoutMst(Map<String, Object> paramMap) {
		String routId= (String)paramMap.get("ROUT_ID");
		logger.debug("getRoutMst() routId="+routId);
		Map<String, Object> routInfo = null;
		
		if(routId==null)return null;
		
		if(g_routMap==null) {
			g_routMap = new HashMap<>();
			routInfo = getRoutMst(paramMap);
			g_routMap.put(routId, routInfo);
			return routInfo;
		}
		
		if ((routId != null) && (routId.isEmpty() == false)) {
			routInfo = (Map<String, Object>)g_routMap.get(routId); 
			if ((routInfo != null)) {
				return routInfo;
			}
			else {
				routInfo = curInfoMapper.getRoutMst(paramMap);
				g_routMap.put(routId, routInfo);
				return routInfo;
			}
		}
		else {
			return null;
		}
	}
	
	/*private String getOperSts(Map<String, Object> paramMap) {
		String runType = paramMap.get("RUN_TYPE")+"";
		logger.debug("getOperSts() runType="+runType);
		Map<String, Object> param = new HashMap<>();
		param.put("CO_CD", "OPER_STS");
		param.put("NUM_VAL4", paramMap.get("RUN_TYPE"));

		Map<String, Object> operSts = null;
		if(g_operStsMap==null) {
			g_operStsMap = new HashMap<>();
			operSts = curInfoMapper.getEventCode(param);
			if(operSts!=null) {
				g_operStsMap.put(runType, operSts);
				return (String) operSts.get("DL_CD");
			}
		}
		
		if ((runType != null) && (runType.isEmpty() == false)) {
			operSts = (Map<String, Object>)g_operStsMap.get(runType); 
			if ((operSts != null)) {
				return (String) operSts.get("DL_CD");
			}
			else {
				operSts = curInfoMapper.getEventCode(param);
				g_operStsMap.put(runType, operSts);
				return (String) operSts.get("DL_CD");
			}
		}
		else {
			return null;
		}
	}*/
	
	private Map<String, Object> getCommonCode( String coCd,String ValType, String value) {
		//String eventCd = paramMap.get("EVENT_CD")+"";
		logger.debug("getEventCode() coCd="+coCd +", eventCd="+value);
		Map<String, Object> param = new HashMap<>();
		String key = coCd+value;
		param.put("CO_CD", coCd);
		param.put("VAL_TYPE", ValType);
		param.put("VAL", value);
		
		Map<String, Object> eventCodeMap = null;
		if(g_operEventCodeMap==null) {
			g_operEventCodeMap = new HashMap<>();
			eventCodeMap = curInfoMapper.getEventCode(param);
			if(eventCodeMap!=null) {
				g_operEventCodeMap.put(key, eventCodeMap);
				return eventCodeMap;
			}
		}
		
		eventCodeMap = (Map<String, Object>)g_operEventCodeMap.get(key); 
		if ((eventCodeMap != null)) {
			return eventCodeMap;
		}
		else {
			eventCodeMap = curInfoMapper.getEventCode(param);
			g_operEventCodeMap.put(key, eventCodeMap);
			return eventCodeMap;
		}
	}

	
	private void insertCurAllocPlInfo(Map<String, Object> map) {
		Map<String, Object> param = new HashMap<>();
		param.put("OPER_DT", map.get("OPER_DT"));
		param.put("REP_ROUT_ID", map.get("REP_ROUT_ID"));
		param.put("ALLOC_NO", map.get("ALLOC_NO"));
		param.put("WAY_DIV", map.get("WAY_DIV"));
		param.put("OPER_VHC_ID", map.get("OPER_VHC_ID"));
		param.put("PRDT_ALLOC_NO", map.get("PRDT_ALLOC_NO"));
		param.put("OPER_SN", map.get("OPER_SN"));
		
		curInfoMapper.insertCurAllocPlInfo(param);
	}

	public EventThread(String sessionId) {
		this.sessionId = sessionId;

		timsMapper = (TimsMapper) BeanUtil.getBean(TimsMapper.class);
		historyMapper = (HistoryMapper) BeanUtil.getBean(HistoryMapper.class);
		curInfoMapper = (CurInfoMapper) BeanUtil.getBean(CurInfoMapper.class);
		commonMapper = (CommonMapper) BeanUtil.getBean(CommonMapper.class);
		webSocketClient = (WsClient) BeanUtil.getBean(WsClient.class);
		kafkaProducer = (KafkaProducer) BeanUtil.getBean(KafkaProducer.class);
	}
	

	public void stop(boolean bStop) {

		bRunning = false;
	}

	@Override
	public void run() {

		while (bRunning) {

			if(getKafkaSize()>0)
			logger.debug("HandleThread Running...kafkaQ.size:{}", getKafkaSize());

			try {
				KafkaMessage msg = getKafkaMessage();

				if (msg != null) {

					 //logger.info("===================== START >> sessionId:{}", sessionId);

					Map<String, Object> map = null;

					String sessionId = msg.getSessionId();
					TimsMessage timsMessage = msg.getTimsMessage();

					map = handle(timsMessage, sessionId);

					// 웹소켓 전송이 필요한 경우
					if (map != null) {
						//logger.info("webSocketClient.sendMessage before");
						webSocketClient.sendMessage(map);
						//logger.info("webSocketClient.sendMessage after");
					}

					 //logger.info("===================== END >> sessionId:{}", sessionId);
				}

				Thread.sleep(1);

			} catch (InterruptedException e) {
				logger.error("Exception {}", e);
			}
		}

	}

	public void addKafkaMessage(KafkaMessage kafkaMessage) {
		kafkaQ.offer(kafkaMessage);
	}

	public KafkaMessage getKafkaMessage() {
		while (kafkaQ.peek() != null) {
			return kafkaQ.poll();
		}
		 return null;
	}

	public int getKafkaSize() {
		return kafkaQ.size();
	}

	//static public Map<String, Object> busInfoMap = new HashMap<>();
    
	public Map<String, Object> handle(TimsMessage timsMessage, String sessionId) {

		// 웹소켓 전송이 필요한 경우 데이터 세팅
		Map<String, Object> wsDataMap = null;

		// 쿼리용 파라미터 맵
		Map<String, Object> paramMap = null;

		Map<String, Object> vhcInfo = null;

		PlEventRequest request = (PlEventRequest) timsMessage.getPayload();

		// logger.info("================ handle Tims Message");

		for (int i = 0; i < request.getAttrCount(); i++) {
			AtMessage atMessage = request.getAttrList().get(i);
			short attrId = atMessage.getAttrId();

			switch (attrId) {
			case BrtAtCode.BUS_INFO: // 정주기 버스 정보

				logger.info("BUS_INFO >> {}", atMessage);
				try {
					AtBusInfo busInfo = (AtBusInfo) atMessage.getAttrData();
	
					// insert to BRT_CUR_OPER_INFO
					Map<String, Object> busInfoMap = busInfo.toMap();
	
					busInfoMap.put("VHC_ID", getBusId(busInfoMap));
					if (CommonUtil.empty(busInfoMap.get("VHC_ID")))
						break;
					
					Map<String, Object> routMap = getRoutMst(busInfoMap);
					if(routMap!=null) {
						busInfoMap.put("REP_ROUT_ID", routMap.get("REP_ROUT_ID"));
						busInfoMap.put("WAY_DIV", routMap.get("WAY_DIV"));
						busInfoMap.put("ROUT_NM", routMap.get("ROUT_NM"));
						busInfoMap.put("REP_ROUT_NM", routMap.get("REP_ROUT_NM"));
					}
					if (CommonUtil.empty(busInfoMap.get("OPER_DT"))) {
						busInfoMap.put("OPER_DT", CommonUtil.getOperDt());
					}
		
					//busInfoMap.put("OPER_STS", getOperSts(busInfoMap));
					Map<String, Object> operSts = getCommonCode("OPER_STS","NUM_VAL4",busInfoMap.get("RUN_TYPE")+"");
					if(operSts!=null) {
						busInfoMap.put("OPER_STS", operSts.get("DL_CD"));
					}
	
					try {
						Map<String, Object> curAllocPlInfo = curInfoMapper.selectCurAllocPlInfoByOperVhcId(busInfoMap);
						if (curAllocPlInfo == null || CommonUtil.empty(curAllocPlInfo.get("OPER_VHC_ID"))) {
							
							 curAllocPlInfo = curInfoMapper.selectCurAllocPlInfoByVhcId(busInfoMap); 
							 if(curAllocPlInfo!=null) {
								 busInfoMap.put("ALLOC_NO", curAllocPlInfo.get("ALLOC_NO"));
								 busInfoMap.put("OPER_SN", curAllocPlInfo.get("OPER_SN"));
							 }
							 else {
								 logger.info("allocPl not BUS_INFO >> {}", atMessage);
								 break;
							 }
						}
						else {
							busInfoMap.put("ALLOC_NO", curAllocPlInfo.get("ALLOC_NO"));
							busInfoMap.put("OPER_SN", curAllocPlInfo.get("OPER_SN"));
							if(busInfoMap.get("REP_ROUT_ID")==null&&curAllocPlInfo.get("REP_ROUT_ID")!=null) {
								busInfoMap.put("REP_ROUT_ID", curAllocPlInfo.get("REP_ROUT_ID"));
							}
							else {
								busInfoMap.put("REP_ROUT_ID", "RR00000002"); //임시로
							}
						}
						
						if("OS001".equals(busInfoMap.get("OPER_STS"))==false) { //운행중이 아닐때만 저장
							busInfoMap.put("EVENT_CD",null);
							setOperEventData(busInfoMap);
							insertCurOperInfo(busInfoMap);
						}
	
					} catch (Exception e) {
						logger.error("Exception {}", e);
					}
	
					// 모니터링용 웹소켓 데이터
					paramMap = new HashMap<>();
					paramMap.put("MNG_ID", sessionId);
	
					vhcInfo = timsMapper.selectVhcInfo(paramMap);
					// Map<String, Object> dataMap =busInfo.toMap();
	
					wsDataMap = new HashMap<>();
					wsDataMap.put("ATTR_ID", attrId);
					wsDataMap.put("ROUT_ID", busInfoMap.get("ROUT_ID"));
					wsDataMap.put("ROUT_NM", busInfoMap.get("ROUT_NM"));
					wsDataMap.put("VHC_NO", busInfoMap.get("BUS_NO"));
	
					wsDataMap.put("CUR_SPD", busInfoMap.get("SPEED"));
					wsDataMap.put("VHC_ID", vhcInfo.get("VHC_ID"));
					wsDataMap.put("DVC_ID", vhcInfo.get("DVC_ID"));
					wsDataMap.put("GPS_X", busInfoMap.get("LONGITUDE"));
					wsDataMap.put("GPS_Y", busInfoMap.get("LATITUDE"));
					wsDataMap.put("PREV_NODE_NM", busInfoMap.get("PREV_NODE_NM")); // 이전 정류소/교차로
					wsDataMap.put("NEXT_NODE_ID", busInfoMap.get("NEXT_NODE_ID")); // 다음 정류소/교차로
					wsDataMap.put("NEXT_NODE_NM", busInfoMap.get("NEXT_NODE_NM"));
					wsDataMap.put("NEXT_NODE_TYPE", busInfoMap.get("NEXT_NODE_TYPE"));
					wsDataMap.put("OPER_STS", busInfoMap.get("OPER_STS"));
					wsDataMap.put("LINK_ID", busInfoMap.get("LINK_ID"));
					wsDataMap.put("NODE_SN", busInfoMap.get("NODE_SN"));
				}
				catch (Exception e) {
					logger.error("Exception {}", e);
				}
				break;

			case BrtAtCode.BUS_ARRIVAL_INFO: // 차량 도착정보

				logger.info("BUS_ARRIVAL_INFO >> {}", atMessage);
				try {
					AtBusArrivalInfo busArrivalInfo = (AtBusArrivalInfo) atMessage.getAttrData();
	
					/* 모니터링용 데이터 생성 */
					wsDataMap = new HashMap<>();
					wsDataMap.put("ATTR_ID", attrId);
					wsDataMap.put("STTN_ID", busArrivalInfo.getStopId());
	
					List<AtBusArrivalInfoItem> arrivalInfoList = busArrivalInfo.getList();
					List<HashMap<String, Object>> arrivalInfoMapList = new ArrayList<>();
	
					for (AtBusArrivalInfoItem arrivalInfoItem : arrivalInfoList) {
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
				}
				catch (Exception e) {
					logger.error("Exception {}", e);
				}
				break;

			case BrtAtCode.BUS_OPER_EVENT: // 운행 이벤트 정보

				logger.info(".BUS_OPER_EVENT >> {}", atMessage);

				// 이벤트 이력정보에 insert

				// String eventData = "";
				try {
					String eventCd = "";
					String eventCdName = "";
					AtBusOperEvent busEvent = (AtBusOperEvent) atMessage.getAttrData();
					String eventData = busEvent.getEventData();
					Map<String, Object> busEventMap = busEvent.toMap();
					byte eventCode = busEvent.getEventCode();
					
					Map<String, Object> eventCodeMap = getCommonCode("OPER_EVT_TYPE","NUM_VAL4",eventCode+"");
					eventCd = (String) eventCodeMap.get("DL_CD");
					eventCdName = (String) eventCodeMap.get("DL_CD_NM");
					
					busEventMap.put("EVT_TYPE", eventCd);
					busEventMap.put("VHC_ID", getBusId(busEventMap));
					if (CommonUtil.empty(busEventMap.get("VHC_ID")))break;
					Map<String, Object> routMap2 = getRoutMst(busEventMap);
					if(routMap2!=null) {
						busEventMap.put("REP_ROUT_ID", routMap2.get("REP_ROUT_ID"));
						busEventMap.put("WAY_DIV", routMap2.get("WAY_DIV"));
						busEventMap.put("ROUT_NM", routMap2.get("ROUT_NM"));
						busEventMap.put("ST_STTN_ID", routMap2.get("ST_STTN_ID"));
						busEventMap.put("ED_STTN_ID", routMap2.get("ED_STTN_ID"));
					}
					
					//busEventMap.put("OPER_STS", getOperSts(busEventMap));
					Map<String, Object> operSts = getCommonCode("OPER_STS","NUM_VAL4",busEventMap.get("RUN_TYPE")+"");
					if(operSts!=null) {
						busEventMap.put("OPER_STS", operSts.get("DL_CD"));
					}
					
					if (CommonUtil.empty(busEventMap.get("OPER_DT"))) {
						busEventMap.put("OPER_DT", CommonUtil.getOperDt());
					}
	
					try {
						
						Map<String, Object> curAllocPlInfo = curInfoMapper.selectCurAllocPlInfoByOperVhcId(busEventMap);
						String allocOperVhcId = null;
		                String allocVhcId = null;
		                if(curAllocPlInfo!=null) {
		                    allocOperVhcId = (String) curAllocPlInfo.get("OPER_VHC_ID"); //실제 운행한 차량 ID
		                    allocVhcId = (String) curAllocPlInfo.get("VHC_ID"); //계획된 차량 ID
		                    
		                    if(CommonUtil.empty(busEventMap.get("REP_ROUT_ID"))&&curAllocPlInfo.get("REP_ROUT_ID")!=null) {
								busEventMap.put("REP_ROUT_ID", curAllocPlInfo.get("REP_ROUT_ID"));
							}
							else {
								busEventMap.put("REP_ROUT_ID", "RR00000002"); //임시로
							}
		                    busEventMap.put("ALLOC_NO", curAllocPlInfo.get("ALLOC_NO"));
		                    busEventMap.put("OPER_SN", curAllocPlInfo.get("OPER_SN"));
		                }
						
		                //운행된 차량 ID 정보가 없는 경우 계획된 차량 ID로 배차 정보를 찾음
						if (curAllocPlInfo == null || CommonUtil.empty(allocOperVhcId) 
								/*||(CommonUtil.empty(allocVhcId)==false)&&(allocVhcId.equals(allocOperVhcId)==false)*/
							) {
							curAllocPlInfo = curInfoMapper.selectCurAllocPlInfoByVhcId(busEventMap);
				            if(curAllocPlInfo==null){
				            	busEventMap.put("REP_ROUT_ID", "RR00000002"); //임시로
				            }
				            else{
				                busEventMap.put("ALLOC_NO", curAllocPlInfo.get("ALLOC_NO"));
				                busEventMap.put("OPER_SN", curAllocPlInfo.get("OPER_SN"));
				                
				                busEventMap.put("OPER_VHC_ID", busEventMap.get("BUS_ID"));
								if(CommonUtil.empty(busEventMap.get("REP_ROUT_ID"))&&curAllocPlInfo.get("REP_ROUT_ID")!=null) {
									busEventMap.put("REP_ROUT_ID", curAllocPlInfo.get("REP_ROUT_ID"));
								}
				                curInfoMapper.updateOperVhcIdCurAllocPlInfo(busEventMap);
				            }
						}
				
						
						if(curAllocPlInfo==null) {
							
							if (eventCode == (byte) 0x03 || eventCode == (byte) 0x04
									||(eventCode==(byte)0x01||eventCode==(byte)0x02) && routMap2.get("ED_STTN_ID").equals(eventData)) //기점, 종점을 잡지 못했을때
							{
								
								String curNearStr = curInfoMapper.getCurNearAllocPlInfo(busEventMap);
								String curNearArr[] = curNearStr.split(",");
								busEventMap.put("ROUT_ID", curNearArr[0]);
								busEventMap.put("COR_ID", curNearArr[1]);
								busEventMap.put("PRDT_ALLOC_NO", curNearArr[2]); //예측한 배차 번호
								busEventMap.put("OPER_SN", curNearArr[3]);					
								int minAllocNo = curInfoMapper.minAllocNoCurAllocPlInfo(busEventMap);
								if(minAllocNo>0)minAllocNo=0;
								else minAllocNo -= 1;
								busEventMap.put("ALLOC_NO", minAllocNo);
								busEventMap.put("OPER_VHC_ID", busEventMap.get("VHC_ID"));
								insertCurAllocPlInfo(busEventMap); //배차가 없는 경우 0보다 작은수로 배차함
							}
							else {
								int minAllocNo = curInfoMapper.minAllocNoCurAllocPlInfo(busEventMap);
								if(minAllocNo>0)minAllocNo=0;
								else minAllocNo -= 1;
							}
						} else {
							
							if (eventCode == (byte) 0x03 || eventCode == (byte) 0x04
									||(eventCode==(byte)0x01||eventCode==(byte)0x02) && routMap2.get("ED_STTN_ID").equals(eventData) //기점, 종점을 잡지 못했을때
									|| checkRoutChangeBusOperEvent(busEvent)) {
								// 현재운행정보도 업데이트
								String curNearStr = "";
								
								if(CommonUtil.empty(curNearStr)) {
									curNearStr = curInfoMapper.getCurNearAllocPlInfo2(busEventMap);
								}
								
								if(CommonUtil.notEmpty(curNearStr)) {
									String curNearArr[] = curNearStr.split(",");
									
		                        	AtSbrtRouteInfo sbrtRouteInfo = new AtSbrtRouteInfo();
		                            sbrtRouteInfo.setUpdateTm(new AtTimeStamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(PlatformConfig.PLF_DT_FORMAT))));
		                            sbrtRouteInfo.setRouteId(curNearArr[0]);
		                            sbrtRouteInfo.setCourseId(curNearArr[1]);
		                            sbrtRouteInfo.setSelectMode((byte) 0x01);
		                            sbrtRouteInfo.setRunType((byte)0x01);
	
		                            TimsMessageBuilder builder = new TimsMessageBuilder(TService.getInstance().getTimsConfig());
		                            TimsMessage setRequest = builder.setRequest(sbrtRouteInfo);
	
		                            //노선,코스 정보를 차량에 전달
		                            kafkaProducer.sendKafka(setRequest, sessionId);
		                            
									busEventMap.put("ROUT_ID", curNearArr[0]);
									busEventMap.put("COR_ID", curNearArr[1]);
									busEventMap.put("OPER_SN", curNearArr[2]);
									curInfoMapper.updateOperVhcIdCurAllocPlInfo(busEventMap);
									
								}
								//busEventMap.put("ALLOC_NO", curNearArr[2]);
							} else {
							}
						}						
						
						if (CommonUtil.empty(busEventMap.get("ALLOC_NO")) == false
								&& CommonUtil.empty(busEventMap.get("REP_ROUT_ID")) == false) 
						{
							logger.debug("[" + busEventMap.get("ALLOC_NO") + "," + busEventMap.get("REP_ROUT_ID") + ","
									+ busEventMap.get("WAY_DIV") + "] In BusOperEvent alloc no");	
							
							if (eventCode == 0x01 || eventCode == 0x02 // 정류장 출/도착 인 경우
									|| eventCode == 0x03 || eventCode == 0x04 // 기점 출/도착 인 경우
									|| eventCode == 0x05 || eventCode == 0x06) // 종점점 출/도착 인 경우
							{
								Map<String, Object> sttnEventMap = new HashMap<String, Object>(busEventMap);
			
								sttnEventMap.put("NODE_ID", busEvent.getEventData()); // 정류장 출/도착인 경우 EVENT_DATA를 사용
			
								setOperEventData(sttnEventMap);
			
								busEventMap.put("NODE_NM", sttnEventMap.get("NODE_NM")); // 출/도착 정류소명
								busEventMap.put("NODE_TYPE", sttnEventMap.get("NODE_TYPE"));
								busEventMap.put("PREV_NODE_NM", sttnEventMap.get("PREV_NODE_NM")); // 이전 정류소/교차로
								busEventMap.put("NEXT_NODE_ID", sttnEventMap.get("NEXT_NODE_ID")); // 다음 정류소/교차로
								busEventMap.put("NEXT_NODE_NM", sttnEventMap.get("NEXT_NODE_NM"));
								busEventMap.put("NEXT_NODE_TYPE", sttnEventMap.get("NEXT_NODE_TYPE"));
								
								busEventMap.put("CUR_NODE_TYPE", sttnEventMap.get("CUR_NODE_TYPE")); // 다음 정류소/교차로
								busEventMap.put("CUR_NODE_ID", sttnEventMap.get("CUR_NODE_ID"));
								busEventMap.put("CUR_NODE_NM", sttnEventMap.get("CUR_NODE_NM"));
								busEventMap.put("CUR_NODE_SN", sttnEventMap.get("CUR_NODE_SN"));
			
							}
							else {
								setOperEventData(busEventMap);
							}
							insertCurOperInfo(busEventMap);
						}
						
					} catch (Exception e) {
						logger.error("Exception {}", e);
					} 
	
					try {
						// 이력 insert
						historyMapper.insertEventHistory(busEventMap);
					} catch (Exception e) {
						logger.error("Exception {}", e);
					}
	
					switch (eventCode) {
					/** 운행 이벤트 **/
					case 0x01: // 정류장 도착
					case 0x02: // 정류장 출발
					case 0x03: // 기점 도착
					case 0x04: // 기점 출발
					case 0x05: // 종점 도착
					case 0x06: // 종점 출발
					case 0x07: // 노드 통과
						// 통플에서 정류장통과시에도 노드 통과 이벤트를 준다?
						// brtMapper.insertLinkSpeed(busEventMap);
					case 0x08: // 음성 출력
					case 0x09: // 차고지 도착
					case 0x0a: // 차고지 출발
	
						/** 특정 이벤트 **/
					case 0x11: // 문 열림
					case 0x12: // 문 닫힘
						
						/*paramMap = new HashMap<>();
	
						paramMap.put("COL", "DL_CD");
						paramMap.put("CO_CD", "OPER_EVT_TYPE");
						paramMap.put("COL3", "NUM_VAL4");
						paramMap.put("COL_VAL3", (int) eventCode);
						eventCd = commonMapper.selectDlCdCol(paramMap);
	
						paramMap.put("COL", "DL_CD_NM");
						paramMap.put("CO_CD", "OPER_EVT_TYPE");
						paramMap.put("COL3", "NUM_VAL4");
						paramMap.put("COL_VAL3", (int) eventCode);
						eventCdName = commonMapper.selectDlCdCol(paramMap);*/
	
						// eventDesc = timsMapper.selectNodeInfo(paramMap);
	
						break;
	
					/** 운행위반 이벤트 **/
					case 0x21: // 무정차 주행
					case 0x22: // 과속 주행
					case 0x23: // 급가속
					case 0x24: // 급감속
					case 0x25: // 급출발
					case 0x26: // 급정지
					case 0x27: // 개문주행
					case 0x28: // 노선이탈
						logger.info("운행위반 발생!! [IMP ID : " + busEvent.getImpId() + "]");
	
						try {
							/*paramMap = new HashMap<>();
							paramMap.put("COL", "DL_CD");
							paramMap.put("CO_CD", "OPER_EVT_TYPE");
							paramMap.put("COL3", "NUM_VAL4");
							paramMap.put("COL_VAL3", (int) eventCode);
							eventCd = commonMapper.selectDlCdCol(paramMap);
	
							paramMap.put("COL", "DL_CD_NM");
							paramMap.put("CO_CD", "OPER_EVT_TYPE");
							paramMap.put("COL3", "NUM_VAL4");
							paramMap.put("COL_VAL3", (int) eventCode);
							eventCdName = commonMapper.selectDlCdCol(paramMap);*/
							Map<String, Object> violtMap = getCommonCode("VIOLT_TYPE","NUM_VAL4",eventCode+"");
							if(violtMap!=null) {
								busEventMap.put("VIOLT_TYPE", (String) violtMap.get("DL_CD"));
							}
							
							historyMapper.insertOperVioltHistory(busEventMap); // 운행위반이력 insert
						} catch (Exception e) {
							logger.error("Exception {}", e);
						}
	
						break;
	
					/** 돌발 **/ // 2021.10.26일자 적용
					case 0x31: // 사고
					case 0x32: // 낙하
					case 0x33: // 고장
					case 0x34: // 기타
						// case 0x35: //테러
						logger.info("돌발 발생!! [IMP ID : " + busEvent.getImpId() + "]");
	
						try {
							/*paramMap = new HashMap<>();
							paramMap.put("COL", "DL_CD");
							paramMap.put("CO_CD", "INCDNT_TYPE");
							paramMap.put("COL3", "NUM_VAL4");
							paramMap.put("COL_VAL3", (int) eventCode);
							eventCd = commonMapper.selectDlCdCol(paramMap);
	
							paramMap.put("COL", "DL_CD_NM");
							paramMap.put("CO_CD", "INCDNT_TYPE");
							paramMap.put("COL3", "NUM_VAL4");
							paramMap.put("COL_VAL3", (int) eventCode);
							eventCdName = commonMapper.selectDlCdCol(paramMap);*/
							Map<String, Object> incdntMap = getCommonCode("INCDNT_TYPE","NUM_VAL4",eventCode+"");
							if(incdntMap!=null) {
								busEventMap.put("INCDNT_TYPE", (String) incdntMap.get("DL_CD"));
							}
	
							curInfoMapper.insertIncidentInfo(busEventMap); // 돌발정보 insert
						} catch (Exception e) {
							logger.error("Exception {}", e);
						}
	
						break;
	
					}
	
					// 모니터링용 웹소켓 데이터
					paramMap = new HashMap<>();
					paramMap.put("MNG_ID", sessionId);
	
					vhcInfo = timsMapper.selectVhcInfo(paramMap);
					// Map<String, Object> dataMap =busInfo.toMap();
	
					wsDataMap = new HashMap<>();
					wsDataMap.put("ATTR_ID", attrId);
					wsDataMap.put("VHC_NO", busEvent.getBusNo());
					wsDataMap.put("ROUT_ID", busEvent.getRouteId());
					wsDataMap.put("ROUT_NM", busEventMap.get("ROUT_NM"));
					wsDataMap.put("VHC_ID", vhcInfo.get("VHC_ID"));
					wsDataMap.put("DVC_ID", vhcInfo.get("DVC_ID"));
					wsDataMap.put("GPS_X", busEventMap.get("LONGITUDE"));
					wsDataMap.put("GPS_Y", busEventMap.get("LATITUDE"));
					wsDataMap.put("OPER_STS", busEventMap.get("OPER_STS"));
					wsDataMap.put("LINK_ID", busEventMap.get("LINK_ID"));
	
					/*if (eventCode == 0x01 || eventCode == 0x02 // 정류장 출/도착 인 경우
							|| eventCode == 0x03 || eventCode == 0x04 // 기점 출/도착 인 경우
							|| eventCode == 0x05 || eventCode == 0x06) // 종점점 출/도착 인 경우
					{
						Map<String, Object> sttnEventMap = new HashMap<String, Object>(busEventMap);
	
						sttnEventMap.put("NODE_ID", busEvent.getEventData()); // 정류장 출/도착인 경우 EVENT_DATA를 사용
	
						setOperEventData(sttnEventMap);
	
						wsDataMap.put("NODE_NM", sttnEventMap.get("NODE_NM")); // 출/도착 정류소명
						wsDataMap.put("NODE_TYPE", sttnEventMap.get("NODE_TYPE"));
						wsDataMap.put("PREV_NODE_NM", sttnEventMap.get("PREV_NODE_NM")); // 이전 정류소/교차로
						wsDataMap.put("NEXT_NODE_ID", sttnEventMap.get("NEXT_NODE_ID")); // 다음 정류소/교차로
						wsDataMap.put("NEXT_NODE_NM", sttnEventMap.get("NEXT_NODE_NM"));
						wsDataMap.put("NEXT_NODE_TYPE", sttnEventMap.get("NEXT_NODE_TYPE"));
	
					} else*/ 
					
					{
						wsDataMap.put("NODE_NM", busEventMap.get("NODE_NM")); // 지나온 노드명
						wsDataMap.put("NODE_TYPE", busEventMap.get("NODE_TYPE")); // 지나온 노드 타입
						wsDataMap.put("PREV_NODE_NM", busEventMap.get("PREV_NODE_NM")); // 이전 정류소/교차로
						wsDataMap.put("NEXT_NODE_ID", busEventMap.get("NEXT_NODE_ID")); // 다음 정류소/교차로
						wsDataMap.put("NEXT_NODE_NM", busEventMap.get("NEXT_NODE_NM"));
						wsDataMap.put("NEXT_NODE_TYPE", busEventMap.get("NEXT_NODE_TYPE"));
						
						wsDataMap.put("CUR_NODE_TYPE", busEventMap.get("CUR_NODE_TYPE")); // 다음 정류소/교차로
						wsDataMap.put("CUR_NODE_ID", busEventMap.get("CUR_NODE_ID"));
						wsDataMap.put("CUR_NODE_NM", busEventMap.get("CUR_NODE_NM"));
						wsDataMap.put("CUR_NODE_SN", busEventMap.get("CUR_NODE_SN"));
					}
	
					wsDataMap.put("EVT_CODE", eventCd);
					wsDataMap.put("EVT_TYPE", eventCdName);
					wsDataMap.put("CUR_SPD", busEventMap.get("SPEED"));
					wsDataMap.put("EVT_DATA", busEventMap.get("EVENT_DATA"));
					wsDataMap.put("NODE_SN", busEventMap.get("NODE_SN"));
				}
				catch (Exception e) {
					logger.error("Exception {}", e);
				}
				break;

			case BrtAtCode.DISPATCH:

				AtDispatch dispatch = (AtDispatch) atMessage.getAttrData();
				Map<String, Object> curInfo = null;
				String routId = "";
				String routNm = "";
				String vhcId = "";
				String vhcNo = "";
				String dpDiv = "";
				String dpLv = "";
				String drvId = "";

				logger.info("디스패치 수신. {}", dispatch);

				try {
					String udpDtm = dispatch.getUpdateTm().toString();
					int msgType = (int) dispatch.getMessageType();
					int msgLv = (int) dispatch.getMessageLevel();

					// 차량정보 가져오기
					paramMap = new HashMap<>();
					paramMap.put("MNG_ID", sessionId);
					paramMap.put("IMP_ID", sessionId);

					vhcInfo = timsMapper.selectVhcInfo(paramMap);
					
					vhcId = String.valueOf(vhcInfo.get("VHC_ID"));
					vhcNo = String.valueOf(vhcInfo.get("VHC_NO"));

					// 디스패치 이력 생성
					// 버스의 현재 정보 가져오기 //BRT_CUR_OPER_INFO
					paramMap.put("UPD_DTM", udpDtm);
					paramMap.put("VHC_ID", vhcId);
					paramMap.put("VHC_NO", vhcNo);

					// 운행일 생성. 시간에 따라 0시(24시) ~ 02시까지는 이전 날짜로 운행일 설정
					String operDt = OperDtUtil.convertTimeToOperDt(udpDtm, "yyyy-MM-dd HH:mm:ss");
					paramMap.put("OPER_DT", operDt);

					curInfo = getBusOperInfo(paramMap);
					//curInfo = curInfoMapper.selectCurOperInfo(paramMap);

					// if(curInfo != null) {

					routId = String.valueOf(curInfo.get("ROUT_ID"));
					routNm = String.valueOf(curInfo.get("ROUT_NM"));
					drvId = String.valueOf(curInfo.get("DRV_ID"));

					// 디스패치 이력 넣기
					// 디스패치 구분코드 가져오기
					paramMap = new HashMap<>();
					/*paramMap.put("CO_CD", "DISPATCH_DIV");
					paramMap.put("COL", "DL_CD");
					paramMap.put("COL3", "TXT_VAL1");
					paramMap.put("COL_VAL3", msgType);
					dpDiv = commonMapper.selectDlCdCol(paramMap);*/
					Map<String, Object> divMap = getCommonCode("DISPATCH_DIV","TXT_VAL1",msgType+"");
					if(divMap!=null) {
						dpDiv = (String) divMap.get("DL_CD");
					}
					/*paramMap.put("CO_CD", "DISPATCH_KIND");
					paramMap.put("COL", "DL_CD");
					paramMap.put("COL3", "TXT_VAL1");
					paramMap.put("COL_VAL3", msgLv);
					dpLv = commonMapper.selectDlCdCol(paramMap);*/
					Map<String, Object> kindMap = getCommonCode("DISPATCH_KIND","TXT_VAL1",msgLv+"");
					if(kindMap!=null) {
						dpLv = (String) kindMap.get("DL_CD");
					}
					
					HashMap<String, Object> dispatchLog = new HashMap<String, Object>(curInfo);
					dispatchLog.put("OPER_DT", operDt);
					dispatchLog.put("SEND_DATE", udpDtm);
					dispatchLog.put("DSPTCH_DIV", dpDiv);
					dispatchLog.put("DSPTCH_KIND", dpLv);
					dispatchLog.put("DRV_ID", drvId);
					dispatchLog.put("DSPTCH_CONTS", dispatch.getMessage());
					dispatchLog.put("GPS_X", curInfo.get("LONGITUDE"));
					dispatchLog.put("GPS_Y", curInfo.get("LATITUDE"));
					dispatchLog.put("REP_ROUT_NM", curInfo.get("REP_ROUT_NM"));
					dispatchLog.put("ROUT_NM", curInfo.get("ROUT_NM"));

					historyMapper.insertDispatchHistory(dispatchLog);

					// } else {
					// logger.info("디스패치 무시됨(현재 운행중인 차량정보 없음) : udpDtm:{}, vhcId:{}", udpDtm,
					// vhcId);
					// }

					if (curInfo != null) {
						// 웹소켓용 데이터 생성
	
						// 디스패치 메시지 넣기
						wsDataMap = new HashMap<>();
	
						wsDataMap.put("ATTR_ID", attrId);
						wsDataMap.put("VHC_ID", vhcId);
						wsDataMap.put("VHC_NO", vhcNo);
						wsDataMap.put("ROUT_ID", routId);
						wsDataMap.put("ROUT_NM", routNm);
						wsDataMap.put("DSPTCH_DIV", dpDiv);
						
						wsDataMap.put("DSPTCH_KIND", dpLv);
						wsDataMap.put("GPS_X", curInfo.get("GPS_X"));
						wsDataMap.put("GPS_Y", curInfo.get("GPS_X"));
						wsDataMap.put("MESSAGE", dispatch.getMessage());
					}
					//logger.info("디스패치 전송 {}", wsDataMap);
				} catch (DuplicateKeyException e) {
					logger.error("DuplicateKeyException {}", e);
				} catch (Exception e) {
					logger.error("Exception {}", e);
				}

				break;

			default:
				break;
			}
		}

		return wsDataMap;
	}

	private void setOperEventData(Map<String, Object> operEventMap) {

		try {

			// 운행일 생성. 시간에 따라 0시(24시) ~ 02시까지는 이전 날짜로 운행일 설정
			operEventMap.put("OPER_DT", OperDtUtil.convertTimeToOperDt(operEventMap.get("UPD_DTM").toString(), "yyyy-MM-dd HH:mm:ss"));

			// 다음노드(교차로 or 정류소)
			Map<String, Object> realNodeInfo = timsMapper.selectNodeByLinkSn(operEventMap); // 통플에서 넘어온 노드순번(실제로는 링크순번)
																							// 으로 실제 노드순번 구하기
			if (realNodeInfo != null) {
				operEventMap.put("ROUT_NM", realNodeInfo.get("ROUT_NM"));
				operEventMap.put("NODE_TYPE", realNodeInfo.get("NODE_TYPE"));
				operEventMap.put("NODE_NM", realNodeInfo.get("NODE_NM"));
				operEventMap.put("NODE_SN", realNodeInfo.get("NODE_SN"));
			}
			
			Map<String, Object> curSttnInfo = timsMapper.selectCurSttnInfo(operEventMap); 
			
			if (realNodeInfo != null) {
				operEventMap.put("CUR_NODE_TYPE", curSttnInfo.get("CUR_NODE_TYPE"));
				operEventMap.put("CUR_NODE_ID", curSttnInfo.get("CUR_NODE_ID"));
				operEventMap.put("CUR_NODE_NM", curSttnInfo.get("CUR_NODE_NM"));
				operEventMap.put("CUR_NODE_SN", curSttnInfo.get("CUR_NODE_SN"));
			}
			
			//Map<String, Object> nextNodeInfo = timsMapper.selectNextSttnCrsInfo(operEventMap);
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("OPER_DT", operEventMap.get("OPER_DT"));
			param.put("BUS_NO", operEventMap.get("BUS_NO"));
			param.put("ROUT_ID", operEventMap.get("ROUT_ID"));
			param.put("NODE_SN", operEventMap.get("CUR_NODE_SN"));
			
			Map<String, Object> nextSttnInfo = timsMapper.selectNextSttnInfo(param);
			if (nextSttnInfo != null) {
				operEventMap.put("PREV_NODE_NM", nextSttnInfo.get("PREV_NODE_NM"));
				operEventMap.put("NEXT_NODE_ID", nextSttnInfo.get("NEXT_NODE_ID"));
				operEventMap.put("NEXT_NODE_NM", nextSttnInfo.get("NEXT_NODE_NM"));
				operEventMap.put("NEXT_NODE_TYPE", nextSttnInfo.get("NEXT_NODE_TYPE"));
			}


		} catch (Exception e) {
			logger.error("Exception {}", e);
		}

	}

	private int insertCurOperInfo(Map<String, Object> curOperInfo) {

		/*
		 * try { //운행일 생성. 시간에 따라 0시(24시) ~ 02시까지는 이전 날짜로 운행일 설정
		 * curOperInfo.put("OPER_DT",
		 * OperDtUtil.convertTimeToOperDt(curOperInfo.get("UPD_DTM").toString(),
		 * "yyyy-MM-dd HH:mm:ss"));
		 * 
		 * 
		 * //다음노드(교차로 or 정류소) Map<String, Object> realNodeInfo =
		 * timsMapper.selectNodeByLinkSn(curOperInfo); //통플에서 넘어온 노드순번(실제로는 링크순번) 으로 실제
		 * 노드순번 구하기 if(realNodeInfo != null) { curOperInfo.put("ROUT_NM",
		 * realNodeInfo.get("ROUT_NM")); curOperInfo.put("NODE_TYPE",
		 * realNodeInfo.get("NODE_TYPE")); curOperInfo.put("NODE_NM",
		 * realNodeInfo.get("NODE_NM")); curOperInfo.put("NODE_SN",
		 * realNodeInfo.get("NODE_SN")); }
		 * 
		 * Map<String, Object> nextNodeInfo =
		 * timsMapper.selectNextSttnCrsInfo(curOperInfo); if(nextNodeInfo != null) {
		 * curOperInfo.put("PREV_NODE_NM", nextNodeInfo.get("PREV_NODE_NM"));
		 * curOperInfo.put("NEXT_NODE_ID", nextNodeInfo.get("NEXT_NODE_ID"));
		 * curOperInfo.put("NEXT_NODE_NM", nextNodeInfo.get("NEXT_NODE_NM"));
		 * curOperInfo.put("NEXT_NODE_TYPE", nextNodeInfo.get("NEXT_NODE_TYPE")); } }
		 * catch (Exception e) {
		 * 
		 * }
		 */

		// 한국일때만 좌표값 튀는거 insert/update 방지
		if ((float) (curOperInfo.get("LONGITUDE")) < 120 || (float) (curOperInfo.get("LONGITUDE")) > 130) {
			
			return 0;
		}

		if ((float) (curOperInfo.get("LATITUDE")) < 30 || (float) (curOperInfo.get("LATITUDE")) > 40) {
			return 0;
		}
		//setOperEventData(curOperInfo);
		try {
			if(checkChangeBusOperInfo(curOperInfo)==true) {		
				return curInfoMapper.insertCurOperInfo(curOperInfo);
			}
		} catch (Exception e) {
			logger.error("Exception {}", e);
		}
		return 0;
	}

}
