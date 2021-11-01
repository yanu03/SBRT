var util = {
	
}

/**
 * 좌측문자열채우기
 * @params
 *  - str : 원 문자열
 *  - padLen : 최대 채우고자 하는 길이
 *  - padStr : 채우고자하는 문자(char)
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
 * @params
 *  - str : 원 문자열
 *  - padLen : 최대 채우고자 하는 길이
 *  - padStr : 채우고자하는 문자(char)
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

util.getDispGps = function(in_gps, point) {
	var index = Math.pow(10, point);
	return Math.floor(in_gps*index)/index;
}

util.timeToSecond = function(time){
	var hour = time.substring(0, 2);
	var minute = time.substring(3, 5);
	var second = time.substring(6, 8);
	var result = hour*3600 + minute*60 + second*1;
	return result;
}

util.secondToTime = function(second){
	var hour = parseInt(second/3600);
	var minute = second%3600/60;
	var second = second%3600%60;

	return util.lpad(hour,2,"0") + ":" + util.lpad(minute,2,"0") + ":" +  util.lpad(second,2,"0");;
}

util.dateMinus = function(stTm, edTm){
	var stTmSec = util.timeToSecond(stTm);
	var edTmSec = util.timeToSecond(edTm);
	var result = edTmSec - stTmSec;
	return result;
}

util.datePlus = function(stTm, edTm){
	var stTmSec = util.timeToSecond(stTm);
	var edTmSec = util.timeToSecond(edTm);
	var result = edTmSec + stTmSec;
	return result;
}

util.dateMinus2 = function(stTm, edTm){
	var stTmSec = util.timeToSecond(stTm);
	var edTmSec = util.timeToSecond(edTm);
	var result = edTmSec - stTmSec;
	return result;
}

util.datePlus2 = function(stTm, edTmSec){
	var stTmSec = util.timeToSecond(stTm);
	var result = edTmSec + stTmSec;
	return result;
}

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