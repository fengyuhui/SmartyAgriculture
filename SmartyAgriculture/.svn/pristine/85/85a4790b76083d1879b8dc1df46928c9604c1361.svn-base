package cn.bupt.smartyagl.util;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	
	public static String getDate(Date date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	//���format,String��Date
	public static Date getDateString(String date,String format){
			try {
				
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				Date d = sdf.parse(date);
				return d;
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
	}
	
	//YYYY-MM-dd,HH:mm
	public static Date getDateStringMm(String date){
		return getDateString(date,"yyyy-MM-dd,HH:mm");
	}
	
	//YYYY-MM-dd,HH:mm
	public static String getDateMm(Date date){
		return getDate(date, "yyyy-MM-dd,HH:mm");
	}
	/**
	 * 获取当前时间的年月日
	 * @return
	 */
	public static String getYMD(){
		Calendar calendar= Calendar.getInstance();
		int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(Calendar.MONTH)+1;
		int day=calendar.get(Calendar.DAY_OF_MONTH);
		String returnString=String.valueOf(year)+"-"
		+String.valueOf(month)+"-"+String.valueOf(day);
		return returnString;
	}
	/**
	 * 获取当前时间
	 * @return
	 */
	public static Date getNow(){
		String now=getYMD();
		Date date=getDateString(now,"yyyy-MM-dd");
		return date;
		
	}

}
