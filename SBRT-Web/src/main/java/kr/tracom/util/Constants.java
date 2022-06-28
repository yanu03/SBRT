package kr.tracom.util;

public class Constants {
	public static final Integer SYSTEM_BIMS = 1; //통합운영관리
	public static final Integer SYSTEM_SBRT = 2; //차량운행관리
	public static final Integer SYSTEM_ALL = 3; //전체
	
	public static final String NODE_TYPE_CROSS = "NT001"; //교차로
	public static final String NODE_TYPE_BUSSTOP = "NT002"; //정류소
	public static final String NODE_TYPE_NORMAL = "NT003"; //일반노드
	public static final String NODE_TYPE_GARAGE = "NT004"; //차고지
	public static final String NODE_TYPE_VERTEX = "NT005"; //버텍스
	public static final String NODE_TYPE_SOUND = "NT006"; //음성노드
	public static final String NODE_TYPE_ENTRY = "NT007"; //교통진출입점
	public static final String NODE_TYPE_SIGNAL = "NT008"; //신호
	public static final String NODE_TYPE_DP = "NT009"; //DP(화면 표출용)
	public static final String NODE_TYPE_BOARD = "NT010"; //횡당보도
	
	public static final String MOCK_NODE_TYPE_CROSS = "101"; //교차로
	public static final String MOCK_NODE_TYPE_END = "102"; //시종점
	public static final String MOCK_NODE_TYPE_CHANGE = "103"; //변화점
	public static final String MOCK_NODE_TYPE_FAC = "104"; //도로시설물
	public static final String MOCK_NODE_TYPE_BOUNDARY = "105"; //행정경계
	public static final String MOCK_NODE_TYPE_CONNECTION = "106"; //접속부
	public static final String MOCK_NODE_TYPE_IC = "107"; //IC 및 JC
	
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
	
	public static class WayDivs {
		public static final String WD001 = "WD001"; //상행
		public static final String WD002 = "WD002"; //하행
		public static final String WD003 = "WD003"; //왕복
		public static final String WD004 = "WD004"; //순환
		public static final String WD005 = "WD005"; //없음
	}
	
	public static class DayDivs {
		public static final String DY001 = "DY001"; //평일
		public static final String DY002 = "DY002"; //휴일
	}
	
	public static String INTG_URL = "INTG_URL";
	public static String API_KEY = "KEY_API";
	public static String SYS_INFO = "SYS_INFO";
	public static String SY012 = "SY012"; //정류소 평균 정차시간
	
	//세종노선상세정보
	public static String SEJONG_ROUT_ID = "IT00000004";
	
	//공공데이터포털 노선연계
	public static String OPENAPI_ROUT_ID = "IT00000005";
	
	//공공데이터포털 노선_정류쟝연계
	public static String OPENAPI_ROUT_STA_ID = "IT00000006";
	
	//공공데이터포털 버스현재위치
	public static String OPENAPI_BUS_LOC_ID = "IT00000007";
	
	//공공데이터포털 도시코드 연계
	public static String OPENAPI_CITY_CODE_ID = "IT00000021";
	
	//국토교통부_(TAGO)_버스도착정보
	public static String OPENAPI_BIT_ID = "IT00000035";
	
	//===================테이블 사용 이전 함수========================
	//세종 노선검색 URL
	public static String URL_CODE_SEJONG_ROUT = "IU001";
	
	//공공데이터포털 노선검색 URL
	public static String URL_CODE_OPENAPI_ROUT = "IU002";
	public static String URL_CODE_OPENAPI_ROUT_STA = "IU003";
	//===================테이블 사용 이전 함수========================
	
	//공공데이터포털 노선검색 KEY
	public static String KEY_CODE_OPENAPI_ROUT = "KEY01";
	
		
	//통플 연동 관련
	//통플ID 자리수
	public static int IMP_ID_DIGIT = 10 ;
	
    public static class CSVForms {
    	public static final String ROW_SEPARATOR			= "\r\n";
    	public static final String COMMA					= ",";
    	public static final String VOICE_PLAYLIST_TITLE		= "Seq_No,Voice_Code,Audio_FileName,Start_Date,Expire_Date,Text (256Byte),ild\r\n";
    	
