package kr.tracom.tims.handler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import kr.tracom.beans.BeanUtil;
import kr.tracom.cm.domain.Common.CommonMapper;
import kr.tracom.cm.domain.Rout.RoutMapper;
import kr.tracom.platform.attribute.BrtAtCode;
import kr.tracom.platform.attribute.bis.AtSbrtRouteInfo;
import kr.tracom.platform.attribute.brt.AtBusArrivalInfo;
import kr.tracom.platform.attribute.brt.AtBusArrivalInfoItem;
import kr.tracom.platform.attribute.brt.AtBusInfo;
import kr.tracom.platform.attribute.brt.AtBusOperEvent;
import kr.tracom.platform.attribute.brt.AtDispatch;
import kr.tracom.platform.attribute.brt.AtTrafficLightStatusResponse;
import kr.tracom.platform.attribute.brt.AtTrafficModule2;
import kr.tracom.platform.attribute.brt.AtTrafficModule3;
import kr.tracom.platform.attribute.common.AtTimeStamp;
import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.TimsMessageBuilder;
import kr.tracom.platform.net.protocol.TimsPayload;
import kr.tracom.platform.net.protocol.attribute.AtMessage;
import kr.tracom.platform.net.protocol.payload.PlCode;
import kr.tracom.platform.net.protocol.payload.PlEventRequest;
import kr.tracom.platform.net.protocol.payload.PlGetResponse;
import kr.tracom.platform.service.TService;
import kr.tracom.platform.service.config.PlatformConfig;
import kr.tracom.platform.service.kafka.model.KafkaMessage;
import kr.tracom.tims.OperDtUtil;
import kr.tracom.tims.domain.CurInfoMapper;
import kr.tracom.tims.domain.HistoryMapper;
import kr.tracom.tims.domain.TimsMapper;
import kr.tracom.util.CommonUtil;
import kr.tracom.util.Constants;
import kr.tracom.util.DataInterface;
import kr.tracom.util.domain.LocationVO;
import kr.tracom.ws.WsClient;

public class MorEventThread extends Thread {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private ConcurrentLinkedQueue<KafkaMessage> kafkaQ = new ConcurrentLinkedQueue<>();
	private String sessionId;

	private boolean bRunning = true;

	// @Autowired
	TimsMapper timsMapper;

	// @Autowired
	// HistoryMapper historyMapper;

	// @Autowired
	CurInfoMapper curInfoMapper;

	RoutMapper routMapper;

	// @Autowired
	// CommonMapper commonMapper;

	// @Autowired
	WsClient webSocketClient;

	// @Autowired
	// KafkaProducer kafkaProducer;

	private static Map<String, Object> g_busOperInfoMap = new HashMap<>();

	private static Map<String, Object> g_busOperEventMap = new HashMap<>();

	private static Map<String, Object> g_busIdMap = new HashMap<>();

	private static Map<String, Object> g_vhcDrInfoMap = new HashMap<>();

	private static Map<String, Object> g_vhcInfoMap = new HashMap<>();

	private static Map<String, Object> g_routMap = new HashMap<>();

	private static Map<String, Object> g_operStsMap = new HashMap<>();

	private static Map<String, Object> g_operEventCodeMap = new HashMap<>();

	private static Map<String, Object> g_operVhcSttnInfoMap = new HashMap<>();

	private static Map<String, Object> g_operCorInfoMap = new HashMap<>();

	private static Map<String, Object> g_routNodeMap = new HashMap<>();

	@PostConstruct
	public void initialize() {
		try {
			initNodeList();
		} catch (Exception e) {
			logger.error("select IntgNodeList Error ", e);
		}

		try {

		} catch (Exception e) {

			logger.error("  ", e);
		}
	}

	private void initNodeList() {
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("TYPE", "REP_ROUT_ID");
		param.put("CONTENT", "RR00000002");

		List<Map<String, Object>> routList = routMapper.selectRoutList(param);
		if (g_routNodeMap == null) {
			g_routNodeMap = new HashMap<>();
		}
		// 전체 노드 리스트를 가져옵니다.
		for (Map<String, Object> rout : routList) {
			List<Map<String, Object>> nodeList = new ArrayList<>();
			nodeList = curInfoMapper.selectIntgNodeList((String) rout.get("ROUT_ID"));

			g_routNodeMap.put((String) rout.get("ROUT_ID"), nodeList);
		}
	}

	private List<Map<String, Object>> getNodeList(Map<String, Object> eventInfo) {
		String routId = (String) eventInfo.get("ROUT_ID");
		if (CommonUtil.empty(routId))
			return null;

		if (g_routNodeMap == null) {
			initNodeList();
		}

		if ((List<Map<String, Object>>) g_routNodeMap.get(routId) == null) {
			List<Map<String, Object>> nodeList = new ArrayList<>();
			nodeList = curInfoMapper.selectIntgNodeList(routId);
			g_routNodeMap.put(routId, nodeList);
		}

		return (List<Map<String, Object>>) g_routNodeMap.get(routId);
	}

	private Map<String, Object> searchNode(String nodeId) {
		if (CommonUtil.empty(nodeId))
			return null;
		for (String key : g_routNodeMap.keySet()) {
			List<Map<String, Object>> nodeList = (List<Map<String, Object>>) g_routNodeMap.get(key);
			for (Map<String, Object> node : nodeList) {
				if (nodeId.equals(node.get("NODE_ID"))) {
					return node;
				}
			}
		}
		return null;
	}
	
	private void setVhcDrInfo(String vhcId, Map<String, Object> eventInfo, Map<String, Object> nodeInfo) {
		Map<String, Object> vhcDrInfo = CommonUtil.deepCopy(nodeInfo);
		vhcDrInfo.put("UPD_DTM", eventInfo.get("UPD_DTM"));
		vhcDrInfo.put("UPD_DTM2", eventInfo.get("UPD_DTM2"));
		vhcDrInfo.put("LONGITUDE", eventInfo.get("LONGITUDE"));
		vhcDrInfo.put("LATITUDE", eventInfo.get("LATITUDE"));
		vhcDrInfo.put("ORG_NODE_ID", eventInfo.get("ORG_NODE_ID"));
		vhcDrInfo.put("NODE_ID", eventInfo.get("NODE_ID"));
		vhcDrInfo.put("IS_STATION", eventInfo.get("IS_STATION"));
		g_vhcDrInfoMap.put(vhcId, vhcDrInfo);
	}
	
