package com.example.springboot;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtil {

	/***
	 * 将字符串变成整形
	 * @param value 源
	 * @param defaultInt 变成整形时若抛出异常则返回默认值
	 * @return
	 */
	public static int toInt(String value, int defaultInt) {
		int result = 0;
		try {
			result = Integer.parseInt(value);
		} catch(NumberFormatException e) {
			result = defaultInt;
		}
		return result;
	}
	
	public static long toLong(String value, long defaultLong) {
		long result = 0;
		try {
			result = Long.parseLong(value);
		} catch(NumberFormatException e) {
			result = defaultLong;
		}
		return result;
	}
	
	public static Long toLong(String value, Long defaultLong) {
		Long result = 0l;
		try {
			result = Long.parseLong(value);
		} catch(NumberFormatException e) {
			result = defaultLong;
		}
		return result;
	}
	
	/**
	 * 将BigDecimal按舍入模式舍入到第Index位，并转换为整型并返回
	 * @param source 源数据
	 * @param rm 舍入模式
	 * @param index 舍入到整型的第n位,个位是0，十位是1，如此类推
	 * @return
	 */
	public static int roundInt(BigDecimal source, RoundingMode rm, int index) {
		return source.setScale(index*-1, rm).intValue();
	}
	
	/**
	 * 将BigDecimal按四舍五入舍入到第Index位，并转换为整型并返回
	 * @param source 源数据
	 * @param index 舍入到整型的第n位,个位是0，十位是1，如此类推
	 * @example 
	 * 	BasicDataUtil.roundIntByHalfDownMode(new BigDecimal("213.99"), 0) -->214
	 * 	BasicDataUtil.roundIntByHalfDownMode(new BigDecimal("213.99"), 1) -->210
	 *  BasicDataUtil.roundIntByHalfDownMode(new BigDecimal("213.99"), 9) -->9
	 * @return int 返回舍入后的整形
	 */
	public static int roundIntByHalfDownMode(BigDecimal source, int index) {
		return source.setScale(index*-1, RoundingMode.HALF_UP).intValue();
	}
	
	/***
	 * 
	 * @param value 要转换的数值
	 * @param length 保留小数长度
	 * @param defalutValueWhenException 当有异常时,返回的默认值
	 * 四舍五入模式
	 * @return 
	 */
	public static BigDecimal getDecimalByHalfUpMode(String value, int length, 
			BigDecimal defalutValueWhenException) {
		BigDecimal bd = null;
		try {
			bd = new BigDecimal(value);
		} catch(Exception e) {
			bd = defalutValueWhenException;
		}
		if(bd != null) {
			bd = bd.setScale(length, RoundingMode.HALF_UP);
		}
		return bd;
	}
	
	
	/***
	 * 
	 * @param value 要转换的数值
	 * @param length 保留小数长度
	 * @param defalutValueWhenException 当有异常时,返回的默认值
	 * @param roundingMode 舍入模式
	 * @return
	 */
	public static BigDecimal getDecimal(String value, int length, 
			BigDecimal defalutValueWhenException, RoundingMode roundingMode) {
		BigDecimal bd = null;
		try {
			bd = new BigDecimal(value);
		} catch(Exception e) {
			bd = defalutValueWhenException;
		}
		if(bd != null) {
			bd = bd.setScale(length, roundingMode);
		}
		return bd;
	}

	public static Boolean toBoolean(String s, boolean b) {
		try {
			return Boolean.parseBoolean(s);
		} catch(Exception e) {
			return b;
		}
	}

	public static Byte toByte(String s, byte bt) {
		try {
			return Byte.parseByte(s);
		} catch(Exception e) {
			return bt;
		}
	}

	public static Float toFloat(String s, float f) {
		try {
			return Float.parseFloat(s);
		} catch(Exception e) {
			return f;
		}
	}

	public static Double toDouble(String s, double d) {
		try {
			return Double.parseDouble(s);
		} catch(Exception e) {
			return d;
		}
	}

	public static Integer toInteger(String value, Integer defaultInt) {
		Integer result = null;
		try {
			result = Integer.parseInt(value);
		} catch(NumberFormatException e) {
			result = defaultInt;
		}
		return result;
	}

	public static Double toDouble(String value, Double defaultDouble) {
		try {
			return Double.parseDouble(value);
		} catch(Exception e) {
			return defaultDouble;
		}
	}

	public static Long[] toLongArr(String[] strArr, Long defaultLong) {
		Long[] longArr = new Long[strArr.length];
		for(int i = 0; i<strArr.length; i++) {
			longArr[i] = toLong(strArr[i], defaultLong);
		}
		return longArr;
	}
}
