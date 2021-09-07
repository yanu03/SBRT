package kr.tracom.cm.domain.File;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.tracom.cm.support.ServiceSupport;

@Service
public class FileService extends ServiceSupport {
	
	@Autowired
	private FileMapper fileMapper;
	
	@Value("${sftp.linux.local.directory}")
	private String ROOT_LINUX_LOCAL_PATH;
	
	@Value("${sftp.windows.local.directory}")
	private String ROOT_WINDOWS_LOCAL_PATH;

	/*
	private Logger logger = LoggerFactory.getLogger(FileService.class);
	
	@Inject
	private FTPHandler handler;
	
	@Inject
	private VoiceService voiceService;
	
	@Inject
	SM0105Mapper DLCDMapper;
	
	@Inject
	private BM0501Service destiService; 
	
	public void preview(RequestParams<?> requestParams, HttpServletResponse response) {
		String type = requestParams.getString("type");
        String path = "";
        File file = null;
        
        try {
			switch (type) {
				case GlobalConstants.Types.IMAGE:
					path = imagePreview(requestParams, response);
					break;
			    case GlobalConstants.Types.VOICE:
			    	path = voicePreview(requestParams, response);
			        break;
			    case GlobalConstants.Types.SAVED_VOICE:
			    	path = savedVoicePreview(requestParams, response);
			    	break;
			    case GlobalConstants.Types.TEMP_VOICE:
			    	path = tempVoicePreview(requestParams, response);
			    	break;
			    case GlobalConstants.Types.VIDEO:
			    	path = videoPreview(requestParams, response);
			        break;
			    case GlobalConstants.Types.BMP:
			    	path = bmpPreview(requestParams, response);
			    	break;
			    case GlobalConstants.Types.BMPLOGO:
			    	path = bmpPreviewLOGO(requestParams, response);
			    	break;
			    case GlobalConstants.Types.PNG:
			    	path = pngPreview(requestParams, response);
			    	break;
			    case GlobalConstants.Types.SELECTED:
			    	path = selectedAudioPreview(requestParams, response);
			    	break;
			}
			
			file = new File(path);
			
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			
			if(file.exists()) {
				response.setContentType(mimeType);
				response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
				response.setContentLength((int) file.length());
			
				InputStream is = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(is, response.getOutputStream());
				is.close();
			}
        } catch(Exception e) {
        	Throwable cause = e.getCause();
        	
        	if(cause instanceof IOException) {
        		logger.info("FileService Error!!");
        	} else {
        		e.printStackTrace();
        	}
        }
	}
	
	// 사원 이미지 미리보기
	private String imagePreview(RequestParams<?> requestParams, HttpServletResponse response) {
		String eplyId = requestParams.getString("eplyId");
		return Paths.get(handler.getRootLocalPath(), "/common/employee", eplyId + ".png").toString();
	}
	
	// 저장된 음성 미리듣기
	private String savedVoicePreview(RequestParams<?> requestParams, HttpServletResponse response) throws Exception {
		String vocId = requestParams.getString("vocId");
		String playType = requestParams.getString("playType");
		String vocType = requestParams.getString("vocType");
		
		File file = null;
		String fileName = "";
		String path = Paths.get(handler.getRootLocalPath(), handler.getCommonAudioPath()).toString();
		
		if(playType.equals("WAV")) {
			fileName = vocId + GlobalConstants.VoiceTypes.US;
		} else if(playType.equals("TTS")){
			if(vocType.equals(GlobalConstants.VoiceTypes.KR)) {
				fileName = vocId + GlobalConstants.VoiceTypes.KR;
			} else {
				fileName = vocId + GlobalConstants.VoiceTypes.EN;
			}
		}
		
		file = Paths.get(path, fileName + ".wav").toFile();
		File tempFile = new File(handler.getRootLocalPath(), "/temp/" + fileName + ".mp3");
		
		Utils.wavToMp3(file, tempFile);
		
		return tempFile.getAbsolutePath();
	}
	
	// 2021 0407 선택음성 미리듣기
	private String selectedAudioPreview(RequestParams<?> requestParams, HttpServletResponse response) throws Exception {
		//int id = Integer.parseInt(requestParams.getString("vocId").substring(2, 7));
		String id = requestParams.getString("vocNum");
		String playType = requestParams.getString("playType");
		String pText = requestParams.getString("pText");
		int nSpeakerId = requestParams.getInt("nSpeakerId");
		int nLanguage = requestParams.getInt("nLanguage");
		String chimeYn = "N";
		
		byte[] buffer = null;
		
		File file = null;
		File tempFile = null;
		
		// playType이 TTS일떄
		if(id == null) {
			buffer = voiceService.getWavBuffer(pText, nLanguage, nSpeakerId, chimeYn);
			file = new File(Paths.get(handler.getRootLocalPath(), "/temp/temp.wav").toString());
			
			FileUtils.writeByteArrayToFile(file, buffer);
		}
		// playType이 WAV일때
		else {
			file = new File(handler.getRootLocalPath(), "/temp/temp.mp3");
		}
		
		tempFile = new File(Paths.get(handler.getRootLocalPath(), "/temp/temp.mp3").toString());
		Utils.wavToMp3(file, tempFile);
		
		return tempFile.getAbsolutePath();
	}
	
	*/
	
