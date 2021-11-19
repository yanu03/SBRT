package kr.tracom.bms.ftp;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

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
import java.util.HashMap;
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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

import kr.tracom.bms.domain.PI0206.PI0206Mapper;
import kr.tracom.bms.domain.PI0503.PI0503Mapper;
import kr.tracom.cm.domain.Common.CommonMapper;
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
	
	@Value("${sftp.routemap.directory}")
	private String ROUTEMAP;
	
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
	
	@Autowired
	private CommonMapper commonMapper;
	
	@Autowired
	private PI0206Mapper pi0206Mapper;
	
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
	
	
	/*
	// 승무사원 관리 승무사원 사진 업로드
	public void uploadSI0300(String fileName, MultipartFile file) {
		String dir1 = Paths.get(getRootLocalPath(), getCommonEmployeePath()).toString();
		
		File saveFile = Paths.get(dir1, fileName).toFile();
		try {
			FileUtils.writeByteArrayToFile(saveFile, file.getBytes());
			
			processSynchronize(getRootLocalPath() + getCommonEmployeePath(), getRootServerPath() + getCommonEmployeePath());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	*/
	
	// 승무사원 관리 승무사원 사진 업로드 to FTP
	public void uploadSI0300() {
		try {
			processSynchronize(getRootLocalPath() + getCommonEmployeePath(), getRootServerPath() + getCommonEmployeePath());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//VD0203 펌웨어파일 업로드
	public void uploadVD0203(String mngId, String srcPath, String srcFileName, String fileExt) {
		
		String ext = fileExt;
		String destPath;
		String destFileName;
		
		
		if(mngId.length() >= Constants.IMP_ID_DIGIT) {
			
			String impId = mngId.substring(0, Constants.IMP_ID_DIGIT);
			
			//가지고온 관리id값이 통플인지 아닌지 비교
			if(mngId.length() == Constants.IMP_ID_DIGIT) {
				//통합플랫폼
				destPath = "/vehicle/" + impId + "/firmware/";
				destFileName = "firmware." + ext;				
			} else {
				
				String dvcId = mngId.substring(Constants.IMP_ID_DIGIT);
				destPath = "/vehicle/" + impId + "/device/" + dvcId + "/firmware/";
				
				//행선지안내기
				if(mngId.substring(Constants.IMP_ID_DIGIT, 12).equals("RD")) {
					destFileName = "SF2016." + ext.toUpperCase();
					//키패드
				}else if(mngId.substring(Constants.IMP_ID_DIGIT, 12).equals("RK")){
					destFileName = "MANAGERV3." + ext.toUpperCase();
					//다른장비
				}else if(mngId.substring(Constants.IMP_ID_DIGIT, 12).equals("ED")) {
					destFileName = "firmware.dat";
				}
				else {
					destFileName = "firmware." + ext;
				}			
				
			}
			
			
			try {
				//doMoveFile(srcPath, destPath, srcFileName, destFileName);
				doCopyFile(srcPath, destPath, srcFileName, destFileName);
				
				processSynchronize(getRootLocalPath() + destPath, getRootServerPath() + destPath);
			} catch(Exception e){
				e.printStackTrace();
			}
			
			
		} else {
			
		}
		
		
		
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
	
	
	
	/*2021 신버전*/
	//PI0702 행선지안내기 예약(파일이동)
	public void reserveDst(Map<String, Object> routeInfo, List<Map<String, Object>> dlist) throws Exception {
		
		//String routeNm = String.valueOf(routeInfo.get("ROUT_NM"));
		//String wayDiv = String.valueOf(routeInfo.get("TXT_VAL1")); //U or D
		//String dvcName =  routeNm + wayDiv; //노선이름+U or D
		
		String dvcName = String.valueOf(routeInfo.get("DVC_NM")); 
		
		
		for(Map<String, Object> map : dlist) {
			
			String impId = String.valueOf(map.get("IMP_ID"));
			String dlCd =  String.valueOf(map.get("DL_CD"));
			
			
			String localPath = Paths.get(getRootLocalPath(), "temp/destination/", dlCd).toString();
			String localPath2 = Paths.get(getRootLocalPath(), "vehicle/", impId, "/device/destination/images/").toString();
			//String ftpPath = getRootServerPath() + "/vehicle/"+ dcvo.getImpId() +"/device/destination/images/";
			
			File temp = new File(localPath2);
			
			//destination폴더 없으면 생성
			if(!temp.isDirectory()) {
				temp.mkdirs();
			}
			
			//images폴더 없으면 생성
			temp = new File(localPath2);
			if(!temp.isDirectory()) {
				temp.mkdirs();
			}
			
			String fileNameHeader = String.valueOf(map.get("TXT_VAL2"));
			String fileNameBody = dvcName;
			String bmpExt = ".BMP";
			String schExt = ".SCH";
					
			// 로고파일 복사
			String logoBmpName = fileNameHeader + "LOGO" + bmpExt;
			String logoSchName = fileNameHeader + "LOGO" + schExt;
			
			File logoBmpFile1 = new File(localPath + "/" + logoBmpName);
			File logoSchFile1 = new File(localPath + "/" + logoSchName);
			
			File logoBmpFile2 = new File(localPath2 + "/" + logoBmpName);
			File logoSchFile2 = new File(localPath2 + "/" + logoSchName);
			
			if(logoBmpFile1.exists()) {
				//복사
				copyFile(logoBmpFile1, logoBmpFile2);
			}else {}
			
			if(logoSchFile1.exists()) {
				//복사
				copyFile(logoSchFile1, logoSchFile2);
			}else {}
			
			File bmp1 = new File(localPath + "/" + fileNameHeader + fileNameBody + bmpExt);
			File bmp2 = new File(localPath2 + "/" + fileNameHeader + fileNameBody + bmpExt);
			File sch1 = new File(localPath + "/" + fileNameHeader + fileNameBody + schExt);
			File sch2 = new File(localPath2 + "/" + fileNameHeader + fileNameBody + schExt);
			
			if(bmp1.exists()) {
				copyFile(bmp1, bmp2);
			}else {}
			
			if(sch1.exists()) {
				copyFile(sch1, sch2);
			}else {}
		}
	}
	
	
	//PI0702 행선지안내기 예약(list 생성)
	public void makeDstConfig(List<Map<String, Object>> vhcList, List<Map<String, Object>> routeInfoList) throws Exception {
		
		//for(VHCInfoVO vhcVo : vhcVOList) {
		for(Map<String, Object> vhc : vhcList) {
			
			String txt = "";
			String mngId = String.valueOf(vhc.get("MNG_ID"));
			String impId = mngId.substring(0, Constants.IMP_ID_DIGIT);
			String localPath2 = Paths.get(getRootLocalPath(), "vehicle/", impId, "/device/destination/images/").toString();
			File fBmpFile = new File(localPath2 + "/FLOGO.BMP");
			if(fBmpFile.exists()) {
				txt +=	"FLOGO.BMP" + Constants.CSVForms.COMMA + "A" + Constants.CSVForms.ROW_SEPARATOR + 
						"FLOGO.SCH" + Constants.CSVForms.COMMA + "A";
				
			}else {}
			File sBmpFile = new File(localPath2 + "/SLOGO.BMP");
			if(sBmpFile.exists()) {
				txt +=	Constants.CSVForms.ROW_SEPARATOR +
						"SLOGO.BMP" + Constants.CSVForms.COMMA + "A" + Constants.CSVForms.ROW_SEPARATOR +
						"SLOGO.SCH" + Constants.CSVForms.COMMA + "A";
			}else {}

			
			for(Map<String, Object> routeInfo : routeInfoList) {
				
				String routeNm = String.valueOf(routeInfo.get("ROUT_NM"));
				String wayDiv = String.valueOf(routeInfo.get("TXT_VAL1")); //U or D
				String dvcName =  routeNm + wayDiv; //노선이름+U or D
				
				File rearFile = new File(localPath2 + "/R" + dvcName + ".BMP");
				
				txt +=  Constants.CSVForms.ROW_SEPARATOR + "F" + dvcName + ".BMP" + Constants.CSVForms.COMMA + "A" + 
						Constants.CSVForms.ROW_SEPARATOR + "F" + dvcName + ".SCH" + Constants.CSVForms.COMMA + "A" +
						Constants.CSVForms.ROW_SEPARATOR + "S" + dvcName + ".BMP" + Constants.CSVForms.COMMA + "A" + 
						Constants.CSVForms.ROW_SEPARATOR + "S" + dvcName + ".SCH" + Constants.CSVForms.COMMA + "A";
				
				if(rearFile.exists()) {
					txt += Constants.CSVForms.ROW_SEPARATOR + "R" + dvcName + ".BMP" + Constants.CSVForms.COMMA + "A" +
							Constants.CSVForms.ROW_SEPARATOR + "R" + dvcName + ".SCH" + Constants.CSVForms.COMMA + "A";
				}				
				
    		}
			
			
			String listLocalPath = Paths.get(getRootLocalPath(), "/vehicle/", impId, "/device/destination/").toString();
			String listFTPPath = getRootServerPath() + "/vehicle/" + impId + "/device/destination/";
			File localList = new File(listLocalPath + "/list/" + "list.csv");
			
			createCSV(localList, txt);
			processSynchronize(listLocalPath, listFTPPath);
			
		}

	}
	
	/** 210826 행선지안내기 스케쥴 파일 Read jh **/
	public List<Map<String, Object>> readSCH(String deviceCd, String fileName) throws IOException {
		String path = Paths.get(getRootLocalPath(), "/temp/destination/", deviceCd).toString();
		File file = new File(path + "/" + fileName);
		FileReader fr = null;
		List<Map<String, Object>> scheduleList = new ArrayList<>();

		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			createSCH(deviceCd, fileName);
			fr = new FileReader(file);
		}
        //입력 버퍼 생성
        BufferedReader br = new BufferedReader(fr);
        String line = "";
        String[] tmp = null;
        
        while((line = br.readLine()) != null){
        	tmp = line.split("\t");
        	Map<String, Object> map = new HashMap<>();
        	Map<String, Object> param = new HashMap<>();
        	param.put("COL", "DL_CD");
        	param.put("CO_CD", "EFFECT_TYPE");
        	param.put("COL3", "TXT_VAL1");
        	param.put("COL_VAL3", tmp[1]);
        	
        	map.put("FRAME_NO", tmp[0]);
        	map.put("EFFECT_TYPE", commonMapper.selectDlCdCol(param));
        	map.put("EFFECT_SPEED", tmp[2]);
        	map.put("SHOW_TIME", tmp[3]);
        	
        	scheduleList.add(map);
        }
        br.close();
        return scheduleList;
	}
	
	/** 210826 행선지안내기 스케쥴 파일 create jh **/
	public boolean createSCH(String deviceCd, String fileName) {
		List<Map<String, Object>> scheduleList = new ArrayList<>();
		int max = 10;
		
		if(fileName.contains("LOGO")) {
			max = 3;
		}
		
		for(int i = 0; i < max; i ++) {
			Map<String, Object> scheduleRow = new HashMap<>();
			int seq = i + 1;
			
			scheduleRow.put("FRAME_NO", "FRAME" + seq);
			scheduleRow.put("EFFECT_TYPE", "ET001");
			scheduleRow.put("EFFECT_SPEED", "05");
			scheduleRow.put("SHOW_TIME", "0000");
			
			scheduleList.add(scheduleRow);
			
		}
		
		return writeSCH(scheduleList, deviceCd, fileName);
	}
	
	/** 210826 행선지안내기 스케쥴 파일 write jh**/
	public boolean writeSCH(List<Map<String, Object>> scheduleList, String deviceCd, String fileName) {
		String path = Paths.get(getRootLocalPath(), "/temp/destination/", deviceCd).toString();
		String txt = "";

		for(int i = 0; i < scheduleList.size(); i++) {
			Map<String, Object> param = new HashMap<>();
			param.put("COL", "TXT_VAL1");
			param.put("CO_CD", "EFFECT_TYPE");
			param.put("COL3", "DL_CD");
			param.put("COL_VAL3", scheduleList.get(i).get("EFFECT_TYPE").toString());
			if(i == 0) {
				txt += scheduleList.get(i).get("FRAME_NO") + Constants.Schedule.TAB 
					+ commonMapper.selectDlCdCol(param) + Constants.Schedule.TAB 
					+ String.format("%02d", Integer.valueOf(scheduleList.get(i).get("EFFECT_SPEED").toString())) + Constants.Schedule.TAB 
					+ String.format("%04d", Integer.valueOf(scheduleList.get(i).get("SHOW_TIME").toString()));
			}else {
				txt += Constants.CSVForms.ROW_SEPARATOR
					+ scheduleList.get(i).get("FRAME_NO") + Constants.Schedule.TAB 
					+ commonMapper.selectDlCdCol(param) + Constants.Schedule.TAB 
					+ String.format("%02d", Integer.valueOf(scheduleList.get(i).get("EFFECT_SPEED").toString())) + Constants.Schedule.TAB 
					+ String.format("%04d", Integer.valueOf(scheduleList.get(i).get("SHOW_TIME").toString()));
			}
		}
		
		File file = new File(path + "/" + fileName);
		
		try {
			createCSV(file, txt);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	//PI0902 전자노선도 예약 jhlee
	public void reserveErm(Map<String, Object> vhcData, Map<String, Object> rpData) throws Exception {
		//관리아이디 길이가 16이 아니면
		if(vhcData.get("MNG_ID").toString().length() != 16) {
			return;
		}
		
		String impId = vhcData.get("MNG_ID").toString().substring(0, 10);
		String dvcId = vhcData.get("MNG_ID").toString().substring(10, 16);
		
		
		String localPath = Paths.get(getRootLocalPath(), "/vehicle/", impId, "/device/" + dvcId, "/config").toString();
		String ftpPath	 = getRootServerPath() + "/vehicle/" + impId + "/device/" + dvcId + "/config";
		
		File dir = new File(localPath);
		
		if(!dir.isDirectory()) {
			dir.mkdirs();
		}
		
		createFtpDirectory(ftpPath);
		
		String header = Constants.CSVForms.ELEC_ROUTER;
		String row = Constants.CSVForms.ROW_SEPARATOR
				 + rpData.get("TIME_KO") + Constants.CSVForms.COMMA
				 + rpData.get("TIME_EN") + Constants.CSVForms.COMMA
				 + rpData.get("CATEGORY_VAL") + Constants.CSVForms.COMMA
				 + rpData.get("FRAME_VAL") + Constants.CSVForms.COMMA
				 + rpData.get("FONT_VAL");
		
		File file = new File(localPath + "/config.csv");
		
		try {
			createCSV(file, header + row);
			processSynchronize(localPath, ftpPath);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/** null 이면 공백으로 jh **/
	private String checkNull(Object txt) {
		if(txt == null) {
			return "";
		}
		else {
			if( txt.equals("null") || txt.equals("NULL") ) {
				return "";
			}else {
				return String.valueOf(txt);
			}
		}
	}
	
	/** 210823 busstop.csv jh **/
	public void uploadBusstop(List<Map<String, Object>> stopList, String fileName, String routVer) throws FileNotFoundException, IOException {
		String txt = Constants.CSVForms.ROUTE_VERSION + routVer + Constants.CSVForms.ROW_SEPARATOR;
		txt += Constants.CSVForms.ROUTE_BUSSTOP_TITLE;
		for(Map<String, Object> map : stopList) {
				txt += Constants.CSVForms.ROW_SEPARATOR +
					checkNull(map.getOrDefault("NODE_ID",		"")) + Constants.CSVForms.COMMA +
					checkNull(map.getOrDefault("NODE_NM",		"")) + Constants.CSVForms.COMMA +
					checkNull(map.getOrDefault("NODE_TYPE",		"")) + Constants.CSVForms.COMMA +
					checkNull(map.getOrDefault("RANGE",			"")) + Constants.CSVForms.COMMA +
					checkNull(map.getOrDefault("X",				"")) + Constants.CSVForms.COMMA +
					checkNull(map.getOrDefault("Y",				"")) + Constants.CSVForms.COMMA +
					checkNull(map.getOrDefault("NODE_ENAME",	"")) + Constants.CSVForms.COMMA +
					checkNull(map.getOrDefault("TRANSIT_CODE",	"")) + Constants.CSVForms.COMMA +
					checkNull(map.getOrDefault("DOOR",	""))		 + Constants.CSVForms.COMMA +
					checkNull(map.getOrDefault("LOCATION_INFO",	""));
				
		}
		
		//sbrt\routemap
		String path = Paths.get(getRootLocalPath(), getRouteMapPath()).toString();
		File file = new File(path + "/" + fileName);
		
		try {
			createCSV(file, txt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/** 210824 node.csv jh **/
	public void uploadNodeList(List<Map<String, Object>> nodeList, String fileName, String routVer) throws FileNotFoundException, IOException {
		String txt = Constants.CSVForms.ROUTE_VERSION + routVer + Constants.CSVForms.ROW_SEPARATOR;
		txt += Constants.CSVForms.ROUTE_NODELIST_TITLE;
		
		for(Map<String, Object> map : nodeList) {
				txt += Constants.CSVForms.ROW_SEPARATOR +
					checkNull(map.getOrDefault("NODE_ID", 	"")) 	+ Constants.CSVForms.COMMA +
					checkNull(map.getOrDefault("NODE_NM",	"")) 	+ Constants.CSVForms.COMMA +
					checkNull(map.getOrDefault("NODE_TYPE",	"")) 	+ Constants.CSVForms.COMMA +
					checkNull(map.getOrDefault("RANGE", 	""))	+ Constants.CSVForms.COMMA +
					checkNull(map.getOrDefault("X", 		""))	+ Constants.CSVForms.COMMA +
					checkNull(map.getOrDefault("Y", 		""));
		}
		
		String path = Paths.get(getRootLocalPath(), getRouteMapPath()).toString();
		File file = new File(path + "/" + fileName);
		
		try {
			createCSV(file, txt);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 210824 routelist.csv 파일 read jh **/
	public List<Map<String, Object>> readRoutList(String fileName) throws IOException {
		String path = Paths.get(getRootLocalPath(), getRouteMapPath()).toString();
		
		File file = new File(path + "/" + fileName);
		
		List<Map<String, Object>> list = new ArrayList<>();
		
        //입력 버퍼 생성
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "CP949"));
        String line = "";
        String[] tmp = null;
        
        int lineNum = 0;
        while((line = br.readLine()) != null){
        	lineNum++;
        	
        	if(lineNum <= 2) {
        		continue;
        	}else {        		
        	Map<String, Object> map = new HashMap<>();
        	tmp = line.split(",");
        	
    		map.put("FILE_NAME",	tmp[0]);
    		map.put("ROUT_NM",		tmp[1]);
    		map.put("ROUT_ENM",		tmp[2]);
    		map.put("VERSION",		tmp[3]);
    		map.put("DESTI_NO",		tmp[4]);
    		map.put("ROUT_SHAPE",	tmp[5]);
    		map.put("DAY1",			tmp[6]);
    		map.put("DAY2",			tmp[7]);
    		map.put("SATDAY1",		tmp[8]);
    		map.put("SATDAY2",		tmp[9]);
    		map.put("SUNDAY1",		tmp[10]);
    		map.put("SUNDAY2",		tmp[11]);
    		map.put("REP_NAME",		tmp[12]);
        		
        	list.add(map);
        	}
        }
        br.close();
        
        return list;
	}
	
	
	/** 210824 routelist.csv 업로드 **/
	public void uploadRoutList(String fileName, String routVer, String maxVer, Map<String, Object> newRow) throws IOException{
		String path = Paths.get(getRootLocalPath(), getRouteMapPath()).toString();
		
		File file = new File(path + "/" + fileName);
		List<Map<String, Object>> list = new ArrayList<>();
		
		if(file.exists()) {
			list = readRoutList("routelist.csv");
		}else {
			
		}
		
		if(list.size() > 0) {
			//기존에 정보가 있으면 업데이트, 없으면 인서트하도록 하겠음
			int seq = 0;
			boolean flag = false;
			
			loop:
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i).get("FILE_NAME").equals(newRow.get("FILE_NAME"))) {
					flag = true;
					seq = i;
					break loop;
				}			
			}
			
			if(flag) {
				list.set(seq, newRow);
			}else {
				list.add(newRow);
			}
			
		}else {
			list.add(newRow);
		}
					
		String txt = "";
		txt += Constants.CSVForms.ROUTE_VERSION + maxVer + Constants.CSVForms.ROW_SEPARATOR;
		txt += Constants.CSVForms.ROUTE_LIST;
		
		for(Map<String, Object> map : list) {
			txt += Constants.CSVForms.ROW_SEPARATOR;
			txt += map.get("FILE_NAME")
				+  Constants.CSVForms.COMMA + map.getOrDefault("ROUT_NM", "")
				+  Constants.CSVForms.COMMA + map.getOrDefault("ROUT_ENM", "")
				+  Constants.CSVForms.COMMA + map.getOrDefault("VERSION", "")
				+  Constants.CSVForms.COMMA + map.getOrDefault("DESTI_NO", "")
				+  Constants.CSVForms.COMMA + map.getOrDefault("ROUT_SHAPE", "")
				+  Constants.CSVForms.COMMA + map.getOrDefault("DAY1", "")
				+  Constants.CSVForms.COMMA + map.getOrDefault("DAY2", "")
				+  Constants.CSVForms.COMMA + map.getOrDefault("SATDAY1", "")
				+  Constants.CSVForms.COMMA + map.getOrDefault("SATDAY2", "")
				+  Constants.CSVForms.COMMA + map.getOrDefault("SUNDAY1", "")
				+  Constants.CSVForms.COMMA + map.getOrDefault("SUNDAY2", "")
				+  Constants.CSVForms.COMMA + map.getOrDefault("REP_NAME", "");
		}
		
		try {
			createCSV(file, txt);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	

	/** 20210830 routelist.csv의 해당 rout_id row 삭제 jh **/
	public void deleteRoutList(String routIdDel, String maxVer) throws IOException {
		String path = Paths.get(getRootLocalPath(), getRoutePath()).toString();
		String fileName = "routelist.csv";
		File file = new File(path + "/" + fileName);
		
		List<Map<String, Object>> list = new ArrayList<>();
		
		if(file.exists()) {
			list = readRoutList("routelist.csv");
		}
		
		for(Map<String, Object> map : list) {
			if(routIdDel.equals(map.get("FILE_NAME").toString())) {
				list.remove(map);
				break;
			}
		}

		String txt = "";
		txt += Constants.CSVForms.ROUTE_VERSION + maxVer + Constants.CSVForms.ROW_SEPARATOR;
		txt += Constants.CSVForms.ROUTE_LIST;
		
		for(Map<String, Object> map : list) {
			txt += Constants.CSVForms.ROW_SEPARATOR;
			txt += map.get("FILE_NAME") 
					+ Constants.CSVForms.COMMA + map.get("ROUT_NM")
					+ Constants.CSVForms.COMMA + map.get("ROUT_ENM")
					+ Constants.CSVForms.COMMA + map.get("VERSION") 
					+ Constants.CSVForms.COMMA + map.get("DESTI_NO") 
					+ Constants.CSVForms.COMMA + map.get("ROUT_SHAPE")
					+ Constants.CSVForms.COMMA + map.get("DAY1")
					+ Constants.CSVForms.COMMA + map.get("DAY2")
					+ Constants.CSVForms.COMMA + map.get("SATDAY1")
					+ Constants.CSVForms.COMMA + map.get("SATDAY2")
					+ Constants.CSVForms.COMMA + map.get("SUNDAY1")
					+ Constants.CSVForms.COMMA + map.get("SUNDAY2")
					+ Constants.CSVForms.COMMA + map.get("REP_NAME");
		}
		
		try {
			createCSV(file, txt);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	/** 210824 노선관련 폴더, 파일 삭제 jh **/
	//TODO: 테스트필요
	public void deleteRoutemap(String routId) {
		String path = Paths.get(getRootLocalPath(), getRouteMapPath(), "/routelist", "/" + routId).toString();
		
		File routFolder = new File(path);
		
		if(routFolder.exists()) {
			deleteFolder(path);
		}
	}
	
	/** 210824 노선별 routeinfo.csv jh **/
	public void uploadRoutNodeList(List<Map<String, Object>> routNodeList, String routId, String routVer) {
		String path = Paths.get(getRootLocalPath(), getRouteMapPath()).toString();
		
		String txt = Constants.CSVForms.ROUTE_VERSION + routVer 
				   + Constants.CSVForms.ROW_SEPARATOR;		
		
		txt += Constants.CSVForms.ROUTE_TITLE;
		
		String fileName = "routeinfo.csv";
		
			
		for(Map<String, Object> nodeMap : routNodeList) {
			txt += Constants.CSVForms.ROW_SEPARATOR + nodeMap.get("NODE_ID");
		}
		
		File file = new File(path + "/" + "routelist" + "/" + routId + "/" + fileName);
		
		try {
			createCSV(file, txt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 210824 노선별 link.csv jh **/
	public void uploadRoutLinkList(List<Map<String, Object>> routLinkList, String routId, String routVer) {
		String path = Paths.get(getRootLocalPath(), getRouteMapPath()).toString();
		
		String txt = Constants.CSVForms.ROUTE_VERSION + routVer 
				   + Constants.CSVForms.ROW_SEPARATOR;		
		
		txt += Constants.CSVForms.ROUTE_LINK_TITLE;
		
		String fileName = "link.csv";
		
		for(Map<String, Object> linkMap : routLinkList) {
			txt += Constants.CSVForms.ROW_SEPARATOR + linkMap.get("LINK_ID")
			 	+  Constants.CSVForms.COMMA + linkMap.get("ST_NODE")
			 	+  Constants.CSVForms.COMMA + linkMap.get("ED_NODE")
			 	+  Constants.CSVForms.COMMA + linkMap.get("LEN")
			 	+  Constants.CSVForms.COMMA + linkMap.get("MAX_SPD")
			 	+  Constants.CSVForms.COMMA + linkMap.get("EVENT_MS");
		}
		
		File file = new File(path + "/" + "routelist" + "/" + routId + "/" + fileName);
		
		try {
			createCSV(file, txt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/** 210824 노선별 playlist파일들 jh **/
	public boolean uploadVoicePlayList(String routId, List<Map<String, Object>> orgaList) {
		String routePath = "/routemap/routelist/" + routId + "/playlist";
		String playListPath = Paths.get(getRootLocalPath(), routePath).toString();
		
		try {
			FileUtils.deleteDirectory(new File(playListPath));
			
			FileUtils.forceMkdir(new File(playListPath));
			
			for(Map<String, Object> orgaInfo : orgaList) {
				String orgaId = orgaInfo.getOrDefault("ORGA_ID", "").toString();
				String fileName = orgaId + ".csv";
				StringBuilder csvContent = new StringBuilder();
				csvContent.append(Constants.CSVForms.VOICE_PLAYLIST_TITLE);
				
				
				List<Map<String, Object>> orgaVocList = pi0206Mapper.selectOrgaVocList(orgaId);
				
				for(Map<String, Object> orgaVoc : orgaVocList) {
					if(orgaVoc.get("PLAY_TYPE").equals("TTS")) {
						
		    			if(Integer.parseInt(orgaVoc.get("VOC_CODE").toString()) == Constants.PlayListVoiceTypes.BUS_KR) {
		    				//한음
			    			csvContent.append(
			    					orgaVoc.get("SEQ") + Constants.CSVForms.COMMA
					    			+ Constants.PlayListVoiceTypes.BUS_KR + Constants.CSVForms.COMMA
					    			+ orgaVoc.get("VOC_ID") + Constants.VoiceTypes.KR + ".wav" + Constants.CSVForms.COMMA 
					    			+ orgaVoc.get("START_DATE") + Constants.CSVForms.COMMA 
					    			+ orgaVoc.get("EXPIRE_DATE") + Constants.CSVForms.COMMA
					    			+ orgaVoc.get("TEXT") + Constants.CSVForms.COMMA
			    					+ setIldID(orgaVoc.get("VOC_ID") + Constants.VoiceTypes.KR)
			    					+ Constants.CSVForms.ROW_SEPARATOR);
			    			
			    			// 영음
			    			csvContent.append(
			    					orgaVoc.get("SEQ") + Constants.CSVForms.COMMA
					    			+ Constants.PlayListVoiceTypes.BUS_EN + Constants.CSVForms.COMMA
					    			+ orgaVoc.get("VOC_ID") + Constants.VoiceTypes.EN + ".wav" + Constants.CSVForms.COMMA 
					    			+ orgaVoc.get("START_DATE") + Constants.CSVForms.COMMA 
					    			+ orgaVoc.get("EXPIRE_DATE") + Constants.CSVForms.COMMA
					    			+ orgaVoc.get("EN_TEXT") + Constants.CSVForms.COMMA
			    					+ setIldID(orgaVoc.get("VOC_ID") + Constants.VoiceTypes.EN));
			    		} else {
			    			// 기타 다른음성들
			    			csvContent.append(
			    					orgaVoc.get("SEQ") + Constants.CSVForms.COMMA
					    			+ orgaVoc.get("VOC_CODE") + Constants.CSVForms.COMMA
					    			+ orgaVoc.get("VOC_ID") + Constants.VoiceTypes.KR + ".wav" + Constants.CSVForms.COMMA 
					    			+ orgaVoc.get("START_DATE") + Constants.CSVForms.COMMA 
					    			+ orgaVoc.get("EXPIRE_DATE") + Constants.CSVForms.COMMA
					    			+ orgaVoc.get("TEXT") + Constants.CSVForms.COMMA
			    					+ setIldID(orgaVoc.get("VOC_ID") + Constants.VoiceTypes.KR));
			    		}
		    		} else {
		    			// WAV 업로드 음성
		    			csvContent.append(
		    					orgaVoc.get("SEQ") + Constants.CSVForms.COMMA
				    			+ orgaVoc.get("VOC_CODE") + Constants.CSVForms.COMMA
				    			+ orgaVoc.get("VOC_ID") + Constants.VoiceTypes.US + ".wav" + Constants.CSVForms.COMMA 
				    			+ orgaVoc.get("START_DATE") + Constants.CSVForms.COMMA 
				    			+ orgaVoc.get("EXPIRE_DATE") + Constants.CSVForms.COMMA
				    			+ orgaVoc.get("TEXT") + Constants.CSVForms.COMMA
				    			+ setIldID(orgaVoc.get("VOC_ID").toString() + Constants.VoiceTypes.US));
		    		}
					
		    		csvContent.append(Constants.CSVForms.ROW_SEPARATOR);
				}
				
				
				createCSV(Paths.get(playListPath, fileName).toFile(), csvContent.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    	return true;
	} 
	
	/** 210824 ildId 세팅 jh **/
	public String setIldID(String vocId) {
		if(Integer.parseInt(pi0206Mapper.isExistIld(vocId)) > 0) {
			return vocId + ".ild";
		}else {
			return "";
		}
	}
	
	
	/** 210825 노선별 courseinfo.csv jh **/
	public void uploadCourseRoutList(List<Map<String, Object>> courseRoutList, String courseId, String courseVer) {
		String path = Paths.get(getRootLocalPath(), getRouteMapPath()).toString();
		String fileName = "courseinfo.csv";
		
		String txt = Constants.CSVForms.ROUTE_VERSION + courseVer 
				   + Constants.CSVForms.ROW_SEPARATOR;		
		
		txt += Constants.CSVForms.COURSE_TITLE;
		
		for(Map<String, Object> routMap : courseRoutList) {
			txt += Constants.CSVForms.ROW_SEPARATOR + courseId
				+ Constants.CSVForms.COMMA + routMap.get("SEQ")
				+ Constants.CSVForms.COMMA + routMap.get("ROUT_ID");
		}
		
		File file = new File(path + "/" + "courselist" + "/" + courseId + "/" + fileName);
		
		try {
			createCSV(file, txt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 210825 courselist.csv 파일 read jh **/
	public List<Map<String, Object>> readCourseList(String fileName) throws IOException {
		String path = Paths.get(getRootLocalPath(), getRouteMapPath()).toString();
		
		File file = new File(path + "/" + fileName);
		
		List<Map<String, Object>> list = new ArrayList<>();
		
        //입력 버퍼 생성
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "CP949"));
        String line = "";
        String[] tmp = null;
        
        int lineNum = 0;
        while((line = br.readLine()) != null){
        	lineNum++;
        	
        	if(lineNum <= 2) {
        		continue;
        	}else {        		
        	Map<String, Object> map = new HashMap<>();
        	tmp = line.split(",");
        	
    		map.put("COR_ID",		tmp[0]);
    		map.put("COR_NM",		tmp[1]);
    		map.put("COR_ENM",		tmp[2]);
    			
        	list.add(map);
        	}
        }
        br.close();
        
        return list;
	}
	
	/** 210825 courselist.csv 생성 jh **/
	public boolean uploadCourseInfo(String fileName, String maxVer, Map<String, Object> newRow) {
		String path = Paths.get(getRootLocalPath(), getRouteMapPath()).toString();
		
		File file = new File(path + "/" + fileName);
		List<Map<String, Object>> list = new ArrayList<>();
		if(file.exists()) {
			try {
				list = readCourseList("courselist.csv");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			
		}
		
		if(list.size() > 0) {
			//기존에 정보가 있으면 업데이트, 없으면 인서트하도록 하겠음
			int seq = 0;
			boolean flag = false;
			loop:
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i).get("COR_ID").equals(newRow.get("COR_ID"))) {
					flag = true;
					seq = i;
					break loop;
				}			
			}
			if(flag) {
				list.set(seq, newRow);
			}else {
				list.add(newRow);
			}
		}else {
			list.add(newRow);
		}
					
		String txt = "";
		txt += Constants.CSVForms.ROUTE_VERSION + maxVer + Constants.CSVForms.ROW_SEPARATOR;
		txt += Constants.CSVForms.COURSE_LIST;
		
		for(Map<String, Object> map : list) {
			txt += Constants.CSVForms.ROW_SEPARATOR;
			txt += map.get("COR_ID")
				+  Constants.CSVForms.COMMA + map.get("COR_NM") 
				+  Constants.CSVForms.COMMA + map.get("COR_ENM");
		}
		try {
			createCSV(file, txt);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/** 210826 routemap 경로 local -> ftp 업로드 **/
	public boolean syncRouteMap() {
		String localPath = Paths.get(getRootLocalPath(), getRouteMapPath()).toString();
		String ftpPath = getRootServerPath() + "/routemap/";
		
		try {
			processSynchronize(localPath, ftpPath);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	
	/*
	 * IL001이면 한글이고
	 * IL002이면 영어임
	 * TXT_A값이 있다면
	 * TXT_VAL1꺼 받아서 젤윗줄붙이고
	 * TXT_A값 그다음줄
	 * TXT_B값이 있다면
	 * TXT_VAL2꺼 받아서 그다음줄
	 * TXT_B값 그다음줄
	 * 
	*/
	//ild생성
	public boolean makeIld(Map<String, Object> ild) {
		String ildPath = Paths.get(getRootLocalPath(), getInnerLEDPath(), "/data").toString();
		String fileNm = null;
		String kr = "IL001";
		String en = "IL002";
		
		String txt = "";
		String ildId = String.valueOf(ild.get("ILD_ID"));
		String txtA = String.valueOf(ild.get("TXT_A"));
		String txtB = String.valueOf(ild.get("TXT_B"));
		String txtCd = String.valueOf(ild.get("TXT_CD"));
		String vocId = String.valueOf(ild.get("VOC_ID"));
		
		if(kr.equals(txtCd)) {//한글안내음성일경우
			Map<String, Object> param = new HashMap<>();
			param.put("CO_CD", "INNER_LED");
			param.put("DL_CD", kr);
			Map<String, Object> map = ((List<Map<String, Object>>)commonMapper.selectCommonDtlList(param)).get(0);
			
			String thisStopKr = String.valueOf(map.get("TXT_VAL1"));
			String nextStopKr = String.valueOf(map.get("TXT_VAL2"));
			
			if(!StringUtils.isEmpty(txtA)) {
				txt += thisStopKr + Constants.CSVForms.ROW_SEPARATOR + txtA;				
			}
			
			if(!StringUtils.isEmpty(txtB)) {
				txt += Constants.CSVForms.ROW_SEPARATOR
						+ nextStopKr + Constants.CSVForms.ROW_SEPARATOR + txtB;			
			}
			
		}else if(en.equals(txtCd)) {//영어안내음성일경우
			Map<String, Object> param = new HashMap<>();
			param.put("CO_CD", "INNER_LED");
			param.put("DL_CD", kr);
			Map<String, Object> map = ((List<Map<String, Object>>)commonMapper.selectCommonDtlList(param)).get(0);
			
			String thisStopEn = String.valueOf(map.get("TXT_VAL1"));
			String nextStopEn = String.valueOf(map.get("TXT_VAL2"));
			
			if(!StringUtils.isEmpty(txtA)) {
				txt += thisStopEn + Constants.CSVForms.ROW_SEPARATOR + txtA;				
			}
			
			if(!StringUtils.isEmpty(txtB)) {			
				txt += Constants.CSVForms.ROW_SEPARATOR
						+ nextStopEn + Constants.CSVForms.ROW_SEPARATOR
						+ txtB;
			}
			
		}else{//기타음성일경우
			txt += txtA + Constants.CSVForms.ROW_SEPARATOR + txtB;
		}
		
		
		if(StringUtils.isEmpty(vocId)) {
			fileNm = ildId;
		}else {
			fileNm = vocId;
		}
		File file = new File(ildPath + "/" + fileNm + ".ild");
		
		try {
			createCSV(file, txt);
			processSynchronize(getRootLocalPath() + getInnerLEDPath() + "/data", getRootServerPath() + getInnerLEDPath() + "/data");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	

	public boolean makeIldList(List<Map<String, Object>> ildList) {
		String listPath = Paths.get(getRootLocalPath(), getInnerLEDPath(), "/list").toString();
		
		String txt = "SEQ_NO" + Constants.CSVForms.COMMA + "FILE_NAME" + Constants.CSVForms.ROW_SEPARATOR;
		
		for(int i=0; i<ildList.size(); i++) {
			Map<String, Object> ild = ildList.get(i);
			
			String seq = String.valueOf(ild.get("SN"));
			String vocId = String.valueOf(ild.get("VOC_ID"));
			
			if(!StringUtils.isEmpty(seq) && !StringUtils.isEmpty(vocId)) {
				txt += seq + Constants.CSVForms.COMMA + vocId + ".ild";		
				if(i < ildList.size() - 1) {
					txt += Constants.CSVForms.ROW_SEPARATOR;
				}else {
				}
			}
		}
		
		File file = new File(listPath + "/list.csv");
		
		try {
			createCSV(file, txt);
			processSynchronize(getRootLocalPath() + getInnerLEDPath() + "/list", getRootServerPath() + getInnerLEDPath() + "/list");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	public boolean deleteIld(String ildId) {
		String dir = Paths.get(getRootLocalPath(), getInnerLEDPath(), "/data").toString();
		
		String fileName = ildId +  ".ild";
		
		File saveFile = Paths.get(dir, fileName).toFile();
		
		if(saveFile.exists()) {
			System.gc();
			System.runFinalization();
			saveFile.delete();
		}
			
		try {
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
		
		try {
			processSynchronize(getRootLocalPath() + getInnerLEDPath() + "/data", getRootServerPath() + getInnerLEDPath() + "/data");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	/** 210830 특정 폴더 하위에 있는 폴더, 파일 삭제 jh **/
	public static void deleteFolder(String path) {
		File folder = new File(path);
		try {
			if(folder.exists()){
				File[] folder_list = folder.listFiles();

				for (int i = 0; i < folder_list.length; i++) {
					if(folder_list[i].isFile()) {
						folder_list[i].delete();
					}else {
						deleteFolder(folder_list[i].getPath());
					}
					folder_list[i].delete();
				}
				folder.delete();
			}
		} catch (Exception e) {
			e.getStackTrace();
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
	
	
	//로컬파일 이동
	protected void doMoveFile(String sourcePath, String destPath, String sourceFileName, String destFileName) throws Exception
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
	
	
	//로컬파일 복사
	protected void doCopyFile(String sourcePath, String destPath, String sourceFileName, String destFileName) throws Exception
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
		Path copySourcePath = Paths.get(strSourcePathFile);
		
		String strDestPathFile = (destPath + destFileName).replace("/", File.separator);
		Path copyDestPath = Paths.get(strDestPathFile);

		Files.copy(copySourcePath, copyDestPath, REPLACE_EXISTING);
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
	
	public String getRouteMapPath() {
		return ROUTEMAP;
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
