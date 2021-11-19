package kr.tracom.bms.domain.PI0206;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import kr.tracom.bms.ftp.FTPHandler;
import kr.tracom.cm.support.ServiceSupport;
import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.util.Result;

@Service
public class PI0206Service extends ServiceSupport {

	@Value("${api.gateway.url}")
	private String apiGatewayUrl;
	
	@Autowired
	private PI0206Mapper pi0206Mapper;
	
	@Autowired
	private FTPHandler ftpHandler;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public List PI0206G0R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_search");
		return pi0206Mapper.PI0206G0R0(map);
	}
	
	public List PI0206SHI0() throws Exception {
		return pi0206Mapper.PI0206SHI0();
	}
	
	public List PI0206G1R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_subsearch");		
		return pi0206Mapper.PI0206G1R0(map);
	}
	
	public Map PI0206G1K0() throws Exception {
		return pi0206Mapper.PI0206G1K0(); 
	}	
	
	public List PI0206G1R1() throws Exception {
		return pi0206Mapper.PI0206G1R1();
	}
	
	//예약
	public Map PI0206G1S0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		//체크한 노선 리스트 (list_param)
		List<Map<String, Object>> list_param = getSimpleList("dlt_BRT_COR_MST");
		//체크한 차량 리스트
		List<Map<String, Object>> param = getSimpleList("dlt_VHC_MST");
				
		
		Map<String, Object> map = getSimpleDataMap("dma_subsearch");
		try {
			//1. 노선 리스트 중복없애서 정리함
			//2. 노선파일 업데이트
			//3. 코스파일 업데이트
			
			Map<String, Object> listMap = new HashMap();
			listMap.put("courseList", list_param);
			
			//중복 제거한 노선 리스트
			List<Map<String, Object>> routList = pi0206Mapper.selectRoutList(listMap);
						
			//공통파일 생성
			if(makeCommonFile()) {
				//노선관련 파일 생성
				List<String> strRoutList = listMapToList(routList, "ROUT_ID");
				if(makeRouteFile(strRoutList)) {
					//코스관련 파일 생성
					List<String> strCourseList = listMapToList(list_param, "COR_ID");
					if(makeCourseFile(strCourseList)) {
						if(ftpHandler.syncRouteMap()) {
							//예약 정보, 예약결과 정보 insert
							Map<String, Object> paramMap = new HashMap<String, Object>();
							
							for (int i = 0; i < param.size(); i++) {
								Map data = (Map) param.get(i);
								String rowStatus = (String) data.get("rowStatus");
								if (rowStatus.equals("U")) {
									
									data.put("COR_ID",map.get("COR_ID"));
									iCnt = pi0206Mapper.PI0206G1I0(data);
									
								}
								else if (rowStatus.equals("D")) {
									dCnt += pi0206Mapper.PI0206G1D0(data);
								} 
							}							
						}
					}
				}
			}
		} catch(Exception e) {
			if (e instanceof DuplicateKeyException)
			{
				throw new MessageException(Result.ERR_KEY, "중복된 키값의 데이터가 존재합니다.");
			}
			else
			{
				throw e;
			}		
		}

		
		Map result = saveResult(iCnt, uCnt, dCnt);
		
		return result;		
		
		
	}
	
	//예약 취소
	public Map PI0206G1U0() throws Exception {
		int iCnt = 0;
		int uCnt = 0;
		int dCnt = 0;		
		
		List<Map<String, Object>> param = getSimpleList("dlt_VHC_MST");
		try {
			for (int i = 0; i < param.size(); i++) {
				Map data = (Map) param.get(i);
				String rowStatus = (String) data.get("rowStatus");
				if (rowStatus.equals("U")) {
					iCnt += pi0206Mapper.PI0206G1U0(data);
				} 
			}			
		} catch(Exception e) {
			if (e instanceof DuplicateKeyException)
			{
				throw new MessageException(Result.ERR_KEY, "중복된 키값의 데이터가 존재합니다.");
			}
			else
			{
				throw e;
			}		
		}
		
		
		Map result = saveResult(iCnt, uCnt, dCnt);
		
		return result;		
		
		
	}
	
	public List PI0206G2R0() throws Exception {
		Map<String, Object> map = getSimpleDataMap("dma_subsearch2");		
		return pi0206Mapper.PI0206G2R0(map);
	}
	
	
	/** 공통경로 하위 파일 생성 jh **/
	public boolean makeCommonFile() {
		//정류장 리스트
		List<Map<String, Object>> stationList = pi0206Mapper.selectStnAudioList();
		
		//노드 리스트
		List<Map<String, Object>> nodeList = pi0206Mapper.selectAllNodeList();
			
		//station맵에 지역정보 추가
		List<Map<String, Object>> stnAndLocList = stnPlusLocation(stationList);
		
		
		//busstop.csv 파일 생성
		try {
			ftpHandler.uploadBusstop(stationList, "busstop.csv", "00000000");
		}catch(IOException e) {
			logger.error("[PI0206Service] IOException in uploadBusstop");
			return false;
		}
		
		//node.csv 파일 생성
		try {
			ftpHandler.uploadNodeList(nodeList, "node.csv", "00000000");
		} catch (IOException e) {
			logger.error("[PI0206Service] IOException in uploadNodeList");
			return false;
		}
		
		return true;
	}

	/** routelist 관련 파일 생성 jh **/
	public boolean makeRouteFile(List<String> routList) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("routList", routList);
		
		//노선 정보
		List<Map<String, Object>> routInfoList = pi0206Mapper.selectRoutInfoList(paramMap);
		
		//routelist 하위
		for(Map<String, Object> routInfo : routInfoList) {
			String routId = routInfo.get("ROUT_ID").toString();
			
			//노선별 노드 리스트
			List<Map<String, Object>> routNodeList = pi0206Mapper.selectRoutNodeList(routId);

			//노선별 링크 리스트
			List<Map<String, Object>> routLinkList = pi0206Mapper.selectRoutLinkList(routId);
			
			//노선별 음성 편성 리스트
			List<Map<String, Object>> routOrgaList = pi0206Mapper.selectRoutOrgaList(routId);
			
			//routeinfo.csv 업로드
			try {
				ftpHandler.uploadRoutNodeList(routNodeList, routId, "00000000");
			} catch(Exception e) {
				logger.error("[PI0206Service] Exception in uploadRoutNodeList....ROUTE_ID = " + routId);
				return false;
			}
			
			//link.csv 업로드
			try {
				ftpHandler.uploadRoutLinkList(routLinkList, routId, "00000000");
			} catch(Exception e) {
				logger.error("[PI0206Service] Exception in uploadRoutLinkList....ROUTE_ID = " + routId);
				return false;
			}
			
			//playlist 생성
			try {
				ftpHandler.uploadVoicePlayList(routId, routOrgaList);
			}catch(Exception e) {
				logger.error("[PI0206Service] Exception in uploadVoicePlayList....ROUTE_ID = " + routId);
				return false;
			}
			
			//routelist.csv 업로드
			try {
				Map<String, Object> newRow = pi0206Mapper.makeRoutNewRow(routId);
				ftpHandler.uploadRoutList("routelist.csv", "00000000", "00000000", newRow);
			} catch (IOException e) {
				logger.error("[PI0206Service] IOException in uploadRoutList....ROUTE_ID = " + routId);
				return false;
			}
		}
		
		return true;
	}
	
	/** courselist경로 하위 파일 생성 jh **/
	public boolean makeCourseFile(List<String> courseList) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("courseList", courseList);
		
		//코스정보 리스트
		List<Map<String, Object>> courseInfoList = pi0206Mapper.selectCourseInfoList(paramMap);
		
		for(Map<String, Object> courseInfo : courseInfoList) {
			String courseId = courseInfo.get("COR_ID").toString();
			
			//코스별 노선 리스트
			List<Map<String, Object>> courseRoutList = pi0206Mapper.selectCourseRoutList(courseId);
			
			//courseinfo.csv 업로드
			try {
				ftpHandler.uploadCourseRoutList(courseRoutList, courseId, "00000000");
			} catch (Exception e) {
				logger.error("[PI0206Service] Exception in uploadCourseRoutList....COR_ID = " + courseId);
				return false;
			}
			
			//courselist.csv 업로드
			try {
				ftpHandler.uploadCourseInfo("courselist.csv", "00000000", courseInfo);
			} catch (Exception e) {
				logger.error("[PI0206Service] Exception in uploadCourseInfo....COR_ID = " + courseId);
				return false;
			}
		}
		
		return true;
	}
	
	/** 정류장 리스트 + 위치정보 jh **/
	public List<Map<String, Object>> stnPlusLocation(List<Map<String, Object>> stationList){
		List<Map<String, Object>> resultList = new ArrayList<>();
		String authKey = pi0206Mapper.selectAPIKey();
		
		for(Map<String, Object> station : stationList) {
			
			String x = station.getOrDefault("X", "").toString();
			String y = station.getOrDefault("Y", "").toString();
			
			String loc = "";
			
			if(!x.equals("") && !y.equals("")) {
				loc = getLocation(x, y, authKey);				
			}
			
			station.put("LOCATION_INFO", loc);
			
			resultList.add(station);
		}
		logger.debug("정류장리스트 사이즈(변경전) : " + stationList.size());
		logger.debug("정류장리스트 사이즈(변경후) : " + resultList.size());
		
		return resultList;
	}
	
	
	/** rest 요청 jh **/
	public String getLocation(String x, String y, String authKey) {
		
		HttpURLConnection conn = null;
		String result = "";

		//좌표로 동네이름 가져오기 (api gateway 로 요청)
		String api = apiGatewayUrl + "/getLocation?x=" + x + "&y=" + y + "&authKey=" + authKey.replace(" ", "%20");

		try {
			URL url = new URL(api);
			try {
				conn = (HttpURLConnection)url.openConnection();
				conn.setRequestMethod("GET");
				
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = "";
				while((line = br.readLine()) != null) {
					sb.append(line);
				}
				try {
					result = sb.toString();
				} catch (Exception e) {
					logger.error("error");
				}
			} catch (IOException e) {
				logger.error("IOException");
			}			
		} catch (MalformedURLException e) {
			logger.error("MalformedURLException");
		}
		
		return result;
	}
	
	
	//좌표로 동네이름 가져오기 (api gateway 에서 처리하도록 옮겨감)
	public String getLocation_old(String x, String y, String authKey) {
		
		HttpURLConnection conn = null;
		JSONObject response = null;
		String result = "";
		
		String api = "https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?x=" + x + "&y=" + y;
		

		try {
			URL url = new URL(api);
			try {
				conn = (HttpURLConnection)url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Authorization", authKey);
				conn.setRequestProperty("Content-Type", "application/json");
				
				conn.setDoOutput(true);
				
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
				
				bw.write("");
				bw.flush();
				bw.close();
				
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = "";
				while((line = br.readLine()) != null) {
					sb.append(line);
				}
				try {
					response = new JSONObject(sb.toString());
				} catch (JSONException e) {
					logger.error("error");
				}
			} catch (IOException e) {
				logger.error("IOException");
			}			
		} catch (MalformedURLException e) {
			logger.error("MalformedURLException");
		}
		
		
		try {
			JSONArray arr = (JSONArray) response.get("documents");
			result = arr.getJSONObject(0).get("address_name").toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		if(result.length() > 30) {
			result = result.substring(0, 30);
		}
		return result;
	}
	
	/** Map List -> List jh **/
	public List<String> listMapToList(List<Map<String, Object>> listMap, String key){
		List<String> result = new ArrayList<>();
		
		for(Map<String, Object> map : listMap) {
			result.add(map.get(key).toString());
		}
		
		return result;
	}
	
}