	/*
	
	// TTS/WAV 미리듣기
	public String voicePreview(RequestParams<?> requestParams, HttpServletResponse response) throws Exception {
		String vocId = requestParams.getString("vocId");
		String routId = requestParams.getString("routId");
		String pText = requestParams.getString("pText");
		int nLanguage = requestParams.getInt("nLanguage");
		int nSpeakerId = requestParams.getInt("nSpeakerId");
		String chimeYn = requestParams.getString("chimeYn");
		
		byte[] buffer = null;
		
		File file = null;
		File tempFile = null;
		
		if(routId != null && !routId.equals("")) {
			file = new File(Paths.get(handler.getRootLocalPath(), "/common/route_audio", routId + ".wav").toString());
		}
		// playType이 TTS일떄
		else if(vocId == null) {
			buffer = voiceService.getWavBuffer(pText, nLanguage, nSpeakerId, chimeYn);
			file = new File(Paths.get(handler.getRootLocalPath(), "/temp/temp.wav").toString());
			
			FileUtils.writeByteArrayToFile(file, buffer);
		}
		// playType이 WAV일때
		else {
			file = new File(Paths.get(handler.getRootLocalPath(), handler.getCommonAudioPath(), vocId + GlobalConstants.VoiceTypes.US + ".wav").toString());
		}
		
		tempFile = new File(Paths.get(handler.getRootLocalPath(), "/temp/temp.mp3").toString());
		Utils.wavToMp3(file, tempFile);
		
		return tempFile.getAbsolutePath();
	}
	
	
	
	// WAV 파일 미리듣기 시 임시(temp) wav파일 mp3 저장
	public void uplaodWavTemp(VoiceInfoVO request) {
		handler.uploadWavTemp(request.getWavFile());
	}
	
	*/
	
	/*
	
	// 임시 mp3파일 경로 반환
	private String tempVoicePreview(RequestParams<?> requestParams, HttpServletResponse response) throws Exception {
		return Paths.get(handler.getRootLocalPath(), "/temp/wav_temp.mp3").toString();
	}
	
	private String videoPreview(RequestParams<?> requestParams, HttpServletResponse response) {
		String fileType = requestParams.getString("fileType");
		String vdoId	= requestParams.getString("vdoId");
		String path 	= null;
		File file		= null;
		switch(fileType) {
		case "AV001" :
			path = handler.getRootLocalPath() + "/video/" + vdoId + ".mp4";
			break;
		case "AV002" :
			path = handler.getRootLocalPath() + "/video/" + vdoId + ".jpg";
			break;
		}
		
		file = new File(Paths.get(path).toString());
		
		return file.getAbsolutePath();
	}
	
	// 행선지안내기 이미지 미리보기
	private String bmpPreview(RequestParams<?> requestParams, HttpServletResponse response) {
		String fileNameHeader = destiService.getHeader(requestParams.getString("dvcKindCd")).getTxtVal2();
		//String userWayDiv = requestParams.getString("userWayDiv");
		//String userWayCode = DLCDMapper.SM0105G2S2(userWayDiv);
		String fileNameBody = requestParams.getString("dvcName");
		String fileNameTail = ".BMP";
		String fileName = "";
		
		String dvcCd = requestParams.getNotEmptyString("dvcKindCd");
		fileName = fileNameHeader + fileNameBody + fileNameTail;
		return Paths.get(handler.getRootLocalPath(), "temp/destination/", dvcCd, "/", fileName).toString();
	}
	
	private String bmpPreviewLOGO(RequestParams<?> requestParams, HttpServletResponse response) {
		String fileNameBody = requestParams.getString("dvcName");
		String fileNameTail = ".BMP";
		String loc = requestParams.getString("dvcKind");
		String dvcKindCd = requestParams.getString("dvcKindCd");
		String fileName = loc + fileNameBody + fileNameTail;
		return Paths.get(handler.getRootLocalPath(), "temp/destination/", dvcKindCd + "/" , fileName).toString();
	}
	
	private String pngPreview(RequestParams<?> requestParams, HttpServletResponse response) {
		String setId = requestParams.getString("setId");
		String fileType = requestParams.getString("fileType");
		String fileNameTail = ".png";
		
		String fileName = fileType + fileNameTail;
		return Paths.get(handler.getRootLocalPath(), "template/", setId, "/" , fileName).toString();
	}
	
	*/
	
	
	public List selectMultiFileList(String fileId)throws Exception
	{
		return fileMapper.selectMultiFileList(fileId);
		
	}
	
