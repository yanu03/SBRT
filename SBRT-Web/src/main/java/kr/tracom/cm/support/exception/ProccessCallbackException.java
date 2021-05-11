package kr.tracom.cm.support.exception;

import kr.tracom.util.Result;

/**
 * 작성자: 트라콤
 * 작성일: 2021. 5. 8.
 * 수정일: 2021. 5. 8.
 * 설명:
 * 사용자에게 메세지를 발생시키지 않는<br/>
 * CallBack 처리용 예외입니다.<br/>
 * code에 따라 후처리가 가능합니다.<br/>
 * var result = gfn_submission();
 * result.MSG_CODE = <codeMassage>;
 * 로 후처리에 따른 분기처리를 할 수 있습니다.
 */
public class ProccessCallbackException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param code
	 */
	public ProccessCallbackException(String code)
	{
		super(Result.CALL_KEY + code);
	}
}
