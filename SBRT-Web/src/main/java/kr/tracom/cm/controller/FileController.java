package kr.tracom.cm.controller;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tracom.cm.domain.File.FileService;
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

	@Autowired
	protected HttpServletRequest request;
	
	@Autowired
	private FileService fileService;
 
	@Value("${fileupload.location}")
	protected String fileRoot;
	
	@Value("${windows.fileupload.location}")
	protected String windowsFileRoot;
	
	private static final String FILE_LIST_LITERAL = "fileList";
	private static final String FILE_ID = "fileId";
	private static final String TASK_PATH = "taskPath";
	private static final String STATUS = "status";
	private static final String ORG_FILE_NAME = "name";
	private static final String FILE_NAME = "fileNm";
	private static final String FILE_PATH = "filePath";
	private static final String FILE_EXT = "fileExtsn";
	private static final String DELETED_FILE_LIST = "deletedFileList";
	private static final String FILE_ROOT_PATH = "NAS_ROOT";
	private static final String FILE_REGISTER = "register";
	private static final String RETURN_VALUE = "return_value";
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String ROW_STATUS = "rowStatus";

	/**
	 * 작성자: 트라콤
	 * 작성일: 2017. 2. 23.
	 * 수정일: 2017. 2. 23.
	 * 목적 : 파일업로드 공통
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/file/doUploadFile.do")
	@ResponseBody
	public Map<String, Object> uploadFile() throws Exception
	{
		List<Map<String, Object>> lstUploadFile = getSimpleList(FILE_LIST_LITERAL);
		Map<String, Object> param = getSimpleParameters(false);
		String taskName = (String) param.get(TASK_PATH);
		String fileId = (String) (String) param.get(FILE_ID);
		
		if(fileId==null||fileId.isEmpty()){	
			//fileId = unifiedService.getNextStringId();
			fileId = fileService.selectNextMultiFileID();
		}

		String sourcePath = fileRoot+"up/";
		if(SystemUtils.IS_OS_WINDOWS) {
			sourcePath = windowsFileRoot+"up/";
		}
		
		GregorianCalendar gc = new GregorianCalendar ( );
		
		String filePath = '/' + taskName + '/'
				+ gc.get ( Calendar.YEAR ) + '/'
				+ String.valueOf ( gc.get ( Calendar.MONTH ) + 1 ) + '/'
				+ gc.get ( Calendar.DATE )+ '/';
		String destPath = sourcePath+ filePath;
		
		String destFileName = taskName + fileId + fileService.selectNextMultiFileSN(fileId);;

		//String badExts[] = propertyService.getString("BAD_EXTS").split(",");
  
		for (Map<String, Object> fileUpload : lstUploadFile){
			if("".equalsIgnoreCase((String)fileUpload.get(STATUS))&&"R".equalsIgnoreCase((String)fileUpload.get(ROW_STATUS))){
				String orgfileName = (String) fileUpload.get(ORG_FILE_NAME);
				orgfileName = orgfileName.replace("&#40;", "(").replace("&#41;", ")"); //파일명 "(" => &#40 "(" => &#41 변환되는 현상 수정
				fileService.doMoveFile(sourcePath,destPath,orgfileName,destFileName);
				
				fileUpload.put(FILE_ID, fileId);
				fileUpload.put(FILE_NAME, destFileName);
				fileUpload.put(FILE_PATH, filePath);
				fileUpload.put(FILE_EXT, orgfileName.substring(orgfileName.lastIndexOf(".")+1));
				fileUpload.put(ORG_FILE_NAME, orgfileName);
				fileService.insertMultiFile(fileUpload);
			}
		}
		
		result.setData("FILE_ID", fileId);

		return result.getResult();
	}

	
	
		
	/**
	 * 작성자: 트라콤
	 * 작성일: 2017. 2. 23.
	 * 수정일: 2017. 2. 23.
	 * 목적 : 파일가져옴 공통
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/file/doSelectFile.do")
	@ResponseBody
	public Map<String, Object>  selectFile() throws Exception
	{
		Map<String, Object> param = getSimpleParameters(false);
		String fileId = (String) (String) param.get(FILE_ID);

		result.setData(FILE_LIST_LITERAL, fileService.selectMultiFileList(fileId));
		return result.getResult();
	}
	
	/**
	 * 작성자: 트라콤
	 * 작성일: 2017. 2. 23.
	 * 수정일: 2017. 2. 23.
	 * 목적 : 파일삭제 공통
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/file/doDeleteFile.do")
	@ResponseBody
	public Map<String, Object> deleteMultiFile() throws Exception
	{
		List<Map<String, Object>> lstDeleteFile = getSimpleList(DELETED_FILE_LIST);
		Map<String, Object> param = getSimpleParameters(false);
		String fileId = (String) (String) param.get(FILE_ID);

		for (Map<String, Object> deleteMultiFile : lstDeleteFile){
			if("C".equalsIgnoreCase((String)deleteMultiFile.get(STATUS)))
			{
				deleteMultiFile.put(FILE_ID, fileId);
				fileService.updateMultiFileDelYN(deleteMultiFile);
			}
		}
		
		result.setData("FILE_ID", fileId);

		return result.getResult();
	}
	
	/**
	 * 작성자: 트라콤
	 *
	 * 작성일: 2017. 2. 19.
	 * 수정일: 2017. 2. 19.
	 * 목적 :첨부파일 다운로드
	 * @throws Exception
	 */
	@RequestMapping("/file/doDownloadFile.do")
	@ResponseBody
	public void downloadfile() throws Exception{
		String destPath = (String) request.getParameter(FILE_PATH);
		String orgFileName = (String) request.getParameter(ORG_FILE_NAME);
		String destFileName = (String) request.getParameter(FILE_NAME);

			//downLoadFileFromNasRoot( destPath , destFileName, orgFileName);
		
	}
	
	@RequestMapping("/file/doCheckFile")
	public @ResponseBody Map<String, Object> doCheckFile() throws Exception {
		result.setData("dma_filechk", fileService.doCheckFile());
		return result.getResult();
	}		
}
