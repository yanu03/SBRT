//util 전역변수
var util = {
	ATTR_ID : {
		BUS_ARRIVAL_INFO : 2022 //정류소 차량 도착정보
	,	TRAFFIC_STATION_ARRIVAL : 4110
	,	TRAFFIC_STATION_LEAVE : 4112
	,	TRAFFIC_SBRT_LOCATION_GPS : 4120
	,	TRAFFIC_BUS_LOCATION_GPS : 4122
	,	TRAFFIC_LINK_SPEED : 4130 
	,	TRAFFIC_DOOR_OPEN_CLOSE : 4140
	,	TRAFFIC_OPER_ALLOC_PLAN : 4150
	,	TRAFFIC_PLAN_INFO_REQUEST : 4210
	,	TRAFFIC_TOD_PLAN_RESPONSE : 4220
	,	TRAFFIC_BASE_PLAN_RESPONSE : 4230
	,	TRAFFIC_HOLIDAY_PLAN_RESPONSE : 4240
	,	TRAFFIC_WEEKLY_PLAN_RESPONSE : 4250
	,	TRAFFIC_MODULE_ONE : 4310
	,	TRAFFIC_MODULE_TWO : 4320
	,	TRAFFIC_MODULE_THREE : 4330
	,	TRAFFIC_CHANGED_TOD_INFO : 4340
	,	TRAFFIC_LIGHT_STATUS_REQUEST : 4350
	,	TRAFFIC_LIGHT_STATUS_RESPONSE : 4360 //신호등 현시
	,	STANDARD_VIOLATE_INFORMATION : 4000
	,	POWER_ON_OFF : 4010
	,	BUS_OPER_EVENT : 4012 //운행 이벤트
	,	BUS_INFO : 4014 //실시간 차량 위치 정보
	,	DISPATCH : 4020 //디스패치
	,	DISPATCH_LIST : 4021
	,	AIRCON_INFO : 5050
	,	SCREENDOOR_INFO : 5051
  , PPC_INFO : 5052 // 피플카운트, 확인후 수정해야함
	},
	
	MSG : {
		DISPATCH_MSG_NORMAL : "안전운행 감사합니다."
	,	DISPATCH_MSG_DELAY : "초 지연운행 중입니다. 조금만 빨리 이동 부탁드립니다."
	,	DISPATCH_MSG_SPEED : "초 과속운행 중입니다. 조금만 천천히 이동 부탁드립니다."
	,	DISPATCH_MSG_STOP : "초 정차 예정입니다."
	,	DISPATCH_MSG_SIGNAL_2 :  "정차제어 {0}초 정차 예정입니다." //정차제어 메시지 예) util.format(util.MSG.DISPATCH_MSG_SIGNAL_2,"123")
	,	DISPATCH_MSG_SIGNAL_3 :   "우선신호제어({0})"
	}
}

/**
 * 좌측문자열채우기
 * @param str : 대상 문자열
 * @param padLen : 최대 채우고자 하는 길이
 * @param padStr : 채우고자하는 문자(char)
 * @return string : 결과 문구
 */
util.lpad = function (str, padLen, padStr) {
    if (padStr.length > padLen) {
        console.log("오류 : 채우고자 하는 문자열이 요청 길이보다 큽니다");
        return str;
    }
    str += ""; // 문자로
    padStr += ""; // 문자로
    while (str.length < padLen)
        str = padStr + str;
    str = str.length >= padLen ? str.substring(0, padLen) : str;
    return str;
}


/**
 * 우측문자열채우기

 * @param str : 대상 문자열
 * @param padLen : 최대 채우고자 하는 길이
 * @param padStr : 채우고자하는 문자(char)
 * @return string : 결과 문구
 */
util.rpad = function (str, padLen, padStr) {
    if (padStr.length > padLen) {
        console.log("오류 : 채우고자 하는 문자열이 요청 길이보다 큽니다");
        return str + "";
    }
    str += ""; // 문자로
    padStr += ""; // 문자로
    while (str.length < padLen)
        str += padStr;
    str = str.length >= padLen ? str.substring(0, padLen) : str;
    return str;
}

/**
 * 현재 날짜 가져오기(년월일시분초)

 * @param day : 대상 날짜
 */
util.getCurrentDate = function(day) {
	var date = new Date();
	if(com.isEmpty(day)==false)date.setDate(date.getDate()+day);
	var year = date.getFullYear().toString();
	
	var month = date.getMonth() + 1;
	month = month < 10 ? '0' + month.toString() : month.toString();
	
	var day = date.getDate();
	day = day < 10 ? '0' + day.toString() : day.toString();
	
	var hour = date.getHours();
	hour = hour < 10 ? '0' + hour.toString() : hour.toString();
	
	var minites = date.getMinutes();
	minites = minites < 10 ? '0' + minites.toString() : minites.toString();
	
	var seconds = date.getSeconds();
	seconds = seconds < 10 ? '0' + seconds.toString() : seconds.toString();
	
	return year + month + day + hour + minites + seconds;
}

/**
 * 특정 자리수만큼 gps 값을 자름

 * @param in_gps : 대상 gps 값
 * @param point : 소수점 자리수
 * @return float : 결과값
 */
