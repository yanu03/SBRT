package kr.tracom.cm.support.http;

public class GenericException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3399490767029863969L;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public GenericException(String message) {
		this.message = message;
	}
}