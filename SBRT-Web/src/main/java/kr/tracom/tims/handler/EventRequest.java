package kr.tracom.tims.handler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.tracom.platform.attribute.BrtAtCode;
import kr.tracom.platform.attribute.brt.AtBusOperEvent;
import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.attribute.AtMessage;
import kr.tracom.platform.net.protocol.payload.PlEventRequest;
import kr.tracom.tims.domain.TimsMapper;
import kr.tracom.ws.WsMessage;

@Component
public class EventRequest {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    TimsMapper timsMapper;
    
    
    public WsMessage handle(TimsMessage timsMessage, String sessionId){
    	
    	WsMessage wsMsg = null;
    	
        PlEventRequest request = (PlEventRequest) timsMessage.getPayload();
        
        //logger.info("================ handle Tims Message");

        for(int i=0; i<request.getAttrCount(); i++){
            AtMessage atMessage = request.getAttrList().get(i);
            short attrId = atMessage.getAttrId();

            switch(attrId){
                case BrtAtCode.BUS_INFO: //정주기 버스 정보
                    
                    break;
                    
                case BrtAtCode.BUS_OPER_EVENT: //운행 이벤트 정보
                	//이벤트 이력정보에 insert
                	
                	 AtBusOperEvent busEvent = (AtBusOperEvent)atMessage.getAttrData();            	
                	 
                	 byte eventCode = busEvent.getEventCode();

                     Map<String, Object> busEventMap = busEvent.toMap();
                     timsMapper.insertEventHistory(busEventMap); //이력 insert

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
        
        
        return wsMsg;
    }
    
    
}
