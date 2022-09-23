package kr.tracom.cm.support.http;

public class ResultCode {

    /**
     * 조회 요청에 따른 정상 결과(OK)에 대한 리턴 코드, HttpStatus.OK 200
     */
    public static final String USER_RCODE_OK = "200"; 					// HttpStatus.OK 200
    /**
     * 생성 요청에 따른 정상 생성(OK)에 대한 리턴 코드, HttpStatus.CREATED 201
     */
    public static final String USER_RCODE_CREATED = "201"; 				// HttpStatus.CREATED 201
    /**
     * 비동기 요청에 따른 정상 요청 응답에 대한 리턴 코드, HttpStatus.ACCEPTED 202
     */
    public static final String USER_RCODE_ACCEPTED = "202"; 				// HttpStatus.ACCEPTED 202
    /**
     * 삭제 또는 Action 요청에 따른 정상 처리에 대한 리턴 코드, HttpStatus.NO_CONTENT 204
     */
    public static final String USER_RCODE_NO_CONTENT = "204"; 			// HttpStatus.NO_CONTENT 204

    // ########## 요청 오류 리턴
    /**
     * ##### Not Found Resource에 대한 리턴 코드, HttpStatus.BAD_REQUEST 400
     */
    public static final String USER_RCODE_NOT_EXIST_RESOURCE = "400"; 	// HttpStatus.BAD_REQUEST 400
    /**
     * 비정상적인 요청에 대한 리턴 코드, HttpStatus.BAD_REQUEST 400
     */
    public static final String USER_RCODE_BAD_REQUEST = "400"; 			// HttpStatus.BAD_REQUEST 400
    /**
     * 인증되지 않은 사용자/앱의 요청에 대한 리턴 코드, HttpStatus.UNAUTHORIZED 401
     */
    public static final String USER_RCODE_UNAUTHORIZED = "401"; 			// HttpStatus.UNAUTHORIZED 401
    /**
     * Not Found Resource에 대한 리턴 코드, HttpStatus.NOT_FOUND 404
     */
    public static final String USER_RCODE_NOT_FOUND = "404"; 			// HttpStatus.NOT_FOUND 404
    /**
     * 권한이 없거나 지원하지 않는 요청에 대한 리턴 코드, HttpStatus.METHOD_NOT_ALLOWED 405
     */
    public static final String USER_RCODE_METHOD_NOT_ALLOWED = "405"; 	// HttpStatus.METHOD_NOT_ALLOWED 405
    /**
     * Header 정보가 없가나 적합하지 않은 요청에 대한 리턴 코드, HttpStatus.NOT_ACCEPTABLE 406
     */
    public static final String USER_RCODE_NOT_ACCEPTABLE = "406"; 		// HttpStatus.NOT_ACCEPTABLE 406
    /**
     * 요청에 대한 지연 처리로 인한 Timeout에 대한 리턴 코드, HttpStatus.REQUEST_TIMEOUT 408
     */
    public static final String USER_RCODE_REQUEST_TIMEOUT = "408"; 		// HttpStatus.REQUEST_TIMEOUT 408
    /**
     * 요청에 대한 자원의 중복 또는 충돌에 대한 리턴 코드, HttpStatus.CONFLICT 409
     */
    public static final String USER_RCODE_CONFLICT = "409"; 				// HttpStatus.CONFLICT 409

    // ########## 서버 내부 오류 리턴
    /**
     * 서비스 내부 서버 오류로 인한 응답에 대한 리턴 코드, HttpStatus.INTERNAL_SERVER_ERROR 500
     */
    public static final String USER_RCODE_INTERNAL_SERVER_ERROR = "500"; // HttpStatus.INTERNAL_SERVER_ERROR 500
    /**
     * 실행 불가한 서비스 요청에 대한 응답에 대한 리턴 코드, HttpStatus.NOT_IMPLEMENTED 501
     */
    public static final String USER_RCODE_NOT_IMPLEMENTED = "501"; 		// HttpStatus.NOT_IMPLEMENTED 501
    /**
     * 하이퍼바이저 연결오류에 대한 리턴 코드, HttpStatus.BAD_GATEWAY 502
     */
    public static final String USER_RCODE_BAD_GATEWAY = "502"; 			// HttpStatus.BAD_GATEWAY 502
    /**
     * 지원하지 않는 서비스 요청에 대한 리턴 코드, HttpStatus.SERVICE_UNAVAILABLE 503
     */
    public static final String USER_RCODE_SERVICE_UNAVAILABLE = "503"; 	// HttpStatus.SERVICE_UNAVAILABLE 503
    /**
     * 하이퍼바이저 연결시간 초과에 대한 리턴 코드, HttpStatus.GATEWAY_TIMEOUT 504
     */
    public static final String USER_RCODE_GATEWAY_TIMEOUT = "504"; 		// HttpStatus.GATEWAY_TIMEOUT 504



    /**
     * 460 유효하지 않은 API에 대한 리턴 코드, HttpStatus.없음
     */
    public static final String USER_RCODE_INVALID_API = "460"; // HttpStatus.없음
    // 460
    /**
     * 470 필수 파라미터에 대한 리턴 코드, HttpStatus.없음
     */
    public static final String USER_RCODE_REQUIRED_PARAM = "470"; // HttpStatus.없음
    // 470
    /**
     * 471 필수 파라미터가 유효성에 대한 리턴 코드, HttpStatus.없음
     */
    public static final String USER_RCODE_INVALID_PARAM = "471"; // HttpStatus.없음
    // 471
    /**
     * 900 사용자 정보 불일치에 대한 리턴 코드, HttpStatus.없음
     */
    public static final String USER_RCODE_WRONG_USERINFO = "900"; // HttpStatus.없음
    // 900
    /**
     * 901 Con-Key 불일치에 대한 리턴 코드, HttpStatus.없음
     */
    public static final String USER_RCODE_WRONG_CONKEY = "901"; // HttpStatus.없음
    // 901

    /**
     * 905 등록되지 않은 사용자 단말에 대한 리턴 코드
     */
    public static final String USER_RCODE_INVALID_USER_TERMINAL = "905";


    /**
     * 906 요청에 대한 작업 실패
     */
    public static final String USER_RCODE_JOB_FAIL = "906";


}
