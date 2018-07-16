/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.utils;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.springframework.web.servlet.support.RequestContext;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.util.GetSessionOrRequestUtils;
import com.hyjf.common.validator.CustomErrors;
import com.hyjf.common.validator.Validator;

/**
 * @author libin
 * @version ValidatorFieldCheckUtil.java, v0.1 2018年7月7日 上午11:43:46
 */
public class AdminValidatorFieldCheckUtil {
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
	public static boolean validateRequired(JSONObject jsonObject, String itemname, String value) {
		if (StringUtils.isEmpty(value)) {
			CustomErrors.add(jsonObject, itemname, REQUIRED, getErrorMessage(REQUIRED));
			return false;
		}
		return true;
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
	 * 自定义错误
	 *
	 * @param mav
	 * @param itemname
	 * @param errorId
	 */
	public static void validateSpecialError(JSONObject jsonObject, String itemname, String errorId) {
		validateSpecialError(jsonObject, itemname, errorId, "");
	}
	
	/**
	 * 自定义错误
	 *
	 * @param mav
	 * @param itemname
	 * @param errorId
	 */
	public static void validateSpecialError(JSONObject jsonObject, String itemname, String errorId, String message) {
		if (Validator.isNull(message)) {
			message = getErrorMessage(errorId);
		}
		CustomErrors.add(jsonObject, itemname, errorId, message);
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
	public static boolean validateSignlessNumLength(JSONObject jsonObject, String itemname, String value,
			int integerMaxLength, int decimalsMaxLength, boolean required) {
		boolean retValue = true;
		if (required) {
			retValue = validateRequired(jsonObject, itemname, value);
		}

		if (retValue && !StringUtils.isEmpty(value)) {
			if (!GenericValidator.isDouble(value)) {
				CustomErrors.add(jsonObject, itemname, SIGNLESSNUMLENGTH,
						getErrorMessage(SIGNLESSNUMLENGTH, integerMaxLength, decimalsMaxLength));
				retValue = false;
			}
		}

		if (retValue && !StringUtils.isEmpty(value)) {
			String[] data = value.split("\\.");
			if (data[0].length() > integerMaxLength || data[0].length() == 0) {
				CustomErrors.add(jsonObject, itemname, SIGNLESSNUMLENGTH,
						getErrorMessage(SIGNLESSNUMLENGTH, integerMaxLength, decimalsMaxLength));
				retValue = false;
			}
			if (retValue && data.length > 1) {
				if (data[1].length() > decimalsMaxLength || data[1].length() == 0) {
					CustomErrors.add(jsonObject, itemname, SIGNLESSNUMLENGTH,
							getErrorMessage(SIGNLESSNUMLENGTH, integerMaxLength, decimalsMaxLength));
					retValue = false;
				}
			}
		}

		if (retValue) {
			BigDecimal bigDecimal = new BigDecimal(value);
			if (bigDecimal.compareTo(new BigDecimal(0.00)) == -1) {
				CustomErrors.add(jsonObject, itemname, SIGNLESSNUMLENGTH,
						getErrorMessage(SIGNLESSNUMLENGTH, integerMaxLength, decimalsMaxLength));
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
	public static boolean validateDecimal(JSONObject jsonObject, String itemname, String value, int maxLength,
			boolean required) {
		boolean retValue = true;
		if (required) {
			retValue = validateRequired(jsonObject, itemname, value);
		}

		if (retValue && !StringUtils.isEmpty(value)) {
			if (!GenericValidator.isDouble(value)) {
				CustomErrors.add(jsonObject, itemname, DECIMAL, getErrorMessage(DECIMAL));
				retValue = false;
			}
		}

		if (retValue && !StringUtils.isEmpty(value)) {
			if (value.replaceFirst(".", "").length() > maxLength) {
				CustomErrors.add(jsonObject, itemname, MAXLENGTH, getErrorMessage(MAXLENGTH, maxLength));
				retValue = false;
			}
		}
		return retValue;
	}
	/**
	 * 判断是否有错误
	 *
	 * @param mav
	 * @return
	 */
	public static boolean hasValidateError(JSONObject jsonObject) {
		return !CustomErrors.isEmpty(jsonObject);
	}
	
	
	
	

}