	private int getVhcDrInfo(Map<String, Object> eventInfo, Map<String, Object> nodeInfo) {
		String vhcId = (String) eventInfo.get("VHC_ID");
		if (CommonUtil.empty(vhcId))
			return -1;
		try {
			Map<String, Object> oldVhcDrInfo = (Map<String, Object>) g_vhcDrInfoMap.get(vhcId);

			byte eventCd = 0;
			String eventData = null;
			String node_id = null;
			if (eventInfo.getOrDefault("EVENT_CD", 0x0) != null) {
				eventCd = Byte.parseByte(eventInfo.getOrDefault("EVENT_CD", 0x0).toString());
				eventData = eventInfo.getOrDefault("EVT_DATA", "").toString();
				node_id = eventInfo.getOrDefault("NODE_ID", "").toString();
			}
			if (eventCd == 0x01 || eventCd == 0x11) { // 정류장 도착 //도어 열림
				String cur_id = "";
				if (eventCd == 0x11) { //도어 닫힘
					cur_id = node_id;
				}
				else {
					cur_id = eventData;
				}
				
				if (cur_id.equals(nodeInfo.get("CUR_STTN_ID"))) {
					eventInfo.put("LONGITUDE", nodeInfo.get("STTN_ST_GPS_X"));
					eventInfo.put("LATITUDE", nodeInfo.get("STTN_ST_GPS_Y"));
				} 
				else if (cur_id.equals(nodeInfo.get("NEXT_STTN_ID"))) {
					eventInfo.put("LONGITUDE", nodeInfo.get("STTN_ED_GPS_X"));
					eventInfo.put("LATITUDE", nodeInfo.get("STTN_ED_GPS_Y"));
					eventInfo.put("ORG_NODE_ID", nodeInfo.get("NODE_ID"));
					eventInfo.put("NODE_ID", nodeInfo.get("NEXT_STTN_ID"));
				} else {
					LocationVO location = DataInterface.getPointToLine(
							CommonUtil.floatToDouble((float)eventInfo.get("LONGITUDE_RAW")),
							CommonUtil.floatToDouble((float)eventInfo.get("LATITUDE_RAW")),
							CommonUtil.stringToDouble(nodeInfo.get("ST_GPS_X")+""),
							CommonUtil.stringToDouble(nodeInfo.get("ST_GPS_Y")+""),
							CommonUtil.stringToDouble(nodeInfo.get("ED_GPS_X")+""),
							CommonUtil.stringToDouble(nodeInfo.get("ED_GPS_Y")+""));
					if(location!=null) {
						if (location.getX() > 0x0) {
							eventInfo.put("LONGITUDE", location.getX());
						}
						if (location.getX() > 0x0) {
							eventInfo.put("LATITUDE", location.getY());
						}
					}
				}
				
				eventInfo.put("IS_STATION", "TRUE");
				logger.debug("NODE_ID=" + nodeInfo.get("NODE_ID") + ",BEARING=" + nodeInfo.get("BEARING"));
				logger.debug("eventInfo=" + eventInfo+",nodeInfo=" + nodeInfo);
			} else {
				LocationVO location = DataInterface.getPointToLine(
						CommonUtil.floatToDouble((float)eventInfo.get("LONGITUDE_RAW")),
						CommonUtil.floatToDouble((float)eventInfo.get("LATITUDE_RAW")),
						CommonUtil.stringToDouble(nodeInfo.get("ST_GPS_X")+""),
						CommonUtil.stringToDouble(nodeInfo.get("ST_GPS_Y")+""),
						CommonUtil.stringToDouble(nodeInfo.get("ED_GPS_X")+""),
						CommonUtil.stringToDouble(nodeInfo.get("ED_GPS_Y")+""));
				logger.debug("rawx={}, rawy={}, nodeInfo={}, stx={}, sty={}, edx={}, ed y={}"
						,CommonUtil.floatToDouble((float)eventInfo.get("LONGITUDE_RAW")),CommonUtil.floatToDouble((float)eventInfo.get("LATITUDE_RAW")),nodeInfo
						,CommonUtil.stringToDouble(nodeInfo.get("ST_GPS_X")+""),CommonUtil.stringToDouble(nodeInfo.get("ST_GPS_Y")+"")
						,CommonUtil.stringToDouble(nodeInfo.get("ED_GPS_X")+""),CommonUtil.stringToDouble(nodeInfo.get("ED_GPS_X")+""));
				if(location!=null) {
					if (location.getX() > 0x0) {
						eventInfo.put("LONGITUDE", location.getX());
					}
					if (location.getY() > 0x0) {
						eventInfo.put("LATITUDE", location.getY());
					}
				}
				else {
					setVhcDrInfo(vhcId, eventInfo, nodeInfo);
					return -1;
				}

				if (eventCd == 0x02 || eventCd == 0x12) { //정류소 출발, 문 닫힘일 경우
					eventInfo.put("IS_STATION", "FALSE");
				}
				if (oldVhcDrInfo != null) {
					Map<String, Object> codeMap =  getCommonCode("SYS_INFO", null, "SY019");
					short limitSpeed = Short.parseShort((String)codeMap.get("TXT_VAL1"));
					short stationZone = Short.parseShort((String)codeMap.get("TXT_VAL2"));
					double len = DataInterface.getDistanceBetween(
						CommonUtil.stringToDouble(oldVhcDrInfo.get("LONGITUDE")+""),
						CommonUtil.stringToDouble(oldVhcDrInfo.get("LATITUDE")+""),
						CommonUtil.stringToDouble(eventInfo.get("LONGITUDE")+""),
						CommonUtil.stringToDouble(eventInfo.get("LATITUDE")+"")
					);

					logger.debug("eventInfo={}, oldVhcDrInfo={}, len={}, stationZone={}", eventInfo, oldVhcDrInfo, len, stationZone);
					if("TRUE".equals((String)oldVhcDrInfo.get("IS_STATION"))) {
						if(len>stationZone) { //정류소 범위를 벗어나면 정류소에서 벗어난걸로 인식함
							eventInfo.put("IS_STATION", "FALSE");
						}
						else {
							logger.debug("in station zone len="+len);
							eventInfo.put("LONGITUDE",oldVhcDrInfo.get("LONGITUDE"));
							eventInfo.put("LATITUDE",oldVhcDrInfo.get("LATITUDE"));
							eventInfo.put("NODE_ID", oldVhcDrInfo.get("NODE_ID"));
							setVhcDrInfo(vhcId, eventInfo, nodeInfo);
							return 0;
						}
					}
					
					//logger.debug("eventInfo UPD_DTM2=" + eventInfo.get("UPD_DTM2") + ", oldVhcDrInfo UPD_DTM2="
					//		+ oldVhcDrInfo.get("UPD_DTM2"));
					Date eventDate = CommonUtil.stringToDate2((String) eventInfo.get("UPD_DTM2") + "0");
					Date oldVhcDrInfotDate = CommonUtil.stringToDate2((String) oldVhcDrInfo.get("UPD_DTM2") + "0");

					long time = 0;
					if (eventDate != null && oldVhcDrInfotDate != null) {
						time = (eventDate.getTime() - oldVhcDrInfotDate.getTime())/1000;
					}
					if (time == 0) {
						setVhcDrInfo(vhcId, eventInfo, nodeInfo);
						return 0;
					}


					double speed = len / time * 3600/ 1000;

					logger.debug("NODE_ID=" + nodeInfo.get("NODE_ID") + ", LINK_ID=" + nodeInfo.get("LINK_ID")
							+ ",BEARING=" + nodeInfo.get("BEARING") + ",speed=" + speed + ",len=" + len+ ",time=" + time);
					//logger.debug("LATITUDE=" + eventInfo.get("LATITUDE") + ",LATITUDE=" + eventInfo.get("LATITUDE"));
					if (limitSpeed < speed) {
						setVhcDrInfo(vhcId, eventInfo, nodeInfo);
						return -1;
					}
				}
			}

			setVhcDrInfo(vhcId, eventInfo, nodeInfo);
		} catch (Exception e) {
			logger.error("getVhcDrInfo() in  Exception {}", e);
			if(nodeInfo!=null) {
				logger.error("nodeInfo = {}", nodeInfo);
			}
		}
		return 0;
	}

	private Map<String, Object> getCurNodeByLinkSn(Map<String, Object> eventInfo) {
		try {
			// String routId = (String) eventInfo.get("ROUT_ID");
			List<Map<String, Object>> nodeList = getNodeList(eventInfo);
			if (nodeList == null)
				return null;
			for (Map<String, Object> node : nodeList) {
				int nodeSn = Integer.parseInt(node.getOrDefault("NODE_SN", 0).toString());
				int eventLinkSn = Integer.parseInt(eventInfo.getOrDefault("LINK_SN", 0).toString());
				if ((nodeSn >= eventLinkSn) && node.get("NODE_ID").equals(eventInfo.get("NODE_ID"))) {
					//logger.debug("getCurNodeByLinkSn node={}, eventNodeSn={}, nodeSn={} ", node, eventLinkSn, nodeSn);
					// logger.debug("getCurNodeByLinkSn nodeSn= " + nodeSn + eventLinkSn);

					return node;
				}
			}
		} catch (Exception e) {
			logger.error("getCurNodeByLinkSn() in  Exception {}", e);
		}
		return null;
	}

	private Map<String, Object> getCurNode(Map<String, Object> eventInfo, String nodeType) {
		try {
			// String routId = (String) eventInfo.get("ROUT_ID");
			List<Map<String, Object>> nodeList = getNodeList(eventInfo);
			if (nodeList == null)
				return null;
			for (int i = nodeList.size() - 1; i >= 0; i--) {
				Map<String, Object> node = nodeList.get(i);
				int nodeSn = Integer.parseInt(node.getOrDefault("NODE_SN", 0).toString());
				int eventNodeSn = Integer.parseInt(eventInfo.getOrDefault("NODE_SN", 0).toString());
				if ((nodeSn <= eventNodeSn)&& 
					(node.get("NODE_TYPE").equals(nodeType)||nodeType.contains((String)node.get("NODE_TYPE")))) {
					//logger.debug("getCurNode node={}, eventNodeSn={}, nodeSn={} ", node, eventNodeSn, nodeSn);
					// logger.debug("getCurNode nodeSn= " + nodeSn + eventNodeSn);
					return node;
				}
				
			}
		} catch (Exception e) {
			logger.error("getCurNode() in Exception {}", e);
		}
		return null;
	}

	private Map<String, Object> getNextNode(Map<String, Object> eventInfo, String nodeType) {
		try {
			// String routId = (String) eventInfo.get("ROUT_ID");
			List<Map<String, Object>> nodeList = getNodeList(eventInfo);

			for (Map<String, Object> node : nodeList) {
				int nodeSn = Integer.parseInt(node.getOrDefault("NODE_SN", 0).toString());
				int eventNodeSn = Integer.parseInt(eventInfo.getOrDefault("NODE_SN", 0).toString());
				if ((nodeSn > eventNodeSn) && node.get("NODE_TYPE").equals(nodeType)) {
					//logger.debug("getNextNode node={}, eventNodeSn={}, nodeSn={} ", node, eventNodeSn, nodeSn);
					// logger.debug("getNextNode nodeSn= " + nodeSn + eventNodeSn);
					return node;
				}
			}
		} catch (Exception e) {
			logger.error("getNextNode() in Exception {}", e);
		}
		return null;
	}

	private Map<String, Object> getCurSttnCrsNode(Map<String, Object> eventInfo) {
		try {
			String routId = (String) eventInfo.get("ROUT_ID");
			List<Map<String, Object>> nodeList = getNodeList(eventInfo);

			for (int i = nodeList.size() - 1; i >= 0; i--) {
				Map<String, Object> node = nodeList.get(i);
				int nodeSn = Integer.parseInt(node.getOrDefault("NODE_SN", 0).toString());
				int eventNodeSn = Integer.parseInt(eventInfo.getOrDefault("NODE_SN", 0).toString());
				if ((nodeSn <= eventNodeSn) && (node.get("NODE_TYPE").equals(Constants.NODE_TYPE_BUSSTOP)
						|| node.get("NODE_TYPE").equals(Constants.NODE_TYPE_CROSS))) {
					//logger.debug("getCurSttnCrsNode node={}, eventNodeSn={}, nodeSn={} ", node, eventNodeSn, nodeSn);
					return node;
				}
			}
		} catch (Exception e) {
			logger.error("getCurSttnCrsNode() in Exception {}", e);
		}
		return null;
	}

