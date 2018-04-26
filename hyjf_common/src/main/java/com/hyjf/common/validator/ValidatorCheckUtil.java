package com.hyjf.common.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * Commons validator
 * </p>
 *
 * @author gogtz
 * @version 1.0.0
 */
public class ValidatorCheckUtil {
	/** 错误信息前缀 */
	private static final String PREFIX = "errors.";
	/** 必须Key */
	private static final String REQUIRED = "required";
	/** 最小长度Key */
	private static final String MINLENGTH = "minlength";
	/** 最大长度Key */
	private static final String MAXLENGTH = "maxlength";
	/** 邮件Key */
	private static final String MAIL = "mail";
	/** 半角英文Key */
	private static final String ALPHA = "alpha";
	/** 半角英文数字Key */
	private static final String ALPHANUM = "alphanum";
	/** 数字Key */
	private static final String NUM = "num";
	/** 正整数数字Key */
	private static final String SIGNLESSNUM = "signlessnum";
	/** 电话号码Key */
	private static final String PHONE = "phone";
	/** 手机号码Key */
	private static final String MOBILE = "mobile";
	/** 相同项目Key */
	private static final String SAME = "same";
	/** 区间Key */
	private static final String RANGE = "range";
	/** 日期Key */
	private static final String DATE = "date";
	/** 日期Key */
	private static final String DATE_MINUTE = "dateminute";
	/** Just长度Key */
	private static final String JUST = "just";
	/** 邮政编码Key */
	private static final String ZIP = "zip";
	/** 浮点型数值Key */
	private static final String DECIMAL = "decimal";
	/** 数字长度Key */
	private static final String NUMLENGTH = "numlength";
	/** 正数字长度Key */
	private static final String SIGNLESSNUMLENGTH = "signlessnumlength";
	/** 汉字Key */
	private static final String CHINESE = "chinese";

	
	/**
     * 判断参数是否包含空格(不包括换行,TAB造成的空格)
     * @param val
     * @return
     */
    public static boolean verfiyChinaFormat(String val) {
    	if (val==null||"".equals(val)) {
			return false;
		}
        Pattern pattern = Pattern.compile("^[^ ]+$");
        Matcher matcher = pattern.matcher(val);
        if (matcher.matches()){
                return true;
        }else {
                return false;
        }
    }
}
