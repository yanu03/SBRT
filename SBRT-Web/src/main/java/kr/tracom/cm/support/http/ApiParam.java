package kr.tracom.cm.support.http;

import java.util.List;

public class ApiParam {
	private String 	uri;
	private String 	method;
	private String 	token;
	private String 	body;
	private String 	conNm;
	private String 	authorization;
	private CallbackEvent callbackEvent;
	private CallbackEvent failCallbackEvent;
	
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}



	public String getConNm() {
		return conNm;
	}

	public void setConNm(String conNm) {
		this.conNm = conNm;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	public CallbackEvent getCallbackEvent() {
		return callbackEvent;
	}

	public void setCallbackEvent(CallbackEvent callbackEvent) {
		this.callbackEvent = callbackEvent;
	}

	public CallbackEvent getFailCallbackEvent() {
		return failCallbackEvent;
	}

	public void setFailCallbackEvent(CallbackEvent failCallbackEvent) {
		this.failCallbackEvent = failCallbackEvent;
	}
}