	private Map<String, Object> getNextSttnCrsNode(Map<String, Object> eventInfo) {
		try {
			String routId = (String) eventInfo.get("ROUT_ID");
			List<Map<String, Object>> nodeList = getNodeList(eventInfo);

			for (Map<String, Object> node : nodeList) {
				int nodeSn = Integer.parseInt(node.getOrDefault("NODE_SN", 0).toString());
				int eventNodeSn = Integer.parseInt(eventInfo.getOrDefault("NODE_SN", 0).toString());
				if ((nodeSn > eventNodeSn) && (node.get("NODE_TYPE").equals(Constants.NODE_TYPE_BUSSTOP)
						|| node.get("NODE_TYPE").equals(Constants.NODE_TYPE_CROSS))) {
					//logger.debug("getNextSttnCrsNode node={}, eventNodeSn={}, nodeSn={} ", node, eventNodeSn, nodeSn);
					return node;
				}
			}
		} catch (Exception e) {
			logger.error("getNextSttnCrsNode() in Exception {}", e);
		}
		return null;
	}

	private Map<String, Object> getCorInfo(Map<String, Object> eventInfo) {
		String repRoutId = (String) eventInfo.get("REP_ROUT_ID");
		if (CommonUtil.empty(eventInfo.get("ROUT_ID")) || CommonUtil.empty(eventInfo.get("COR_ID")))
			return null;
		if ((repRoutId != null) && (repRoutId.isEmpty() == false)) {
			List<Map<String, Object>> operCorInfoList = null;

			if (g_operCorInfoMap == null) {
				g_operCorInfoMap = new HashMap<>();
				operCorInfoList = curInfoMapper.selectCorDtlInfo(repRoutId);
				g_operCorInfoMap.put(repRoutId, operCorInfoList);
			}
			operCorInfoList = (List<Map<String, Object>>) g_operCorInfoMap.get(repRoutId);
			if (operCorInfoList == null || operCorInfoList.size() == 0) {
				operCorInfoList = curInfoMapper.selectCorDtlInfo(repRoutId);
				g_operCorInfoMap.put(repRoutId, operCorInfoList);
			}

			for (Map<String, Object> corInfo : operCorInfoList) {
				if (eventInfo.get("ROUT_ID").equals(corInfo.get("ROUT_ID"))
						&& eventInfo.get("COR_ID").equals(corInfo.get("COR_ID"))) {
					return corInfo;
				}
			}

			return null;
		}
		return null;
	}

	/*
	 * private Map<String, Object> getCurSttnCrsNode(Map<String, Object> eventInfo)
	 * { String routId = (String)eventInfo.get("ROUT_ID"); List<Map<String, Object>>
	 * nodeList = getNodeList(eventInfo);
	 * 
	 * for(Map<String, Object> node : nodeList) {
	 * if((int)node.get("LINK_SN")>=(int)eventInfo.get("LINK_SN")
	 * &&(node.get("NODE_TYPE").equals(Constants.NODE_TYPE_BUSSTOP))
	 * ||(node.get("NODE_TYPE").equals(Constants.NODE_TYPE_CROSS))) {
	 * 
	 * return node; } } return null; }
	 */

	private Map<String, Object> getVhcInfo(Map<String, Object> paramMap) {
		String impID = (String) paramMap.get("MNG_ID");
		logger.debug("getVhcInfo() impID=" + impID);
		Map<String, Object> vhcInfo;
		if (g_vhcInfoMap == null) {
			g_vhcInfoMap = new HashMap<>();
			vhcInfo = timsMapper.selectVhcInfo(paramMap);
			g_vhcInfoMap.put(impID, vhcInfo);
			return vhcInfo;
		}

		if ((impID != null) && (impID.isEmpty() == false)) {
			vhcInfo = (Map<String, Object>) g_vhcInfoMap.get(impID);
			if ((vhcInfo != null)) {
				return vhcInfo;
			} else {
				vhcInfo = timsMapper.selectVhcInfo(paramMap);
				g_vhcInfoMap.put(impID, vhcInfo);
				return vhcInfo;
			}
		} else {
			return null;
		}
	}

	/*
	 * private Map<String, Object> getVhcDrInfo(Map<String, Object> paramMap) {
	 * String impID = (String)paramMap.get("MNG_ID");
	 * logger.debug("getVhcDrInfo() impID="+impID); Map<String, Object> vhcInfo;
	 * if(g_vhcDrInfoMap==null) { g_vhcDrInfoMap = new HashMap<>(); vhcInfo =
	 * timsMapper.selectVhcInfo(paramMap); g_vhcInfoMap.put(impID, vhcInfo); return
	 * vhcInfo; }
	 * 
	 * 
	 * if ((impID != null) && (impID.isEmpty() == false)) { vhcInfo = (Map<String,
	 * Object>)g_vhcInfoMap.get(impID); if ((vhcInfo != null)) { return vhcInfo; }
	 * else { vhcInfo = timsMapper.selectVhcInfo(paramMap); g_vhcInfoMap.put(impID,
	 * vhcInfo); return vhcInfo; } } else { return null; } }
	 */

	private String getBusId(Map<String, Object> paramMap) {
		String busNo = (String) paramMap.get("BUS_NO");
		logger.debug("getBusId() busNo=" + busNo);
		String busId = null;
		if (g_busIdMap == null) {
			g_busIdMap = new HashMap<>();
			busId = curInfoMapper.getBusId(paramMap);
			g_busIdMap.put(busNo, busId);
			return busId;
		}

		if ((busNo != null) && (busNo.isEmpty() == false)) {
			busId = (String) g_busIdMap.get(busNo);
			if ((busId != null) && (busId.isEmpty() == false)) {
				return busId;
			} else {
				busId = curInfoMapper.getBusId(paramMap);
				g_busIdMap.put(busNo, busId);
				return busId;
			}
		} else {
			return null;
		}
	}

	private Map<String, Object> getRoutMst(Map<String, Object> paramMap) {
		String routId = (String) paramMap.get("ROUT_ID");
		logger.debug("getRoutMst() routId=" + routId);
		Map<String, Object> routInfo = null;

		if (routId == null)
			return null;

		if (g_routMap == null) {
			g_routMap = new HashMap<>();
			routInfo = getRoutMst(paramMap);
			g_routMap.put(routId, routInfo);
			return routInfo;
		}

		if ((routId != null) && (routId.isEmpty() == false)) {
			routInfo = (Map<String, Object>) g_routMap.get(routId);
			if ((routInfo != null)) {
				return routInfo;
			} else {
				routInfo = curInfoMapper.getRoutMst(paramMap);
				g_routMap.put(routId, routInfo);
				return routInfo;
			}
		} else {
			return null;
		}
	}

	private Map<String, Object> getCommonCode(String coCd, String ValType, String value) {
		// String eventCd = paramMap.get("EVENT_CD")+"";
		logger.debug("getEventCode() coCd=" + coCd + ", eventCd=" + value);
		Map<String, Object> param = new HashMap<>();
		String key = coCd + value;
		param.put("CO_CD", coCd);

		if (ValType == null) {
			param.put("VAL_TYPE", "DL_CD");
		} else {
			param.put("VAL_TYPE", ValType);
		}
		param.put("VAL", value);

		Map<String, Object> eventCodeMap = null;
		if (g_operEventCodeMap == null) {
			g_operEventCodeMap = new HashMap<>();
			eventCodeMap = curInfoMapper.getEventCode(param);
			if (eventCodeMap != null) {
				g_operEventCodeMap.put(key, eventCodeMap);
				return eventCodeMap;
			}
		}

		eventCodeMap = (Map<String, Object>) g_operEventCodeMap.get(key);
		if ((eventCodeMap != null)) {
			return eventCodeMap;
		} else {
			eventCodeMap = curInfoMapper.getEventCode(param);
			g_operEventCodeMap.put(key, eventCodeMap);
			return eventCodeMap;
		}
	}

