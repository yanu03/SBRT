package kr.tracom.cm.support;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import kr.tracom.cm.support.exception.MessageException;
import kr.tracom.cm.support.exception.ProccessCallbackException;
import kr.tracom.util.CommonUtil;
import kr.tracom.util.Result;



/**
 * ICS(통합고객시스템)프레임 워크의
 * 예외상황 처리 표준 핸들러입니다.
 * 모든 예외는 본 클래스의 예외처리 핸들러의 제어를 받습니다.
 * 
 * @author 트라콤
 */
@Component
public class ExceptionResolver extends SimpleMappingExceptionResolver {

	/**
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionResolver.class);

	//@Autowired
	//private CommonService commonService;

	private String unkown;

	/**
	 * 작성자: 트라콤
	 * 작성일: 2021. 5. 8.
	 * 수정일: 2021. 5. 8.
	 * 목적 : Null 공백처리
	 * 
	 * @param object
	 * @return
	 */
	private String doNullToBlank(Object object)
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
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex
	 * @return
	 */
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex)
	{
		Result result = new Result();
		String errorMessage = ex.getMessage();

		StackTraceElement[] traces = ex.getStackTrace();

		String classInfoMessage = "";

		if (errorMessage != null)
		{
			LOGGER.error(errorMessage);
		}

		for (StackTraceElement trace : traces)
		{
			if (trace.getClassName().contains("kr.tracom") && !trace.getClassName().contains("Support"))
			{
				if (trace.getLineNumber() > 0)
				{
					classInfoMessage = "\tat " + trace.getClassName();
					classInfoMessage += "." + trace.getMethodName();
					classInfoMessage += "(" + trace.getFileName();
					classInfoMessage += ":" + trace.getLineNumber() + ")";

					LOGGER.error(classInfoMessage);
				}
			}
		}

		HttpSession session = request.getSession(false);

		/**
		 * 세션 timeOut
		 * 프로세스상 정상 예외 제외
		 */
		if (session != null && !(ex instanceof MessageException) && !(ex instanceof ProccessCallbackException))
		{
			unkown = "unknown";
			String ipAddress = request.getHeader("X-Forwarded-For");
			if (ipAddress == null || ipAddress.length() == 0 || unkown.equalsIgnoreCase(ipAddress))
			{
				ipAddress = request.getHeader("Proxy-Client-ipAddress");
			}
			if (ipAddress == null || ipAddress.length() == 0 || unkown.equalsIgnoreCase(ipAddress))
			{
				ipAddress = request.getHeader("WL-Proxy-Client-ipAddress");
			}
			if (ipAddress == null || ipAddress.length() == 0 || unkown.equalsIgnoreCase(ipAddress))
			{
				ipAddress = request.getHeader("HTTP_CLIENT_ipAddress");
			}
			if (ipAddress == null || ipAddress.length() == 0 || unkown.equalsIgnoreCase(ipAddress))
			{
				ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ipAddress == null || ipAddress.length() == 0 || unkown.equalsIgnoreCase(ipAddress))
			{
				ipAddress = request.getRemoteAddr();
			}

			/*Map<String, Object> map = new HashMap<String, Object>();
			map.put("MNGT_ID", CommonConstant.VPVL);
			map.put("HOST_NM", CommonUtil.getHostName());
			map.put("OCCUR_USER_ID", doNullToBlank(session.getAttribute("USER_ID")));
			map.put("OCCUR_REMOTE_IP", ipAddress);
			map.put("OCCUR_MNU_NM", doNullToBlank(session.getAttribute("CURRENT_THIRD_MNU_NM")));
			map.put("OCCUR_CLASS", classInfoMessage);
			if (ex.getCause() != null)
			{
				map.put("OCCUR_EXCEPTION", ex.getCause().toString());
			}
			else
			{
				map.put("OCCUR_EXCEPTION", ex.toString());
			}
			map.put("OCCUR_MSG", ex.toString());
			map.put("FRST_REG_ID", "SYSTEM");

			try
			{
				commonService.doSaveOnlineErrorLog(map);
			}
			catch(Exception e1)
			{
				CommonUtil.unwrapThrowable(e1);
			}*/
		}

		if (ex instanceof MyBatisSystemException)
		{
			try
			{
				String exMessage = ex.getMessage();
				if (exMessage.contains("Could not set parameters for mapping"))
				{
					String sqlExMessage = "";
					sqlExMessage = "SQL 매개변수 ";
					sqlExMessage += exMessage.substring(exMessage.indexOf('=') + 1, exMessage.indexOf(','));
					sqlExMessage += "를 취득 할 수 없습니다.";
					
					LOGGER.error(sqlExMessage);
				}
				else if (exMessage.contains("허용"))
				{
					String myBatisMessage = "update 쿼리에 insert 태그(혹은 반대)를 넣었는지 확인하십시오.";

					LOGGER.error(myBatisMessage);
				}
			}
			catch(Exception e)
			{
				CommonUtil.unwrapThrowable(e);
			}
		}

		ModelAndView mav = new ModelAndView();

		if (request.getContentType() != null && request.getContentType().startsWith("application/json"))
		{

			response.setContentType("application/json");

			ServletOutputStream out = null;
			PrintWriter pw = null;
			BufferedWriter bw = null;

			try
			{
				out = response.getOutputStream();
				pw = new PrintWriter(out);
				bw = new BufferedWriter(pw);

				if (errorMessage != null)
				{
					if (errorMessage.startsWith(Result.CALL_KEY))
					{
						String errMsg = errorMessage.replace(Result.CALL_KEY, "");
						result.setErrorMsg(errMsg, result.STATUS_WARNING_MESSAGE, ex);
						JSONObject json =  new JSONObject(result.getResult());
						bw.write(new String(json.toString()));
					}
					else if (errorMessage.startsWith(Result.ERR_KEY))
					{
						String errMsg = errorMessage.replace(Result.ERR_KEY, "");
						
						int pos = errMsg.lastIndexOf( Result.DELIM );
						
						if(pos<0) {
							result.setErrorMsg(errMsg, result.STATUS_ERROR_MESSAGE, null);
						}
						else {
							String statusCode = errMsg.substring(0, pos);
							String msg = errMsg.substring( pos + Result.DELIM.length());
							
							result.setErrorMsg(statusCode, msg, null);
						}
						JSONObject json =  new JSONObject(result.getResult());
						bw.write(new String(json.toString()));
					}
					else
					{
						result.setMsg(result.STATUS_ERROR);
						JSONObject json =  new JSONObject(result.getResult());
						bw.write(new String(json.toString()));
					}
				}
				else
				{
					result.setMsg(result.STATUS_ERROR);
					JSONObject json =  new JSONObject(result.getResult());
					bw.write(new String(json.toString()));
				}
			}
			catch(IOException e)
			{
				CommonUtil.unwrapThrowable(e);
			}
			finally
			{
				try
				{
					bw.flush();
					bw.close();
					pw.close();
					out.close();
				}
				catch(IOException e)
				{
					CommonUtil.unwrapThrowable(e);
				}
			}
		}
		else
		{
			mav.addObject("errorMessage", "작업 도중 오류가 발생하였습니다.");
			mav.setViewName("errorHandler");
			return mav;
		}

		return mav;
	}
}
