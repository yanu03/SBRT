package kr.tracom.cm.support;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.SystemUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import kr.tracom.util.CommonUtil;
import kr.tracom.util.Constants;
import kr.tracom.util.Result;
import kr.tracom.util.UserInfo;

/**
 * ICS(통합고객시스템)프레임 워크의
 * Service Layer의 지원 역할을 합니다.
 * 
 * @author 트라콤
 */
@SuppressWarnings("unchecked")
public class ServiceSupport {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceSupport.class);


	/**
	 * 
	 */
	@Autowired
	protected HttpServletRequest request;

	@Autowired
	private UserInfo user;
	

	@Value("${fileupload.location}")
	protected String fileRoot;
	
	@Value("${windows.fileupload.location}")
	protected String windowsFileRoot;

	/**
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@PostConstruct
	private void intilize() throws Exception
	{
	
	}

	/**
	 * 작성자: 트라콤
	 * 작성일: 2021. 05. 07.
	 * 수정일: 2021. 05. 07.
	 * 목적 : Null 공백처리
	 * 
	 * @param object
	 * @return
	 */
	protected String doNullToBlank(Object object)
	{
		if (object == null)
		{
			return "";
		}
		else
		{
			return String.valueOf(object);
		}
	}

	/**
	 * 세션정보를 추가합니다.
	 * 
	 * @param message 메세지
	 */
	private void doAppendUserInfo(Map<String, Object> map)
	{
		try
		{
			map.put(Constants.SSN_USER_ID, doNullToBlank(user.getUserId()));
			map.put(Constants.SSN_USER_NM, doNullToBlank(user.getUserName()));
			map.put(Constants.UPD_DTM, Instant.now(Clock.systemUTC()));
		}
		catch(Exception e)
		{
			/**
			 * 로그인시 session의 미발급에 따른 예외처리
			 */
			if (!(e instanceof NullPointerException))
			{
				throw e;
			}
		}
	}

	/**
	 * 단일 요청정보를 취득합니다.
	 * 
	 * @param key 취득키
	 * @param isEncodeXSS 웹방어 문자 변조처리 여부
	 * @return 단일 요청정보(STRING)
	 * @throws Exception
	 */
	protected String getSimpleParameter(String key, boolean isEncodeXSS) throws Exception
	{
		Object obj = request.getAttribute(Constants.TRACOM_DATA_KEY);

		if (obj == null)
		{
			throw new IllegalArgumentException("요청 정보가 없습니다.");
		}

		Map<String, Object> map = (Map<String, Object>) obj;
		Set<String> keySets = map.keySet();
		Iterator<String> keys = keySets.iterator();
		while (keys.hasNext())
		{
			String keyDummy = keys.next();
			if (keyDummy.equals(key) && map.get(keyDummy) instanceof String)
			{
				LOGGER.debug("[getSimpleParameter - 정보] : [" + key + "]:"
						+ (String) map.get(keyDummy));
				if (isEncodeXSS)
				{
					return CommonUtil.encodeXSS((String) map.get(keyDummy));
				}
				else
				{
					return (String) map.get(keyDummy);
				}
			}
		}

		return null;
	}

	/**
	 * 단일 요청정보를 취득합니다.
	 * 
	 * @param key 취득키
	 * @return 단일 요청정보(STRING)
	 * @throws Exception
	 */
	protected String getSimpleParameter(String key) throws Exception
	{
		return getSimpleParameter(key, false);
	}

	/**
	 * 모든 단일요청정보들을 취득합니다.
	 * 
	 * @param isEncodeXSS 웹방어 문자 변조처리 여부
	 * @return 단일요청정보들(MAP)
	 * @throws Exception
	 */
	protected Map<String, Object> getSimpleParameters(boolean isEncodeXSS) throws Exception
	{
		Object obj = request.getAttribute(Constants.TRACOM_DATA_KEY);

		if (obj == null)
		{
			throw new IllegalArgumentException("요청 정보가 없습니다.");
		}

		Map<String, Object> returnMap = new HashMap<String, Object>();

		Map<String, Object> map = (Map<String, Object>) obj;
		Set<String> keySets = map.keySet();
		Iterator<String> keys = keySets.iterator();
		while (keys.hasNext())
		{
			String key = keys.next();
			if (map.get(key) instanceof String)
			{
				LOGGER.debug("[getSimpleParameters - 정보] : [" + key + "]:" + map.get(key));

				if (isEncodeXSS)
				{
					returnMap.put(key, CommonUtil.encodeXSS((String) map.get(key)));
				}
				else
				{
					returnMap.put(key, (String) map.get(key));
				}
			}
		}

		if (returnMap.isEmpty())
		{
			throw new IllegalArgumentException("단일 요청정보가 없습니다. gfn_submission 부분을 확인하십시오.");
		}

		doAppendUserInfo(returnMap);

		return returnMap;
	}
	
	/**
	 * 모든 단일요청정보들을 취득합니다.
	 * 
	 * @param isEncodeXSS 웹방어 문자 변조처리 여부
	 * @return 단일요청정보들(MAP)
	 * @throws Exception
	 */
	protected Map<String, Object> getSimpleParametersEdit(boolean isEncodeXSS) throws Exception
	{
		Object obj = request.getAttribute(Constants.TRACOM_DATA_KEY);

		if (obj == null)
		{
			throw new IllegalArgumentException("요청 정보가 없습니다.");
		}

		Map<String, Object> returnMap = new HashMap<String, Object>();

		Map<String, Object> map = (Map<String, Object>) obj;
		Set<String> keySets = map.keySet();
		Iterator<String> keys = keySets.iterator();
		while (keys.hasNext())
		{
			String key = keys.next();
			if (map.get(key) instanceof String)
			{
				LOGGER.debug("[getSimpleParameters - 정보] : [" + key + "]:" + map.get(key));

				if (isEncodeXSS)
				{
					returnMap.put(key, CommonUtil.encodeXSS2((String) map.get(key)));
				}
				else
				{
					returnMap.put(key, (String) map.get(key));
				}
			}
		}

		if (returnMap.isEmpty())
		{
			throw new IllegalArgumentException("단일 요청정보가 없습니다. gfn_submission 부분을 확인하십시오.");
		}

		doAppendUserInfo(returnMap);

		return returnMap;
	}

	/**
	 * 모든 단일요청정보들을 취득합니다.
	 * 
	 * @return 단일요청정보들(MAP)
	 * @throws Exception
	 */
	protected Map<String, Object> getSimpleParameters() throws Exception
	{
		return getSimpleParameters(false);
	}

	/**
	 * DataMap을 취득합니다.
	 * 
	 * @param key 취득키
	 * @param isEncodeXSS 웹방어 문자 변조처리 여부
	 * @return DataMap
	 * @throws Exception
	 */
	protected Map<String, Object> getSimpleDataMap(String key, boolean isEncodeXSS) throws Exception
	{
		if (request.getAttribute(Constants.TRACOM_DATA_KEY) == null)
		{
			throw new IllegalArgumentException("요청이 json Data 타입인지 확인하십시오.");
		}

		Object obj = request.getAttribute(Constants.TRACOM_DATA_KEY);

		if (((Map<?, ?>) obj).get(key) == null)
		{
			throw new IllegalArgumentException("Reference[" + key + "]가 존재하지 않습니다. gfn_submission 부분을 확인하십시오.");
		}

		Map<String, Object> map = null;

		try
		{
			map = (Map<String, Object>) ((Map<?, ?>) obj).get(key);
			Set<String> keySets = map.keySet();
			Iterator<String> keys = keySets.iterator();

			while (keys.hasNext())
			{
				String keyDummy = keys.next();
				LOGGER.debug("[getSimpleDataMap - 정보] : [" + keyDummy + "]:"
						+ map.get(keyDummy));

				if (isEncodeXSS)
				{
					map.put(keyDummy, CommonUtil.encodeXSS(doNullToBlank(map.get(keyDummy))));
				}
				else
				{
					map.put(keyDummy, doNullToBlank(map.get(keyDummy)));
				}
			}
		}
		catch(Exception e)
		{
			if (e instanceof ClassCastException)
			{
				throw new IllegalArgumentException("해당 자료는 dataMap형식이 아닙니다.");
			}
			else
			{
				throw e;
			}
		}

		doAppendUserInfo(map);

		return map;
	}

	/**
	 * DataMap을 취득합니다.
	 * 
	 * @param key 취득키
	 * @return DataMap
	 * @throws Exception
	 */
	protected Map<String, Object> getSimpleDataMap(String key) throws Exception
	{
		return getSimpleDataMap(key, false);
	}

	/**
	 * DataList를 취득합니다.<br/>
	 * DataList형 자료는 rowStatus(행상태)가 포함되어 옵니다.<br/>
	 * 초기상태 : R (변화없음)<br/>
	 * 갱신 : U (update API 호출 시)<br/>
	 * 삽입 : C (insert API 호출 시)<br/>
	 * 삭제 : D (delete API 호출 시) - 행이 삭제되지 않으며 상태 값을 D로 변경<br/>
	 * 삽입후 삭제 : V (insert API 후 delete API 호출)<br/>
	 * 제거 : E (remove API 호출 시) - 행을 삭제하며 상태 값을 E로 변경
	 * 
	 * @param key 취득키
	 * @param isEncodeXSS 웹방어 문자 변조처리 여부
	 * @return DataList
	 * @throws Exception
	 */
	protected List<Map<String, Object>> getSimpleList(String key, boolean isEncodeXSS) throws Exception
	{
		if (request.getAttribute(Constants.TRACOM_DATA_KEY) == null)
		{
			throw new IllegalArgumentException("요청이 json Data 타입인지 확인하십시오.");
		}

		Object obj = request.getAttribute(Constants.TRACOM_DATA_KEY);

		if (((Map<?, ?>) obj).get(key) == null)
		{
			String message = "Reference[" + key + "]가 존재하지 않습니다. gfn_submission 부분을 확인하십시오.";
			throw new IllegalArgumentException(message);
		}

		List<Map<String, Object>> lstResult = null;
		Object map = ((Map<?, ?>) obj).get(key);
		try
		{
			lstResult = (List<Map<String, Object>>) map;
			int iLstSize = lstResult.size();
			if (iLstSize == 0)
			{
				LOGGER.info("[getSimpleList - 정보] : Reference[" + key + "] 리스트의 내용이 비어있습니다.");
			}
			int index = 0;
			for (Map<String, Object> mapDummy : lstResult)
			{
				Set<String> keySets = mapDummy.keySet();
				Iterator<String> keys = keySets.iterator();
				String message = "[getSimpleList - 정보] : [";

				while (keys.hasNext())
				{
					String keyDummy = keys.next();
					message += "{" + keyDummy + ":";
					message += mapDummy.get(keyDummy);
					message += "}, ";
				}
				message = message.trim();
				message += "]";
				message = message.replace(",]", "]");
				message = message.replace("}, {", ", ");

				LOGGER.debug(message);

				index++;
				if (index > 2)
				{
					break;
				}
			}

			if (iLstSize > 3)
			{
				LOGGER.debug("[getSimpleList - 정보] : 외 " + (iLstSize - 3) + "건");
			}

			for (Map<String, Object> mapDummy : lstResult)
			{
				Set<String> keySets = mapDummy.keySet();
				Iterator<String> keys = keySets.iterator();

				while (keys.hasNext())
				{
					String keyDummy = keys.next();
					if (isEncodeXSS)
					{
						mapDummy.put(keyDummy, CommonUtil.encodeXSS(doNullToBlank(mapDummy.get(keyDummy))));
					}
					else
					{
						mapDummy.put(keyDummy, doNullToBlank(mapDummy.get(keyDummy)));
					}
				}
			}

			for (Map<String, Object> mapDummy : lstResult)
			{
				doAppendUserInfo(mapDummy);
			}
		}
		catch(Exception e)
		{
			if ((e instanceof ClassCastException))
			{
				/**
				 * 리스트가 한개행일 경우 Map으로 전송됨에 따라
				 * 리스트를 생성하여 return
				 */
				if (map instanceof Map)
				{
					if (((Map<?, ?>) map).get("rowStatus") != null)
					{
						List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

						Set<String> keySets = ((Map<String, Object>) map).keySet();
						Iterator<String> keys = keySets.iterator();

						while (keys.hasNext())
						{
							String keyDummy = keys.next();

							if (isEncodeXSS)
							{
								((Map<String, Object>) map).put(keyDummy,
										CommonUtil.encodeXSS(doNullToBlank(((Map<String, Object>) map).get(keyDummy))));
							}
							else
							{
								((Map<String, Object>) map).put(keyDummy,
										doNullToBlank(((Map<String, Object>) map).get(keyDummy)));
							}
						}

						doAppendUserInfo((Map<String, Object>) map);
						list.add((Map<String, Object>) map);
						return list;
					}
					else
					{
						String message = "Reference[" + key + "]는 dataMap입니다. getSimpleMap을 사용하십시오.";
						throw new IllegalArgumentException(message);
					}
				}
				else
				{
					throw e;
				}
			}
			else
			{
				throw e;
			}
		}

		return lstResult;
	}

	/**
	 * DataList를 취득합니다.<br/>
	 * DataList형 자료는 rowStatus(행상태)가 포함되어 옵니다.<br/>
	 * 초기상태 : R (변화없음)<br/>
	 * 갱신 : U (update API 호출 시)<br/>
	 * 삽입 : C (insert API 호출 시)<br/>
	 * 삭제 : D (delete API 호출 시) - 행이 삭제되지 않으며 상태 값을 D로 변경<br/>
	 * 삽입후 삭제 : V (insert API 후 delete API 호출)<br/>
	 * 제거 : E (remove API 호출 시) - 행을 삭제하며 상태 값을 E로 변경
	 * 
	 * @param key 취득키
	 * @return DataList
	 * @throws Exception
	 */
	protected List<Map<String, Object>> getSimpleList(String key) throws Exception
	{
		return getSimpleList(key, false);
	}
	
	/**
	 * DataList를 취득합니다.<br/>
	 * DataList형 자료는 rowStatus(행상태)가 포함되어 옵니다.<br/>
	 * 초기상태 : R (변화없음)<br/>
	 * 갱신 : U (update API 호출 시)<br/>
	 * 삽입 : C (insert API 호출 시)<br/>
	 * 삭제 : D (delete API 호출 시) - 행이 삭제되지 않으며 상태 값을 D로 변경<br/>
	 * 삽입후 삭제 : V (insert API 후 delete API 호출)<br/>
	 * 제거 : E (remove API 호출 시) - 행을 삭제하며 상태 값을 E로 변경
	 * 
	 * @param key 취득키
	 * @return DataList
	 * @throws Exception
	 */
	protected List<Map<String, Object>> getSimpleList2(String key) throws Exception
	{
		return getSimpleList(key, false);
	}

	protected Map saveResult(int iCnt) {
		Map result = new HashMap();
		result.put("STATUS", Result.STATUS_SAVE);
		result.put("ICNT", String.valueOf(iCnt));
		return result;
	}
	
	protected Map saveResult(int iCnt, int uCnt) {
		Map result = new HashMap();
		result.put("STATUS",Result.STATUS_SAVE);
		result.put("ICNT", String.valueOf(iCnt));
		result.put("UCNT", String.valueOf(uCnt));
		return result;
	}
	
	protected Map saveResult(int iCnt, int uCnt, int dCnt) {
		Map result = new HashMap();
		result.put("STATUS", Result.STATUS_SAVE);
		result.put("ICNT", String.valueOf(iCnt));
		result.put("UCNT", String.valueOf(uCnt));
		result.put("DCNT", String.valueOf(dCnt));
		return result;
	}
	
	protected Map saveResult(int iCnt_grd0, int iCnt_grd1, int uCnt_grd0, int uCnt_grd1, int dCnt_grd0, int dCnt_grd1) {
		Map result = new HashMap();
		result.put("STATUS", Result.STATUS_SAVE);
		result.put("ICNT", String.valueOf(iCnt_grd0));
		result.put("ICNT", String.valueOf(iCnt_grd1));
		result.put("UCNT", String.valueOf(uCnt_grd0));
		result.put("UCNT", String.valueOf(uCnt_grd1));
		result.put("DCNT", String.valueOf(dCnt_grd0));
		result.put("DCNT", String.valueOf(dCnt_grd1));
		return result;
	}
	
	protected String doMoveFile(String strFilePath, File file) throws Exception
	{
		if (file == null)
		{
			return "";
		}
 
		if (strFilePath == null || "".equals(strFilePath))
		{
			throw new IllegalArgumentException("경로가 없습니다.");
		}

		if (!strFilePath.endsWith("/"))
		{
			throw new IllegalArgumentException("경로는 '/'로 끝나야합니다.");
		}

		String strFileName = file.getName();

		if (file == null || "".equals(strFileName))
		{
			throw new IllegalArgumentException("파일이름이 유효하지 않습니다.");
		}

		if (!file.exists())
		{
			throw new IllegalArgumentException("파일이 존재하지 않습니다.");
		}
	    
		String strDestPath = (fileRoot + strFilePath).replace("/", File.separator);
		
		if(SystemUtils.IS_OS_WINDOWS) {
			strDestPath = (windowsFileRoot + strFilePath).replace("/", File.separator);
		}

		File fileDestPath = new File(strDestPath);
		if (!fileDestPath.exists())
		{
			fileDestPath.mkdirs();
		}

		String strDestPathFile = (fileRoot + strFilePath + strFileName).replace("/", File.separator);

		Path filePreTarget = Paths.get(file.getAbsolutePath());
		Path moveDestPath = Paths.get(strDestPathFile);

		Files.move(filePreTarget, moveDestPath, REPLACE_EXISTING);

		return strFileName;
	}
	
	protected void doMoveFile(String sourcePath, String destPath, String sourceFileName, String destFileName) throws Exception
	{
		if (sourceFileName == null||"".equals(sourceFileName))
		{
			throw new IllegalArgumentException("파일이름이 유효하지 않습니다.");
		}
		
		if(SystemUtils.IS_OS_WINDOWS) {
			sourcePath = windowsFileRoot + sourcePath;
			destPath = windowsFileRoot + destPath;
		} else {
			sourcePath = fileRoot + sourcePath;
			destPath = fileRoot + destPath;	
		}
		
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
	
	protected void doCopyFile(String sourcePath, String destPath, String sourceFileName, String destFileName) throws Exception
	{
		if (sourceFileName == null||"".equals(sourceFileName))
		{
			throw new IllegalArgumentException("파일이름이 유효하지 않습니다.");
		}
		
		if(SystemUtils.IS_OS_WINDOWS) {
			sourcePath = windowsFileRoot + sourcePath;
			destPath = windowsFileRoot + destPath;
		} else {
			sourcePath = fileRoot + sourcePath;
			destPath = fileRoot + destPath;
		}
				
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
}