	private Map<String, Object> getVhcSttnInfo(Map<String, Object> paramMap) {
		String impId = (String) paramMap.get("MNG_ID");
		String evtType = (String) paramMap.get("EVT_TYPE");
		String evtData = (String) paramMap.get("EVENT_DATA");
		String updDtm = (String) paramMap.get("UPD_DTM");
		String nodeNm = (String) paramMap.get("NODE_NM");

		logger.debug("getVhcSttnInfo() impId=" + impId + ",evtType=" + evtType + ",evtData=" + evtData + ",updDtm="
				+ updDtm);

		if (Constants.OPER_EVENT_ARRIVAL.equals(evtType) == false
				&& Constants.OPER_EVENT_DEPART.equals(evtType) == false) {
			return null;
		}

		Map<String, Object> vhcSttnInfo = null;

		if (impId == null || impId.isEmpty())
			return null;

		String sttnLinkId = curInfoMapper.getSttnLinkId(paramMap);
		if (g_operVhcSttnInfoMap == null) {
			g_operVhcSttnInfoMap = new HashMap<>();
			vhcSttnInfo = new HashMap<>();
			vhcSttnInfo.put("ROUT_STTN_LINK_ID", sttnLinkId);
			vhcSttnInfo.put("EVT_TYPE", evtType);
			vhcSttnInfo.put("STTN_ID", evtData);
			vhcSttnInfo.put("UPD_DTM", updDtm);
			vhcSttnInfo.put("STOP_TM", 0);
			vhcSttnInfo.put("NODE_NM", nodeNm);

			g_operVhcSttnInfoMap.put(impId, vhcSttnInfo);
			return vhcSttnInfo;
		}

		if ((impId != null) && (impId.isEmpty() == false)) {

			vhcSttnInfo = (Map<String, Object>) g_operVhcSttnInfoMap.get(impId);
			logger.debug("getVhcSttnInfo() {}", vhcSttnInfo);
			if (vhcSttnInfo == null) {
				g_operVhcSttnInfoMap = new HashMap<>();
				vhcSttnInfo = new HashMap<>();
				vhcSttnInfo.put("ROUT_STTN_LINK_ID", sttnLinkId);
				vhcSttnInfo.put("EVT_TYPE", evtType);
				vhcSttnInfo.put("STTN_ID", evtData);
				vhcSttnInfo.put("UPD_DTM", updDtm);
				vhcSttnInfo.put("STOP_TM", 0);
				vhcSttnInfo.put("NODE_NM", nodeNm);

				g_operVhcSttnInfoMap.put(impId, vhcSttnInfo);
				return vhcSttnInfo;
			} else {
				if (Constants.OPER_EVENT_ARRIVAL.equals(evtType)) {
					vhcSttnInfo.put("ROUT_STTN_LINK_ID", sttnLinkId);
					vhcSttnInfo.put("EVT_TYPE", evtType);
					vhcSttnInfo.put("STTN_ID", evtData);
					vhcSttnInfo.put("UPD_DTM", updDtm);
					vhcSttnInfo.put("STOP_TM", 0);
					vhcSttnInfo.put("NODE_NM", nodeNm);
				} else {

					String oldSttnId = (String) vhcSttnInfo.get("STTN_ID");
					String oldEvtType = (String) vhcSttnInfo.get("EVT_TYPE");
					String oldUpdDtm = (String) vhcSttnInfo.get("UPD_DTM");
					logger.debug("getVhcSttnInfo() {}", vhcSttnInfo);
					if (Constants.OPER_EVENT_ARRIVAL.equals(oldEvtType)
							&& Constants.OPER_EVENT_DEPART.equals(evtType)) {
						try {
							long time = CommonUtil.stringToDate((String) updDtm).getTime()
									- CommonUtil.stringToDate(oldUpdDtm).getTime();
							logger.debug("getVhcSttnInfo() time = " + time);
							vhcSttnInfo.put("STOP_TM", time / 1000);
						} catch (Exception e) {
							logger.error("Exception {}", e);
						}
					} else {
						vhcSttnInfo.put("STOP_TM", 0);
					}
					vhcSttnInfo.put("ROUT_STTN_LINK_ID", sttnLinkId);
					vhcSttnInfo.put("EVT_TYPE", evtType);
					vhcSttnInfo.put("STTN_ID", evtData);
					vhcSttnInfo.put("UPD_DTM", oldUpdDtm);
					vhcSttnInfo.put("NODE_NM", nodeNm);
				}
			}
		}
		return vhcSttnInfo;
	}
	
