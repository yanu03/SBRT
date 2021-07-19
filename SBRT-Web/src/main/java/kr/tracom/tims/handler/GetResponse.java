package kr.tracom.tims.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import kr.tracom.platform.attribute.BrtAtCode;
import kr.tracom.platform.attribute.brt.AtBusInfo;
import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.attribute.AtMessage;
import kr.tracom.platform.net.protocol.payload.PlGetResponse;
import kr.tracom.ws.WsMessage;

@Component
public class GetResponse {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    public WsMessage handle(TimsMessage timsMessage, String sessionId){
    	
    	WsMessage wsMsg = null;
        PlGetResponse payload = (PlGetResponse)timsMessage.getPayload();
        

        for(AtMessage atMessage : payload.getAttrList()){
            short attrId = atMessage.getAttrId();

            switch(attrId){

                case BrtAtCode.DEVICE_LOCATION_INFO:
                    break;
                case BrtAtCode.DEVICE_STATUS_INFO:
                    break;
                case BrtAtCode.BUS_INFO:
                	AtBusInfo busInfo = (AtBusInfo)atMessage.getAttrData();

                	//test [[
                	/*
                	logger.info("lati:{}, longi:{}", busInfo.getLati(), busInfo.getLongi());
                	
                	wsMsg = new WsMessage();
                	wsMsg.setContent("VH00000001");
                	*/
                	//test ]]
                	

                    break;
                default:
                    break;


            } //switch(attrId)

        } //for(AtMessage atMessage : payload.getAttrList())


        return wsMsg;
        
    } //handler()


} //GetResponse()
