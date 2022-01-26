var util = {
	
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

	return util.lpad(hour,2,"0") + ":" + util.lpad(minute,2,"0") + ":" +  util.lpad(second,2,"0");;
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