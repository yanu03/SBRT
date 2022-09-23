package kr.tracom.cm.support.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiException extends Exception {
	private static final long serialVersionUID = 7853106442826788165L;
	private final  Logger log = LoggerFactory.getLogger(ApiException.class);

	private final int statusCode;

	public ApiException(int statusCode, String message) {
		super(message);
		this.statusCode = statusCode;
		
		StackTraceElement[] trace = this.getStackTrace();
		log.debug("Exception[{}] : {}", statusCode, message);
		log.debug("Exception - FileName : [{}], LineNumber : [{}]", trace[0].getFileName(), trace[0].getLineNumber());
	}

	public ApiException(int statusCode, String message, Throwable throwable) {
		super(message, throwable);
		this.statusCode = statusCode;

		StackTraceElement[] trace = throwable.getStackTrace();
		log.debug("Exception[{}] : {}", statusCode, message);
		log.debug("Exception - FileName : [{}], LineNumber : [{}]", trace[0].getFileName(), trace[0].getLineNumber());
	}

	public int getStatusCode() {
		return statusCode;
	}
}