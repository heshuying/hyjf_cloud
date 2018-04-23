package com.hyjf.common.validator;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.validator.GenericValidator;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetSessionOrRequestUtils;

/**
 * <p>
 * Commons validator
 * </p>
 *
 * @author gogtz
 * @version 1.0.0
 */
public class ValidatorFieldCheckUtil {
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
	/** 排他check */
	private static final String SYN_OPERATION = "syn.operation";
	
	
	/** 优惠券发行数量校验 */
	private static final String NUM_COUPON_COMPARE = "errors.num.coupon.compare";
	/** 两个数值大小校验 */
	private static final String NUM_MAIN_GREATER = "errors.num.main.greater";
	
	/**超出阈值 add by jijun 2018/03/15 */
	private static final String EXCEED_THRESHOLD = "exceed.threshold";
	
	/**
	 * 必须输入项目
	 *
	 * @param ModelAndView
	 * @param itemname
	 * @param value
	 * @return true:正常 false:错误
	 */
	public static boolean validateRequired(ModelAndView mav, String itemname, String value) {
		if (StringUtils.isEmpty(value)) {
			CustomErrors.add(mav, itemname, REQUIRED, getErrorMessage(REQUIRED));
			return false;
		}
		return true;
	}

	/**
	 * 检查最小文字数
	 *
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param minlength
	 * @param required
	 * @return
	 */
	public static boolean validateMinLength(ModelAndView mav, String itemname, String value, int minlength,
			boolean required) {

		boolean retValue = true;
		if (required) {
			retValue = validateRequired(mav, itemname, value);
		}
		if (retValue) {
			if (value != null) {
				try {
					retValue = GenericValidator.minLength(value, minlength);
					if (!retValue) {
						CustomErrors.add(mav, itemname, MINLENGTH, getErrorMessage(MINLENGTH, minlength));
						retValue = false;
					}
				} catch (Exception e) {
					CustomErrors.add(mav, itemname, MINLENGTH, getErrorMessage(MINLENGTH, minlength));
					retValue = false;
				}
			}
		}
		return retValue;
	}

	/**
	 * 检查最大文字数
	 *
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param maxlength
	 * @param required
	 * @return
	 */
	public static boolean validateMaxLength(ModelAndView mav, String itemname, String value, int maxlength,
			boolean required) {
		boolean retValue = true;
		if (required) {
			retValue = validateRequired(mav, itemname, value);
		}

		if (retValue) {
			if (value != null && maxlength > 0) {
				try {
					retValue = GenericValidator.maxLength(value, maxlength);
					if (!retValue) {
						CustomErrors.add(mav, itemname, MAXLENGTH, getErrorMessage(MAXLENGTH, maxlength));
						retValue = false;
					}
				} catch (Exception e) {
					CustomErrors.add(mav, itemname, MAXLENGTH, getErrorMessage(MAXLENGTH, maxlength));
					retValue = false;
				}
			}
		}
		return retValue;
	}

	/**
	 * 检查邮件地址格式和最大长度
	 *
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param maxlength
	 * @param required
	 * @return
	 */
	public static boolean validateMailAndMaxLength(ModelAndView mav, String itemname, String value, int maxlength,
			boolean required) {
		boolean retValue = validateMaxLength(mav, itemname, value, maxlength, required);

		if (retValue && !StringUtils.isEmpty(value)) {
			retValue = validateMail(mav, itemname, value, required);
		}
		return retValue;
	}