    	public static final String ROUTE_VERSION			= "VERSION:";
    	public static final String ROUTE_LIST				= "ROUT_ID,ROUT_NM,ROUT_ENM,VERSION,DESTI_NO,ROUT_SHAPE,DAY1,DAY2,SATDAY1,SATDAY2,SUNDAY1,SUNDAY2,REP_NAME";
    	public static final String COURSE_LIST				= "COURSE_ID,COURSE_NAME_KO,COURSE_NAME_EN";
    	public static final String COURSE_TITLE				= "COURSE_ID,SEQ,ROUT_ID";
    	public static final String ROUTE_BUSSTOP_TITLE		= "NODE_ID,NODE_NAME,TYPE,RANGE,X,Y,NODE_ENAME,TRANSIT_CODE,DOOR_OPEN,LOCATION_INFO";
    	public static final String ROUTE_NODELIST_TITLE		= "NODE_ID,NODE_NAME,TYPE,RANGE,X,Y";
    	public static final String ROUTE_TITLE				= "NODE_ID";
    	public static final String ROUTE_LINK_TITLE			= "LINK_ID,ST_NODE,ED_NODE,LEN,MAX_SPD,EVENT_MS";
    	public static final String VIDEO_PLAY_LIST			="Seq_No,Video_Type,Video_File,Start_Date,Expire_Date,Runtime";
    	public static final String ELEC_ROUTER				="TIME_KO,TIME_EN,CATEGORY,FRAME,FONT";
    	
    	public static final String GARAGE_TITLE		= "GARAGE_ID,GARAGE_NAME,COUNT,SEQ,X,Y"; //차고지 bhmin 220119
    }
	
    public static class PlayListVoiceTypes {
    	public static final int BUS_KR		= 0; // 한글정류장안내 음성
    	public static final int BUS_EN		= 1; // 영문정류장안내 음성
    	public static final int AD			= 2; // 홍보음성
    	public static final int ETC			= 3; // 기타음성
    	public static final int ROUTE		= 4; // 노선선택시 재생 음성
    }
    
    public static class VoiceTypes {
    	public static final String KR		= "K";	// 한국어
    	public static final String EN		= "E";	// 영어
    	public static final String US		= "U";	// 사용자 음성
    	public static final String RT		= "R";	// 노선별 음성
    }
    
    public static class Schedule {
    	public static final String ROW_SEPARATOR			= "\r\n";
    	public static final String TAB						= "\t";
    }
    
    public static int VHC_MAX_SPEED = 50; //차량 최대 속도
    
    

    public static class OperPlanCalc {

    	//변경타입 //0:없음, 1:변경운행, 2:단순 수정
    	public static final int CHG_TYPE_NONE = 0;
    	public static final int CHG_TYPE_CHG_OPER = 1;
    	public static final int CHG_TYPE_MODIFY = 2;

    	public static final int MAX_TRY_COUNT = 50;
    	
    	public static final float MAX_SPEED_DEFAULT = 50.0f;
    	public static final float MAX_SPEED_LIMIT = 59.0f;
    	public static final float MIN_SPEED_LIMIT = 35.0f;
    	public static final int LIMIT_DIFF_SEC = 35;
    	public static final int MAX_DELAY_SEC = 20;
    	public static final float STD_AAC = 1.67f; //기준가속(1.67)
    	public static final float STD_DEC = -2.5f; //기준감속(-2.5)
    	
    	
    	public static final String DL_CD_MAX_SPEED_DEFAULT = "SS020"; //BRT 제한속도(운행계획)
    	public static final String DL_CD_MAX_SPEED_LIMIT = "SS021"; //BRT 최대 제한속도(운행계획)	
    	public static final String DL_CD_MIN_SPEED_LIMIT = "SS010"; //목표 표정속도
    	public static final String DL_CD_LIMIT_DIFF_SEC = "SS022"; //종점 도착 시간 차(운행계획)	
    	public static final String DL_CD_MAX_DELAY_SEC = "SS023"; //정류소 최대 추가 대기 시간(운행계획)	
    	public static final String DL_CD_STD_AAC = "SS024"; //기준가속
    	public static final String DL_CD_STD_DEC = "SS025"; //기준감속

    }
    
    
    public static class DispatchType {

    	public static final int DISPATCH_TYPE_1 = 1; //일반메시지
    	public static final int DISPATCH_TYPE_2 = 2; //운행중 메시지
    	public static final int DISPATCH_TYPE_3 = 3; //정차중 메시지
    	
    	public static final int DISPATCH_LV_1 = 1;
    	public static final int DISPATCH_LV_2 = 2;
    	public static final int DISPATCH_LV_3 = 3;
    	
    }
    
    public static class VhcDistType {

        public static final String FIX = "FIX"; //고정배포
    	public static final String DESC = "DESC"; //순환배포(내림)
    	public static final String ASC = "ASC"; //순환배포(오름)
    	
    }

    public static String Separator = "TRACOM";
    public static String SsoCookieName = "TracomSSO";
    
    public static double AVG_ERAD =6376.5;//(6378.137)   //Average Earth Radius
    public static double PIE = 3.1415926535898;
}