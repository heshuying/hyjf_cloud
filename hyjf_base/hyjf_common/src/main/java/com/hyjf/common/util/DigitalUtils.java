package com.hyjf.common.util;

import java.util.regex.Pattern;

//数值格式判断工具类
public class DigitalUtils {
	/*
	 * 判断是否为整数
	 * 
	 * @param str 传入的字符串
	 * 
	 * @return 是整数返回true,否则返回false
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	/*
	 *  金额验证
	 * 
	 * @param str 传入的字符串
	 * 
	 * @return 是整数返回true,否则返回false
	 */
	public static boolean isNumber(String str) {
		Pattern pattern = Pattern
				.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
		java.util.regex.Matcher match = pattern.matcher(str);
		if (match.matches() == false) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * String 转 Integer
	 * String为空或者“” Integer为空
	 * @param str
	 * @return
	 */
	public static Integer toInteger(String str) {
		Integer result = null;
		if (str != null && !str.isEmpty()){result = Integer.valueOf(str);}
		return result;
	}

	public static void main(String[] args) {
		boolean integeFlag = isInteger("1000");
		System.out.println(integeFlag);
		
		boolean numberFlag = isNumber("1000.33");
		System.out.println(numberFlag);

		String i = null;
		toInteger(i);
	}

}