	List selectMultiImageFileList()throws Exception
	{
		return fileMapper.selectMultiImageFileList(getSimpleParameters());
	}
	
	List selectMultiFileView()throws Exception
	{
		return fileMapper.selectMultiFileView(getSimpleParameters());
	}
	
	List selectMultiFileViewLast()throws Exception
	{
		return fileMapper.selectMultiFileViewLast(getSimpleParameters());
	}
	
	public void  insertMultiFile(Map<String, Object> fileUpload)throws Exception
	{
		fileMapper.insertMultiFile(fileUpload);
	}
	
	public void  updateMultiFileDelYN(Map<String, Object> deleteMultiFile)throws Exception
	{
		fileMapper.updateMultiFileDelYN(deleteMultiFile);
	}
	
	void  deleteMultiFile(Map<String, Object> deleteMultiFile)throws Exception
	{
		fileMapper.deleteMultiFile(deleteMultiFile);
	}
	
	void  updateMultiFileCn()throws Exception
	{
		fileMapper.updateMultiFileCn(getSimpleParameters());
	}
	
	public String selectNextMultiFileID()throws Exception
	{
		return fileMapper.selectNextMultiFileID();
	}
	
	public String selectNextMultiFileSN(String fileId)throws Exception
	{
		return fileMapper.selectNextMultiFileSN(fileId);
	}
	
	public void doMoveFile(String sourcePath, String destPath, String sourceFileName, String destFileName) throws Exception
	{
		if (sourceFileName == null||"".equals(sourceFileName))
		{
			throw new IllegalArgumentException("파일이름이 유효하지 않습니다.");
		}
		sourcePath = getRootLocalPath() + sourcePath;
		destPath = getRootLocalPath() + destPath;
		
		File fileSourcePath = new File(sourcePath);
		if (!fileSourcePath.exists())
		{
			fileSourcePath.mkdirs();
		}
		
		File fileDestPath = new File(destPath);
		if (!fileDestPath.exists())
		{
			fileDestPath.mkdirs();
		}
		
		String strSourcePathFile = (sourcePath + sourceFileName).replace("/", File.separator);
		Path moveSourcePath = Paths.get(strSourcePathFile);
		
		String strDestPathFile = (destPath + destFileName).replace("/", File.separator);
		Path moveDestPath = Paths.get(strDestPathFile);

		Files.move(moveSourcePath, moveDestPath, REPLACE_EXISTING);
	}
	
	public String getRootLocalPath() {
		if(SystemUtils.IS_OS_WINDOWS) {
			return ROOT_WINDOWS_LOCAL_PATH;
		} else if(SystemUtils.IS_OS_LINUX) {
			return ROOT_LINUX_LOCAL_PATH;
		} else {
			return ROOT_LINUX_LOCAL_PATH;
		}
	}
	
	//파일 경로가 실제 존재하는지 체크
	public Map doCheckFile() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_fileinfo");
		String strFilePath = map.get("FILE_PATH").toString();
		
		String strDestPath = (getRootLocalPath() + strFilePath.replace("/fileUpload/", "")).replace("/", File.separator);
		File fileDestPath = new File(strDestPath);
		
		Map result = new HashMap();
		if(fileDestPath.exists()) {
			result.put("CHK", "1"); 
		} else {
			result.put("CHK", "0");
		}
		return result;
	}	
}
