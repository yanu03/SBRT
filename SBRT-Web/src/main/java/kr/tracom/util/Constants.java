package kr.tracom.util;

public class Constants {
	public static final Integer SYSTEM_BIS = 1; //통합운영관리
	public static final Integer SYSTEM_BRT = 2; //차량운행관리
	public static final Integer SYSTEM_ALL = 3; //전체
	
	public static final String NODE_TYPE_1 = "NT01"; //교차로
	public static final String NODE_TYPE_2 = "NT02"; //정류소
	public static final String NODE_TYPE_3 = "NT03"; //일반노드
	public static final String NODE_TYPE_4 = "NT04"; //차고지
	public static final String NODE_TYPE_5 = "NT05"; //버텍스
	public static final String NODE_TYPE_6 = "NT06"; //음성노드
	public static final String NODE_TYPE_7 = "NT07"; //교통진출입점
	
	/**
	 *
	 */
	public static final String TRACOM_DATA_KEY = "TRACOM_DATA_KEY";
	
	/**
	 * 세션 키 값 정의
	 */
	public static final String SSN_USER_ID = "SSN_USER_ID";
	public static final String SSN_USER_NM = "SSN_USER_NM";
	public static final String SSN_SYSTEM_BIT = "SSN_SYSTEM_BIT";
	public static final String SSN_CUR_SYSTEM = "SSN_CUR_SYSTEM";
	public static final String SSN_IS_ADMIN = "SSN_IS_ADMIN";
	public static final String SSN_MENU_LIST = "SSN_MENU_LIST";
	
	public static final String UPD_DTM = "UPD_DTM";
	public static final String SSN_DELETED = "SSN_DELETED"; //세션 초기화중
	
	
	public static String INTG_URL = "INTG_URL";
	public static String API_KEY = "KEY_API";
	
	//세종 노선검색 URL
	public static String URL_CODE_SEJONG_ROUT = "ITU01";
	
	//공공데이터포털 노선검색 URL
	public static String URL_CODE_OPENAPI_ROUT = "ITU02";
	public static String URL_CODE_OPENAPI_ROUT_STA = "ITU03";
	
	//공공데이터포털 노선검색 KEY
	public static String KEY_CODE_OPENAPI_ROUT = "KEY01";
}