	/**
	 * 检查邮件地址格式
	 *
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param required
	 * @return
	 */
	public static boolean validateMail(ModelAndView mav, String itemname, String value, boolean required) {
		boolean retValue = validateRequired(mav, itemname, value);

		if (retValue && !StringUtils.isEmpty(value)) {

			String patten = "([a-zA-Z]|\\d|[!#\\$%&\'\\*\\+\\/=\\?\\^_{\\|}~`])((([a-zA-Z]|\\d|[!#\\$%&\'\\*\\+\\/=\\?\\^_\\-{\\|}~`])*(\\.([a-zA-Z]|\\d|[!#\\$%&\'\\*\\+\\/=\\?\\^_{\\|}~`\\-])+)*))@((([a-zA-Z]|\\d)|(([a-zA-Z]|\\d)([a-zA-Z]|\\d|\\-)*([a-zA-Z]|\\d)))\\.)+(([a-zA-Z]|\\d)|(([a-zA-Z]|\\d)([a-zA-Z]|\\d|\\-)*([a-zA-Z]|\\d)))";

			Pattern _emailAddressPattern = Pattern.compile(patten);
			Matcher matcher = _emailAddressPattern.matcher(value);

			if (!matcher.matches()) {
				CustomErrors.add(mav, itemname, MAIL, getErrorMessage(MAIL));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查是否半角英文
	 *
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param maxlength
	 * @param required
	 * @return
	 */
	public static boolean validateAlphaAndMaxLength(ModelAndView mav, String itemname, String value, int maxlength,
			boolean required) {
		boolean retValue = validateMaxLength(mav, itemname, value, maxlength, required);

		if (retValue && !StringUtils.isEmpty(value)) {
			if (!isAlphaNumeric(value)) {
				CustomErrors.add(mav, itemname, ALPHA, getErrorMessage(ALPHA));
				retValue = false;
			}
		}
		return retValue;
	}
	
	
	/**
	 * 排他check
	 * @param mav
	 * @param itemname
	 * @param beforeUpdatetime
	 * @param currentUpdatetime
	 */
	public static void validateSynOperation(ModelAndView mav, String itemname, int beforeUpdatetime,int currentUpdatetime){
		if(beforeUpdatetime != currentUpdatetime){
			CustomErrors.add(mav, itemname, SYN_OPERATION, getErrorMessage(SYN_OPERATION));
		}
	}

	/**
	 * 检查是否半角英文数字
	 *
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param maxlength
	 * @param required
	 * @return
	 */
	public static boolean validateAlphaNumericAndMaxLength(ModelAndView mav, String itemname, String value,
			int maxlength, boolean required) {
		boolean retValue = validateMaxLength(mav, itemname, value, maxlength, required);

		if (retValue && !StringUtils.isEmpty(value)) {
			if (!isAlphaNumeric(value)) {
				CustomErrors.add(mav, itemname, ALPHANUM, getErrorMessage(ALPHANUM));
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
	 * 0-9 a-z A-Z
	 *
	 * @return boolean
	 * @throws UnsupportedEncodingException
	 */
	private static boolean isAlphabetOrNumeric(char Ch) {
		// 英字判定
		char ch = Character.toLowerCase(Ch);
		if (('a' <= ch && ch <= 'z') || ('0' <= ch && ch <= '9') || ('A' <= ch && ch <= 'Z') || ('_' == ch)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查是否半角数字
	 *
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param required
	 * @return
	 */
	public static boolean validateNum(ModelAndView mav, String itemname, String value, boolean required) {
		boolean retValue = validateMaxLength(mav, itemname, value, 0, required);

		if (retValue && !StringUtils.isEmpty(value)) {
			try {
				new Long(value);
			} catch (Exception ex) {
				CustomErrors.add(mav, itemname, NUM, getErrorMessage(NUM));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查是否汉字
	 *
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param required
	 * @return
	 */
	public static boolean validateChinese(ModelAndView mav, String itemname, String value, int maxlength,
			boolean required) {
		boolean retValue = validateMaxLength(mav, itemname, value, maxlength, required);

		if (retValue && !StringUtils.isEmpty(value)) {
			String reg = "^[\u4E00-\u9FA5\uf900-\ufa2d]+$";
			if (!StringUtils.isEmpty(value) && !value.matches(reg)) {
				CustomErrors.add(mav, itemname, CHINESE, getErrorMessage(CHINESE));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查是否电话号码
	 *
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param required
	 * @return
	 */
	public static boolean validateMobile(ModelAndView mav, String itemname, String value, 
			boolean required) {
		boolean retValue = validateMaxLength(mav, itemname, value, 11, required);

		if (retValue && !StringUtils.isEmpty(value)) {
			String reg = "^[1][0-9]{10}$";//^[1][3,4,5,7,8][0-9]{9}$
			if (!StringUtils.isEmpty(value) && !value.matches(reg)) {
				CustomErrors.add(mav, itemname, MOBILE, getErrorMessage(MOBILE));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查是否手机号码
	 *
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param required
	 * @return
	 */
	public static boolean validatePhone(ModelAndView mav, String itemname, String value, int maxlength,
			boolean required) {
		boolean retValue = validateMaxLength(mav, itemname, value, maxlength, required);

		if (retValue && !StringUtils.isEmpty(value)) {
			String reg = "^[0-9\\-]*$";
			if (!StringUtils.isEmpty(value) && !value.matches(reg)) {
				CustomErrors.add(mav, itemname, PHONE, getErrorMessage(PHONE));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查两个值是否相同
	 *
	 * @param mav
	 * @param errorMsg
	 * @param valueBefore
	 * @param valueAfter
	 * @param required
	 * @return
	 */
	public static boolean validateSameInput(ModelAndView mav, String itemname, String valueBefore, String valueAfter,
			Object... params) {
		boolean retValue = true;
		if (!valueBefore.equals(valueAfter)) {
			CustomErrors.add(mav, itemname, SAME, getErrorMessage(SAME, params));
			retValue = false;
		}
		return retValue;
	}

	/**
	 * 自定义错误
	 *
	 * @param mav
	 * @param itemname
	 * @param errorId
	 */
	public static void validateSpecialError(ModelAndView mav, String itemname, String errorId) {
		validateSpecialError(mav, itemname, errorId, "");
	}

	/**
	 * 自定义错误
	 *
	 * @param mav
	 * @param itemname
	 * @param errorId
	 */
	public static void validateSpecialError(ModelAndView mav, String itemname, String errorId, String message) {
		if (Validator.isNull(message)) {
			message = getErrorMessage(errorId);
		}
		CustomErrors.add(mav, itemname, errorId, message);
	}

	/**
	 * 检查半角和区间
	 *
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param maxlength
	 * @param maxlength
	 * @param required
	 * @return
	 */
	public static boolean validateAlphaNumericRange(ModelAndView mav, String itemname, String value, int minlength,
			int maxlength, boolean required) {
		boolean retValue = true;
		if (required) {
			retValue = validateRequired(mav, itemname, value);
		}

		if (retValue) {
			if (value != null) {
				try {
					retValue = GenericValidator.isInRange(value.length(), minlength, maxlength);
					if (!retValue) {
						CustomErrors.add(mav, itemname, RANGE, getErrorMessage(RANGE, minlength, maxlength));
						retValue = false;
					}
				} catch (Exception e) {
					CustomErrors.add(mav, itemname, RANGE, getErrorMessage(RANGE, minlength, maxlength));
					retValue = false;
				}
			}
		}

		if (retValue && !StringUtils.isEmpty(value)) {
			if (!isAlphaNumeric(value)) {
				CustomErrors.add(mav, itemname, ALPHANUM, getErrorMessage(ALPHANUM));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查日期格式(yyyy-mm-dd)
	 *
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param required
	 * @return
	 */
	public static boolean validateDate(ModelAndView mav, String itemname, String value, boolean required) {
		boolean retValue = true;
		if (required) {
			retValue = validateRequired(mav, itemname, value);
		}

		if (retValue && !StringUtils.isEmpty(value)) {
			try {
				if (value.length() != 10) {
					CustomErrors.add(mav, itemname, DATE, getErrorMessage(DATE));
					retValue = false;
				} else {
					SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
					fm.setLenient(false);
					fm.parse(value);
				}
			} catch (Exception e) {
				CustomErrors.add(mav, itemname, DATE, getErrorMessage(DATE));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查日期格式(yyyy/mm/dd hh24:mi)
	 *
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param required
	 * @return
	 */
	public static boolean validateDateYYYMMDDHH24MI(ModelAndView mav, String itemname, String value, boolean required) {
		boolean retValue = true;
		if (required) {
			retValue = validateRequired(mav, itemname, value);
		}

		if (retValue && !StringUtils.isEmpty(value)) {
			try {
				if (value.length() != 16) {
					CustomErrors.add(mav, itemname, DATE, getErrorMessage(DATE));
					retValue = false;
				} else {
					SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					fm.setLenient(false);
					fm.parse(value);
				}
			} catch (Exception e) {
				CustomErrors.add(mav, itemname, DATE, getErrorMessage(DATE));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查日期格式(yyyy/m/d)
	 *
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param required
	 * @return
	 */
	public static boolean validateDateZeroTrim(ModelAndView mav, String itemname, String value, boolean required) {
		boolean retValue = true;
		if (required) {
			retValue = validateRequired(mav, itemname, value);
		}

		if (retValue && !StringUtils.isEmpty(value)) {
			try {
				if (value.length() > 10 || value.length() < 8) {
					CustomErrors.add(mav, itemname, DATE, getErrorMessage(DATE));
					retValue = false;
				} else {
					SimpleDateFormat fm = new SimpleDateFormat("yyyy-M-d");
					fm.setLenient(false);
					fm.parse(value);
				}
			} catch (Exception e) {
				CustomErrors.add(mav, itemname, DATE, getErrorMessage(DATE));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查日期格式(yyyymmdd)
	 *
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param required
	 * @return
	 */
	public static boolean validateDateYYYYMMDD(ModelAndView mav, String itemname, String value, boolean required) {
		boolean retValue = true;
		if (required) {
			retValue = validateRequired(mav, itemname, value);
		}

		if (retValue && !StringUtils.isEmpty(value)) {
			try {
				SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd");
				fm.setLenient(false);
				fm.parse(value);
			} catch (Exception e) {
				CustomErrors.add(mav, itemname, DATE, getErrorMessage(DATE));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查日期格式(yyyy/mm)
	 *
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param required
	 * @return
	 */
	public static boolean validateDateYYYYMM(ModelAndView mav, String itemname, String value, boolean required) {
		boolean retValue = true;
		if (required) {
			retValue = validateRequired(mav, itemname, value);
		}

		if (retValue && !StringUtils.isEmpty(value)) {
			try {
				String format = "yyyy/MM/dd";
				SimpleDateFormat fm = new SimpleDateFormat(format);
				fm.setLenient(false);
				fm.parse(value + "/01");
			} catch (Exception e) {
				CustomErrors.add(mav, itemname, DATE, getErrorMessage(DATE));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查日期格式(yyyyMMddHHmmss)
	 *
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param required
	 * @return
	 */
	public static boolean validateDateYYYYMMDDHHMISS(ModelAndView mav, String itemname, String value,
			boolean required) {
		boolean retValue = true;
		if (required) {
			retValue = validateRequired(mav, itemname, value);
		}

		if (retValue && !StringUtils.isEmpty(value)) {
			try {
				SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmmss");
				fm.setLenient(false);
				fm.parse(value);
			} catch (Exception e) {
				CustomErrors.add(mav, itemname, DATE, getErrorMessage(DATE));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查半角数字固定长度
	 *
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param justLength
	 * @param required
	 * @return
	 */
	public static boolean validateNumJustLength(ModelAndView mav, String itemname, String value, int justLength,
			boolean required) {
		boolean retValue = true;
		if (required) {
			retValue = validateRequired(mav, itemname, value);
		}

		if (retValue && !StringUtils.isEmpty(value)) {
			if (String.valueOf(value).length() != justLength) {
				CustomErrors.add(mav, itemname, JUST, getErrorMessage(JUST, justLength));
				retValue = false;
			}
		}

		if (retValue && !StringUtils.isEmpty(value)) {
			if (!GenericValidator.isLong(value) || !NumberUtils.isNumber(value)) {
				CustomErrors.add(mav, itemname, NUM, getErrorMessage(NUM));
				retValue = false;
			}
		}

		return retValue;
	}

	/**
	 * 检查固定长度
	 *
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param justLength
	 * @param required
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
			if (!GenericValidator.isLong(value) || !NumberUtils.isNumber(value)) {
				CustomErrors.add(info, itemname, key, getErrorMessage(NUM));
				retValue = false;
			}
		}

		return retValue;
	}

	/**
	 * 检查邮政编码
	 *
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param required
	 * @return
	 */
	public static boolean validateZip(ModelAndView mav, String itemname, String value, boolean required) {
		boolean retValue = validateMaxLength(mav, itemname, value, 0, required);

		if (retValue && !StringUtils.isEmpty(value)) {
			String reg = "\\d{6}";
			if (!StringUtils.isEmpty(value) && !value.matches(reg)) {
				CustomErrors.add(mav, itemname, ZIP, getErrorMessage(ZIP));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查半角数字最大长度（无小数点）
	 *
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param maxlength
	 * @param required
	 * @return
	 */
	public static boolean validateNum(ModelAndView mav, String itemname, String value, int maxLength,
			boolean required) {
		boolean retValue = validateMaxLength(mav, itemname, value, maxLength, required);

		if (retValue && !StringUtils.isEmpty(value)) {
			if (!GenericValidator.isLong(value) || !NumberUtils.isNumber(value)) {
				CustomErrors.add(mav, itemname, NUM, getErrorMessage(NUM));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查半角数字最大长度（无小数点）正整数
	 *
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param maxlength
	 * @param required
	 * @return
	 */
	public static boolean validateSignlessNum(ModelAndView mav, String itemname, String value, int maxLength,
			boolean required) {
		boolean retValue = validateMaxLength(mav, itemname, value, maxLength, required);

		if (retValue && !StringUtils.isEmpty(value)) {
			if (!GenericValidator.isInt(value) || !NumberUtils.isNumber(value) || Integer.valueOf(value) < 0) {
				CustomErrors.add(mav, itemname, SIGNLESSNUM, getErrorMessage(SIGNLESSNUM, maxLength));
				retValue = false;
			}
		}
		return retValue;
	}
	/**
	 * 检查半角数字两数字大小
	 * mainValue大于subValue 合法 返回true
	 * 反之 异常 返回false
	 *
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param maxlength
	 * @param required
	 * @return
	 */
	public static boolean validateNumMainGreaterSub(ModelAndView mav, String itemname, int mainValue,int subValue, boolean required) {
		boolean retValue = true;
		if (!StringUtils.isEmpty(mainValue)&&!StringUtils.isEmpty(subValue)) {
			try {
				new Long(mainValue);
				new Long(subValue);
				if(mainValue<=subValue){
					CustomErrors.add(mav, itemname, NUM_MAIN_GREATER, getErrorMessage(NUM_MAIN_GREATER));
					retValue = false;
				}
			} catch (Exception ex) {
				CustomErrors.add(mav, itemname, NUM_MAIN_GREATER, getErrorMessage(NUM_MAIN_GREATER));
				retValue = false;
			}
		}
		return retValue;
	}
	

	/**
	 * 检查优惠券发送数量与已发送到用户手中的数量的大小
	 * mainValue大于subValue 合法 返回true
	 * 反之 异常 返回false
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param maxlength
	 * @param required
	 * @return
	 */
	public static boolean validateCouponCount(ModelAndView mav, String itemname, int mainValue,int subValue, boolean required) {
		boolean retValue = true;
		if (!StringUtils.isEmpty(mainValue)&&!StringUtils.isEmpty(subValue)) {
			try {
				new Long(mainValue);
				new Long(subValue);
				if(mainValue < subValue){
					CustomErrors.add(mav, itemname, NUM_COUPON_COMPARE, getErrorMessage(NUM_COUPON_COMPARE));
					retValue = false;
				}
			} catch (Exception ex) {
				CustomErrors.add(mav, itemname, NUM_COUPON_COMPARE, getErrorMessage(NUM_COUPON_COMPARE));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查半角数字最大长度（有小数点）
	 *
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param maxlength
	 * @param required
	 * @return
	 */
	public static boolean validateDecimal(ModelAndView mav, String itemname, String value, int maxLength,
			boolean required) {
		boolean retValue = true;
		if (required) {
			retValue = validateRequired(mav, itemname, value);
		}

		if (retValue && !StringUtils.isEmpty(value)) {
			if (!GenericValidator.isDouble(value)) {
				CustomErrors.add(mav, itemname, DECIMAL, getErrorMessage(DECIMAL));
				retValue = false;
			}
		}

		if (retValue && !StringUtils.isEmpty(value)) {
			if (value.replaceFirst(".", "").length() > maxLength) {
				CustomErrors.add(mav, itemname, MAXLENGTH, getErrorMessage(MAXLENGTH, maxLength));
				retValue = false;
			}
		}
		return retValue;
	}

	/**
	 * 检查半角数字最大长度（有小数点，整数部和小数部分开）
	 *
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param integerMaxLength
	 * @param decimalsMaxLength
	 * @param required
	 * @return
	 */
	public static boolean validateNumLength(ModelAndView mav, String itemname, String value, int integerMaxLength,
			int decimalsMaxLength, boolean required) {
		boolean retValue = true;
		if (required) {
			retValue = validateRequired(mav, itemname, value);
		}

		if (retValue && !StringUtils.isEmpty(value)) {
			if (!GenericValidator.isDouble(value)) {
				CustomErrors.add(mav, itemname, NUMLENGTH,
						getErrorMessage(NUMLENGTH, integerMaxLength, decimalsMaxLength));
				retValue = false;
			}
		}

		if (retValue && !StringUtils.isEmpty(value)) {
			String[] data = value.split("\\.");
			if (data[0].length() > integerMaxLength || data[0].length() == 0) {
				CustomErrors.add(mav, itemname, NUMLENGTH,
						getErrorMessage(NUMLENGTH, integerMaxLength, decimalsMaxLength));
				retValue = false;
			}
			if (retValue && data.length > 1) {
				if (data[1].length() > decimalsMaxLength || data[1].length() == 0) {
					CustomErrors.add(mav, itemname, NUMLENGTH,
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
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param integerMaxLength
	 * @param decimalsMaxLength
	 * @param required
	 * @return
	 */
	public static boolean validateSignlessNumLength(ModelAndView mav, String itemname, String value,
			int integerMaxLength, int decimalsMaxLength, boolean required) {
		boolean retValue = true;
		if (required) {
			retValue = validateRequired(mav, itemname, value);
		}

		if (retValue && !StringUtils.isEmpty(value)) {
			if (!GenericValidator.isDouble(value)) {
				CustomErrors.add(mav, itemname, SIGNLESSNUMLENGTH,
						getErrorMessage(SIGNLESSNUMLENGTH, integerMaxLength, decimalsMaxLength));
				retValue = false;
			}
		}

		if (retValue && !StringUtils.isEmpty(value)) {
			String[] data = value.split("\\.");
			if (data[0].length() > integerMaxLength || data[0].length() == 0) {
				CustomErrors.add(mav, itemname, SIGNLESSNUMLENGTH,
						getErrorMessage(SIGNLESSNUMLENGTH, integerMaxLength, decimalsMaxLength));
				retValue = false;
			}
			if (retValue && data.length > 1) {
				if (data[1].length() > decimalsMaxLength || data[1].length() == 0) {
					CustomErrors.add(mav, itemname, SIGNLESSNUMLENGTH,
							getErrorMessage(SIGNLESSNUMLENGTH, integerMaxLength, decimalsMaxLength));
					retValue = false;
				}
			}
		}

		if (retValue) {
			BigDecimal bigDecimal = new BigDecimal(value);
			if (bigDecimal.compareTo(new BigDecimal(0.00)) == -1) {
				CustomErrors.add(mav, itemname, SIGNLESSNUMLENGTH,
						getErrorMessage(SIGNLESSNUMLENGTH, integerMaxLength, decimalsMaxLength));
			}
		}

		return retValue;
	}

	/**
	 * 检查邮件格式和最大长度
	 *
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param minlength
	 * @param maxlength
	 * @param required
	 * @return
	 */
	public static boolean validateMailAndRangeLength(ModelAndView mav, String itemname, String value, int minlength,
			int maxlength, boolean required) {
		boolean retValue = true;
		if (required) {
			retValue = validateRequired(mav, itemname, value);
		}

		if (retValue && !StringUtils.isEmpty(value)) {
			try {
				retValue = GenericValidator.isInRange(value.length(), minlength, maxlength);
				if (!retValue) {
					CustomErrors.add(mav, itemname, RANGE, getErrorMessage(RANGE, minlength, maxlength));
					retValue = false;
				}
			} catch (Exception e) {
			}
		}

		if (retValue && !StringUtils.isEmpty(value)) {
			retValue = validateMail(mav, itemname, value, required);
		}
		return retValue;
	}

	/**
	 * 检查半角英文数字的范围
	 *
	 * @param mav
	 * @param itemname
	 * @param value
	 * @param maxlength
	 * @param required
	 * @return
	 */
	public static boolean validateIdCard(ModelAndView mav, String itemname, String value, int minlength, int maxlength,
			boolean required) {

		boolean retValue = true;
		// 判断是否为空
		if (required) {
			retValue = validateRequired(mav, itemname, value);
		}
		// 判断idcard范围
		if (retValue && !StringUtils.isEmpty(value)) {
			try {
				retValue = GenericValidator.isInRange(value.length(), minlength, maxlength);
				if (!retValue) {
					CustomErrors.add(mav, itemname, RANGE, getErrorMessage(RANGE, minlength, maxlength));
					retValue = false;
				}
			} catch (Exception e) {
			}
		}
		// 判断是否只是数字和字母
		if (retValue && !StringUtils.isEmpty(value)) {
			if (!isAlphaNumeric(value)) {
				CustomErrors.add(mav, itemname, ALPHANUM, getErrorMessage(ALPHANUM));
				retValue = false;
			}
		}
		// 判断是否只是数字和字母
		if (retValue && !StringUtils.isEmpty(value)) {
			if (!isIDCard(value)) {
				CustomErrors.add(mav, itemname, IDCARD, getErrorMessage(IDCARD));
				retValue = false;
			}
		}
		return retValue;
	}
	/**
	 * 身份证验证的工具（支持18位省份证） 身份证号码结构：
	 * 17位数字和1位校验码：6位地址码数字，8位生日数字，3位出生时间顺序号，1位校验码。
	 * 地址码（前6位）：表示对象常住户口所在县（市、镇、区）的行政区划代码，按GB/T2260的规定执行。 出生日期码，（第七位
	 * 至十四位）：表示编码对象出生年、月、日，按GB按GB/T7408的规定执行，年、月、日代码之间不用分隔符。
	 * 顺序码（第十五位至十七位）：表示在同一地址码所标示的区域范围内，对同年、同月、同日出生的人编订的顺序号， 顺序码的奇数分配给男性，偶数分配给女性。
	 * 校验码（第十八位数）： 十七位数字本体码加权求和公式 s = sum(Ai*Wi), i = 0,,16，先对前17位数字的权求和；
	 * Ai:表示第i位置上的身份证号码数字值.Wi:表示第i位置上的加权因.Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4
	 * 2； 计算模 Y = mod(S, 11) 通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9
	 * 8 7 6 5 4 3 2
	 */

	/**
	 * 身份证验证
	 * 
	 * @param s
	 *            号码内容
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
		if (certNo == null || certNo.length() != 18)
			return false;
		final char[] cs = certNo.toUpperCase().toCharArray();
		// 校验位数
		int power = 0;
		for (int i = 0; i < cs.length; i++) {
			if (i == cs.length - 1 && cs[i] == 'X')
				break;// 最后一位可以 是X或x
			if (cs[i] < '0' || cs[i] > '9')
				return false;
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
		if (iyear < 1800 || iyear > Calendar.getInstance().get(Calendar.YEAR))
			return false;// 1900年的PASS，超过今年的PASS

		// 校验月份
		String month = certNo.length() == 15 ? certNo.substring(8, 10) : certNo.substring(10, 12);
		final int imonth = Integer.parseInt(month);
		if (imonth < 1 || imonth > 12) {
			return false;
		}

		// 校验天数
		String day = certNo.length() == 15 ? certNo.substring(10, 12) : certNo.substring(12, 14);
		final int iday = Integer.parseInt(day);
		if (iday < 1 || iday > 31)
			return false;

		// 校验"校验码"
		if (certNo.length() == 15)
			return true;
		return cs[cs.length - 1] == PARITYBIT[power % 11];
	}

	/**
	 * 取得错误信息
	 * 
	 * @param errKey
	 * @param param
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
	 * @param mav
	 * @return
	 */
	public static boolean hasValidateError(ModelAndView mav) {
		return !CustomErrors.isEmpty(mav);
	}
	
	/**
	 * 校验差距是否超出阈值 add by jijun 2018/03/15
	 * @return
	 */
	public static boolean validateExceedThreshold(ModelAndView modelAndView, String regTimeStartSrch,
			String regTimeEndSrch,int threshold) {
		//将查询开始时间的string转成int
		Integer startTimestampInt=GetDate.strYYYYMMDD2Timestamp2(regTimeStartSrch);
		//在开始时间的基础上加一个月
		Integer startTimestampIntAdd1Month=GetDate.countDate(startTimestampInt,2,threshold);
		//将查询结束时间的string转成int
		Integer endTimestampInt=GetDate.strYYYYMMDD2Timestamp2(regTimeEndSrch);
		
		//开始查询条件加1个月之后仍小于结束查询条件,说明查询条件跨度超过了一个月,校验不通过 add by jijun 2018/03/15
		if(startTimestampIntAdd1Month<endTimestampInt){
			CustomErrors.add(modelAndView, regTimeStartSrch+","+regTimeEndSrch, EXCEED_THRESHOLD, getErrorMessage(EXCEED_THRESHOLD,threshold+"个月"));
			return false;
		}
		
		return true;
	}

}
