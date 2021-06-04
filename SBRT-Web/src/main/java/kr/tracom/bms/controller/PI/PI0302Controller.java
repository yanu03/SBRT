package kr.tracom.bms.controller.PI;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.PI0302.PI0302Service;
import kr.tracom.cm.support.ControllerSupport;


@Controller
@Scope("request")
public class PI0302Controller extends ControllerSupport {

	@Autowired
	private PI0302Service PI0302Service;

	@RequestMapping("/pi/PI0302G0R0")
	public @ResponseBody Map<String, Object> PI0302G0R0() throws Exception {
		result.setData("dlt_BMS_NEWS_INFO", PI0302Service.PI0302G0R0());
		return result.getResult();
	}	
}