package kr.tracom.cm.support.http;

import com.google.gson.Gson;

import kr.tracom.util.Constants;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;

//@Component
public class HttpClientApi {
	private static Logger log = LoggerFactory.getLogger(HttpClientApi.class);

	//@Value("${api.url}")
	private String baseUrl = "";

	private final static int CON_TIME_OUT = 1000 * 60 * 5;

	private void setURI(ApiParam apiParam) {
		apiParam.setUri(baseUrl + apiParam.getUri());
	}

	private Object call(ApiParam apiParam, HttpResponse response) throws ApiException {
		log.info("-------------------------------API CALL S-------------------------------");
		log.info("// Request URI: {} | Method : {}", apiParam.getUri(), apiParam.getMethod());
		log.info("// Request Body : {}", apiParam.getBody());

		HttpEntity entity = null;
		String result = null;

		try {
			entity = response.getEntity();
			//result = entity != null ? IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8) : "";
			result = entity != null ? IOUtils.toString(entity.getContent()): "";
		} catch (RuntimeException e) {
			throw new GenericException("HttpClient Exception");
		} catch (Exception e) {
			throw new ApiException(400, "HttpClient Exception");
		}

		log.debug("// Response : {}", result);
		log.info("-------------------------------API CALL E-------------------------------");

		try {
			int statusCode = response.getStatusLine().getStatusCode();
			Gson gson = new Gson();
			RtnVo rtnVo = gson.fromJson(result, RtnVo.class);
			if (statusCode < 200 || statusCode > 299) {
				throw new ApiException(statusCode, rtnVo.getRetMsg());
			} else {
				int retCode = Integer.parseInt(rtnVo.getRetCode());
				if (retCode < 200 || retCode > 299) {
					throw new ApiException(retCode, rtnVo.getRetMsg());
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}

		return result;
	}

	public Object get(ApiParam apiParam) throws ApiException {
		HttpResponse response = null;
		setURI(apiParam);
		apiParam.setMethod(Constants.PARAM_GET);

		try {
			Request request = Request.Get(apiParam.getUri());
			request.connectTimeout(CON_TIME_OUT);
			request.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			if (!StringUtils.isEmpty(apiParam.getToken())) {
				request.addHeader(Constants.AUTH_TOKEN, apiParam.getToken());
				request.addHeader("Authorization", "Bearer " + apiParam.getToken());
			}
			if (!StringUtils.isEmpty(apiParam.getConNm())) {
				request.addHeader(Constants.CON_NAME, apiParam.getConNm());
			}
			response = request.execute().returnResponse();
		} catch (RuntimeException e) {
			throw new GenericException("HttpClient Exception");
		} catch (Exception e) {
			throw new ApiException(400, "HttpClient Exception");
		}

		return call(apiParam, response);
	}

	public Object post(ApiParam apiParam) throws ApiException {
		HttpResponse response = null;
		setURI(apiParam);
		apiParam.setMethod(Constants.PARAM_POST);

		try {
			Request request = Request.Post(apiParam.getUri());
			request.connectTimeout(CON_TIME_OUT);
			request.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			if (!StringUtils.isEmpty(apiParam.getToken())) {
				request.addHeader(Constants.AUTH_TOKEN, apiParam.getToken());
			}
			if (!StringUtils.isEmpty(apiParam.getConNm())) {
				request.addHeader(Constants.CON_NAME, apiParam.getConNm());
			}
			if (!StringUtils.isEmpty(apiParam.getBody())) {
				request.bodyString(apiParam.getBody(), ContentType.APPLICATION_JSON);
			}
			response = request.execute().returnResponse();
		} catch (RuntimeException e) {
			throw new GenericException("HttpClient Exception");
		} catch (Exception e) {
			throw new ApiException(400, "HttpClient Exception");
		}

		return call(apiParam, response);
	}
	
	public Object post(ApiParam apiParam, String baseUrl) throws ApiException {
		HttpResponse response = null;
		apiParam.setUri(baseUrl + apiParam.getUri());
		apiParam.setMethod(Constants.PARAM_POST);

		try {
			Request request = Request.Post(apiParam.getUri());
			request.connectTimeout(CON_TIME_OUT);
			request.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			if (!StringUtils.isEmpty(apiParam.getToken())) {
				request.addHeader(Constants.AUTH_TOKEN, apiParam.getToken());
			}
			if (!StringUtils.isEmpty(apiParam.getConNm())) {
				request.addHeader(Constants.CON_NAME, apiParam.getConNm());
			}
			if (!StringUtils.isEmpty(apiParam.getBody())) {
				request.bodyString(apiParam.getBody(), ContentType.APPLICATION_JSON);
			}
			response = request.execute().returnResponse();
		} catch (RuntimeException e) {
			throw new GenericException("HttpClient Exception");
		} catch (Exception e) {
			throw new ApiException(400, "HttpClient Exception");
		}

		return call(apiParam, response);
	}

	public Object put(ApiParam apiParam) throws ApiException {
		HttpResponse response = null;
		setURI(apiParam);
		apiParam.setMethod(Constants.PARAM_PUT);

		try {
			Request request = Request.Put(apiParam.getUri());
			request.connectTimeout(CON_TIME_OUT);
			request.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			if (!StringUtils.isEmpty(apiParam.getToken())) {
				request.addHeader(Constants.AUTH_TOKEN, apiParam.getToken());
			}
			if (!StringUtils.isEmpty(apiParam.getConNm())) {
				request.addHeader(Constants.CON_NAME, apiParam.getConNm());
			}
			if (!StringUtils.isEmpty(apiParam.getBody())) {
				request.bodyString(apiParam.getBody(), ContentType.APPLICATION_JSON);
			}
			response = request.execute().returnResponse();
		} catch (RuntimeException e) {
			throw new GenericException("HttpClient Exception");
		} catch (Exception e) {
			throw new ApiException(400, "HttpClient Exception");
		}

		return call(apiParam, response);
	}

	public Object delete(ApiParam apiParam) throws ApiException {
		HttpResponse response = null;
		setURI(apiParam);
		apiParam.setMethod(Constants.PARAM_DELETE);

		try {
			Request request = Request.Delete(apiParam.getUri());
			request.connectTimeout(CON_TIME_OUT);
			request.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			if (!StringUtils.isEmpty(apiParam.getToken())) {
				request.addHeader(Constants.AUTH_TOKEN, apiParam.getToken());
			}
			if (!StringUtils.isEmpty(apiParam.getConNm())) {
				request.addHeader(Constants.CON_NAME, apiParam.getConNm());
			}
			response = request.execute().returnResponse();
		} catch (RuntimeException e) {
			throw new GenericException("HttpClient Exception");
		} catch (Exception e) {
			throw new ApiException(400, "HttpClient Exception");
		}

		return call(apiParam, response);
	}
}