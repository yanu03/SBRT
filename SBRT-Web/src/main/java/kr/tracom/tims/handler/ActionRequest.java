package kr.tracom.tims.handler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.tracom.cm.domain.OperPlan.OperPlanService;
import kr.tracom.platform.attribute.AtCode;
import kr.tracom.platform.attribute.common.AtBrtAction;
import kr.tracom.platform.attribute.common.AtServiceLogInOut;
import kr.tracom.platform.attribute.common.AtTimeStamp;
import kr.tracom.platform.net.config.TimsConfig;
import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.TimsMessageBuilder;
import kr.tracom.platform.net.protocol.attribute.AtMessage;
import kr.tracom.platform.net.protocol.payload.PlActionRequest;
import kr.tracom.platform.service.TService;
import kr.tracom.platform.service.config.KafkaTopics;
import kr.tracom.tims.kafka.KafkaProducer;
import kr.tracom.tims.manager.ThreadManager;
import kr.tracom.util.DateUtil;
import kr.tracom.ws.WsClient;

@Component
public class ActionRequest {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    OperPlanService operPlanService;
    
    @Autowired
    KafkaProducer kafkaProducer;
    
    @Autowired
    ThreadManager threadManager;
    
    @Autowired
	WsClient webSocketClient;

    public Map<String, Object> handle(TimsMessage timsMessage, String sessionId){
    	
    	logger.info("<== PlCode.OP_ACTION_REQ message:{}, sessionId:{}", timsMessage, sessionId);
    	
    	Map<String, Object> resultMap = null;
    	
        PlActionRequest request = (PlActionRequest) timsMessage.getPayload();
        //PlActionResponse response = new PlActionResponse();

        AtMessage atMessage = request.getAtMessage();
        short attrId = atMessage.getAttrId();

        switch(attrId){
            case AtCode.SERVICE_LOGINOUT :
                AtServiceLogInOut atData = (AtServiceLogInOut) atMessage.getAttrData();
                byte inOut = atData.getInOut();

                if(inOut == (byte)0) {
                    logger.info("login {}", sessionId);
                    
                    
                    threadManager.getEventThread(sessionId);
                    
                    threadManager.getMorEventThread(sessionId);

                }
                else if(inOut == (byte)1){
                    logger.info("logout");
                    
                    threadManager.removeEventThread(sessionId);
                    threadManager.removeMorEventThread(sessionId);
                }

                break;
                
            case AtCode.BRT_ACTION :
        		AtBrtAction brtAction = (AtBrtAction) atMessage.getAttrData();
        		byte actionCode = brtAction.getActionCode();
        		

            	if(actionCode == AtBrtAction.changeOperRequest) { //변경운행 요청 수신
            		//변경운행 생성
            		String actionData = brtAction.getReserved();        		
            		String dataArr[] = actionData.split(",");
            		
            		logger.info("======== 변경운행 요청 수신 : {}", actionData);
            		
            		if(dataArr.length == 15) {
	            		String operDt = dataArr[0];
	            		String busId = dataArr[1];
	            		String repRoutId = dataArr[2];
	            		String courseId = dataArr[3];
	            		String routId = dataArr[4];
	            		String allocNo = dataArr[5];
	            		int operSn = Integer.valueOf(dataArr[6]);
	            		String stNodeId = dataArr[7];
	            		int stNodeSn = Integer.valueOf(dataArr[8]);
	            		String linkId = String.valueOf(dataArr[9]);
	            		int timeDiff = Integer.valueOf(dataArr[10]);
	            		String timeMin = String.valueOf(dataArr[11]);
	            		String timeMax = String.valueOf(dataArr[12]);
	            		String gps_x = String.valueOf(dataArr[13]);
	            		String gps_y = String.valueOf(dataArr[14]);
	            		
	            		timeMin = timeMin.substring(11);
	            		timeMax = timeMax.substring(11);
	            		
	            		List<Map<String, Object>> operPlanList = operPlanService.makeChgOperAllocPlNodeInfo(busId, routId, operDt, operSn, stNodeId, stNodeSn, linkId, timeDiff, timeMin, timeMax, gps_x, gps_y,true);
	            		
            		} else {
            			//변경운행 데이터 오류
            			logger.info("변경운행 데이터 오류!! dataArr.length:{}", dataArr.length);
            		}
            		
            		
            		//변경운행 생성 후 완료 전송
            		//logger.info("======== 변경운행 생성완료 : {}", operPlanList);
            		AtBrtAction brtRequest = new AtBrtAction();

            		brtRequest.setTimeStamp(new AtTimeStamp(DateUtil.now("yyyyMMddHHmmssSSS")));
            		brtRequest.setActionCode(AtBrtAction.changeOperResponse);
            		brtRequest.setData("");
            		brtRequest.setReserved(actionData);

                    
                    TimsConfig timsConfig = TService.getInstance().getTimsConfig();
                    TimsMessageBuilder builder = new TimsMessageBuilder(timsConfig);
                    TimsMessage tMessage = builder.actionRequest(brtRequest);
                    
                    logger.info("======== 변경운행 결과 전송 : {}", tMessage);
            		
                    
                    kafkaProducer.sendKafka(KafkaTopics.T_BRT, tMessage, "");	
            		

            	}
            	if(actionCode == AtBrtAction.facilityParam) {
            		String actionData = brtAction.getReserved();
            		//String dataArr[] = actionData.split(",");
            		
            		Gson gson = new Gson();
					Type resultType = new TypeToken<Map<String, Object>>(){}.getType();
					Map<String, Object> map= gson.fromJson(actionData, resultType);
					map.put("ATTR_ID", "5051");
            		
					//Map<String, Object> map = new HashMap<String, Object>();
					ArrayList jsonList = new ArrayList<Map<String, Object>>();
					jsonList.add(map);
					webSocketClient.sendMessageList("/topic/facilityParam",jsonList);

            		
            		logger.info("======== 시설물 매개변수: {}", actionData);
            	}
            	/*else if(actionCode == AtBrtAction.milsstatusinfo){
            		String actionData = brtAction.getReserved();
            		
            		Gson gson = new Gson();
					Type resultType = new TypeToken<Map<String, Object>>(){}.getType();
					Map<String, Object> map= gson.fromJson(actionData, resultType);
					map.put("ATTR_ID", "5052");
            		
					//Map<String, Object> map = new HashMap<String, Object>();
					ArrayList jsonList = new ArrayList<Map<String, Object>>();
					jsonList.add(map);
					webSocketClient.sendMessageList("/topic/facilityParam",jsonList);

            		
            		logger.info("======== 승객감지장치 시설물 매개변수: {}", actionData);

            	}*/

            	
            	
                break;                
                
        }
        
        
        return resultMap;
        
    }
    
    
}
