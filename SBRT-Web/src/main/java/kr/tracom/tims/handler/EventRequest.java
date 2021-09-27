package kr.tracom.tims.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.tracom.platform.attribute.BrtAtCode;
import kr.tracom.platform.attribute.brt.AtBusArrivalInfo;
import kr.tracom.platform.attribute.brt.AtBusArrivalInfoItem;
import kr.tracom.platform.attribute.brt.AtBusInfo;
import kr.tracom.platform.attribute.brt.AtBusOperEvent;
import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.attribute.AtMessage;
import kr.tracom.platform.net.protocol.payload.PlEventRequest;
import kr.tracom.tims.domain.TimsMapper;

@Component
public class EventRequest {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    TimsMapper timsMapper;
    
    
    public Map<String, Object> handle(TimsMessage timsMessage, String sessionId){
    	
    	//웹소켓 전송이 필요한 경우 데이터 세팅
    	Map<String, Object> resultMap = null;
    	
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
               

					//TODO
                	// OPER_DT, REP_ROUT_ID, VHC_ID, ROUT_ID, ALLOC_NO, OPER_SN, NODE_ID, COR_ID, VHC_NO, DRV_ID
                	// GPS_X, GPS_Y, TM_X, TM_Y, OPER_STS, BUS_STS, OBE_STS, SNSTVTY, DRV_ANGLE, CUR_SPD, ACLRTN_YN, CUR_STOP_TM
                	// OPER_LEN, REP_ROUT_NM, NODE_SN, NODE_TYPE, ARRV_TM, DPRT_TM, LINK_ID, LINK_SN, LINK_SPD, GET_OFF_CNT
                	// PSG_CNT, NEXT_STTN_ID, NEXT_STTN_ARRV_TM, NEXT_CRS_ID, NEXT_CRS_ARRV_TM, DOOR_CD, DOOR_TM, UPD_DTM
                	
                	try {
                		timsMapper.insertCurOperInfo(busInfoMap);
                	} catch (Exception e) {
						// TODO: handle exception
					}
                	
                	
                	//모니터링용 웹소켓 데이터
                	Map<String, Object> paramMap = new HashMap<>();
                	paramMap.put("MNG_ID", sessionId);

                	Map<String, Object> vhcInfoMap = timsMapper.selectVhcInfo(paramMap);
                	Map<String, Object> dataMap =busInfo.toMap();
                	
                	resultMap = new HashMap<>();
                	resultMap.put("ATTR_ID", attrId);
                	resultMap.put("VHC_ID", vhcInfoMap.get("VHC_ID"));
                	resultMap.put("DVC_ID", vhcInfoMap.get("DVC_ID"));
                	resultMap.put("GPS_X", dataMap.get("LONGITUDE"));
                	resultMap.put("GPS_Y", dataMap.get("LATITUDE"));
                	
                	
                    break;
                    
                    
            	case BrtAtCode.BUS_ARRIVAL_INFO: //차량 도착정보
                	
                	AtBusArrivalInfo busArrivalInfo = (AtBusArrivalInfo)atMessage.getAttrData();            	
                	
                	
                	/*모니터링용 데이터 생성*/
                	resultMap = new HashMap<>();
                	resultMap.put("ATTR_ID", attrId);
                	resultMap.put("STTN_ID", busArrivalInfo.getStopId());
                	
                	
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
                	
                	
                	resultMap.put("LIST", arrivalInfoMapList);
                	                                
                	
                	break;                    
                    
                    
                case BrtAtCode.BUS_OPER_EVENT: //운행 이벤트 정보
                	//이벤트 이력정보에 insert
                	
                	 AtBusOperEvent busEvent = (AtBusOperEvent)atMessage.getAttrData();            	
                	 
                	 byte eventCode = busEvent.getEventCode();
                	 Map<String, Object> busEventMap = busEvent.toMap();                     

                	 try {
                		 timsMapper.insertEventHistory(busEventMap); //이력 insert
                	 } catch (Exception e) {
                		 // TODO: handle exception
                	 }

                     switch(eventCode){
                         //운행 이벤트
                         case 0x01: //정류장 도착
                             //TODO:정류장정차시간 Dispatch
                             break;

                         case 0x02: //정류장 출발

                             break;

                         case 0x03: //기점 도착
                             break;

                         case 0x04: //기점 출발
                             break;

                         case 0x05: //종점 도착
                             break;

                         case 0x06: //종점 출발
                             break;

                         case 0x07: //노드 통과
                             //TODO: 링크속도 계산

                             break;

                         case 0x08: //가상지점 통과
                             break;

                         /**특정 이벤트 **/
                         case 0x11: //단말기 시동
                             break;

                         case 0x12: //단말기 종료
                             break;

                         case 0x13: //문 열림
                             break;

                         case 0x14: //문 닫힘
                             break;

                         /** 운행위반 이벤트 **/
                         case 0x21: //무정차 주행
                             break;

                         case 0x22: //과속 주행
                             break;

                         case 0x23: //장기과속 주행
                             break;

                         case 0x24: //회차 위반
                             break;

                         case 0x25: //급가속
                             break;

                         case 0x26: //급감속
                             break;

                         case 0x27: //급출발
                             break;

                         case 0x28: //급정지
                             break;

                         case 0x29: //개문주행
                             break;

                         /** 돌발 **/

                         case 0x31: //차량 고장
                         case 0x32: //차량 사고
                         case 0x33: //차내 폭력 사고
                         case 0x34: //강도
                         case 0x35: //테러

                             break;


                     }

                	
                    
                    break;                    
                    
                
                    
                default:
                	break;
            }
        }
        
        
        return resultMap;
    }
    
    
}
