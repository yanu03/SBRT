package kr.tracom.ws;

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
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("content", msg.getContent());
		
		return map;
		//return new WsMessage(HtmlUtils.htmlEscape(msg.getContent()));
    }
	
	
	
}
