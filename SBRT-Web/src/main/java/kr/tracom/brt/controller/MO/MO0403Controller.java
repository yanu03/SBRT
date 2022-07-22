package kr.tracom.brt.controller.MO;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.brt.domain.MO0403.MO0403Service;
import kr.tracom.cm.support.ControllerSupport;
import kr.tracom.platform.attribute.brt.AtDispatch;
import kr.tracom.platform.attribute.common.AtTimeStamp;
import kr.tracom.platform.net.config.TimsConfig;
import kr.tracom.platform.net.protocol.TimsAddress;
import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.TimsMessageBuilder;
import kr.tracom.platform.service.TService;
import kr.tracom.platform.service.config.KafkaTopics;
import kr.tracom.util.Constants;
import kr.tracom.util.DateUtil;

@Controller
@Scope("request")
public class MO0403Controller extends ControllerSupport{

	@Autowired
	private MO0403Service mo0403Service;
	
	@RequestMapping("/mo/MO0403G0R0")
	public @ResponseBody Map<String, Object> MO0403G0R0() throws Exception {
		result.setData("dlt_BMS_VHC_MST", mo0403Service.MO0403G0R0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0403SHI0")
	public @ResponseBody Map<String, Object> MO0403SHI0() throws Exception {
		result.setData("dlt_vhcSearchItem", mo0403Service.MO0403SHI0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0403SHI1")
	public @ResponseBody Map<String, Object> MO0403SHI1() throws Exception {
		result.setData("dlt_searchitem", mo0403Service.MO0403SHI1());
		return result.getResult();
	}
	
	//메시지 전송 소켓
	@RequestMapping("/mo/MO0403SCK")
	public @ResponseBody Map<String, Object> MO0403SCK() throws Exception {		
		
		result.setData("", mo0403Service.MO0403SCK0());
		return result.getResult();
	}
	
	@RequestMapping("/mo/MO0403G1R0")
	public @ResponseBody Map<String, Object> MO0403G1R0() throws Exception {
		result.setData("dlt_BRT_OPER_INST_MSG_MST", mo0403Service.MO0403G1R0());
		return result.getResult();
	}
	
}
