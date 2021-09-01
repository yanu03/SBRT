package kr.tracom.ws;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class WsController {
	
	private static final Logger logger = LoggerFactory.getLogger(WsController.class);
	

	public WsController() {
	}

	
	/*
	 * test
	 */
	@RequestMapping(value = "/websocket", method = RequestMethod.GET)
	public String webSocketPage(HttpServletRequest request, Model model) throws Exception {
		
		return "websocket/wstest";
	}
	
	/*
	@MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public WsMessage sendMessage(@Payload WsMessage msg) {
        return new WsMessage(HtmlUtils.htmlEscape(msg.getContent()));
    }
    */

	
	@MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public @ResponseBody Map<String, Object> sendMessage(@Payload WsMessage msg) {
		
		ObjectMapper mapper = new ObjectMapper(); 
		Map<String, Object> map = new HashMap();
		try { 
			
			// convert JSON string to Map 
			map = mapper.readValue(msg.getContent(), Map.class); 
			// it works //Map<String, String> map = mapper.readValue(json, new TypeReference<Map<String, String>>() {});
			System.out.println(map);
		} catch (IOException e) { e.printStackTrace(); }
					
		
		//map.put("GPS_X", 127.999);
		//map.put("GPS_Y", 36.999);
		//map.put("VHC_ID", "VH00000005");
		
		return map;
		//return new WsMessage(HtmlUtils.htmlEscape(msg.getContent()));
    }
	
	
	
}
