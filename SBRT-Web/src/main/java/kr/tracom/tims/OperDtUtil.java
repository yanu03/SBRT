package kr.tracom.tims;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OperDtUtil {

	private static Calendar oCal = null;
	private static SimpleDateFormat oFormat = null;

	public OperDtUtil() {
		this.oCal = Calendar.getInstance();
		this.oFormat = new SimpleDateFormat();
	}
	
	
	//운행일 가져오기
	public static String convertTimeToOperDt(String time, String pattern) {
		
		String operDt = "1900-01-01";
		String convtTime = "";

		try {
				
			SimpleDateFormat transFormat = new SimpleDateFormat(pattern);
			Date date = transFormat.parse(time);

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

			int hour = cal.get(Calendar.HOUR_OF_DAY);
			
			//24시부터 02시까지는 이전날짜 운행일로 변경
			if (hour >= 0 && hour <= 2) {
				cal.add(Calendar.DATE, -1);
			}
			
			convtTime = transFormat.format(cal.getTime());
			operDt = convtTime.substring(0, 10);
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return operDt;
		
	}
	
	
	
}
