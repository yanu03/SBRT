package kr.tracom.cm.support.http;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Response Rtn
 */
public class RtnVo {

	private String retCode;
	private String retMsg;

	public String getRetCode() {
		return retCode;
	}

	@XmlElement
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	@XmlElement
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	/**
	 * 정상 처리 200
	 * 
	 * @return
	 */
	public void setCustomRetGETOK() {
		this.retCode = ResultCode.USER_RCODE_OK;
		this.retMsg = "It's been handled normally."; //"요청이 정상처리되었습니다.";
	}

	/**
	 * 생성 정상 처리 201
	 * 
	 * @return
	 */
	public void setCustomRetPOSTOK() {
		this.retCode = ResultCode.USER_RCODE_CREATED;
		this.retMsg = "It's been handled normally."; //"요청이 정상처리되었습니다.";
	}

	/**
	 * PUT 정상 처리 204
	 * 
	 * @return
	 */
	public void setCustomRetPUTOK() {
		this.retCode = ResultCode.USER_RCODE_NO_CONTENT;
		this.retMsg = "It's been handled normally."; //"요청이 정상처리되었습니다.";
	}

	/**
	 * POST Action 정상 처리 204
	 * 
	 * @return
	 */
	public void setCustomRetACTIONOK() {
		this.retCode = ResultCode.USER_RCODE_NO_CONTENT;
		this.retMsg = "It's been handled normally."; //"요청이 정상처리되었습니다.";
	}

	/**
	 * Delete 정상 처리 204
	 * 
	 * @return
	 */
	public void setCustomRetDELETEOK() {
		this.retCode = ResultCode.USER_RCODE_NO_CONTENT;
		this.retMsg = "It's been handled normally."; //"요청이 정상처리되었습니다.";
	}

	/**
	 * 요청 인자의 validation 오류 api response 객체용 Code(400-INVALID_VALUE), Msg Set
	 * 
	 * @return
	 */
	public void setCustomRetInValid() {
		setCustomRetBadRequest();
	}

	/**
	 * 요청 인자의 validation 오류 api response 객체용 Code(400-INVALID_VALUE), Msg Set
	 * 
	 * @return
	 */
	public void setCustomRetBadRequest() {
		this.retCode = ResultCode.USER_RCODE_BAD_REQUEST;
		this.retMsg = "Invalied request."; //"잘못된 요청입니다.";
	}

	/**
	 * 요청 인자의 validation 오류 api response 객체용 Code(401-INVALID_VALUE), Msg Set
	 * 
	 * @return
	 */
	public void setCustomRetUnAuthorized() {
		this.retCode = ResultCode.USER_RCODE_UNAUTHORIZED;
		this.retMsg = "Unauthenticated user request."; //"인증되지 않은 사용자/앱의 요청입니다.";
	}

	/**
	 * 유효하지 않은 자원에 대한 요청 api response 객체용 Code(404-NOT_FOUND), Msg Set
	 * 
	 * @return
	 */
	public void setCustomRetNotFoundResource() {
		this.retCode = ResultCode.USER_RCODE_NOT_FOUND;// .HttpStatus.NOT_FOUND.toString();
														// // "404";
		this.retMsg = "Resource not found."; //"Resource를 찾을수 없습니다.";
	}

	/**
	 * 허용되지 않은(처리할 수 없는) 서비스 api response 객체용 Code(405-METHOD_NOT_ALLOWED), Msg
	 * Set
	 * 
	 * @return
	 */
	public void setCustomRetNotAllowed() {
		this.retCode = ResultCode.USER_RCODE_METHOD_NOT_ALLOWED;
		this.retMsg = "This is an unacceptable service."; //"허용되지 않은(처리할 수 없는) 서비스입니다.";
	}

	/**
	 * 유효하지 않는 서비스 api response 객체용 Code(460-INVALID_API), Msg Set
	 * 
	 * @return
	 */
	public void setCustomRetInvalidApi() {
		this.retCode = ResultCode.USER_RCODE_INVALID_API;
		this.retMsg = "Invalid service API."; //"유효하지 않는 서비스 API입니다.";
	}

	/**
	 * 필수 파라미터에 대한 잘못된 요청 api response 객체용 Code(470-REQUIRED_PARAM), Msg Set
	 * 
	 * @return
	 */
	public void setCustomRetRequiredParam() {
		this.retCode = ResultCode.USER_RCODE_REQUIRED_PARAM;
		this.retMsg = "Invalid request for required parameters."; //"필수 파라미터에 대한 잘못된 요청입니다.";
	}

