var util = {
	
}

/**
 * �������ڿ�ä���
 * @param str : ��� ���ڿ�
 * @param padLen : �ִ� ä����� �ϴ� ����
 * @param padStr : ä������ϴ� ����(char)
 * @return string : ��� ����
 */
util.lpad = function (str, padLen, padStr) {
    if (padStr.length > padLen) {
        console.log("���� : ä����� �ϴ� ���ڿ��� ��û ���̺��� Ů�ϴ�");
        return str;
    }
    str += ""; // ���ڷ�
    padStr += ""; // ���ڷ�
    while (str.length < padLen)
        str = padStr + str;
    str = str.length >= padLen ? str.substring(0, padLen) : str;
    return str;
}


/**
 * �������ڿ�ä���

 * @param str : ��� ���ڿ�
 * @param padLen : �ִ� ä����� �ϴ� ����
 * @param padStr : ä������ϴ� ����(char)
 * @return string : ��� ����
 */
util.rpad = function (str, padLen, padStr) {
    if (padStr.length > padLen) {
        console.log("���� : ä����� �ϴ� ���ڿ��� ��û ���̺��� Ů�ϴ�");
        return str + "";
    }
    str += ""; // ���ڷ�
    padStr += ""; // ���ڷ�
    while (str.length < padLen)
        str += padStr;
    str = str.length >= padLen ? str.substring(0, padLen) : str;
    return str;
}

/**
 * ���� ��¥ ��������(����Ͻú���)

 * @param day : ��� ��¥
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
 * Ư�� �ڸ�����ŭ gps ���� �ڸ�

 * @param in_gps : ��� gps ��
 * @param point : �Ҽ��� �ڸ���
 * @return float : �����
 */
util.getDispGps = function(in_gps, point) {
	var index = Math.pow(10, point);
	return Math.floor(in_gps*index)/index;
}

/**
 * �ú��ʸ� �ʷ� �ٲ�

 * @param time : �ú��� ����
 * @return int : ����� ��
 */
util.timeToSecond = function(time){
	var hour = time.substring(0, 2);
	var minute = time.substring(3, 5);
	var second = time.substring(6, 8);
	var result = hour*3600 + minute*60 + second*1;
	return result;
}


/**
 * �ʸ� �ú��ʷ� �ٲ�

 * @param second : ��
 * @return String : ����� �ú���
 */
util.secondToTime = function(second){
	var hour = parseInt(second/3600);
	var minute = second%3600/60;
	var second = second%3600%60;

	return util.lpad(hour,2,"0") + ":" + util.lpad(minute,2,"0") + ":" +  util.lpad(second,2,"0");;
}

/**
 * �ð� ���� : 2���� �ú��ʸ� ���� �ʷ� return ��

 * @param stTm : ���۽ú���
 * @param edTm : ����ú���
 * @return int : ���� ��
 */
util.dateMinus = function(stTm, edTm){
	var stTmSec = util.timeToSecond(stTm);
	var edTmSec = util.timeToSecond(edTm);
	var result = edTmSec - stTmSec;
	return result;
}

/**
 * �ð� ���ϱ� : 2���� �ú��ʸ� ���ؼ� �ʷ� return ��

 * @param stTm : ���۽ú���
 * @param edTm : ����ú���
 * @return int : ���� ��
 */
util.datePlus = function(stTm, edTm){
	var stTmSec = util.timeToSecond(stTm);
	var edTmSec = util.timeToSecond(edTm);
	var result = edTmSec + stTmSec;
	return result;
}

/**
 * �ð� ���� : 2���� �ú��ʸ� ���� �ú��ʷ� return ��

 * @param stTm : ���۽ú���
 * @param edTm : ����ú���
 * @return int : ���� �ú���
 */
util.dateMinus2 = function(stTm, edTm){
	var stTmSec = util.timeToSecond(stTm);
	var edTmSec = util.timeToSecond(edTm);
	var result = edTmSec - stTmSec;
	
	return util.secondToTime(result);
}

/**
 * �ð� ���ϱ� : 2���� �ú��ʸ� ���ؼ� �ú��ʷ� return ��

 * @param stTm : ���۽ú���
 * @param edTm : ����ú���
 * @return int : ���� �ú���
 */
util.datePlus2 = function(stTm, edTm){
	var stTmSec = util.timeToSecond(stTm);
	var edTmSec = util.timeToSecond(edTm);
	var result = edTmSec + stTmSec;
	return util.secondToTime(result);
}

/**
 * ���� ��¥ ��������(�����)

 * @param day : ��� ��¥
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
 * ���� ��¥ ��������(��-��-��)

 * @param day : ��� ��¥
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
 * ���� ��¥�� ���ʷ� ��ȯ

 * @param exlDate : ���� ��¥
 * @return string : ��ȯ�� ����
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