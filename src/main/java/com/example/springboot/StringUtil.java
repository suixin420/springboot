package com.example.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class StringUtil {

	private static Logger logger = LoggerFactory.getLogger(StringUtil.class);
	
	/**只返回不重复的查询结果*/
	public static String RETURN_DISTINCT = "DISTINCT ";

	/**
	 * 判断字符串是否为非空
	 * 
	 * @param str
	 *            字符串
	 * @return 非空返回true，空串返回false
	 */
	public static boolean isNotNull(final String str) {
		return (str != null && str.length() > 0);
	}

	/**
	 * 去除字符串右端的空格
	 * 
	 * @param str
	 *            字符串
	 * @return 去除右端空格后的字符串
	 */
	public static String trimRight(final String str) {
		if (isNotNull(str)) {
			return str.substring(0, findLastNot(str, " ") + 1);
		} else {
			return null;
		}
	}

	/**
	 * 去除字符串左端的空格
	 * 
	 * @param str
	 *            字符串
	 * @return 去除左端空格后的字符串
	 */
	public static String trimLeft(final String str) {
		if (isNotNull(str)) {
			return str.substring(findFirstNot(str, " "));
		} else {
			return null;
		}
	}

	/**
	 * 查找不包含在特定字符集里的最后一个字符的位置
	 * 
	 * @param str
	 *            字符串
	 * @param patten
	 *            特定字符集
	 * @return 位置地址，找不到返回-1
	 */
	public static int findLastNot(final String str, final String patten) {
		int index = -1;

		if (isNotNull(str)) {
			for (index = str.length() - 1; index >= 0; index--) {
				boolean flag = false;
				for (int i = 0; i < patten.length(); i++) {
					if (patten.charAt(i) == str.charAt(index)) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					break;
				}
			}
		}
		return index;
	}

	/**
	 * 查找不包含在特定字符集里的第一个字符的位置
	 * 
	 * @param str
	 *            字符串
	 * @param patten
	 *            特定字符集
	 * @return 位置地址，找不到返回-1
	 */
	public static int findFirstNot(final String str, final String patten) {
		int index = -1;

		if (isNotNull(str)) {
			for (index = 0; index < str.length(); index++) {
				boolean flag = false;
				for (int i = 0; i < patten.length(); i++) {
					if (patten.charAt(i) == str.charAt(index)) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					break;
				}
			}
			if (index >= str.length()) {
				index = -1;
			}
		}

		return index;
	}
	
	

	/**
	 * 判断字符串是否不为空 空格字符串返回false
	 * 
	 * @param string
	 * @return
	 */
	public static boolean stringNotNull(String string) {
		if ((null == string) || (string.trim().equals(""))) {
			return false;
		}
		return true;
	}

	/**
	 * 把空串转换为null
	 * 
	 * @param str
	 * @return
	 */
	public static String stringToNull(String str) {
		if (!"".equals(str)) {
			return str;
		}
		return null;
	}

	/**
	 * 把Null字符串转换为Null
	 * 
	 * @param str
	 * @return
	 */
	public static String nullstringToNull(String str) {
		if (!"".equals(str) && !"null".equals(str)) {
			return str;
		}
		return null;
	}

	

	/**
	 * Character转化成String
	 * 
	 * @param character
	 * @return
	 * @throws Exception
	 */
	public static String characterToString(Character character)
			throws Exception {
		String newString = "";
		if (null != character) {
			newString = character.toString();
		}
		return newString;
	}

	public static Integer stringToInteger(String string) throws Exception {

		if (!stringNotNull(string)) {

			return null;
		}

		try {

			return new Integer(string);

		} catch (NumberFormatException e) {
			logger.error(e.getMessage(), e);
			// TODO 自动生成 catch 坄1�7
			// e.printStackTrace();
			throw new Exception();
		}
	}

	public static Short stringToShort(String string) throws Exception {

		if (!stringNotNull(string)) {

			return null;
		}

		try {

			return new Short(string);

		} catch (NumberFormatException e) {
			logger.error(e.getMessage(), e);
			// TODO 自动生成 catch 坄1�7
			// e.printStackTrace();
			throw new Exception();
		}
	}

	public static Long stringToLong(String string) throws Exception {

		if (!stringNotNull(string)) {

			return null;
		}

		try {

			return new Long(string);

		} catch (NumberFormatException e) {
			logger.error(e.getMessage(), e);
			// TODO 自动生成 catch 坄1�7
			// e.printStackTrace();
			throw new Exception();
		}
	}

	public static BigDecimal stringToBigDecimal(String string) throws Exception {

		if (!stringNotNull(string)) {

			return null;
		}

		try {

			return new BigDecimal(string);

		} catch (NumberFormatException e) {
			logger.error(e.getMessage(), e);
			// TODO 自动生成 catch 坄1�7
			// e.printStackTrace();
			throw new Exception();
		}
	}

	public static String longToString(Long integer) {

		if (integer == null) {

			return null;
		}

		return integer.toString();

	}

	public static String integerToString(Integer integer) {

		if (integer == null) {

			return null;
		}

		return integer.toString();

	}

	public static String integerToZeroString(Integer integer) {

		if (integer == null) {

			return "0";
		}

		return integer.toString();

	}

	public static String shortToString(Short _short) {

		if (_short == null) {

			return null;
		}

		return _short.toString();

	}

	public static String bigDecimalToString(BigDecimal bigDecimal) {

		if (bigDecimal == null) {

			return null;
		}

		return bigDecimal.toString();
	}

	public static String bigDecimalToZeroString(BigDecimal bigDecimal) {

		if (bigDecimal == null) {

			return "0";
		}

		return bigDecimal.toString();
	}

	public static String isNull(String string1, String string2) {

		if (string1 == null) {

			return string2;
		} else {

			return string1;
		}
	}


	public static String floatToString(Float float1) {

		if (float1 == null) {

			return float1.toString();
		}

		return null;
	}

	public static Float stringToFloat(String string) throws Exception {

		if (string == null) {

			return null;
		}

		try {

			return new Float(string);

		} catch (NumberFormatException e) {
			logger.error(e.getMessage(), e);
			// TODO 自动生成 catch 坄1�7
			// e.printStackTrace();
			throw new Exception();
		}
	}

	/**
	 * 将字符串变为大写 2006-11-17 haoman
	 * 
	 * @param string
	 * @return
	 */
	public static String stringToUpperCase(String string) {

		if ((string != null) && (!string.trim().equals(""))) {

			return string.toUpperCase();
		} else {

			return string;
		}
	}

	public static Double stringToDouble(String num) throws Exception {

		if (!stringNotNull(num)) {

			return null;
		}

		try {

			Double dx = new Double(num);
			return dx;

		} catch (NumberFormatException e) {
			logger.error(e.getMessage(), e);
			// TODO 自动生成 catch 坄1�7
			// e.printStackTrace();
			throw new Exception();
		}

	}

	public static String doubleToString(Double num) throws Exception {

		if (num == null) {

			return null;
		}

		try {
			DecimalFormat dFormat = new DecimalFormat();
			dFormat.applyPattern("####0.00");

			return dFormat.format(num);

		} catch (NumberFormatException e) {
			logger.error(e.getMessage(), e);
			// TODO 自动生成 catch 坄1�7
			// e.printStackTrace();
			throw new Exception();
		}

	}

	public static int stringToInt(String string) throws Exception {
		try {

			return Integer.parseInt(string);

		} catch (NumberFormatException e) {
			logger.error(e.getMessage(), e);
			// TODO 自动生成 catch 坄1�7
			// e.printStackTrace();
			throw new Exception();
		}

	}

	public static String intToString(int it) throws Exception {
		try {

			return "" + it;

		} catch (NumberFormatException e) {
			logger.error(e.getMessage(), e);
			// TODO 自动生成 catch 坄1�7
			// e.printStackTrace();
			throw new Exception();
		}

	}

	/**
	 * trim to zero
	 * 
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public static String trimToZero(String string) throws Exception {
		String ret = "0";
		if (null != string) {
			ret = string.trim();
			if ("".equals(ret)) {
				ret = "0";
			}
		}
		return ret;
	}

	/**
	 * trim to other
	 * 
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public static String trimToOther(String string, String other)
			throws Exception {
		String ret = other;
		if (null != string) {
			ret = string.trim();
			if ("".equals(ret)) {
				ret = other;
			}
		}
		return ret;
	}

	/**
	 * 获得下一个数孄1�7
	 * 
	 * @param str
	 * @return
	 */
	public static String GetNextNumber(String str) {
		int i = new Integer(str).intValue() + 1;
		if (i / 100 >= 1) {
			str = i + "";
			return str;
		}
		if (i / 10 >= 1) {
			str = "0" + i + "";
			return str;
		} else {
			return "00" + i;
		}
	}

	/**
	 * 将null字符串变成空丄1�7
	 * 
	 * @param str
	 * @return
	 */
	public static String nullStringToEmpty(final String str) {
		String newStr = "";
		if (null == str) {
			newStr = "";
		} else {
			newStr = str;
		}
		return newStr;
	}

	/**
	 * 将null字符串变戄1�7"^^"
	 * 
	 * @param str
	 * @return
	 */
	public static String nullStringToUpCorner(final String str) {
		String newStr = "";
		if (stringNotNull(str)) {
			newStr = str;
		}
		return newStr;
	}
	public static String trim(String str)
    {
        return str != null ? str.trim() : null;
    }

    public static String trimToNull(String str)
    {
        String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }
    public static boolean isEmpty(String str)
    {
        return str == null || str.length() == 0;
    }
    /**
     * 多行文本分隔成单行字符串的数组
     * @param str
     * @return
     */
    public static String[] stringSplitByLine(String str){
    	return str.trim().split("\n");
    }
    /**
     * 文本以某种字符分隔成字符串数组
     * @param str
     * @param flag
     * @return
     */
    public static String[] stringSplitByFlag(String str, String flag){
    	String[] s =  str.trim().split(flag);
    	for(int i = 0, j = 0; i < s.length; i++){
    		if(!"".equals(s[i].trim())){
    			s[j] = s[i].trim();
    			j++;
    		}
    	}
    	return s;
    }

	/**
	 * 将首字母变大写,其他字母大小写不变
	 * @param str
	 * @return
	 */
	public static String upperFirst(String str) {
		if(str == null || "".equals(str.trim())) return str;
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	/**
	 * 若字符串为null，返回空串
	 */
	public static String nullToEmpty(String str) {
		return str == null?"":str;
	}
	
	public static String nullToEmpty(Object obj) {
		return obj == null?"":obj.toString();
	}
	public static String nullToTrimEmpty(Object obj) {
		return obj==null?"":obj.toString().trim();
	}
}
