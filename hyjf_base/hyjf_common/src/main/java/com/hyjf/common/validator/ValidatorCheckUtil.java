package com.hyjf.common.validator;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.util.GetSessionOrRequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
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
	/** IdCard Key */
	private static final String IDCARD = "idcard";

	/**
	 * 必须输入项目
	 *
	 * @param
	 * @param itemname
	 * @param value
	 * @return true:正常 false:错误
	 */
	public static boolean validateRequired(JSONObject info, String itemname, String key, String value) {
		if (StringUtils.isEmpty(value)) {
			CustomErrors.add(info, itemname, key, getErrorMessage(REQUIRED));
			return false;
		}
		return true;
	}

	/**
	 * 检查最小文字数
	 *
	 * @param
	 * @param itemname
	 * @param value
	 * @param minlength
	 * @param required
	 * @return
	 */
	public static boolean validateMinLength(JSONObject info, String itemname, String key, String value, int minlength,
			boolean required) {

		boolean retValue = true;
		if (required) {
			retValue = validateRequired(info, itemname, key, value);
		}
		if (value != null) {
			try {
				retValue = GenericValidator.minLength(value, minlength);
				if (!retValue) {
					CustomErrors.add(info, itemname, key, getErrorMessage(MINLENGTH, minlength));
					retValue = false;
				}
			} catch (Exception e) {
				CustomErrors.add(info, itemname, key, getErrorMessage(MINLENGTH, minlength));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查最大文字数
	 *
	 * @param
	 * @param itemname
	 * @param value
	 * @param maxlength
	 * @param required
	 * @return
	 */
	public static boolean validateMaxLength(JSONObject info, String itemname, String key, String value, int maxlength,
			boolean required) {

		boolean retValue = true;
		if (required) {
			retValue = validateRequired(info, itemname, key, value);
		}
		if (retValue) {
			if (value != null && maxlength > 0) {
				try {
					retValue = GenericValidator.maxLength(value, maxlength);
					if (!retValue) {
						CustomErrors.add(info, itemname, key, getErrorMessage(MAXLENGTH, maxlength));
						retValue = false;
					}
				} catch (Exception e) {
					CustomErrors.add(info, itemname, key, getErrorMessage(MAXLENGTH, maxlength));
					retValue = false;
				}
			}
		}
		return retValue;
	}

	/**
	 * 检查邮件地址格式和最大长度
	 *
	 * @param
	 * @param itemname
	 * @param value
	 * @param maxlength
	 * @param required
	 * @return
	 */
	public static boolean validateMailAndMaxLength(JSONObject info, String itemname, String key, String value,
			int maxlength, boolean required) {
		boolean retValue = validateMaxLength(info, itemname, key, value, maxlength, required);

		if (retValue && !StringUtils.isEmpty(value)) {
			retValue = validateMail(info, itemname, key, value, required);
		}
		return retValue;
	}

	/**
	 * 检查邮件地址格式
	 *
	 * @param
	 * @param itemname
	 * @param value
	 * @param required
	 * @return
	 */
	public static boolean validateMail(JSONObject info, String itemname, String key, String value, boolean required) {

		boolean retValue = validateRequired(info, itemname, key, value);
		if (!StringUtils.isEmpty(value)) {

			String patten = "([a-zA-Z]|\\d|[!#\\$%&\'\\*\\+\\/=\\?\\^_{\\|}~`])((([a-zA-Z]|\\d|[!#\\$%&\'\\*\\+\\/=\\?\\^_\\-{\\|}~`])*(\\.([a-zA-Z]|\\d|[!#\\$%&\'\\*\\+\\/=\\?\\^_{\\|}~`\\-])+)*))@((([a-zA-Z]|\\d)|(([a-zA-Z]|\\d)([a-zA-Z]|\\d|\\-)*([a-zA-Z]|\\d)))\\.)+(([a-zA-Z]|\\d)|(([a-zA-Z]|\\d)([a-zA-Z]|\\d|\\-)*([a-zA-Z]|\\d)))";

			Pattern _emailAddressPattern = Pattern.compile(patten);
			Matcher matcher = _emailAddressPattern.matcher(value);

			if (!matcher.matches()) {
				CustomErrors.add(info, itemname, key, getErrorMessage(MAIL));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查是否半角英文
	 *
	 * @param
	 * @param itemname
	 * @param value
	 * @param maxlength
	 * @param required
	 * @return
	 */
	public static boolean validateAlphaAndMaxLength(JSONObject info, String itemname, String key, String value,
			int maxlength, boolean required) {
		boolean retValue = validateMaxLength(info, itemname, key, value, maxlength, required);

		if (retValue && !StringUtils.isEmpty(value)) {
			if (!Validator.isAlphanumericName(value)) {
				CustomErrors.add(info, itemname, key, getErrorMessage(ALPHA));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查是否半角英文数字
	 *
	 * @param
	 * @param itemname
	 * @param value
	 * @param maxlength
	 * @param required
	 * @return
	 */
	public static boolean validateAlphaNumericAndMaxLength(JSONObject info, String itemname, String key, String value,
			int maxlength, boolean required) {

		boolean retValue = validateMaxLength(info, itemname, key, value, maxlength, required);
		if (retValue && !StringUtils.isEmpty(value)) {
			if (!isAlphaNumeric(value)) {
				CustomErrors.add(info, itemname, key, getErrorMessage(ALPHANUM));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 0-9 a-z A-Z
	 *
	 * @return boolean
	 * @throws UnsupportedEncodingException
	 */
	private static boolean isAlphaNumeric(String value) {
		for (int i = 0; i < value.length(); i++) {
			if (!isAlphabetOrNumeric(value.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 0-9 a-z A-Z 和下划线
	 *
	 * @return boolean
	 * @throws UnsupportedEncodingException
	 */
	private static boolean isAlphabetOrNumeric(char Ch) {
		// 英字判定
		char ch = Character.toLowerCase(Ch);
		if (('a' <= ch && ch <= 'z') || ('0' <= ch && ch <= '9') || ('A' <= ch && ch <= 'Z')|| ch=='_') {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查是否半角数字
	 *
	 * @param
	 * @param itemname
	 * @param value
	 * @param required
	 * @return
	 */
	public static boolean validateNum(JSONObject info, String itemname, String key, String value, boolean required) {

		boolean retValue = validateMaxLength(info, itemname, key, value, 0, required);
		if (retValue && !StringUtils.isEmpty(value)) {
			try {
				new Long(value);
			} catch (Exception ex) {
				CustomErrors.add(info, itemname, key, getErrorMessage(NUM));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查是否汉字
	 *
	 * @param
	 * @param itemname
	 * @param value
	 * @param required
	 * @return
	 */
	public static boolean validateChinese(JSONObject info, String itemname, String key, String value, int maxlength,
			boolean required) {

		boolean retValue = validateMaxLength(info, itemname, key, value, maxlength, required);
		if (retValue && !StringUtils.isEmpty(value)) {
			String reg = "^[\u4E00-\u9FA5\uf900-\ufa2d]+$";
			if (!StringUtils.isEmpty(value) && !value.matches(reg)) {
				CustomErrors.add(info, itemname, key, getErrorMessage(CHINESE));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查是否电话号码
	 *
	 * @param
	 * @param itemname
	 * @param value
	 * @param required
	 * @return
	 */
	public static boolean validatePhone(JSONObject info, String itemname, String key, String value, int maxlength,
			boolean required) {

		boolean retValue = validateMaxLength(info, itemname, key, value, maxlength, required);
		if (retValue && !StringUtils.isEmpty(value)) {
			String reg = "^[0-9\\-]*$";
			if (!StringUtils.isEmpty(value) && !value.matches(reg)) {
				CustomErrors.add(info, itemname, key, getErrorMessage(PHONE));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查是否身份证
	 *
	 * @param
	 * @param idcard
	 * @param itemname
	 * @param value
	 * @param required
	 * @return
	 */
	public static boolean validateIdCard(JSONObject info, String idcard, String itemname, String key, String value, int minlength, int maxlength,
										 boolean required) {

		boolean retValue = true;
		// 判断是否为空
		if (required) {
			retValue = validateNumJustLength(info, itemname, key, value, maxlength, required);
		}
		// 判断idcard范围
		if (retValue && !org.springframework.util.StringUtils.isEmpty(value)) {
			try {
				retValue = GenericValidator.isInRange(value.length(), minlength, maxlength);
				if (!retValue) {
					CustomErrors.add(info, itemname, RANGE, getErrorMessage(RANGE, minlength, maxlength));
					retValue = false;
				}
			} catch (Exception e) {
			}
		}
		// 判断是否只是数字和字母
		if (retValue && !org.springframework.util.StringUtils.isEmpty(value)) {
			if (!isAlphaNumeric(value)) {
				CustomErrors.add(info, itemname, ALPHANUM, getErrorMessage(ALPHANUM));
				retValue = false;
			}
		}
		// 判断是否只是数字和字母
		if (retValue && !org.springframework.util.StringUtils.isEmpty(value)) {
			if (!isIDCard(value)) {
				CustomErrors.add(info, itemname, IDCARD, getErrorMessage(IDCARD));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查是否电话号码
	 *
	 * @param
	 * @param itemname
	 * @param value
	 * @param required
	 * @return
	 */
	public static boolean validateMobile(JSONObject info, String itemname, String key, String value, int maxlength,
										 boolean required) {

		boolean retValue = validateNumJustLength(info, itemname, key, value, maxlength, required);
		if (retValue && !StringUtils.isEmpty(value)) {
//			String reg = "^[0-9\\-]*$";
			if (!Validator.isMobile(value)) {
				CustomErrors.add(info, itemname, key, getErrorMessage(MOBILE));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查两个值是否相同
	 *
	 * @param
	 * @param
	 * @param valueBefore
	 * @param valueAfter
	 * @param
	 * @return
	 */
	public static boolean validateSameInput(JSONObject info, String itemname, String key, String valueBefore,
			String valueAfter, Object... params) {
		boolean retValue = true;
		if (valueBefore == null || !valueBefore.equals(valueAfter)) {
			CustomErrors.add(info, itemname, key, getErrorMessage(SAME, params));
			retValue = false;
		}
		return retValue;
	}

	/**
	 * 自定义错误（前端key与错误消息key相同）
	 *
	 * @param
	 * @param itemname
	 * @param errorId
	 */
	public static void validateSpecialErrorKey(JSONObject info, String itemname, String errorId) {
		validateSpecialErrorKey(info, itemname, errorId, "");
	}

	/**
	 * 自定义错误（前端key与错误消息key相同）
	 *
	 * @param
	 * @param itemname
	 * @param
	 */
	public static void validateSpecialErrorKey(JSONObject info, String itemname, String key, String message) {
		if (Validator.isNull(message)) {
			message = getErrorMessage(key);
		}
		CustomErrors.add(info, itemname, key, message);
	}

	/**
	 * 自定义错误(重用共通消息,前端key不同于错误消息key)
	 *
	 * @param
	 * @param itemname
	 * @param errorId
	 */
	public static void validateSpecialErrorMsg(JSONObject info, String itemname, String key, String errorId) {
		validateSpecialErrorMsg(info, itemname, key, errorId, "");
	}

	/**
	 * 自定义错误(重用共通消息,前端key不同于错误消息key)
	 *
	 * @param
	 * @param itemname
	 * @param errorId
	 */
	public static void validateSpecialErrorMsg(JSONObject info, String itemname, String key, String errorId,
			String message) {
		if (Validator.isNull(message)) {
			message = getErrorMessage(errorId);
		}
		CustomErrors.add(info, itemname, key, message);
	}

	/**
	 * 自定义错误(自定义错误消息，前端key不同于错误消息key)
	 *
	 * @param
	 * @param
	 * @param errorId
	 */
	public static void validateSpecialError(JSONObject info, String key, String errorId) {
		validateSpecialError(info, key, errorId, "");
	}

	/**
	 * 自定义错误(自定义错误消息，前端key不同于错误消息key)
	 *
	 * @param
	 * @param
	 * @param errorId
	 */
	public static void validateSpecialError(JSONObject info, String key, String errorId, String message) {
		if (Validator.isNull(message)) {
			message = getErrorMessage(errorId);
		}
		CustomErrors.add(info, key, message);
	}

	/**
	 * 检查半角和区间
	 *
	 * @param
	 * @param itemname
	 * @param value
	 * @param maxlength
	 * @param maxlength
	 * @param required
	 * @return
	 */
	public static boolean validateAlphaNumericRange(JSONObject info, String itemname, String key, String value,
			int minlength, int maxlength, boolean required) {

		boolean retValue = true;
		if (required) {
			retValue = validateRequired(info, itemname, key, value);
		}
		if (retValue) {
			if (value != null) {
				try {
					retValue = GenericValidator.isInRange(value.length(), minlength, maxlength);
					if (!retValue) {
						CustomErrors.add(info, itemname, key, getErrorMessage(RANGE, minlength, maxlength));
						retValue = false;
					}
				} catch (Exception e) {
					CustomErrors.add(info, itemname, key, getErrorMessage(RANGE, minlength, maxlength));
					retValue = false;
				}
			}
		}
		if (retValue && !StringUtils.isEmpty(value)) {
			if (!isAlphaNumeric(value)) {
				CustomErrors.add(info, itemname, key, getErrorMessage(ALPHANUM));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查日期格式(yyyy-mm-dd)
	 *
	 * @param
	 * @param itemname
	 * @param value
	 * @param required
	 * @return
	 */
	public static boolean validateDate(JSONObject info, String itemname, String key, String value, boolean required) {

		boolean retValue = true;
		if (required) {
			retValue = validateRequired(info, itemname, key, value);
		}
		if (retValue && !StringUtils.isEmpty(value)) {
			try {
				if (value.length() != 10) {
					CustomErrors.add(info, itemname, key, getErrorMessage(DATE));
					retValue = false;
				} else {
					SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
					fm.setLenient(false);
					fm.parse(value);
				}
			} catch (Exception e) {
				CustomErrors.add(info, itemname, key, getErrorMessage(DATE));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查日期格式(yyyy-mm-dd)
	 *
	 * @param
	 * @param itemname
	 * @param value
	 * @param required
	 * @return
	 */
	public static boolean validateDateFormat(JSONObject info, String itemname, String key, String value, String format,
			boolean required) {

		boolean retValue = true;
		if (required) {
			retValue = validateRequired(info, itemname, key, value);
		}
		if (retValue && !StringUtils.isEmpty(value)) {
			try {
				if (value.length() != 11 && value.length() != 10) {
					CustomErrors.add(info, itemname, key, getErrorMessage(DATE));
					retValue = false;
				} else {
					SimpleDateFormat fm = new SimpleDateFormat(format);
					fm.setLenient(false);
					fm.parse(value);
				}
			} catch (Exception e) {
				CustomErrors.add(info, itemname, key, getErrorMessage(DATE));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查日期格式(yyyy/mm/dd hh24:mi)
	 *
	 * @param
	 * @param itemname
	 * @param value
	 * @param required
	 * @return
	 */
	public static boolean validateDateYYYMMDDHH24MI(JSONObject info, String itemname, String key, String value,
			boolean required) {

		boolean retValue = true;
		if (required) {
			retValue = validateRequired(info, itemname, key, value);
		}
		if (retValue && !StringUtils.isEmpty(value)) {
			try {
				if (value.length() != 10) {
					CustomErrors.add(info, itemname, key, getErrorMessage(DATE));
					retValue = false;
				} else {
					SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					fm.setLenient(false);
					fm.parse(value);
				}
			} catch (Exception e) {
				CustomErrors.add(info, itemname, key, getErrorMessage(DATE));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查日期格式(yyyy/m/d)
	 *
	 * @param
	 * @param itemname
	 * @param value
	 * @param required
	 * @return
	 */
	public static boolean validateDateZeroTrim(JSONObject info, String itemname, String key, String value,
			boolean required) {

		boolean retValue = true;
		if (required) {
			retValue = validateRequired(info, itemname, key, value);
		}
		if (retValue && !StringUtils.isEmpty(value)) {
			try {
				if (value.length() > 10 || value.length() < 8) {
					CustomErrors.add(info, itemname, key, getErrorMessage(DATE));
					retValue = false;
				} else {
					SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-d");
					fm.setLenient(false);
					fm.parse(value);
				}
			} catch (Exception e) {
				CustomErrors.add(info, itemname, key, getErrorMessage(DATE));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查日期格式(yyyymmdd)
	 *
	 * @param
	 * @param itemname
	 * @param value
	 * @param required
	 * @return
	 */
	public static boolean validateDateYYYYMMDD(JSONObject info, String itemname, String key, String value,
			boolean required) {

		boolean retValue = true;
		if (required) {
			retValue = validateRequired(info, itemname, key, value);
		}
		if (retValue && !StringUtils.isEmpty(value)) {
			try {
				SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd");
				fm.setLenient(false);
				fm.parse(value);
			} catch (Exception e) {
				CustomErrors.add(info, itemname, key, getErrorMessage(DATE));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查日期格式(yyyy/mm)
	 *
	 * @param
	 * @param itemname
	 * @param value
	 * @param required
	 * @return
	 */
	public static boolean validateDateYYYYMM(JSONObject info, String itemname, String key, String value,
			boolean required) {

		boolean retValue = true;
		if (required) {
			retValue = validateRequired(info, itemname, key, value);
		}
		if (retValue && !StringUtils.isEmpty(value)) {
			try {
				String format = "yyyy/MM/dd";
				SimpleDateFormat fm = new SimpleDateFormat(format);
				fm.setLenient(false);
				fm.parse(value + "/01");
			} catch (Exception e) {
				CustomErrors.add(info, itemname, key, getErrorMessage(DATE));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查日期格式(yyyyMMddHHmmss)
	 *
	 * @param
	 * @param itemname
	 * @param value
	 * @param required
	 * @return
	 */
	public static boolean validateDateYYYYMMDDHHMISS(JSONObject info, String itemname, String key, String value,
			boolean required) {

		boolean retValue = true;
		if (required) {
			retValue = validateRequired(info, itemname, key, value);
		}
		if (retValue && !StringUtils.isEmpty(value)) {
			try {
				SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmmss");
				fm.setLenient(false);
				fm.parse(value);
			} catch (Exception e) {
				CustomErrors.add(info, itemname, key, getErrorMessage(DATE));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查固定长度
	 *
	 * @param
	 * @param itemname
	 * @param value
	 * @param justLength
	 * @param required
	 * @return
	 */
	public static boolean validateNumJustLength(JSONObject info, String itemname, String key, String value,
			int justLength, boolean required) {

		boolean retValue = true;
		if (required) {
			retValue = validateRequired(info, itemname, key, value);
		}
		if (retValue && !StringUtils.isEmpty(value)) {
			if (String.valueOf(value).length() != justLength) {
				CustomErrors.add(info, itemname, key, getErrorMessage(JUST, justLength));
				retValue = false;
			}
		}
		if (retValue && !StringUtils.isEmpty(value)) {
			if (!GenericValidator.isLong(value) || !StringUtils.isNumeric(value)) {
				CustomErrors.add(info, itemname, key, getErrorMessage(NUM));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查固定长度
	 *
	 * @param
	 * @param itemname
	 * @param value
	 * @param justLength
	 * @param
	 * @return
	 */
	public static boolean validateNum(JSONObject info, String itemname, String key, String value, int justLength) {
		boolean retValue = true;

		if (retValue && !StringUtils.isEmpty(value)) {
			if (String.valueOf(value).length() != justLength) {
				CustomErrors.add(info, itemname, key, getErrorMessage(JUST, justLength));
				retValue = false;
			}
		}

		if (retValue && !StringUtils.isEmpty(value)) {
			if (!GenericValidator.isLong(value) || !StringUtils.isNumeric(value)) {
				CustomErrors.add(info, itemname, key, getErrorMessage(NUM));
				retValue = false;
			}
		}

		return retValue;
	}

	/**
	 * 检查邮政编码
	 *
	 * @param
	 * @param itemname
	 * @param value
	 * @param required
	 * @return
	 */
	public static boolean validateZip(JSONObject info, String itemname, String key, String value, boolean required) {

		boolean retValue = validateMaxLength(info, itemname, key, value, 0, required);
		if (retValue && !StringUtils.isEmpty(value)) {
			String reg = "\\d{6}";
			if (!StringUtils.isEmpty(value) && !value.matches(reg)) {
				CustomErrors.add(info, itemname, key, getErrorMessage(ZIP));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查半角数字最大长度（无小数点）
	 *
	 * @param
	 * @param itemname
	 * @param value
	 * @param
	 * @param required
	 * @return
	 */
	public static boolean validateNum(JSONObject info, String itemname, String key, String value, int maxLength,
			boolean required) {
		boolean retValue = validateMaxLength(info, itemname, key, value, maxLength, required);

		if (retValue && !StringUtils.isEmpty(value)) {
			if (!GenericValidator.isLong(value) || !StringUtils.isNumeric(value)) {
				CustomErrors.add(info, itemname, NUM, getErrorMessage(NUM));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查半角数字最大长度（无小数点）正整数
	 *
	 * @param
	 * @param itemname
	 * @param value
	 * @param
	 * @param required
	 * @return
	 */
	public static boolean validateSignlessNum(JSONObject info, String itemname, String key, String value, int maxLength,
			boolean required) {

		boolean retValue = validateMaxLength(info, itemname, key, value, 0, required);

		if (retValue && !StringUtils.isEmpty(value)) {
			if (!GenericValidator.isInt(value) || !StringUtils.isNumeric(value) || Integer.valueOf(value) < 0) {
				CustomErrors.add(info, itemname, key, getErrorMessage(SIGNLESSNUM, maxLength));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查半角数字最大长度（有小数点）
	 *
	 * @param
	 * @param itemname
	 * @param value
	 * @param
	 * @param required
	 * @return
	 */
	public static boolean validateDecimal(JSONObject info, String itemname, String key, String value, int maxLength,
			boolean required) {

		boolean retValue = true;
		if (required) {
			retValue = validateRequired(info, itemname, key, value);
		}
		if (retValue && !StringUtils.isEmpty(value)) {
			if (!GenericValidator.isDouble(value)) {
				CustomErrors.add(info, itemname, key, getErrorMessage(DECIMAL));
				retValue = false;
			}
		}

		if (retValue && !StringUtils.isEmpty(value)) {
			if (value.replaceFirst(".", "").length() > maxLength) {
				CustomErrors.add(info, itemname, key, getErrorMessage(MAXLENGTH, maxLength));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查半角数字最大长度（有小数点，整数部和小数部分开）
	 *
	 * @param
	 * @param itemname
	 * @param value
	 * @param integerMaxLength
	 * @param decimalsMaxLength
	 * @param required
	 * @return
	 */
	public static boolean validateNumLength(JSONObject info, String itemname, String key, String value,
			int integerMaxLength, int decimalsMaxLength, boolean required) {

		boolean retValue = true;
		if (required) {
			retValue = validateRequired(info, itemname, key, value);
		}
		if (retValue && !StringUtils.isEmpty(value)) {
			if (!GenericValidator.isDouble(value)) {
				CustomErrors.add(info, itemname, key, getErrorMessage(NUMLENGTH, integerMaxLength, decimalsMaxLength));
				retValue = false;
			}
		}

		if (retValue && !StringUtils.isEmpty(value)) {
			String[] data = value.split("\\.");
			if (data[0].length() > integerMaxLength || data[0].length() == 0) {
				CustomErrors.add(info, itemname, key, getErrorMessage(NUMLENGTH, integerMaxLength, decimalsMaxLength));
				retValue = false;
			}
			if (retValue && data.length > 1) {
				if (data[1].length() > decimalsMaxLength || data[1].length() == 0) {
					CustomErrors.add(info, itemname, key,
							getErrorMessage(NUMLENGTH, integerMaxLength, decimalsMaxLength));
					retValue = false;
				}
			}
		}
		return retValue;
	}

	/**
	 * 检查半角正数字最大长度（有小数点，整数部和小数部分开）
	 *
	 * @param
	 * @param itemname
	 * @param value
	 * @param integerMaxLength
	 * @param decimalsMaxLength
	 * @param required
	 * @return
	 */
	public static boolean validateSignlessNumLength(JSONObject info, String itemname, String key, String value,
			int integerMaxLength, int decimalsMaxLength, boolean required) {

		boolean retValue = true;
		if (required) {
			retValue = validateRequired(info, itemname, key, value);
		}
		if (retValue && !StringUtils.isEmpty(value)) {
			if (!GenericValidator.isDouble(value)) {
				CustomErrors.add(info, itemname, key,
						getErrorMessage(SIGNLESSNUMLENGTH, integerMaxLength, decimalsMaxLength));
				retValue = false;
			}
		}

		if (retValue && !StringUtils.isEmpty(value)) {
			String[] data = value.split("\\.");
			if (data[0].length() > integerMaxLength || data[0].length() == 0) {
				CustomErrors.add(info, itemname, key,
						getErrorMessage(SIGNLESSNUMLENGTH, integerMaxLength, decimalsMaxLength));
				retValue = false;
			}
			if (retValue && data.length > 1) {
				if (data[1].length() > decimalsMaxLength || data[1].length() == 0) {
					CustomErrors.add(info, itemname, key,
							getErrorMessage(SIGNLESSNUMLENGTH, integerMaxLength, decimalsMaxLength));
					retValue = false;
				}
			}
		}

		if (retValue) {
			BigDecimal bigDecimal = new BigDecimal(value);
			if (bigDecimal.compareTo(BigDecimal.valueOf(0.00)) < 0) {
				CustomErrors.add(info, itemname, key,
						getErrorMessage(SIGNLESSNUMLENGTH, integerMaxLength, decimalsMaxLength));
			}
		}

		return retValue;
	}

	/**
	 * 检查邮件格式和最大长度
	 *
	 * @param
	 * @param itemname
	 * @param value
	 * @param minlength
	 * @param maxlength
	 * @param required
	 * @return
	 */
	public static boolean validateMailAndRangeLength(JSONObject info, String itemname, String key, String value,
			int minlength, int maxlength, boolean required) {

		boolean retValue = true;
		if (required) {
			retValue = validateRequired(info, itemname, key, value);
		}

		if (retValue && !StringUtils.isEmpty(value)) {
			try {
				retValue = GenericValidator.isInRange(value.length(), minlength, maxlength);
				if (!retValue) {
					CustomErrors.add(info, itemname, key, getErrorMessage(RANGE, minlength, maxlength));
					retValue = false;
				}
			} catch (Exception e) {
			}
		}

		if (retValue && !StringUtils.isEmpty(value)) {
			retValue = validateMail(info, itemname, key, value, required);
		}
		return retValue;
	}

	/**
	 * 取得错误信息
	 *
	 * @param errKey
	 * @param
	 * @return
	 */
	public static String getErrorMessage(String errKey, Object... params) {

		HttpServletRequest request = GetSessionOrRequestUtils.getRequest();
		RequestContext requestContext = new RequestContext(request);
		String message = requestContext.getMessage(PREFIX + errKey);
		if (Validator.isNotNull(message)) {
			if (message.contains("{0}")) {
				if (Validator.isNotNull(params)) {
					int i = 0;
					for (Object p : params) {
						message = message.replace("{" + i + "}", p == null ? "" : String.valueOf(p));
						i++;
					}
				}
			}
		}

		return message;
	}

	/**
	 * 判断是否有错误
	 *
	 * @param
	 * @return
	 */
	public static boolean hasValidateError(JSONObject info) {
		return !CustomErrors.isEmpty(info);
	}
	
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
	/**
	 * 身份证验证
	 *
	 * @param
	 *
	 * @return 是否有效 null和"" 都是false
	 */
	public static boolean isIDCard(String certNo) {
		Map<Integer, String> zoneNum = new HashMap<Integer, String>();
		zoneNum.put(11, "北京");
		zoneNum.put(12, "天津");
		zoneNum.put(13, "河北");
		zoneNum.put(14, "山西");
		zoneNum.put(15, "内蒙古");
		zoneNum.put(21, "辽宁");
		zoneNum.put(22, "吉林");
		zoneNum.put(23, "黑龙江");
		zoneNum.put(31, "上海");
		zoneNum.put(32, "江苏");
		zoneNum.put(33, "浙江");
		zoneNum.put(34, "安徽");
		zoneNum.put(35, "福建");
		zoneNum.put(36, "江西");
		zoneNum.put(37, "山东");
		zoneNum.put(41, "河南");
		zoneNum.put(42, "湖北");
		zoneNum.put(43, "湖南");
		zoneNum.put(44, "广东");
		zoneNum.put(45, "广西");
		zoneNum.put(46, "海南");
		zoneNum.put(50, "重庆");
		zoneNum.put(51, "四川");
		zoneNum.put(52, "贵州");
		zoneNum.put(53, "云南");
		zoneNum.put(54, "西藏");
		zoneNum.put(61, "陕西");
		zoneNum.put(62, "甘肃");
		zoneNum.put(63, "青海");
		zoneNum.put(64, "新疆");
		zoneNum.put(71, "台湾");
		zoneNum.put(81, "香港");
		zoneNum.put(82, "澳门");
		zoneNum.put(91, "外国");
		char[] PARITYBIT = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
		int[] POWER_LIST = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
		if (certNo == null || certNo.length() != 18) {
			return false;
		}
		final char[] cs = certNo.toUpperCase().toCharArray();
		// 校验位数
		int power = 0;
		for (int i = 0; i < cs.length; i++) {
			if (i == cs.length - 1 && cs[i] == 'X') {
				break;// 最后一位可以 是X或x
			}
			if (cs[i] < '0' || cs[i] > '9') {
				return false;
			}
			if (i < cs.length - 1) {
				power += (cs[i] - '0') * POWER_LIST[i];
			}
		}

		// 校验区位码
		if (!zoneNum.containsKey(Integer.valueOf(certNo.substring(0, 2)))) {
			return false;
		}

		// 校验年份
		String year = certNo.substring(6, 10);

		final int iyear = Integer.parseInt(year);
		if (iyear < 1800 || iyear > Calendar.getInstance().get(Calendar.YEAR)) {
			return false;// 1900年的PASS，超过今年的PASS
		}

		// 校验月份
		String month = certNo.length() == 15 ? certNo.substring(8, 10) : certNo.substring(10, 12);
		final int imonth = Integer.parseInt(month);
		if (imonth < 1 || imonth > 12) {
			return false;
		}

		// 校验天数
		String day = certNo.length() == 15 ? certNo.substring(10, 12) : certNo.substring(12, 14);
		final int iday = Integer.parseInt(day);
		if (iday < 1 || iday > 31) {
			return false;
		}

		// 校验"校验码"
		if (certNo.length() == 15) {
			return true;
		}
		return cs[cs.length - 1] == PARITYBIT[power % 11];
	}

}