	private boolean checkChangeBusOperInfo(Map<String, Object> busInfo) {
		String impId = (String) busInfo.get("MNG_ID");
		if ((impId != null) && (impId.isEmpty() == false)) {
			Map<String, Object> oldBusOperInfo = (Map<String, Object>) g_busOperInfoMap.get(impId);
			if (oldBusOperInfo == null || oldBusOperInfo.get("OPER_STS") != busInfo.get("OPER_STS")
					|| busInfo.get("EVENT_CD")!=null&&oldBusOperInfo.get("EVENT_CD") != busInfo.get("EVENT_CD")
					|| oldBusOperInfo.get("LATITUDE") != busInfo.get("LATITUDE")
					|| oldBusOperInfo.get("LONGITUDE") != busInfo.get("LONGITUDE")) {
				if (busInfo != null && oldBusOperInfo != null) {
					//logger.debug("checkChangeBusOperInfo not equal busInfo: {}, oldBusOperInfo: {}", busInfo,
							//oldBusOperInfo);
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
		String impId = (String) busInfo.get("MNG_ID");

		if ((impId != null) && (impId.isEmpty() == false)) {
			busInfo.put("GPS_X", busInfo.get("LONGITUDE"));
			busInfo.put("GPS_Y", busInfo.get("LATITUDE"));
			if (g_busOperInfoMap != null) {
				g_busOperInfoMap.put(impId, CommonUtil.deepCopy(busInfo));
			} else {
				g_busOperInfoMap = new HashMap<>();
				g_busOperInfoMap.put(impId, CommonUtil.deepCopy(busInfo));
			}
		}
	}

	private Map<String, Object> getBusOperInfo(Map<String, Object> busInfo) {
		String impId = (String) busInfo.get("MNG_ID");
		if (CommonUtil.empty(g_busOperInfoMap.get(impId)))
			return curInfoMapper.selectCurOperInfo(busInfo);
		else
			return (Map<String, Object>) g_busOperInfoMap.get(impId);
	}

	public MorEventThread(String sessionId) {
		this.sessionId = sessionId;

		timsMapper = (TimsMapper) BeanUtil.getBean(TimsMapper.class);
		// historyMapper = (HistoryMapper) BeanUtil.getBean(HistoryMapper.class);
		curInfoMapper = (CurInfoMapper) BeanUtil.getBean(CurInfoMapper.class);
		// commonMapper = (CommonMapper) BeanUtil.getBean(CommonMapper.class);
		webSocketClient = (WsClient) BeanUtil.getBean(WsClient.class);
		routMapper = (RoutMapper) BeanUtil.getBean(RoutMapper.class);
		// kafkaProducer = (KafkaProducer) BeanUtil.getBean(KafkaProducer.class);
	}

	public void stop(boolean bStop) {

		bRunning = false;
	}

	@Override
	public void run() {

		while (bRunning) {

			if (getKafkaSize() > 0)
				logger.debug("HandleThread Running...kafkaQ.size:{}", getKafkaSize());

			try {
				KafkaMessage msg = getKafkaMessage();

				if (msg != null) {

					// logger.info("===================== START >> sessionId:{}", sessionId);

					Map<String, Object> map = null;

					String sessionId = msg.getSessionId();
					TimsMessage timsMessage = msg.getTimsMessage();

					map = handle(timsMessage, sessionId);

					// 웹소켓 전송이 필요한 경우
					if (map != null) {
						// logger.info("webSocketClient.sendMessage before");
						webSocketClient.sendMessage(map);
						// logger.info("webSocketClient.sendMessage after");
					}

					// logger.info("===================== END >> sessionId:{}", sessionId);
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

	// static public Map<String, Object> busInfoMap = new HashMap<>();

	public Map<String, Object> handle(TimsMessage timsMessage, String sessionId) {

		// 웹소켓 전송이 필요한 경우 데이터 세팅
		Map<String, Object> wsDataMap = null;

		// 쿼리용 파라미터 맵
		Map<String, Object> paramMap = null;

		// Map<String, Object> vhcInfo = null;

		TimsPayload timsPayload = timsMessage.getPayload();

		byte opCode = timsPayload.OpCode;
		logger.debug("handle() opCode == {}", opCode);
		if (opCode == PlCode.OP_EVENT_REQ) {
			PlEventRequest request = (PlEventRequest) timsPayload;
			for (int i = 0; i < request.getAttrCount(); i++) {
				AtMessage atMessage = request.getAttrList().get(i);
				short attrId = atMessage.getAttrId();

				switch (attrId) {
				case BrtAtCode.BUS_INFO: // 정주기 버스 정보

					logger.debug("BUS_INFO >> {}", atMessage);
					try {
						AtBusInfo busInfo = (AtBusInfo) atMessage.getAttrData();

						// insert to BRT_CUR_OPER_INFO
						Map<String, Object> busInfoMap = busInfo.toMap();
						busInfoMap.put("UPD_DTM2", busInfo.getUpdateTm().toString());
						busInfoMap.put("MNG_ID", sessionId);
						busInfoMap.put("VHC_ID", getBusId(busInfoMap));
						if (CommonUtil.empty(busInfoMap.get("VHC_ID")))
							break;

						Map<String, Object> routMap = getRoutMst(busInfoMap);
						if (routMap != null) {
							busInfoMap.put("REP_ROUT_ID", routMap.get("REP_ROUT_ID"));
							busInfoMap.put("ROUT_ID", routMap.get("ROUT_ID"));
							busInfoMap.put("WAY_DIV", routMap.get("WAY_DIV"));
							busInfoMap.put("ROUT_NM", routMap.get("ROUT_NM"));
							busInfoMap.put("REP_ROUT_NM", routMap.get("REP_ROUT_NM"));
						}
						if (CommonUtil.empty(busInfoMap.get("OPER_DT"))) {
							busInfoMap.put("OPER_DT", CommonUtil.getOperDt());
						}

						// busInfoMap.put("OPER_STS", getOperSts(busInfoMap));
						Map<String, Object> operSts = getCommonCode("OPER_STS", "NUM_VAL4",
								busInfoMap.get("RUN_TYPE") + "");
						if (operSts != null) {
							busInfoMap.put("OPER_STS", operSts.get("DL_CD"));
						}

						try {
							// if("OS001".equals(busInfoMap.get("OPER_STS"))==false) { //운행중이 아닐때만 저장
							busInfoMap.put("EVENT_CD", null);
							// setOperEventData(busInfoMap);
							// }

						} catch (Exception e) {
							logger.error("Exception {}", e);
						}

						// 모니터링용 웹소켓 데이터
						paramMap = new HashMap<>();
						paramMap.put("MNG_ID", sessionId);

						// vhcInfo = timsMapper.selectVhcInfo(paramMap);
						Map<String, Object> vhcInfo = getVhcInfo(paramMap);
						// Map<String, Object> dataMap =busInfo.toMap();

						if (setOperEventData(busInfoMap) == -1)
							return null;

						wsDataMap = new HashMap<>();
						wsDataMap.put("ATTR_ID", attrId);
						wsDataMap.put("ROUT_ID", busInfoMap.get("ROUT_ID"));
						wsDataMap.put("ROUT_NM", busInfoMap.get("ROUT_NM"));
						wsDataMap.put("VHC_NO", busInfoMap.get("BUS_NO"));
						wsDataMap.put("CUR_SPD", busInfoMap.get("SPEED"));
						wsDataMap.put("VHC_ID", busInfoMap.get("VHC_ID"));
						wsDataMap.put("DVC_ID", vhcInfo.get("DVC_ID"));
						wsDataMap.put("GPS_X", busInfoMap.get("LONGITUDE"));
						wsDataMap.put("GPS_Y", busInfoMap.get("LATITUDE"));
						wsDataMap.put("BEARING", busInfoMap.get("BEARING"));
						wsDataMap.put("PREV_NODE_NM", busInfoMap.get("PREV_NODE_NM")); // 이전 정류소/교차로
						wsDataMap.put("NEXT_NODE_ID", busInfoMap.get("NEXT_NODE_ID")); // 다음 정류소/교차로
						wsDataMap.put("NEXT_NODE_NM", busInfoMap.get("NEXT_NODE_NM"));
						wsDataMap.put("NEXT_NODE_TYPE", busInfoMap.get("NEXT_NODE_TYPE"));
						wsDataMap.put("OPER_STS", busInfoMap.get("OPER_STS"));
						wsDataMap.put("LINK_ID", busInfoMap.get("LINK_ID"));
						wsDataMap.put("NODE_ID", busInfoMap.get("NODE_ID"));
						wsDataMap.put("NODE_SN", busInfoMap.get("NODE_SN"));
						wsDataMap.put("CUR_STTN_CRS_ID", busInfoMap.get("CUR_STTN_CRS_ID"));
						wsDataMap.put("CUR_CRS_ID", busInfoMap.get("CUR_CRS_ID"));
						wsDataMap.put("CUR_NODE_ID", busInfoMap.get("CUR_NODE_ID"));

						Map<String, Object> corInfo = getCorInfo(busInfoMap);
						if (corInfo != null)
							wsDataMap.put("COR_NM", corInfo.get("COR_NM"));
					}

					catch (Exception e) {
						logger.error("Exception {}", e);
					}
					break;

				case BrtAtCode.BUS_ARRIVAL_INFO: // 차량 도착정보

					logger.debug("BUS_ARRIVAL_INFO >> {}", atMessage);
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

							// String routNm = timsMapper.selectRoutName(routId);
							Map<String, Object> routIdMap = new HashMap<>();
							routIdMap.put("ROUT_ID", routId);
							Object routNm = getRoutMst(routIdMap);

							arrivalInfoMap.put("ROUT_ID", routId);
							arrivalInfoMap.put("ROUT_NM", routNm);
							arrivalInfoMap.put("VHC_TYPE", "VT" + arrivalInfoItem.getBusType());
							arrivalInfoMap.put("REMAIN_STTN", loc);
							arrivalInfoMap.put("REMAIN_TM", remainSec);

							arrivalInfoMapList.add(arrivalInfoMap);
						}

						wsDataMap.put("LIST", arrivalInfoMapList);
					} catch (Exception e) {
						logger.error("Exception {}", e);
					}
					break;

				case BrtAtCode.BUS_OPER_EVENT: // 운행 이벤트 정보

					logger.debug(".BUS_OPER_EVENT >> {}", atMessage);

					// 이벤트 이력정보에 insert

					// String eventData = "";
					try {
						String eventCd = "";
						String eventCdName = "";
						AtBusOperEvent busEvent = (AtBusOperEvent) atMessage.getAttrData();
						String eventData = busEvent.getEventData();
						Map<String, Object> busEventMap = busEvent.toMap();
						busEventMap.put("UPD_DTM2", busEvent.getUpdateTm().toString());
						busEventMap.put("MNG_ID", sessionId);
						byte eventCode = busEvent.getEventCode();

						Map<String, Object> eventCodeMap = getCommonCode("OPER_EVT_TYPE", "NUM_VAL4", eventCode + "");
						eventCd = (String) eventCodeMap.get("DL_CD");
						eventCdName = (String) eventCodeMap.get("DL_CD_NM");

						busEventMap.put("EVT_TYPE", eventCd);
						busEventMap.put("VHC_ID", getBusId(busEventMap));
						if (CommonUtil.empty(busEventMap.get("VHC_ID")))
							break;
						Map<String, Object> routMap2 = getRoutMst(busEventMap);
						if (routMap2 != null) {
							busEventMap.put("REP_ROUT_ID", routMap2.get("REP_ROUT_ID"));
							busEventMap.put("WAY_DIV", routMap2.get("WAY_DIV"));
							busEventMap.put("ROUT_NM", routMap2.get("ROUT_NM"));
							busEventMap.put("ST_STTN_ID", routMap2.get("ST_STTN_ID"));
							busEventMap.put("ED_STTN_ID", routMap2.get("ED_STTN_ID"));
						}

						// busEventMap.put("OPER_STS", getOperSts(busEventMap));
						Map<String, Object> operSts = getCommonCode("OPER_STS", "NUM_VAL4",
								busEventMap.get("RUN_TYPE") + "");
						if (operSts != null) {
							busEventMap.put("OPER_STS", operSts.get("DL_CD"));
						}

						if (CommonUtil.empty(busEventMap.get("OPER_DT"))) {
							busEventMap.put("OPER_DT", CommonUtil.getOperDt());
						}

						try {
							/*
							 * //Map<String, Object> curAllocPlInfo =
							 * curInfoMapper.selectCurAllocPlInfoByOperVhcId(busEventMap); String
							 * allocOperVhcId = null; String allocVhcId = null; if(curAllocPlInfo!=null) {
							 * allocOperVhcId = (String) curAllocPlInfo.get("OPER_VHC_ID"); //실제 운행한 차량 ID
							 * allocVhcId = (String) curAllocPlInfo.get("VHC_ID"); //계획된 차량 ID
							 * 
							 * if(CommonUtil.empty(busEventMap.get("REP_ROUT_ID"))&&curAllocPlInfo.get(
							 * "REP_ROUT_ID")!=null) { busEventMap.put("REP_ROUT_ID",
							 * curAllocPlInfo.get("REP_ROUT_ID")); } else { busEventMap.put("REP_ROUT_ID",
							 * "RR00000002"); //임시로 } busEventMap.put("ALLOC_NO",
							 * curAllocPlInfo.get("ALLOC_NO")); busEventMap.put("OPER_SN",
							 * curAllocPlInfo.get("OPER_SN")); }
							 * 
							 * //운행된 차량 ID 정보가 없는 경우 계획된 차량 ID로 배차 정보를 찾음 if (curAllocPlInfo == null ||
							 * CommonUtil.empty(allocOperVhcId)
							 * ||(CommonUtil.empty(allocVhcId)==false)&&(allocVhcId.equals(allocOperVhcId)==
							 * false) ) { curAllocPlInfo =
							 * curInfoMapper.selectCurAllocPlInfoByVhcId(busEventMap);
							 * if(curAllocPlInfo==null){ busEventMap.put("REP_ROUT_ID", "RR00000002"); //임시로
							 * } else{ busEventMap.put("ALLOC_NO", curAllocPlInfo.get("ALLOC_NO"));
							 * busEventMap.put("OPER_SN", curAllocPlInfo.get("OPER_SN"));
							 * 
							 * busEventMap.put("OPER_VHC_ID", busEventMap.get("BUS_ID"));
							 * if(CommonUtil.empty(busEventMap.get("REP_ROUT_ID"))&&curAllocPlInfo.get(
							 * "REP_ROUT_ID")!=null) { busEventMap.put("REP_ROUT_ID",
							 * curAllocPlInfo.get("REP_ROUT_ID")); } //
							 * curInfoMapper.updateOperVhcIdCurAllocPlInfo(busEventMap); } }
							 * 
							 * 
							 * if(curAllocPlInfo==null) {
							 * 
							 * if (checkLastCourse(busEventMap)==false&& (eventCode == (byte)0x03 ||
							 * eventCode == (byte)0x04 ||(eventCode==(byte)0x01||eventCode==(byte)0x02) &&
							 * routMap2.get("ED_STTN_ID").equals(eventData))) //기점, 종점을 잡지 못했을때 {
							 * 
							 * String curNearStr = curInfoMapper.selectCurNearAllocPlInfo(busEventMap);
							 * String curNearArr[] = curNearStr.split(","); busEventMap.put("ROUT_ID",
							 * curNearArr[0]); busEventMap.put("COR_ID", curNearArr[1]);
							 * busEventMap.put("PRDT_ALLOC_NO", curNearArr[2]); //예측한 배차 번호
							 * busEventMap.put("OPER_SN", curNearArr[3]); int minAllocNo =
							 * curInfoMapper.minAllocNoCurAllocPlInfo(busEventMap);
							 * logger.debug("minAllocNo = "+minAllocNo); //임시로그 if(minAllocNo>0) {
							 * minAllocNo=0; logger.debug("0 ?? minAllocNo = "+minAllocNo); //임시로그 } else {
							 * minAllocNo = minAllocNo - 1;
							 * logger.debug("minus ?? minAllocNo = "+minAllocNo); //임시로그 }
							 * busEventMap.put("ALLOC_NO", minAllocNo); busEventMap.put("OPER_VHC_ID",
							 * busEventMap.get("VHC_ID")); } else { int minAllocNo =
							 * curInfoMapper.minAllocNoCurAllocPlInfo(busEventMap);
							 * logger.debug("minAllocNo = "+minAllocNo); //임시로그 if(minAllocNo > 0) {
							 * minAllocNo = 0; logger.debug("0 ?? minAllocNo = "+minAllocNo); //임시로그 } else
							 * { minAllocNo = minAllocNo - 1;
							 * logger.debug("minus ?? minAllocNo = "+minAllocNo); //임시로그 } } } else {
							 * 
							 * if (checkLastCourse(busEventMap)==false&& //해당 노선이 코스의 마지막이 아닐때 (eventCode ==
							 * (byte) 0x03 || eventCode == (byte) 0x04
							 * ||(eventCode==(byte)0x01||eventCode==(byte)0x02) &&
							 * routMap2.get("ED_STTN_ID").equals(eventData) //기점, 종점을 잡지 못했을때 ||
							 * checkRoutChangeBusOperEvent(busEventMap))) { // 현재운행정보도 업데이트 String
							 * curNearStr = "";
							 * 
							 * if(CommonUtil.empty(curNearStr)) { curNearStr =
							 * curInfoMapper.selectCurNearAllocPlInfo2(busEventMap); }
							 * 
							 * if(CommonUtil.notEmpty(curNearStr)) { String curNearArr[] =
							 * curNearStr.split(",");
							 * 
							 * AtSbrtRouteInfo sbrtRouteInfo = new AtSbrtRouteInfo();
							 * sbrtRouteInfo.setUpdateTm(new
							 * AtTimeStamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(
							 * PlatformConfig.PLF_DT_FORMAT)))); sbrtRouteInfo.setRouteId(curNearArr[0]);
							 * sbrtRouteInfo.setCourseId(curNearArr[1]); sbrtRouteInfo.setSelectMode((byte)
							 * 0x01); sbrtRouteInfo.setRunType((byte) 0x01);
							 * 
							 * TimsMessageBuilder builder = new TimsMessageBuilder(
							 * TService.getInstance().getTimsConfig()); TimsMessage setRequest =
							 * builder.setRequest(sbrtRouteInfo);
							 * 
							 * try{ sbrtRouteInfo.setReserved( busEventMap.get("ALLOC_NO")+"");
							 * if(CommonUtil.empty(curNearArr[2])==false) {
							 * sbrtRouteInfo.setOperCount(Byte.parseByte(curNearArr[2])); } }catch(Exception
							 * e){ logger.error("SbrtRouteInfo Exception!!! {}", e); }
							 * 
							 * // 노선,코스 정보를 차량에 전달 //kafkaProducer.sendKafka(setRequest, sessionId);
							 * 
							 * if (eventCode != (byte) 0x03 && eventCode != (byte) 0x01) {
							 * busEventMap.put("ROUT_ID", curNearArr[0]); busEventMap.put("COR_ID",
							 * curNearArr[1]); busEventMap.put("OPER_SN", curNearArr[2]); }
							 * //curInfoMapper.updateOperVhcIdCurAllocPlInfo(busEventMap);
							 * 
							 * } //busEventMap.put("ALLOC_NO", curNearArr[2]); } else { } }
							 */

							if (CommonUtil.empty(busEventMap.get("ALLOC_NO")) == false
									&& CommonUtil.empty(busEventMap.get("REP_ROUT_ID")) == false) {
								logger.debug("[" + busEventMap.get("ALLOC_NO") + "," + busEventMap.get("REP_ROUT_ID")
										+ "," + busEventMap.get("WAY_DIV") + "] In BusOperEvent alloc no");

								if (eventCode == 0x01 || eventCode == 0x02 // 정류장 출/도착 인 경우
										|| eventCode == 0x03 || eventCode == 0x04 // 기점 출/도착 인 경우
										|| eventCode == 0x05 || eventCode == 0x06) // 종점점 출/도착 인 경우
								{
									//Map<String, Object> sttnEventMap = new HashMap<String, Object>(busEventMap);
									//sttnEventMap.put("NODE_ID", busEvent.getEventData()); // 정류장 출/도착인 경우 EVENT_DATA를 사용

									/*if (setOperEventData(busEventMap) == -1)
										return null;
									busEventMap.put("CUR_NODE_SN", sttnEventMap.get("CUR_NODE_SN"));
									busEventMap.put("NODE_NM", sttnEventMap.get("NODE_NM")); // 출/도착 정류소명
									busEventMap.put("NODE_TYPE", sttnEventMap.get("NODE_TYPE"));
									busEventMap.put("PREV_NODE_NM", sttnEventMap.get("PREV_NODE_NM")); // 이전 정류소/교차로
									busEventMap.put("NEXT_NODE_ID", sttnEventMap.get("NEXT_NODE_ID")); // 다음 정류소/교차로
									busEventMap.put("NEXT_NODE_NM", sttnEventMap.get("NEXT_NODE_NM"));
									busEventMap.put("NEXT_NODE_TYPE", sttnEventMap.get("NEXT_NODE_TYPE"));

									busEventMap.put("CUR_NODE_TYPE", sttnEventMap.get("CUR_NODE_TYPE")); // 현재 정류소/교차로
									busEventMap.put("CUR_NODE_ID", sttnEventMap.get("CUR_NODE_ID"));
									busEventMap.put("CUR_NODE_NM", sttnEventMap.get("CUR_NODE_NM"));
									busEventMap.put("CUR_NODE_SN", sttnEventMap.get("CUR_NODE_SN"));
									busEventMap.put("ORG_ID", sttnEventMap.get("NODE_ID"));
									logger.debug("busEventMap = {}", busEventMap);*/
									if((CommonUtil.empty(busEvent.getEventData()) == false)) {
										busEventMap.put("NODE_ID", busEvent.getEventData());
										busEventMap.put("ORG_NODE_ID", busEventMap.get("NODE_ID"));
									}
								}
								if (setOperEventData(busEventMap) == -1)
									return null;
								checkChangeBusOperInfo(busEventMap);
							}
							
							
						} catch (Exception e) {
							logger.error("Exception {}", e);
						}

						try {
							Map<String, Object> vhcSttnInfo = getVhcSttnInfo(busEventMap);
							if (vhcSttnInfo != null) {
								busEventMap.put("STOP_TM", vhcSttnInfo.get("STOP_TM"));
								busEventMap.put("ROUT_STTN_LINK_ID", vhcSttnInfo.get("ROUT_STTN_LINK_ID"));
							}

							// 이력 insert
							// historyMapper.insertEventHistory(busEventMap);
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
						case 0x08: // 음성 출력
						case 0x09: // 차고지 도착
						case 0x0a: // 차고지 출발

							/** 특정 이벤트 **/
						case 0x11: // 문 열림
						case 0x12: // 문 닫힘

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
							logger.debug("운행위반 발생!! [IMP ID : " + busEvent.getImpId() + "]");

							try {
								Map<String, Object> violtMap = getCommonCode("VIOLT_TYPE", "NUM_VAL4", eventCode + "");
								if (violtMap != null) {
									busEventMap.put("VIOLT_TYPE", (String) violtMap.get("DL_CD"));
								}

								// historyMapper.insertOperVioltHistory(busEventMap); // 운행위반이력 insert
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
							logger.debug("돌발 발생!! [IMP ID : " + busEvent.getImpId() + "]");

							try {
								/*
								 * paramMap = new HashMap<>(); paramMap.put("COL", "DL_CD");
								 * paramMap.put("CO_CD", "INCDNT_TYPE"); paramMap.put("COL3", "NUM_VAL4");
								 * paramMap.put("COL_VAL3", (int) eventCode); eventCd =
								 * commonMapper.selectDlCdCol(paramMap);
								 * 
								 * paramMap.put("COL", "DL_CD_NM"); paramMap.put("CO_CD", "INCDNT_TYPE");
								 * paramMap.put("COL3", "NUM_VAL4"); paramMap.put("COL_VAL3", (int) eventCode);
								 * eventCdName = commonMapper.selectDlCdCol(paramMap);
								 */
								Map<String, Object> incdntMap = getCommonCode("INCDNT_TYPE", "NUM_VAL4",
										eventCode + "");
								if (incdntMap != null) {
									busEventMap.put("INCDNT_TYPE", (String) incdntMap.get("DL_CD"));
								}

								// curInfoMapper.insertIncidentInfo(busEventMap); // 돌발정보 insert
							} catch (Exception e) {
								logger.error("Exception {}", e);
							}

							break;

						}

						// 모니터링용 웹소켓 데이터
						paramMap = new HashMap<>();
						paramMap.put("MNG_ID", sessionId);
						busEventMap.put("VHC_ID", getBusId(busEventMap));
						// vhcInfo = timsMapper.selectVhcInfo(paramMap);
						Map<String, Object> vhcInfo = getVhcInfo(paramMap);
						// Map<String, Object> dataMap =busInfo.toMap();

						wsDataMap = new HashMap<>();
						wsDataMap.put("ATTR_ID", attrId);
						wsDataMap.put("VHC_NO", busEvent.getBusNo());
						wsDataMap.put("ROUT_ID", busEvent.getRouteId());
						wsDataMap.put("ROUT_NM", busEventMap.get("ROUT_NM"));
						// wsDataMap.put("VHC_ID", vhcInfo.get("VHC_ID"));
						wsDataMap.put("VHC_ID", busEventMap.get("VHC_ID"));
						wsDataMap.put("DVC_ID", vhcInfo.get("DVC_ID"));
						wsDataMap.put("GPS_X", busEventMap.get("LONGITUDE"));
						wsDataMap.put("GPS_Y", busEventMap.get("LATITUDE"));
						wsDataMap.put("OPER_STS", busEventMap.get("OPER_STS"));
						wsDataMap.put("LINK_ID", busEventMap.get("LINK_ID"));

						{
							// wsDataMap.put("NODE_NM", busEventMap.get("NODE_NM")); // 지나온 노드명
							wsDataMap.put("BEARING", busEventMap.get("BEARING"));
							wsDataMap.put("NODE_NM", busEventMap.get("CUR_NODE_NM")); // 지나온 노드명
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
						// if ("IMP0000977".equals(sessionId))
						{ // dashboard
							wsDataMap.put("WAY_DIV", busEventMap.get("WAY_DIV"));
							wsDataMap.put("CUR_STTN_CRS_ID", busEventMap.get("CUR_STTN_CRS_ID"));
							wsDataMap.put("CUR_CRS_ID", busEventMap.get("CUR_CRS_ID"));
							/*wsDataMap.put("NEXT_CRS_ID", busEventMap.get("NEXT_CRS_ID"));
							wsDataMap.put("NEXT_CRS_NM", busEventMap.get("NEXT_CRS_NM"));
							wsDataMap.put("NEXT_CRS_TYPE", busEventMap.get("NEXT_CRS_TYPE"));
							wsDataMap.put("NEXT_STTN_CRS_ID", busEventMap.get("NEXT_STTN_CRS_ID"));
							wsDataMap.put("NEXT_STTN_CRS_NM", busEventMap.get("NEXT_STTN_CRS_NM"));
							wsDataMap.put("NEXT_STTN_CRS_TYPE", busEventMap.get("NEXT_STTN_CRS_TYPE"));*/
						}
						Map<String, Object> corInfo = getCorInfo(busEventMap);
						if (corInfo != null)
							wsDataMap.put("COR_NM", corInfo.get("COR_NM"));
					} catch (Exception e) {
						logger.error("Exception {}", e);
					}
					break;

				case BrtAtCode.DISPATCH:

					AtDispatch dispatch = (AtDispatch) atMessage.getAttrData();
					Map<String, Object> curInfo = null;
					String repRoutId = "";
					String routId = "";
					String routNm = "";
					String vhcId = "";
					String vhcNo = "";
					String dpDiv = "";
					String dpLv = "";
					String drvId = "";

					logger.debug("디스패치 수신. {}", dispatch);

					try {
						String udpDtm = dispatch.getUpdateTm().toString();
						int msgType = (int) dispatch.getMessageType();
						if(msgType>3)return null;
						int msgLv = (int) dispatch.getMessageLevel();

						// 차량정보 가져오기
						paramMap = new HashMap<>();
						paramMap.put("MNG_ID", sessionId);
						// paramMap.put("IMP_ID", sessionId);

						// vhcInfo = timsMapper.selectVhcInfo(paramMap);
						Map<String, Object> vhcInfo = getVhcInfo(paramMap);
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
						// curInfo = curInfoMapper.selectCurOperInfo(paramMap);

						// if(curInfo != null) {

						routId = String.valueOf(curInfo.get("ROUT_ID"));
						routNm = String.valueOf(curInfo.get("ROUT_NM"));
						drvId = String.valueOf(curInfo.get("DRV_ID"));

						// 디스패치 이력 넣기
						// 디스패치 구분코드 가져오기
						paramMap = new HashMap<>();
						Map<String, Object> divMap = getCommonCode("DISPATCH_DIV", "TXT_VAL1", msgType + "");
						if (divMap != null) {
							dpDiv = (String) divMap.get("DL_CD");
						}
						Map<String, Object> kindMap = getCommonCode("DISPATCH_KIND", "TXT_VAL1", msgLv + "");
						if (kindMap != null) {
							dpLv = (String) kindMap.get("DL_CD");
						}

						HashMap<String, Object> dispatchLog = new HashMap<String, Object>(curInfo);
						//dispatchLog.put("VHC_NO", vhcNo);
						//dispatchLog.put("OPER_DT", operDt);
						//dispatchLog.put("SEND_DATE", udpDtm);
						//dispatchLog.put("DSPTCH_DIV", dpDiv);
						//dispatchLog.put("DSPTCH_KIND", dpLv);
						//dispatchLog.put("DRV_ID", drvId);
						//dispatchLog.put("DSPTCH_CONTS", dispatch.getMessage());
						//dispatchLog.put("GPS_X", curInfo.get("LONGITUDE"));
						//dispatchLog.put("GPS_Y", curInfo.get("LATITUDE"));
						//dispatchLog.put("REP_ROUT_NM", curInfo.get("REP_ROUT_NM"));
						//dispatchLog.put("ROUT_NM", curInfo.get("ROUT_NM"));

						// historyMapper.insertDispatchHistory(dispatchLog);

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
							wsDataMap.put("GPS_Y", curInfo.get("GPS_Y"));
							wsDataMap.put("MESSAGE", dispatch.getMessage());
							logger.debug("DISPATCH curInfo={}", curInfo);
						}
						// logger.info("디스패치 전송 {}", wsDataMap);
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
		} else if (opCode == PlCode.OP_GET_RES) {
			try {
				PlGetResponse payload = (PlGetResponse)timsPayload;
				for(AtMessage atMessage : payload.getAttrList()){
					logger.debug("atMessage : {}", atMessage);
					short attrId = atMessage.getAttrId();
					switch(attrId){
						// 신호정보
						case BrtAtCode.TRAFFIC_LIGHT_STATUS_RESPONSE:
				
							List<HashMap<String, Object>> phaseInfoMapList = new ArrayList<>();
							AtTrafficLightStatusResponse lightStatus = (AtTrafficLightStatusResponse) atMessage.getAttrData();
				
							String crsId = lightStatus.getCrossNodeId(); // 교차로id
							int contSt = lightStatus.getControllerStatus(); // 제어기상태
							int conMode = lightStatus.getControlMode(); // 신호제어 모드
							int phaseNoA = lightStatus.getPhaseNumA(); // 현시 A
							int phaseNoB = lightStatus.getPhaseNumB(); // 현지 B
							short pahseTmA = lightStatus.getPhaseTimeA(); // 현시 진행시간 A
							short pahseTmB = lightStatus.getPhaseTimeB(); // 현시 진행시간 B
				
							HashMap<String, Object> phaseInfoMap = new HashMap<>();
							phaseInfoMap.put("CRS_ID", crsId);
							//phaseInfoMap.put("CRS_NM", searchNode(crsId).get("NODE_NM"));
							phaseInfoMap.put("CONT_ST", contSt);
							phaseInfoMap.put("CONT_MODE", conMode);
							phaseInfoMap.put("PHASE_NO", phaseNoA);
							phaseInfoMap.put("PHASE_NO_B", phaseNoB);
							phaseInfoMap.put("PHASE_TM_A", pahseTmA);
							phaseInfoMap.put("PHASE_TM_B", pahseTmB);
				
							phaseInfoMapList.add(phaseInfoMap);
				
							// 웹소켓 데이터 세팅
							wsDataMap = new HashMap<>();
							wsDataMap.put("ATTR_ID", BrtAtCode.TRAFFIC_LIGHT_STATUS_RESPONSE);
							wsDataMap.put("LIST", phaseInfoMapList);
				
							// logger.info("================ 교차로아이디:{}, 현시번호 : {}", crsId, phaseNo);
				
							break;
						case BrtAtCode.TRAFFIC_MODULE_TWO:
							try {
								AtTrafficModule2 trafficModule2 = (AtTrafficModule2) atMessage.getAttrData();
								logger.info("TRAFFIC_MODULE_TWO : {}", trafficModule2);
								List<HashMap<String, Object>> trafficModule2MapList = new ArrayList<>();
				
								HashMap<String, Object> moduleTwoMap = new HashMap<>();
								moduleTwoMap.put("VHC_NO", trafficModule2.getBusNum());
								moduleTwoMap.put("OPER_DT", CommonUtil.getOperDt());
								moduleTwoMap.put("NODE_ID", trafficModule2.getStationNodeId());
								/*try {
									String nodeNm = (String) searchNode(trafficModule2.getStationNodeId()).get("NODE_NM");
									moduleTwoMap.put("NODE_NM", nodeNm);
								} catch (Exception e) {
									logger.error("Exception {}", e);
								}*/
								moduleTwoMap.put("CTRL_LV", 2);
								moduleTwoMap.put("STOP_SEC", trafficModule2.getWaitTm());
								moduleTwoMap.put("OCR_DTM", trafficModule2.getUpdateTm().toString());
				
								trafficModule2MapList.add(moduleTwoMap);
				
								// 웹소켓 데이터 세팅
								wsDataMap = new HashMap<>();
								wsDataMap.put("ATTR_ID", BrtAtCode.TRAFFIC_MODULE_TWO);
								wsDataMap.put("LIST", trafficModule2MapList);
				
							} catch (Exception e) {
								logger.error("Exception {}", e);
							}
							break;
						case BrtAtCode.TRAFFIC_MODULE_THREE:
							try {
								AtTrafficModule3 trafficModule3 = (AtTrafficModule3) atMessage.getAttrData();
								logger.info("TRAFFIC_MODULE_THREE : {}", trafficModule3);
								List<HashMap<String, Object>> trafficModule3MapList = new ArrayList<>();
				
								HashMap<String, Object> moduleThreeMap = new HashMap<>();
								moduleThreeMap.put("VHC_NO", trafficModule3.getBusNum());
								moduleThreeMap.put("OPER_DT", CommonUtil.getOperDt());
								moduleThreeMap.put("NODE_ID", trafficModule3.getCrossNodeId());
								/*try {
									String nodeNm = (String) searchNode(trafficModule3.getCrossNodeId()).get("NODE_NM");
									moduleThreeMap.put("NODE_NM", nodeNm);
								} catch (Exception e) {
									logger.error("Exception {}", e);
								}*/
								moduleThreeMap.put("CTRL_LV", 3);
								moduleThreeMap.put("CTRL_TYPE",
										getCommonCode("SIG_CTL_TYPE", "TXT_VAL1", trafficModule3.getControlType() + "")
												.get("DL_CD"));
				
								moduleThreeMap.put("CTRL_PHASE_NO", trafficModule3.getControlPhaseNum());
								moduleThreeMap.put("OCR_DTM", trafficModule3.getUpdateTm().toString());
				
								trafficModule3MapList.add(moduleThreeMap);
				
								// 웹소켓 데이터 세팅
								wsDataMap = new HashMap<>();
								wsDataMap.put("ATTR_ID", BrtAtCode.TRAFFIC_MODULE_THREE);
								wsDataMap.put("LIST", trafficModule3MapList);
							} catch (Exception e) {
								logger.error("Exception {}", e);
							}
							break;
						default:
							break;
					}
				}
			} catch (Exception e) {
				logger.error("Exception {}", e);
			}
		}

		return wsDataMap;
	}

	private int setOperEventData(Map<String, Object> operEventMap) {
		int returnValue = 0;

		try {

			// 운행일 생성. 시간에 따라 0시(24시) ~ 02시까지는 이전 날짜로 운행일 설정
			operEventMap.put("OPER_DT",
					OperDtUtil.convertTimeToOperDt(operEventMap.get("UPD_DTM").toString(), "yyyy-MM-dd HH:mm:ss"));

			operEventMap.put("LINK_SN", operEventMap.get("NODE_SN")); // 통합 플랫폼의 노드 순번은 링크 순번임

			// 다음노드(교차로 or 정류소)
			// Map<String, Object> realNodeInfo =
			// timsMapper.selectNodeByLinkSn(operEventMap); // 통플에서 넘어온 노드순번(실제로는 링크순번)
			// // 으로 실제 노드순번 구하기

			Map<String, Object> realNodeInfo = getCurNodeByLinkSn(operEventMap);

			

			if (realNodeInfo != null) {
				returnValue = getVhcDrInfo(operEventMap, realNodeInfo);
				// operEventMap.put("ROUT_NM", realNodeInfo.get("ROUT_NM"));
				// operEventMap.put("NODE_TYPE", realNodeInfo.get("NODE_TYPE"));
				// operEventMap.put("NODE_NM", realNodeInfo.get("NODE_NM"));
				operEventMap.put("NODE_SN", realNodeInfo.get("NODE_SN"));
				operEventMap.put("BEARING", realNodeInfo.get("BEARING"));
			}
			else {
				return -1;
			}

			// Map<String, Object> curSttnInfo = timsMapper.selectCurSttnInfo(operEventMap);
			Map<String, Object> curSttnInfo = getCurNode(operEventMap, Constants.NODE_TYPE_BUSSTOP);
			if (curSttnInfo != null) {
				operEventMap.put("CUR_NODE_TYPE", curSttnInfo.get("NODE_TYPE"));
				// operEventMap.put("CUR_NODE_ID", curSttnInfo.get("CUR_STTN_ID"));
				// operEventMap.put("CUR_NODE_NM", curSttnInfo.get("CUR_STTN_NM"));
				operEventMap.put("CUR_NODE_ID", curSttnInfo.get("NODE_ID"));
				operEventMap.put("CUR_NODE_NM", curSttnInfo.get("NODE_NM"));
				operEventMap.put("CUR_NODE_SN", curSttnInfo.get("NODE_SN"));
				operEventMap.put("NEXT_NODE_ID", curSttnInfo.get("NEXT_STTN_ID"));
				operEventMap.put("NEXT_NODE_NM", curSttnInfo.get("NEXT_STTN_NM"));
				operEventMap.put("NEXT_NODE_TYPE", curSttnInfo.get("NODE_TYPE"));
			}

			Map<String, Object> curCrsInfo = getCurNode(operEventMap, Constants.NODE_TYPE_CROSS);
			if (curCrsInfo != null) {
				operEventMap.put("CUR_CRS_ID", curCrsInfo.get("NODE_ID"));
			}
			
			
			Map<String, Object> curCrsSttnInfo = getCurNode(operEventMap, Constants.NODE_TYPE_CROSS_BUSSTOP);
			if (curCrsSttnInfo != null) {
				operEventMap.put("CUR_STTN_CRS_ID", curCrsSttnInfo.get("NODE_ID"));
			}
			
			// if ("IMP0000977".equals(sessionId))
			/*{
				Map<String, Object> nextCrsInfo = getNextNode(operEventMap, Constants.NODE_TYPE_CROSS);
				if (nextCrsInfo != null) {
					operEventMap.put("NEXT_CRS_ID", nextCrsInfo.get("NODE_ID"));
					operEventMap.put("NEXT_CRS_NM", nextCrsInfo.get("NODE_NM"));
					operEventMap.put("NEXT_CRS_TYPE", nextCrsInfo.get("NODE_TYPE"));
				}
				Map<String, Object> nextSttnCrsInfo = getNextSttnCrsNode(operEventMap);
				if (nextSttnCrsInfo != null) {
					operEventMap.put("NEXT_STTN_CRS_ID", nextSttnCrsInfo.get("NODE_ID"));
					operEventMap.put("NEXT_STTN_CRS_NM", nextSttnCrsInfo.get("NODE_NM"));
					operEventMap.put("NEXT_STTN_CRS_TYPE", nextSttnCrsInfo.get("NODE_TYPE"));
				}
			}*/

			// Map<String, Object> nextNodeInfo =
			// timsMapper.selectNextSttnCrsInfo(operEventMap);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("OPER_DT", operEventMap.get("OPER_DT"));
			param.put("BUS_NO", operEventMap.get("BUS_NO"));
			param.put("ROUT_ID", operEventMap.get("ROUT_ID"));
			param.put("NODE_SN", operEventMap.get("CUR_NODE_SN"));
			operEventMap.put("PRV_PLCE_NM", operEventMap.get("CUR_NODE_NM"));
			operEventMap.put("PREV_NODE_NM", operEventMap.get("NODE_NM"));

			// Map<String, Object> nextSttnInfo = timsMapper.selectNextSttnInfo(param);
			/*
			 * Map<String, Object> nextSttnInfo = getNextNode(param); if (nextSttnInfo !=
			 * null) { //operEventMap.put("NEXT_NODE_ID", nextSttnInfo.get("CUR_STTN_ID"));
			 * //operEventMap.put("NEXT_NODE_NM", nextSttnInfo.get("CUR_STTN_NM"));
			 * operEventMap.put("NEXT_NODE_ID", nextSttnInfo.get("NODE_ID"));
			 * operEventMap.put("NEXT_NODE_NM", nextSttnInfo.get("NODE_NM"));
			 * operEventMap.put("NEXT_NODE_TYPE", nextSttnInfo.get("NODE_TYPE")); }
			 */

		} catch (Exception e) {
			logger.error("setOperEventData Exception {}", e);
		}
		return returnValue;
	}

}
