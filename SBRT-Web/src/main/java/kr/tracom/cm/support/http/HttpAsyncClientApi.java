package kr.tracom.cm.support.http;

import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import kr.tracom.util.Constants;

//@Component
public class HttpAsyncClientApi {
	private static Logger log = LoggerFactory.getLogger(HttpAsyncClientApi.class);

	//@Value("${api.url}")
	private String baseUrl = "";

	private final static int CON_TIME_OUT = 1000 * 60 * 5;

	private void setURI(ApiParam apiParam) {
		apiParam.setUri(baseUrl + apiParam.getUri());
	}

	private void call(ApiParam apiParam, HttpResponse response) throws ApiException {
		log.info("-------------------------------API CALL S-------------------------------");
		log.info("// Request URI: {} | Method : {}", apiParam.getUri(), apiParam.getMethod());
		log.info("// Request Body : {}", apiParam.getBody());

		HttpEntity entity = null;
		String result = null;

		try {
			entity = response.getEntity();
			
			result = entity != null ? IOUtils.toString(entity.getContent()): "";
			//result = entity != null ? IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8) : "";
		} catch (Exception e) {
			if (apiParam.getFailCallbackEvent() != null) {
				apiParam.getFailCallbackEvent().callBackMethod(result);
			}
			throw new ApiException(400, "HttpAsyncClient Exception");
		}

		log.info("// Response : {}", result);
		log.info("-------------------------------API CALL E-------------------------------");

		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode < 200 || statusCode > 299) {
			if (apiParam.getFailCallbackEvent() != null) {
				apiParam.getFailCallbackEvent().callBackMethod(result);
			}
			throw new ApiException(statusCode, result);
		}

		apiParam.getCallbackEvent().callBackMethod(result);
	}

	public void post(final ApiParam apiParam) throws ApiException {
		setURI(apiParam);
		apiParam.setMethod(Constants.PARAM_POST);

		final CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();
		try {
			httpClient.start();
			HttpPost request = new HttpPost(apiParam.getUri());
			request.setConfig(RequestConfig.custom().setConnectTimeout(CON_TIME_OUT).build());
			request.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			if (!StringUtils.isEmpty(apiParam.getToken())) {
				request.addHeader(Constants.AUTH_TOKEN, apiParam.getToken());
			}
			if (!StringUtils.isEmpty(apiParam.getConNm())) {
				request.addHeader(Constants.CON_NAME, apiParam.getConNm());
			}
			if (!StringUtils.isEmpty(apiParam.getBody())) {
				StringEntity input = new StringEntity(apiParam.getBody(), StandardCharsets.UTF_8);
				input.setContentType(MediaType.APPLICATION_JSON_VALUE);
				request.setEntity(input);
			}
			httpClient.execute(request, new FutureCallback<HttpResponse>() {

				@Override
				public void completed(HttpResponse response) {
					try {
						call(apiParam, response);
					} catch (Exception e) {
						log.error("{}", e);
					}

					try {
						httpClient.close();
					} catch (Exception e) {
						log.error("{}", e);
					}
				}

				@Override
				public void failed(Exception ex) {
					try {
						httpClient.close();
					} catch (Exception e) {
						log.error("{}", e);
					}
				}

				@Override
				public void cancelled() {
					try {
						httpClient.close();
					} catch (Exception e) {
						log.error("{}", e);
					}
				}
			});

		} catch (Exception e) {
			throw new ApiException(400, "HttpAsyncClient Exception");
		}
	}

	public void put(final ApiParam apiParam) throws ApiException {
		setURI(apiParam);
		apiParam.setMethod(Constants.PARAM_PUT);

		final CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();
		try {
			httpClient.start();
			HttpPut request = new HttpPut(apiParam.getUri());
			request.setConfig(RequestConfig.custom().setConnectTimeout(CON_TIME_OUT).build());
			request.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			if (!StringUtils.isEmpty(apiParam.getToken())) {
				request.addHeader(Constants.AUTH_TOKEN, apiParam.getToken());
			}
			if (!StringUtils.isEmpty(apiParam.getConNm())) {
				request.addHeader(Constants.CON_NAME, apiParam.getConNm());
			}
			if (!StringUtils.isEmpty(apiParam.getBody())) {
				StringEntity input = new StringEntity(apiParam.getBody(), StandardCharsets.UTF_8);
				input.setContentType(MediaType.APPLICATION_JSON_VALUE);
				request.setEntity(input);
			}
			httpClient.execute(request, new FutureCallback<HttpResponse>() {

				@Override
				public void completed(HttpResponse response) {
					try {
						call(apiParam, response);
					} catch (Exception e) {
					}
					try {
						httpClient.close();
					} catch (Exception e) {

					}
				}

				@Override
				public void failed(Exception ex) {
					try {
						httpClient.close();
					} catch (Exception e) {

					}
				}

				@Override
				public void cancelled() {
					try {
						httpClient.close();
					} catch (Exception e) {

					}
				}
			});
		} catch (Exception e) {
			throw new ApiException(400, "HttpAsyncClient Exception");
		}
	}

	public void delete(final ApiParam apiParam) throws ApiException {
		setURI(apiParam);
		apiParam.setMethod(Constants.PARAM_DELETE);

		final CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();
		try {
			httpClient.start();
			HttpDelete request = new HttpDelete(apiParam.getUri());
			request.setConfig(RequestConfig.custom().setConnectTimeout(CON_TIME_OUT).build());
			request.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			if (!StringUtils.isEmpty(apiParam.getToken())) {
				request.addHeader(Constants.AUTH_TOKEN, apiParam.getToken());
			}
			if (!StringUtils.isEmpty(apiParam.getConNm())) {
				request.addHeader(Constants.CON_NAME, apiParam.getConNm());
			}
			httpClient.execute(request, new FutureCallback<HttpResponse>() {

				@Override
				public void completed(HttpResponse response) {
					try {
						call(apiParam, response);
					} catch (Exception e) {
					}
					try {
						httpClient.close();
					} catch (Exception e) {

					}
				}

				@Override
				public void failed(Exception ex) {
					try {
						httpClient.close();
					} catch (Exception e) {

					}
				}

				@Override
				public void cancelled() {
					try {
						httpClient.close();
					} catch (Exception e) {

					}
				}
			});
		} catch (Exception e) {
			throw new ApiException(400, "HttpAsyncClient Exception");
		}
	}
}