	/**
	 * 필수 파라미터가 유효하지 않는 api response 객체용 Code(471-INVALID_PARAM), Msg Set
	 * 
	 * @return
	 */
	public void setCustomRetInvalidParam() {
		this.retCode = ResultCode.USER_RCODE_INVALID_PARAM;
		this.retMsg = "Invalid request that required parameter is invalid."; //"필수 파라미터가 유효하지 않는 잘못된 요청입니다.";
	}

	/**
	 * 서비스 내부 서버 오류 api response 객체용 Code(500-INTERNAL_SERVER_ERROR), Msg Set
	 * 
	 * @return
	 */
	public void setCustomRetInternalServerError() {
		this.retCode = ResultCode.USER_RCODE_INTERNAL_SERVER_ERROR;
		this.retMsg = "Internal server error."; //"서비스 내부 서버 오류가 발생하였습니다.";
	}

	/**
	 * 지원해야 하나 아직 처리가 되지 않은 api response 객체용 Code(501-NOT_IMPLEMENTED), Msg Set
	 * 
	 * @return
	 */
	public void setCustomRetUnReady() {
		this.retCode = ResultCode.USER_RCODE_NOT_IMPLEMENTED;// HttpStatus.NOT_IMPLEMENTED.toString();
															// // "501";
		this.retMsg = "Not implemented."; //"준비중입니다.";
	}

	/**
	 * 하이퍼바이저 연결(Connection 획득 실패시) 오류 api response 객체용 Code(502-BAD_GATEWAY),
	 * Msg Set
	 * 
	 * @return
	 */
	public void setCustomRetNoConnection() {
		this.retCode = ResultCode.USER_RCODE_BAD_GATEWAY; // HttpStatus.BAD_GATEWAY.toString();
															// // "502";
		this.retMsg = "Unable to connect to hypervisor. (check connection info or network!)"; //"하이퍼바이저에 연결할 수 없습니다.(연결정보 또는 네트워크를 확인해 주세요!)";
	}

	/**
	 * 지원하지 않는 서비스 api response 객체용 Code(503-SERVICE_UNAVAILABLE), Msg Set
	 * 
	 * @return
	 */
	public void setCustomRetServiceUnavailabled() {
		this.retCode = ResultCode.USER_RCODE_SERVICE_UNAVAILABLE;
		this.retMsg = "The service is not supported."; //"지원하지 않는 서비스입니다.";
	}

	/**
	 * 사용자 정보 불일치 api response 객체용 Code(900-WRONG_USERINFO), Msg Set
	 * 
	 * @return
	 */
	public void setCustomRetWrongUserInfo() {
		this.retCode = ResultCode.USER_RCODE_WRONG_USERINFO;
		this.retMsg = "User information does not match."; //"사용자 정보가 일치하지 않습니다.";
	}

	/**
	 * Con-Key 불일치 api response 객체용 Code(901-WRONG_CONKEY), Msg Set
	 * 
	 * @return
	 */
	public void setCustomRetWrongConKey() {
		this.retCode = ResultCode.USER_RCODE_WRONG_CONKEY;
		this.retMsg = "Con-key does not match."; //"Con-Key가 일치하지 않습니다.";
	}
	
	
	/**
	 * Session-Key 불일치 api response 객체용 Code(901-WRONG_CONKEY), Msg Set
	 * 
	 * @return
	 */
	public void setCustomRetWrongSessKey() {
		this.retCode = ResultCode.USER_RCODE_WRONG_CONKEY;
		this.retMsg = "Session-Key does not match."; //"Session-Key가 일치하지 않습니다.";
	}
	
	/**
	 * MAC 주소가 등록되지 않은 단말은 사용불가 Code(905)
	 * 
	 * @return
	 */
	public void setCustomRetInvalidTerminal() {
	    this.retCode = ResultCode.USER_RCODE_INVALID_USER_TERMINAL;
	    this.retMsg = "It is an unregistered device."; //"등록되지 않은 단말입니다.";
    }
	
	/**
	 * 요청된 작업 처리 실패 Code(906)
	 * 
	 * @return
	 */	
	public void setCustomRetJobFail() {
	    this.retCode = ResultCode.USER_RCODE_JOB_FAIL;
	    this.retMsg = "Job failed."; //"작업을 처리하지 못했습니다.";
    }


	/**
	 * return String format { (Code Number) Code Description }
	 * 
	 * @return
	 */
	public String logReturnCodeInfo() {
		return "(" + this.retCode + ") " + this.retMsg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
