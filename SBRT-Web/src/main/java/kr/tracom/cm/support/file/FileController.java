package kr.tracom.cm.support.file;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.bms.domain.PI0100.PI0100Service;
import kr.tracom.cm.support.ControllerSupport;


//import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Controller
@Scope("request")
public class FileController extends ControllerSupport {
	
	String strUniqErrorMessage = "중복된 ID값이 존재합니다. 대중소별로 1개의 ID를 지정하십시오.";
	/**
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

	@RequestMapping("/cm//fileUpload.do")
	public @ResponseBody Map<String, Object> PI0100G0R0() throws Exception {

		return result.getResult();
	}
}
