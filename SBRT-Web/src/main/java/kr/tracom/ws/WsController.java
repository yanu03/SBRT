package kr.tracom.ws;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.tracom.platform.attribute.BrtAtCode;
import kr.tracom.platform.attribute.common.AtBrtAction;
import kr.tracom.platform.net.config.TimsConfig;
import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.TimsMessageBuilder;
import kr.tracom.platform.service.TService;
import kr.tracom.platform.service.config.KafkaTopics;
import kr.tracom.tims.kafka.KafkaProducer;

@Controller
public class WsController {
	
	private static final Logger logger = LoggerFactory.getLogger(WsController.class);
	
	@Autowired
	KafkaProducer kafkaProducer;
	
	@Autowired
	WsClient wsClient;
	

	public WsController() {
	}

	
	/*
	 * test
	 */
	@RequestMapping(value = "/websocket", method = RequestMethod.GET)
	public String webSocketPage(HttpServletRequest request, Model model) throws Exception {
		
		return "websocket/wstest";
	}
	
	
	@RequestMapping(value = "/getBitInfo", method = RequestMethod.GET)
	public String getBitInfo(HttpServletRequest request, Model model) throws Exception {
		
		logger.info("getBitInfo >>>>>>>>>>>>>>>>");
		
		AtBrtAction brtRequest = new AtBrtAction();

		brtRequest.setActionCode((byte)1);
		brtRequest.setData("ND00000017");

        
        TimsConfig timsConfig = TService.getInstance().getTimsConfig();
        TimsMessageBuilder builder = new TimsMessageBuilder(timsConfig);
        TimsMessage timsMessage = builder.actionRequest(brtRequest);
        
        kafkaProducer.sendKafka(KafkaTopics.T_BRT, timsMessage, "ND00000017");		
				
		
		return "success";
	}
	
	
	
	@MessageMapping("/sendMessage")
    @SendTo("/topic/public")
	public @ResponseBody Map<String, Object> sendMessage(@Payload Map<String, Object> msgMap) {
    //public @ResponseBody Map<String, Object> sendMessage(@Payload WsMessage msg) {
	//public @ResponseBody WsMessage sendMessage(@Payload WsMessage msg) {
		
		
		Map<String, Object> map = new HashMap();
		
		ObjectMapper mapper = new ObjectMapper(); 
		try { 
			// convert JSON string to Map 
			//map = mapper.readValue(msg.getContent(), Map.class); 
			// it works //Map<String, String> map = mapper.readValue(json, new TypeReference<Map<String, String>>() {});
			
		//	map.put("ATTR_ID", msg.getAttrId());
			
			/*
			map.put("GPS_X", msg.getGpsX());
			map.put("GPS_Y", msg.getGpsY());
			map.put("VHC_ID", msg.getVhcId());
			map.put("DVC_ID", msg.getDvcId());
			map.put("DVC_COND", msg.getDvcCond());
			*/
			
			System.out.println(msgMap);
		} catch (Exception e) { e.printStackTrace(); }
		
		//map.put("GPS_X", 127.270876);
		//map.put("GPS_Y", 36.470945);
		//map.put("VHC_ID", "VH00000005");
		
		return msgMap;
		//return map;
		//return new WsMessage(HtmlUtils.htmlEscape(msg.getContent()));
		
    }
	
	/*
	    * test
	    */
	   @RequestMapping(value = "/gpsTest", method = RequestMethod.GET)
	   public String makeOperPl(HttpServletRequest request, Model model) throws Exception {
	      
	      String locArry[][] = {
	            {"127.261780", "36.481083"}
	            ,{"127.262083", "36.480707"}
	            ,{"127.263062", "36.479225"}
	            ,{"127.264267", "36.477531"}
	            ,{"127.267860", "36.472557"}
	            ,{"127.268585", "36.471756"}
	            ,{"127.269432", "36.471264"}
	            ,{"127.270325", "36.471035"}
	            ,{"127.271027", "36.470894"}
	            ,{"127.272049", "36.470665"}
	            ,{"127.273186", "36.470642"}
	            ,{"127.273491", "36.470776"}
	            ,{"127.274635", "36.471157"}
	            ,{"127.275734", "36.472096"}
	            ,{"127.276443", "36.472149"}
	            
	      };
	      
	      
	      for(int i=0; i<15; i++) {

	         //모니터링용 웹소켓 데이터
	           
	         /*
	         Map<String, Object> paramMap = new HashMap<>();
	           paramMap.put("MNG_ID", sessionId);

	           Map<String, Object> vhcInfoMap = timsMapper.selectVhcInfo(paramMap);
	           Map<String, Object> dataMap =busInfo.toMap();
	           */
	           
	           Map<String, Object> resultMap = new HashMap<>();
	           resultMap.put("ATTR_ID", BrtAtCode.BUS_INFO);
	           resultMap.put("VHC_ID", "VH00000005");
	           //resultMap.put("DVC_ID", vhcInfoMap.get("DVC_ID"));
	           resultMap.put("GPS_X", locArry[i][0]);
	           resultMap.put("GPS_Y", locArry[i][1]);
	           
	           wsClient.sendMessage(resultMap);
	         
	           logger.info(resultMap.toString());
	           
	         Thread.sleep(1000);
	      }
	      
	            
	      
	      return "success";
	   }	
}