util.getDispGps = function(in_gps, point) {
	var index = Math.pow(10, point);
	return Math.floor(in_gps*index)/index;
}

/**
 * 시분초를 초로 바꿈

 * @param time : 시분초 문구
 * @return int : 변경된 초
 */
util.timeToSecond = function(time){
	var hour = time.substring(0, 2);
	var minute = time.substring(3, 5);
	var second = time.substring(6, 8);
	var result = hour*3600 + minute*60 + second*1;
	return result;
}


/**
 * 초를 시분초로 바꿈

 * @param second : 초
 * @return String : 변경된 시분초
 */
util.secondToTime = function(second){
	var hour = parseInt(second/3600);
	var minute = second%3600/60;
	var second = second%3600%60;

	return util.lpad(hour,2,"0") + ":" + util.lpad(minute,2,"0") + ":" +  util.lpad(second,2,"0");
}

util.getToday = function(){
	var today = new Date(); 
	return util.lpad(today.getHours(),2,"0") + ":" + util.lpad(today.getMinutes(),2,"0") + ":" +  util.lpad(today.getSeconds(),2,"0");
}

/**
 * 시간 빼기 : 2개의 시분초를 빼서 초로 return 함

 * @param stTm : 시작시분초
 * @param edTm : 종료시분초
 * @return int : 계산된 초
 */
util.dateMinus = function(stTm, edTm){
	var stTmSec = util.timeToSecond(stTm);
	var edTmSec = util.timeToSecond(edTm);
	var result = edTmSec - stTmSec;
	return result;
}

/**
 * 시간 더하기 : 2개의 시분초를 더해서 초로 return 함

 * @param stTm : 시작시분초
 * @param edTm : 종료시분초
 * @return int : 계산된 초
 */
util.datePlus = function(stTm, edTm){
	var stTmSec = util.timeToSecond(stTm);
	var edTmSec = util.timeToSecond(edTm);
	var result = edTmSec + stTmSec;
	return result;
}

/**
 * 시간 빼기 : 2개의 시분초를 빼서 시분초로 return 함

 * @param stTm : 시작시분초
 * @param edTm : 종료시분초
 * @return int : 계산된 시분초
 */
util.dateMinus2 = function(stTm, edTm){
	var stTmSec = util.timeToSecond(stTm);
	var edTmSec = util.timeToSecond(edTm);
	var result = edTmSec - stTmSec;
	
	return util.secondToTime(result);
}

/**
 * 시간 더하기 : 2개의 시분초를 더해서 시분초로 return 함

 * @param stTm : 시작시분초
 * @param edTm : 종료시분초
 * @return int : 계산된 시분초
 */
util.datePlus2 = function(stTm, edTm){
	var stTmSec = util.timeToSecond(stTm);
	var edTmSec = util.timeToSecond(edTm);
	var result = edTmSec + stTmSec;
	return util.secondToTime(result);
}

/**
 * 현재 날짜 가져오기(년월일)

 * @param day : 대상 날짜
 */
util.getCurrentDate2 = function(day){
	var date = new Date();
	if(com.isEmpty(day)==false)date.setDate(date.getDate()+day);
	var year = date.getFullYear().toString();

	var month = date.getMonth() + 1;
	month = month < 10 ? '0' + month.toString() : month.toString();

	var day = date.getDate();
	day = day < 10 ? '0' + day.toString() : day.toString();

	return year + month + day;
}

/**
 * 현재 날짜 가져오기(년-월-일)

 * @param day : 대상 날짜
 */
util.getCurrentDate3 = function(day){
	var date = new Date();
	if(com.isEmpty(day)==false)date.setDate(date.getDate()+day);
	var year = date.getFullYear().toString();

	var month = date.getMonth() + 1;
	month = month < 10 ? '0' + month.toString() : month.toString();

	var day = date.getDate();
	day = day < 10 ? '0' + day.toString() : day.toString();

	return year + "-" + month + "-" + day;
}

/**
 * 엑셀 날짜를 분초로 변환

 * @param exlDate : 엑셀 날짜
 * @return string : 변환한 분초
 */
util.getExlDateToMinSec = function(exlDate) {
	if(exlDate.length>5){
		var date = new Date(exlDate);
	
		var hour = date.getHours();
		hour = hour < 10 ? '0' + hour.toString() : hour.toString();
		
		var minites = date.getMinutes();
		minites = minites < 10 ? '0' + minites.toString() : minites.toString();
		
		var seconds = date.getSeconds();
		seconds = seconds < 10 ? '0' + seconds.toString() : seconds.toString();
		
		return minites + ":" + seconds;
	}
	else return exlDate;
}

/**
 * 일시 변경 (년-월-일 시:분:초)
 * 
 * @param dateTime : 년월일시분초
 */
util.getDateTime = function(dateTime){
	var output = dateTime.substring(0, 4) + "-" + dateTime.substring(4, 6) + "-" + dateTime.substring(6, 8) + " " + dateTime.substring(8, 10) + ":" + 
					dateTime.substring(10, 12) + ":" + dateTime.substring(12);
	return output;
}


util.format = function() {
	var args = Array.prototype.slice.call(arguments, 1);
	return arguments[0].replace(/\{(\d+)\}/g, function(match, index) {
		return args[index];
	});
}