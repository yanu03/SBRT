package kr.tracom.bms.ftp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

import kr.tracom.bms.domain.PI0503.PI0503Mapper;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.util.Constants;

@Component
public class FTPHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FTPHandler.class);
	
	
	@Value("${sftp.remote.directory}")
	private String ROOT_SERVER_PATH;
	
	@Value("${sftp.linux.local.directory}")
	private String ROOT_LINUX_LOCAL_PATH;
	
	@Value("${sftp.windows.local.directory}")
	private String ROOT_WINDOWS_LOCAL_PATH;
	
	@Value("${sftp.employee.directory}")
	private String COMMON_EMPLOYEE_PATH;
	
	@Value("${sftp.audio.directory}")
	private String COMMON_AUDIO_PATH;
	
	@Value("${sftp.route.audio.directory}")
	private String ROUTE_AUDIO_PATH;
	
	@Value("${sftp.route.directory}")
	private String ROUTE_PATH;
	
	@Value("${sftp.destination.directory}")
	private String DESTINATION_PATH;
	
	@Value("${sftp.destination.images}")
	private String DESTINATION_IMAGES_PATH;
	
	@Value("${sftp.destination.list}")
	private String DESTINATION_LIST_PATH;
	
	@Value("${sftp.routeori.directory}")
	private String ROUTE_ORI;
	
	@Value("${sftp.device.firmware.directory}")
	private String DEVICE_FIRMWARE_PATH;
	
	@Value("${sftp.vehicle.directory}")
	private String VEHICLE_PATH;
	
	@Value("${sftp.device.directory}")
	private String DEVICE_PATH;
	
	@Value("${sftp.device.config.directory}")
	private String DEVICE_CONFIG_PATH;
	
	@Value("${sftp.device.passenger.directory}")
	private String DEVICE_PASSENGER_PATH;
	
	@Value("${sftp.device.elecrouter.directory}")
	private String DEVICE_ELECROUTER_PATH;
	
	@Value("${sftp.device.log.directory}")
	private String DEVICE_LOG_PATH;
	
	@Value("${sftp.playlist.directory}")
	private String PLAYLIST_PATH;
	
	@Value("${sftp.video.directory}")
	private String VIDEO_PATH;
	
	//2021 선택음성
	@Value("${sftp.common.selectedAudio}")
	private String SELECTED_AUDIO_PATH;
	
	//2021 실내led
	@Value("${sftp.common.innerLED}")
	private String INNER_LED_PATH;
	
	
	@Value("${sftp.host}")
    private String sftpHost;
 
    @Value("${sftp.port}")
    private int sftpPort;
 
    @Value("${sftp.user}")
    private String sftpUser;
 
    @Value("${sftp.password}")
    private String sftpPasword;	
	
	//@Autowired
	//private ChannelSftp sftpChannel;
    private ChannelSftp sftp = null;
	
	@Autowired
	private PI0503Mapper pi0503Mapper;
	
	
	private ArrayList<String> serverContentList;
	private ArrayList<String> pathList;
	private final List<String> ignoreList = Arrays.asList("temp", "chime", "video");
	
	
	private ChannelSftp sftpChannel() {
		
		try {
			
			if(sftp == null) {
				LOGGER.info("sftpChannel is null. create SFTP Channel");
				sftp = SftpManager.getSftpChannel(sftpHost, sftpPort, sftpUser, sftpPasword);				
			} else {
				
				if(!sftp.isConnected()) {
					
					LOGGER.info("sftpChannel is not connected. connecting...");
					
					sftp.connect();
				}				
			}  
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sftp;
		
	}
	
	
	
	//PI0503 영상예약
	public void reserveVideo(Map<String, Object> param, List<Map<String, Object>> playList) throws Exception {
		
		
		String impId = (String)param.get("IMP_ID");
		String dvcId = (String)param.get("DVC_ID");
		String orgaId = (String)param.get("ORGA_ID");
		
		
		String videoPath = "/vehicle/" + impId + "/device/" + dvcId + "/playlist";
		String path = Paths.get(getRootLocalPath(), videoPath).toString();
		String fromPath = Paths.get(getRootLocalPath(), "/video").toString();
		String toPath = Paths.get(getRootLocalPath(), "/vehicle", "/", impId, "/device/passenger").toString();
		
		String fPath = getRootServerPath() + "/vehicle/" + impId + "/device/passenger";
		String vfPath = getRootServerPath() + "/vehicle/" + impId + "/device/" + dvcId + "/playlist";
		
		File dir = new File(toPath);
		File listDir = new File(path);

		if(!dir.isDirectory()) {
			dir.mkdirs();
		}

		if(!listDir.isDirectory()) {
			listDir.mkdirs();
		}
		
		
		//파일을 복사할 로컬 디렉토리 내 파일 삭제 
		deleteFiles(toPath);
		

		createFtpDirectory(fPath);
		createFtpDirectory(vfPath);


		String txt = Constants.CSVForms.VIDEO_PLAY_LIST;


		for(int i = 0; i < playList.size(); i++) {
			
			String videoType = String.valueOf(playList.get(i).get("VIDEO_TYPE"));
			String videoFile = String.valueOf(playList.get(i).get("VIDEO_FILE"));
			String playStDt = String.valueOf(playList.get(i).get("PLAY_ST_DT"));
			String playEdDt = String.valueOf(playList.get(i).get("PLAY_ED_DT"));
			String runTime = String.valueOf(playList.get(i).get("RUN_TIME"));
			
			
			String row = Constants.CSVForms.ROW_SEPARATOR
					+ (i+1) + Constants.CSVForms.COMMA
					+ videoType + Constants.CSVForms.COMMA
					+ videoFile + Constants.CSVForms.COMMA
					+ playStDt + Constants.CSVForms.COMMA
					+ playEdDt + Constants.CSVForms.COMMA
					+ runTime;
			txt += row;

			File fFile = new File(fromPath + "/" + videoFile);
			File tFile = new File(toPath + "/" + videoFile);

			copyFile(fFile, tFile);

		}

		File file = new File(path + "/playlist.csv");


		try {
			createCSV(file, txt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//디렉토리 내 파일 삭제 
	public void deleteFiles(String dirPath) {
		
		File deleteFolder = new File(dirPath);
		
		//해당 폴더 내 파일들만 삭제
		if(deleteFolder.exists()){
			File[] deleteFolderList = deleteFolder.listFiles();
			
			for (int j = 0; j < deleteFolderList.length; j++) {
				deleteFolderList[j].delete(); 
			}
		}
		
	}
	
	
	public void copyFile(File fFile, File tFile) throws IOException {
		FileUtils.copyFile(fFile, tFile);
	}	
	
	public static void createCSV(File file, String content) throws Exception {
		//FileUtils.writeStringToFile(file, content, Charsets.ISO_8859_1);
		FileUtils.writeStringToFile(file, content, Charset.forName("CP949").name());
	}	
	
	//영상 예약 싱크
	public void syncVdoFile(String impId, String dvcId) throws Exception {
		
		String videoPath = "/vehicle/" + impId + "/device/" + dvcId + "/playlist";
		String path = Paths.get(getRootLocalPath(), videoPath).toString();
		String toPath = Paths.get(getRootLocalPath(), "/vehicle", "/", impId, "/device/passenger").toString();
		String fPath = getRootServerPath() + "/vehicle/" + impId + "/device/passenger";
		
		String vfPath = getRootServerPath() + "/vehicle/" + impId + "/device/" + dvcId + "/playlist";
		
		processSynchronize(toPath, fPath);
		processSynchronize(path, vfPath);
	}
	
	
	// 서버FTP와 Synchronization
	private void processSynchronize(String localPath, String serverPath) throws Exception {
		LOGGER.info("fileSync: {}, {}", localPath, serverPath);
		
		setServerDirectory(localPath, serverPath);
		synchronize(new File(localPath), serverPath);
	}
	
	
	/************************ FTP 공통 모듈 ****************************************/
	private void createFtpDirectory(String ftpPath) throws SftpException {
		// 최상위 폴더 이동
		sftpChannel().cd("/");
		try{
			sftpChannel().cd(ftpPath);
		} catch(Exception e){
			System.out.println(ftpPath + " don't exist on your server!");
			
			String[] pathList = ftpPath.split("/");
			
			for(int i = 1; i < pathList.length; i++) {
				String path = pathList[i];
				
				SftpATTRS attrs = null;
				
				try {
				    attrs = sftpChannel().stat(path);
				} catch (Exception ee) {
				    System.out.println(path + " not found");
				}

				if (attrs != null) {
				    System.out.println("Directory exists IsDir=" + attrs.isDir());
				} else {
				    System.out.println("Creating dir " + path);
				    sftpChannel().mkdir(path);
				}
				sftpChannel().cd(path);
			}
		}
	}
	
	// 초기 디렉토리 셋팅
	private void setServerDirectory(String localPath, String serverPath) throws SftpException {
		createFtpDirectory(serverPath);
		
		File localDirectory = new File(localPath);
		String serverFolder = serverPath.substring(serverPath.lastIndexOf('/') + 1, serverPath.length());
		if(!localDirectory.getName().equals(serverFolder)){
			try{
				sftpChannel().mkdir(localDirectory.getName());
				sftpChannel().cd(localDirectory.getName());
			} catch (Exception e){
				sftpChannel().cd(localDirectory.getName());
			}
			serverPath = serverPath + "/" + localDirectory.getName();
		}
		
		serverContentList = new ArrayList<String>();
		pathList = new ArrayList<String>();
	}
	
	// 서버 폴더 내 목록 가져오기
	public void setToContentList(String serverFolder) throws SftpException{
		@SuppressWarnings("unchecked")
		Vector<LsEntry> fileList = sftpChannel().ls(serverFolder);
		int size = fileList.size();
		for(int i = 0; i < size; i++){
			if(!fileList.get(i).getFilename().startsWith(".")){
				serverContentList.add(fileList.get(i).getFilename());
				pathList.add(serverFolder);
			}
		}
	}
	
	/*
	 * Deletes the synchronized elements from the Lists
	 */
	public void deleteFromLists(String name){
		int	position = serverContentList.lastIndexOf(name);
				
		if(position >= 0){	
			serverContentList.remove(position);
			pathList.remove(position);
		}
	}
	
	/*
	 * FTP synchronization 
	 */
	@SuppressWarnings("unchecked")
	private void synchronize(File localFolder, String serverDir) throws SftpException, FileNotFoundException{
		if(sftpChannel().pwd() != serverDir){
			sftpChannel().cd(serverDir);
		}
		setToContentList(serverDir);
		
		// 로컬 폴더 파일 리스트
		File[] localList = localFolder.listFiles();
		
		/********************************** 로컬에 없는 폴더 및 파일 FTP서버에서 삭제 **************************************/
		Vector<LsEntry> serverList = sftpChannel().ls(serverDir);
		
		serverList = serverList.stream().filter(l -> {
			return !l.getFilename().equals("..") && !l.getFilename().equals(".");
		}).collect(Collectors.toCollection(Vector::new));
		
		
		for(int i = 0; i < localList.length; i++){
			final int index = i;
			serverList = serverList.stream()
							.filter(l ->  !l.getFilename().equals(localList[index].getName()))
							.collect(Collectors.toCollection(Vector::new));
		}
		
		// 로컬 디렉토리에 없는 파일이 FTP서버에 존재할 경우 삭제
		serverList.forEach(l -> {
			String path = serverDir + "/" + l.getFilename();
			try {
				if(sftpChannel().stat(path).isDir()) {
					recursiveDirectoryDelete(serverDir + "/" + l.getFilename());
				} else {
					sftpChannel().rm(serverDir + "/" + l.getFilename());
				}
			} catch (SftpException e) {
				e.printStackTrace();
			}
		});
		//*/
		/*******************************************************************************************************/
		
		for(int i = 0; i < localList.length; i++) {
			// 동기화하지 않을 파일일 경우 continue
			if(checkIgnoreFile(localList[i].getName())) {
				continue;
			}
			
			if(localList[i].isDirectory()){
				if(!checkFolder(localList[i], serverDir)) {
					newFileMaster(localList[i], serverDir);
				}
				
				// 재귀 돌면서 디렉토리 구조 동기화
				synchronize(localList[i], serverDir + "/" + localList[i].getName());
			} else {
				checkFile(localList[i], serverDir);
			}
			deleteFromLists(localList[i].getName());
		}
	}
	
	/*
	 * 해당 경로에 파일 덮어쓰기
	 */
	private void newFileMaster(File sourcePath, String destPath) throws FileNotFoundException, SftpException {
    	try {
    		sftpChannel().cd(destPath);
    		if(sourcePath.isDirectory()) {
    			sftpChannel().mkdir(sourcePath.getName());
    		} else {
	    		BufferedInputStream ios = new BufferedInputStream(new FileInputStream(sourcePath));
	        	sftpChannel().put(ios, sourcePath.getName());
				ios.close();
    		}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 *  해당 폴더 및 하위 내용 모두 삭제
	 */
	@SuppressWarnings("unchecked")
	private void recursiveDirectoryDelete(String path) throws SftpException {
	    Collection<ChannelSftp.LsEntry> fileAndFolderList = sftpChannel().ls(path);

	    for (ChannelSftp.LsEntry item : fileAndFolderList) {
	        if (!item.getAttrs().isDir()) {
	            sftpChannel().rm(path + "/" + item.getFilename());
	        } else if (!(".".equals(item.getFilename()) || "..".equals(item.getFilename()))) {
	            try {
	            	sftpChannel().rmdir(path + "/" + item.getFilename());
	            } catch (Exception e) { 
	            	recursiveDirectoryDelete(path + "/" + item.getFilename());
	            }
	        }
	    }
	    sftpChannel().rmdir(path);
	}
	
	/*
	 * FTP 서버에 파일이 있는지 검사 후 없으면 생성 있으면 수정 날짜 비교 후 덮어쓰기
	 */
	private void checkFile(File file, String path) throws SftpException, FileNotFoundException{
		sftpChannel().cd(path);
		
		if(!serverContentList.contains(file.getName())){
			newFileMaster(file, path);
		} else {
			Long localTimeStamp = file.lastModified();
			Long timeStamp =  sftpChannel().stat(file.getName()).getMTime() * 1000L;
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date localDate = null;
			Date remoteDate = null;
			try {
				localDate = format.parse(format.format(localTimeStamp));
				remoteDate = format.parse(format.format(timeStamp));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			if(localDate.compareTo(remoteDate) == 1){
				newFileMaster(file, path);
			}
		}
		deleteFromLists(file.getName());
	}
	
	/*
	 * 해당 경로의 폴더가 있는지 검사
	 */
	private boolean checkFolder(File folder, String path) throws SftpException{
		sftpChannel().cd(path);
		
		if(serverContentList.contains(folder.getName())){
			return true;
		} else { 
			return false;
		}
	}
	
	private boolean checkIgnoreFile(String fileName) {
		boolean check = false;
		try {
			for(String ignoreFile : ignoreList) {
				if(fileName.contains(ignoreFile)) {
					check = true;
					break;
				}
			}
			
			return check;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return check;
	}
	
	/*********************************************************************************************/
	
	
	
	public String getRootServerPath() {
		return ROOT_SERVER_PATH;
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
	
	public String getCommonAudioPath() {
		return COMMON_AUDIO_PATH;
	}
	
	public String getRouteAudioPath() {
		return ROUTE_AUDIO_PATH;
	}
	
	public String getRoutePath() {
		return ROUTE_PATH;
	}
	
	public String getRouteOriPath() {
		return ROUTE_ORI;
	}
	
	public String getDestinationPath() {
		return DESTINATION_PATH;
	}
	
	public String getDestinationImagesPath() {
		return DESTINATION_IMAGES_PATH;
	}
	
	public String getDestinationListPath() {
		return DESTINATION_LIST_PATH;
	}
	
	public String getCommonEmployeePath() {
		return COMMON_EMPLOYEE_PATH;
	}
	
	public String getFirmwarePath() {
		return DEVICE_FIRMWARE_PATH;
	}
	
	public String getVehiclePath() {
		return VEHICLE_PATH;
	}
	
	public String getDevicePath() {
		return DEVICE_PATH;
	}
	
	public String getDeviceConfigPath() {
		return DEVICE_CONFIG_PATH;
	}
	
	public String getDevicePassengerPath() {
		return DEVICE_PASSENGER_PATH;
	}
	
	public String getDeviceElecRouterPath() {
		return DEVICE_ELECROUTER_PATH;
	}
	
	public String getDeviceLogPath() {
		return DEVICE_LOG_PATH;
	}
	
	public String getPlayListPath() {
		return PLAYLIST_PATH;
	}
	
	public String getVideoPath() {
		return VIDEO_PATH;
	}
	
	//2021 선택음성
	public String getSelectedAudioPath() {
		return SELECTED_AUDIO_PATH;
	}
	
	//2021 실내led
	public String getInnerLEDPath() {
		return INNER_LED_PATH;
	}
	
	
}
