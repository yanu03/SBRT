package kr.tracom.cm.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.cm.domain.Phrase.PhraseService;
import kr.tracom.cm.support.ControllerSupport;

@Controller
@Scope("request")
public class PhraseController extends ControllerSupport{

	@Autowired
	private PhraseService phraseService;
	
	@RequestMapping("/phrase/phraseList")
	public @ResponseBody Map<String, Object> PhraseList() throws Exception {
		result.setData("dlt_BMS_DL_CD_INFO", phraseService.PhraseList());
		return result.getResult();
	}		
	
	
}
