package com.example.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtail {

	private static Logger logger = LoggerFactory.getLogger(DateUtail.class);

	/**
     * 
     */
	public static final String TIMEZONE_GMT8 = "GMT+08:00";
	/**
	 * DDMMyy
	 */
	public static final String DATE_FORMAT_DMY = "ddMMMyy";
	/**
	 * DDMMMyyyy/HH:mm
	 */
	public static final String DATE_FORMAT_DMYHM = "ddMMMyyyy/HH:mm";
	/**
	 * ddMMM
	 */
	public static final String DATE_FORMAT_DM = "ddMMM";
	
	/**
	 * ddMMMHHmm
	 * 17JUN0230
	 */
	public static final String DATE_FORMAT_DMHM = "ddMMMHHmm";
	/**
	 * MMddyy
	 */
	public static final String DATE_FORMAT_MDY = "MMddyy";

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String DATE_FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String DATE_FORMAT_DATE_HOUR = "yyyy-MM-dd HH";
	
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String DATE_FORMAT_LONG_YMDHMS = "yyyyMMddHHmmss";

	/**
	 * yyyy-MM-dd
	 */
	public static final String DATE_FORMAT_SHORT = "yyyy-MM-dd";
	/**
	 * yyyy-MM-dd
	 */
	public static final String DATECHINESS_FORMAT_SHORT = "yyyy年MM月dd日";
	/**
	 * yyyy/MM/dd
	 */
	public static final String DATE_FORMAT_SLANTLINE = "yyyy/MM/dd";

	/**
	 * yyyy-MM-dd HH:mm
	 */
	public static final String DATE_FORMAT_MINUTE = "yyyy-MM-dd HH:mm";
	/**
	 * yyyyMMddHHmm
	 */
	public static final String DATE_FORMAT_MINUTE_YMDHM = "yyyyMMddHHmm";
	/**
	 * HH:mm
	 */
	public static final String DATE_FORMAT_HM = "HH:mm";
	/**
	 * HH:mm
	 */
	public static final String DATE_FORMAT_HM_SS = "HH:mm:ss";
	/**
	 * yyyy
	 */
	public static final String DATE_FORMAT_YYYY = "yyyy";
	/**
	 * MM
	 */
	public static final String DATE_FORMAT_MM = "MM";
	/**
	 * dd
	 */
	public static final String DATE_FORMAT_DD = "dd";
	/**
	 * EEEddMMM
	 */
	public static final String DATE_FORMAT_EDM = "EEEddMMM";

	/**
	 * DATE_HHMMSS_FORMATE:TODO（描述变量用途） yyyy-MM-dd hh:mm:ss.SSS 格式常量
	 */
	public static String DATE_HHMMSSSSS_FORMATE = "yyyy-MM-dd HH:mm:ss.SSS";
	
	public static String DATE_FORMAT_MILLISECOND ="yyyyMMddHHmmssSS";
	
	/**
	 * yyyyMMdd
	 */
	public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";

	public static final String DATE_FORMAT_CHINA = "yyyyMM月";
	
	/**
	 * 格式化时间
	 * @param time 201500
	 * @return 20：15：00
	 */
	public static String formatTime(String time) {
		StringBuffer result = new StringBuffer();
		if (StringUtil.stringNotNull(time)) {
			String h=time.substring(0, 2);
			String m=time.substring(2, 4);
			String s="";
			if (time.length()>4) {
				s=time.substring(4, 6);
			}
			
			result.append(h+":");
			result.append(m+":");
			if (StringUtil.isEmpty(s)) {
				result.append("00");
			}
			result.append(s);
		}
		return result.toString();
	}
	/**
	 * 
	 * 
	 * @param currentDate
	 * @param timeZoneId
	 * @return
	 */
	public static Date getDateInTimeZone(final Date currentDate, final String timeZoneId) {

		String timeZone = timeZoneId;
		//
		if (timeZoneId == null || timeZoneId.trim().equals("")) {
			timeZone = TIMEZONE_GMT8;
		}

		Calendar mbCal = new GregorianCalendar(TimeZone.getTimeZone(timeZone));
		mbCal.setTimeInMillis(currentDate.getTime());

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, mbCal.get(Calendar.YEAR));
		cal.set(Calendar.MONTH, mbCal.get(Calendar.MONTH));
		cal.set(Calendar.DAY_OF_MONTH, mbCal.get(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, mbCal.get(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, mbCal.get(Calendar.MINUTE));
		cal.set(Calendar.SECOND, mbCal.get(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, mbCal.get(Calendar.MILLISECOND));

		return cal.getTime();
	}

	/**
	 * 按一定的格式，获取时间字符串。
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateStr(Date date, String dateFormate) {
		String result = "";
		if (date != null) {
			java.text.DateFormat df = new java.text.SimpleDateFormat(dateFormate);
			result = df.format(date);
		}
		return result;
	}

	/**
	 * 按一定的格式，获取时间英文字符串。 ddMMM时，会出现崇文的问题，所以指定US Locale
	 *
	 * @param date
	 * @return
	 */
	public static String getDateUsStr(Date date, String dateFormate) {
		String result = "";
		if (date != null) {
			java.text.DateFormat df = new java.text.SimpleDateFormat(dateFormate, new Locale("US"));
			result = df.format(date);
		}
		return result;
	}

	/**
	 *
	 * getDateFlightSegmentStr: 获取rt结果航段日期格式，例如TH03DEC
	 *
	 * @param date
	 * @return String 日期例如:TH03DEC
	 *
	 * @author
	 *
	 * @see
	 *
	 */
	public static String getDateFlightSegmentStr(Date date) {
		String dateFormate = "EEddMMM";
		java.text.DateFormat df = new java.text.SimpleDateFormat(dateFormate, new Locale("US"));
		String time = df.format(date).toUpperCase();
		time = time.substring(0, 2) + time.substring(3, time.length());
		return time;
	}

	/**
	 *
	 *
	 * @param date
	 * @param timeZoneId
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static Date formatDateInTimeZone(final String date, final String timeZoneId, final String format) throws Exception {
		//
		if (date == null || date.trim() == "") {
			return null;
		}

		//
		String timeZone = timeZoneId;
		if (timeZoneId == null || timeZoneId.trim().equals("")) {
			timeZone = TIMEZONE_GMT8;
		}
		SimpleDateFormat fmt = new SimpleDateFormat(format, new Locale("US"));
		//
		fmt.setTimeZone(TimeZone.getTimeZone(timeZone));
		return fmt.parse(date);
	}

	/**
	 *
	 *
	 * @param date
	 * @param timeZoneId
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static String getFormatDateInTimeZone(final Date date, final String timeZoneId, final String format) {
		//
		String timeZone = timeZoneId;
		if (timeZoneId == null || timeZoneId.trim().equals("")) {
			timeZone = TIMEZONE_GMT8;
		}
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		//
		fmt.setTimeZone(TimeZone.getTimeZone(timeZone));
		return fmt.format(date);
	}

	/**
	 * 根据日期和格式转换成字符串格式
	 *
	 * @param date
	 * @param format
	 * @return
	 * @since 1.0
	 * @author
	 */
	public static String timeToFormatString(Date date, String format) {

		if (date != null) {

			SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

			return sdf.format(date);
		}

		return null;
	}

	/**
	 * 增加月数
	 *
	 * @param date
	 * @return
	 * @since 1.0
	 * @author
	 */
	public static Date addMonth(final Date date, final int month) {

		if (date != null) {

			Calendar calendar = new GregorianCalendar();

			calendar.setTime(date);

			calendar.add(Calendar.MONTH, month);

			return calendar.getTime();

		}

		else {

			return null;
		}
	}

	/**
	 * 增加天数
	 *
	 * @param date
	 * @param days
	 * @return
	 * @since 1.0
	 * @author
	 */
	public static Date addDays(final Date date, final int days) {

		if (date != null) {

			Calendar calendar = new GregorianCalendar();

			calendar.setTime(date);

			calendar.add(Calendar.DATE, days);

			return calendar.getTime();

		}

		else {

			return null;
		}
	}

	/**
	 * 给指定的日期添加几个月
	 * @描述:
	 * @方法名: addMonths
	 * @param date
	 * @param months
	 * @return
	 * @返回类型 Date
	 * @创建人 ningsw	@创建时间 2018年9月20日 下午5:40:11
	 * @修改人 ningsw	@修改时间 2018年9月20日 下午5:40:11
	 * @修改备注
	 * @since
	 * @throws
	 */
	public static Date addMonths(final Date date, final int months) {
		if (date != null) {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, months);
			return calendar.getTime();
		} else {
			return null;
		}
	}

	public static Date addMinutes(final Date date, final int minutes) {
		if (date != null) {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.MINUTE, minutes);
			return calendar.getTime();
		} else {
			return null;
		}
	}

	/**
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 * @author WangXiaopeng
	 */
	public static Date timeToFormat(Date date, String format) throws Exception {
		if (date != null) {

			SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

			return sdf.parse(sdf.format(date));
		}

		return null;
	}
	   /*
     * 获取某年第一天日期
     */
    public static Date getYearFirst(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, Integer.parseInt(getDateUsStr(date, DATE_FORMAT_YYYY)));
        Date currYearFirst = calendar.getTime();

        return currYearFirst;
    }

	/**
	 * 将字符串转换成日期
	 *
	 * @param date
	 * @return
	 * @throws ParseException
	 * @since 1.0
	 * @author
	 */
	public static Date StringToDate(String date) throws ParseException {
		if (date != null && !date.equals("")) {
			SimpleDateFormat sdf = new SimpleDateFormat(DateUtail.DATE_FORMAT_SHORT);
			return sdf.parse(date);
		}
		return null;
	}

	public static Time StringToTime(String date, String format) throws ParseException {
		if (date != null && !date.equals("")) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date d=sdf.parse(date);
			Time time = new Time(d.getTime());
			return time;
		}
		return null;
	}

	public static String getTimeChangeStr(Time time, String format) throws ParseException {
		String result="";
		if (time != null && !time.equals("")) {
			java.text.DateFormat df = new java.text.SimpleDateFormat(format);
			result = df.format(time);
		}
		return result;
	}



	/**
	 * 将字符串转换成日期
	 *
	 * @param date
	 * @return
	 * @throws ParseException
	 * @since 1.0
	 * @author
	 */
	public static Date usStringToDate(String date, String format) throws ParseException {
		if (date != null && !date.equals("")) {
			SimpleDateFormat sdf = new SimpleDateFormat(format, new Locale("US"));
			return sdf.parse(date);
		}
		return null;
	}

	/**
	 * 将字符串转换成日期
	 *
	 * @param date
	 * @return
	 * @throws ParseException
	 * @since 1.0
	 * @author
	 */
	public static Date timeStringToDate(String date) throws ParseException {
		if (date != null && !date.equals("")) {
			SimpleDateFormat sdf = new SimpleDateFormat(DateUtail.DATE_FORMAT_LONG);
			return sdf.parse(date);
		}

		return null;
	}

	/**
	 *
	 * formatXmlToDate: 转换为Date类型
	 *
	 * @param dateXml
	 *            xml的date类型
	 * @return Date
	 *
	 * @author：
	 *
	 *
	 */
	public static Date formatXmlToDate(XMLGregorianCalendar dateXml) {
		XMLGregorianCalendar dateType = dateXml;
		int year = dateType.getYear();
		int month = dateType.getMonth();
		int day = dateType.getDay();
		int hour = dateType.getHour();
		int minute = dateType.getMinute();
		int second = dateType.getSecond();

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day, hour, minute, second);
		Date date = calendar.getTime();
		return date;
	}

	/**
	 *
	 * formatXmlTime: 转换为XMLGregorianCalendar类型（完整时间）
	 *
	 * @param date
	 * @return XMLGregorianCalendar 例如：yyyy-MM-ddTHH:mm:ss.SSS+08:00
	 *
	 *
	 */
	public static XMLGregorianCalendar formatXmlTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		javax.xml.datatype.DatatypeFactory dtf = null;
		try {
			dtf = javax.xml.datatype.DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		XMLGregorianCalendar dateArgs = dtf.newXMLGregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
				calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
				calendar.get(Calendar.SECOND), calendar.get(Calendar.MILLISECOND), calendar.get(Calendar.ZONE_OFFSET) / (1000 * 60));

		return dateArgs;
	}

	/**
	 *
	 * formatXmlDateShortTime: 返回xml日期（只有年月日，格式为yyyy-MM-dd）
	 *
	 * @param date
	 * @return XMLGregorianCalendar 例如：yyyy-MM-dd
	 *
	 *
	 * @see
	 *
	 */
	public static XMLGregorianCalendar formatXmlDateShortTime(Date date) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		XMLGregorianCalendar gc = null;
		try {
			gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return gc;
	}

	/**
	 *
	 * @param dateStr
	 * @return
	 */
	public static XMLGregorianCalendar formatXmlDateShortTime(String dateStr) {
		javax.xml.datatype.DatatypeFactory dtf = null;
		try {
			dtf = javax.xml.datatype.DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		XMLGregorianCalendar dateArgs = dtf.newXMLGregorianCalendar(dateStr);
		return dateArgs;
	}

	/**
	 * ddMMMyy形式的日期字符格式转成日期
	 * 
	 * @param dateStr
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date formatToTime(String dateStr, String format) {
		if (dateStr != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
			try {
				return sdf.parse(dateStr);
			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 指定时分秒 例如：hms格式[09:05:01]
	 * 
	 * @param hms
	 * @return
	 */
	public static Date setHMS(Date date, String hms) {
		String[] times = hms.split(":");
		date.setHours(Integer.valueOf(times[0]));
		date.setMinutes(Integer.valueOf(times[1]));
		date.setSeconds(Integer.valueOf(times[2]));
		return date;
	}

	/**
	 * 计算两个日期之间相差的天数，忽略时分秒
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) {
		if (smdate == null || bdate == null) {
			return 0;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
			Calendar cal = Calendar.getInstance();
			cal.setTime(smdate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(bdate);
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 3600 * 24);

			return Integer.parseInt(String.valueOf(between_days));
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取标准格林尼治时间下日期时间对应的时间戳
	 * 
	 * @return
	 */
	public static long calculateGmtSystemTimeMilli() {
		long unixTime = System.currentTimeMillis();
		long unixTimeGmt = unixTime - TimeZone.getDefault().getRawOffset();
		return unixTimeGmt;
	}
	
	/**
	 * 获取时间差
	 * @描述:
	 * @方法名: getDateDifference
	 * @return
	 * @返回类型 String
	 * @since
	 * @throws
	 */
	public static String getDateDifference(Date beginDate, Date endDate) {
	    String result = "";
	    long between=(endDate.getTime()-beginDate.getTime())/1000;//除以1000是为了转换成秒   
	    long day=between/(24*3600);   
	    long hour=between%(24*3600)/3600;   
	    long minute=between%3600/60;   
	    long second=between%60/60;   
	    if (day > 0) {
	        result += day + "天";
        }
	    if (hour > 0) {
	        result += hour + "小时";
	    }
	    if (minute > 0) {
	        result += minute + "分";
	    }
	    return result;
	}
	
	   public static Date beforeYear(Date date){  
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	        Calendar rightNow = Calendar.getInstance();  
	        rightNow.setTime(date);  
	  
	        rightNow.add(Calendar.MONTH, -12);  
	        Date dt1 = rightNow.getTime();  
	       // String reStr = sdf.format(dt1);  
	  
	        return dt1;  
	    } 
	   public static String strToDateFormat(String date) throws ParseException {
	       SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	       formatter.setLenient(false);
	       Date newDate= formatter.parse(date);
	       formatter = new SimpleDateFormat("yyyy-MM-dd");
	       return formatter.format(newDate);
	   }
	   
	   public static Long getSecondDifference(Date beginDate, Date endDate) throws ParseException {
		   Long between=(beginDate.getTime()-endDate.getTime())/1000;//除以1000是为了转换成秒   
		   Long aa=Math.abs(between);
		   return aa;
		}
	   
	   public static Long getSecondDifference2(Date beginDate, Date endDate) throws ParseException {
		   Long between=(beginDate.getTime()-endDate.getTime())/1000;//除以1000是为了转换成秒   
		   return between;
		}
	   
	   public static String getFormatType(String date){
		   String formatType="";
		   if(date.indexOf("-") != -1){
			   formatType=DATE_FORMAT_SHORT;
		   }else if(date.indexOf("/") != -1){
			   formatType=DATE_FORMAT_SLANTLINE;
		   }else if(date.indexOf("年") != -1){
			   formatType=DATECHINESS_FORMAT_SHORT;
		   }else{
			   formatType=DATE_FORMAT_YYYYMMDD;
		   }
		return formatType;
		   
	   }
	   
	   public static Date formatStringDate(String date){
		   if(date != null){
			   Date date2=formatToTime(date, getFormatType(date));
			   return date2;
		   }
		   return null;
	   }
	   public static void main(String[] args) {
//		   try {
			System.out.println(getDateStr(formatStringDate("2018/02/02"), DATE_FORMAT_SHORT));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
}
