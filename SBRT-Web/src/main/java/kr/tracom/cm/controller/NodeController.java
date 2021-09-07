package kr.tracom.cm.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.cm.domain.Node.NodeService;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class NodeController extends ControllerSupport {
	
	@Autowired
	private NodeService nodeService;	
	
	@RequestMapping("/node/selectNodeList")
	public @ResponseBody Map<String, Object> selectNodeList() throws Exception {
		result.setData("dlt_BMS_NODE_MST", nodeService.selectNodeList());
		return result.getResult();
	}

	@RequestMapping("/node/selectNodeItem")
	public @ResponseBody Map<String, Object> selectNodeItem() throws Exception {
		result.setData("dlt_searchitem", nodeService.selectNodeItem());
		return result.getResult();
	}
}
