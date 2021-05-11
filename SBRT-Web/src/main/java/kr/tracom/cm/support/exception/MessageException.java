package kr.tracom.cm.support.exception;

import kr.tracom.util.Result;

/**
 * 작성자: 트라콤
 * 작성일: 2021. 5. 8.
 * 수정일: 2021. 5. 8.
 * 설명: exception 메세지를 전달하는 예외입니다.
 */
public class MessageException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public MessageException(String errorCode, String message)
	{
		super(Result.ERR_KEY + errorCode + Result.DELIM + message);
	}
}
