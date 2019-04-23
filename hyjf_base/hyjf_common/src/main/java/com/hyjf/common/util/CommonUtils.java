package com.hyjf.common.util;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author xiasq
 * @version CommonUtils, v0.1 2017/11/11 11:51
 */
public class CommonUtils {

	private final static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

	// 项目要求投资者风险测评类型描述
	public final static String DESC_PROJECT_RISK_LEVEL_DESC = "您当前的风险测评类型为 #{0}# \n达到 #{1}# 及以上才可进行出借";

	/**
	 * 提供对象属性null转""方法，目前只支持String的属性
	 * 
	 * @param obj
	 */
	public static Object convertNullToEmptyString(Object obj) {
		if (obj == null) {
            return obj;
        }
		// 获取对象属性
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			// 跳过静态属性
			String mod = Modifier.toString(field.getModifiers());
			if (mod.indexOf("static") != -1) {
                continue;
            }

			// 得到属性的类名
			String className = field.getType().getSimpleName();
			if ("String".equalsIgnoreCase(className)) {
				try {
					field.setAccessible(true); // 设置些属性是可以访问的
					Object val = field.get(obj);
					if (val == null) {
						field.set(obj, "");
					}
				} catch (IllegalAccessException e) {
					logger.error(e.getMessage());
				}
			}
		}
		return obj;
	}

	/**
	 * 提供对象属性null转""方法，目前只支持String的属性
	 * 
	 * @param colls
	 * @return
	 */
	public static Collection convertNullToEmptyString(Collection colls) {
		for (Object coll : colls) {
			convertNullToEmptyString(coll);
		}
		return colls;
	}

	/**
	 * 如果为空就从collection中移除
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	public static void removeIfNullOrNullStr(Collection collection){
		collection.removeAll(Collections.singleton(null));
		collection.removeAll(Collections.singleton(""));
	}
	/**
	 * 根据还款类型获取还款单位
	 * 
	 * @param repayStyle
	 * @return
	 */
	public static String getPeriodUnitByRepayStyle(String repayStyle) {

		if (StringUtils.isEmpty(repayStyle)) {
            return StringUtils.EMPTY;
        }

		String periodUnit;
		if (CustomConstants.BORROW_STYLE_ENDDAY.equals(repayStyle)) {
			periodUnit = "天";
		} else {
			periodUnit = "个月";
		}
		return periodUnit;
	}

	/**
	 * 判断是否是分期还款 true:是分期
	 * 
	 * @param repayStyle
	 * @return
	 */
	public static boolean isStageRepay(String repayStyle) {
		if (StringUtils.isEmpty(repayStyle)) {
            return false;
        }
		if (Arrays.asList(CustomConstants.BORROW_STYLE_ENDDAY, CustomConstants.BORROW_STYLE_END).contains(repayStyle)) {
            return false;
        }
		return true;
	}

	/**
	 * 适应app返回格式，数据不能是null,转换为指定格式
	 * 
	 * @param obj
	 * @param val
	 * @return
	 */
	public static Object nvl(Object obj, String val) {
		if (obj == null) {
			return val;
		}
		return obj;
	}

	/**
	 * 将返回app的url拼接公共参数
	 * 
	 * @param request
	 * @param url
	 * @return
	 */
	public static String concatReturnUrl(HttpServletRequest request, String url) {

		// 版本号
		String version = request.getParameter("version");
		// 网络状态
		String netStatus = request.getParameter("netStatus");
		// 平台
		String platform = request.getParameter("platform");
		// 随机字符串
		String randomString = request.getParameter("randomString");
		// 唯一标识
		String sign = request.getParameter("sign");
		// token
		String token = request.getParameter("token");
		// order
		String order = request.getParameter("order");

		if (StringUtils.isEmpty(url)) {
			return null;
		}
		StringBuilder sb = new StringBuilder(url);
		if (url.indexOf("?") > 0) {
			sb.append("&");
		} else {
			sb.append("?");
		}

		if (!Validator.isNull(sign) && url.indexOf("sign") <= 0) {
			sb.append("sign=").append(sign);
		}
		if (!Validator.isNull(version) && url.indexOf("version") <= 0) {
			sb.append("&version=").append(version);
		}
		if (!Validator.isNull(netStatus) && url.indexOf("netStatus") <= 0) {
			sb.append("&netStatus=").append(netStatus);
		}
		if (!Validator.isNull(platform) && url.indexOf("platform") <= 0) {
			sb.append("&platform=").append(platform);
		}
		if (!Validator.isNull(randomString) && url.indexOf("randomString") <= 0) {
			sb.append("&randomString=").append(randomString);
		}
		try {
			if (!Validator.isNull(order) && url.indexOf("order") <= 0) {
				sb.append("&order=").append(URLEncoder.encode(order, "utf-8"));
			}
			if (!Validator.isNull(token) && url.indexOf("token") <= 0) {
				sb.append("&token=").append(URLEncoder.encode(token, "utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("转码错误....", e);
		}

		return sb.toString();
	}

	/**
	 * 手机号验证
	 *
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,6,7,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	/***
	 * 金额格式化 10，000.00
	 * 
	 * @param version
	 * @param amount
	 * @return
	 */
	public static String formatAmount(String version, BigDecimal amount) {

		// 金额显示格式
		DecimalFormat moneyFormat = null;
		// 判断选择哪种金融样式
		if (StringUtils.isNotBlank(version) && version.contains(CustomConstants.APP_VERSION_NUM)) {
			moneyFormat = CustomConstants.DF_FOR_VIEW_V1;
		} else {
			moneyFormat = CustomConstants.DF_FOR_VIEW;
		}

		String formatAmount;
		try {
			formatAmount = moneyFormat.format(amount);
		} catch (Exception e) {
			logger.error("金额格式化失败...", e);
			formatAmount = String.valueOf(amount);
		}
		return formatAmount;
	}

	/**
	 * 支持不带版本的格式化
	 * @param amount
	 * @return
	 */
	public static String formatAmount(BigDecimal amount) {
		return formatAmount("", amount);
	}


	/***
	 * 金额格式化 10，000.00
	 *
	 * @param version
	 * @param
	 * @return
	 */
	public static String formatAmount(String version, String money) {
		if (StringUtils.isNotEmpty(money)) {
			BigDecimal amount = BigDecimal.ZERO;
			try {
				amount = new BigDecimal(money);
			} catch (Exception e) {
				logger.info("money is : {}", money);
				logger.error("金额转换失败...", e);
				return money;
			}
			return formatAmount(version, amount);
		}
		return "0.00";
	}

	/**
	 * 支持不带版本的格式化
	 * @param
	 * @return
	 */
	public static String formatAmount(String money) {
		return formatAmount("", money);
	}
	/**
	 * 格式化金额
	 *
	 * @param number
	 * @return
	 */
	public static String formatNumber(String number) {
		String ret = "0.00";
		if (Validator.isNull(number)) {
			return ret;
		}
		if (!NumberUtils.isNumber(number)) {
			return ret;
		}

		try {
			BigDecimal b = new BigDecimal(number);
			ret = new DecimalFormat("############0.00").format(b.doubleValue());
		} catch (Exception e) {
			logger.error(CustomUtil.class.getName(), "formatAm", e);
		}

		return ret;
	}

	/**
	 * 金额格式化不保留小数点后"0"
	 */
	public static String formatBigDecimal(BigDecimal money) {
		DecimalFormat df = new DecimalFormat("###.####");
		return df.format(money).toString();
	}

	// 判断小数点后2位的数字的正则表达式
	private final static String IS_AMOUNT_REGEX_STR = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$";

	/**
	 * 金额验证 保留2位小数
	 */
	public static boolean isNumber(String amount) {
		Pattern pattern = Pattern.compile(IS_AMOUNT_REGEX_STR);
		Matcher match = pattern.matcher(amount);
		return match.matches();
	}

	/**
	 * list数据循环copyProperties
	 * @param sources
	 * @param clazz
	 * @param <S>
	 * @param <T>
	 * @return
	 * @author zhangyk
	 * @date 2018年6月6日14:57:50
	 */
	public static <S, T> List<T> convertBeanList(List<S> sources, Class<T> clazz) {
		return sources.stream().map(source -> convertBean(source, clazz)).collect(Collectors.toList());
	}

	/**
	 * 简单属性copy
	 * @param s
	 * @param clazz
	 * @param <S>
	 * @param <T>
	 * @author zhangyk
	 * @date 2018年6月6日14:57:50
	 */
	public static <S, T> T convertBean(S s, Class<T> clazz) {
		if (s == null) {
			return null;
		}
		try {
			T t = clazz.newInstance();
			BeanUtils.copyProperties(s, t);
			return t;
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("拷贝属性异常", e);
			throw new RuntimeException("拷贝属性异常");
		}
	}


	/**
	 * 从redis里面获取授权配置
	 * @param key
	 * @return
	 */
	public static HjhUserAuthConfigVO getAuthConfigFromCache(String key){
		HjhUserAuthConfigVO hjhUserAuthConfig=RedisUtils.getObj(key,HjhUserAuthConfigVO.class);
		return hjhUserAuthConfig;
	}

	/**
	 * 检查还款授权和服务费授权状态
	 * @param autoRepayStatus
	 * @param paymentAuthStatus
	 * @return
	 */
    public static int checkAuthStatus(Integer autoRepayStatus,Integer paymentAuthStatus) {
		HjhUserAuthConfigVO paymenthCconfig = getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH);
		HjhUserAuthConfigVO repayCconfig = getAuthConfigFromCache(RedisConstants.KEY_REPAYMENT_AUTH);
		logger.info("paymenthCconfig is :{}", JSONObject.toJSON(paymenthCconfig));
		logger.info("repayCconfig is :{}", JSONObject.toJSON(repayCconfig));
		if (paymenthCconfig != null && repayCconfig != null && paymenthCconfig.getEnabledStatus() - 1 == 0
				&& repayCconfig.getEnabledStatus() - 1 == 0) {
			// 如果两个都开启了
			if ((paymentAuthStatus == null || paymentAuthStatus - 1 != 0)
					&& (autoRepayStatus == null || autoRepayStatus - 1 != 0)) {
				// 借款人必须服务费授权
				return 7;
			}
		}
		// 服务费授权
		if (paymenthCconfig != null && paymenthCconfig.getEnabledStatus() - 1 == 0) {
			if (paymentAuthStatus == null || paymentAuthStatus - 1 != 0) {
				// 借款人必须服务费授权
				return 5;
			}
		}
		// 还款授权
		if (repayCconfig != null && repayCconfig.getEnabledStatus() - 1 == 0) {
			if (autoRepayStatus == null || autoRepayStatus - 1 != 0) {
				// 借款人必须还款授权
				return 6;
			}
		}
		return 0;
    }

	/**
	 * 检查服务费授权
	 * @author sunss
	 * @param authStatus
	 * @return 0未授权   1已授权
	 */
	public static Integer checkPaymentAuthStatus(Integer authStatus){
		HjhUserAuthConfigVO paymenthCconfig = getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH);
		// 服务费授权
		if (paymenthCconfig != null && paymenthCconfig.getEnabledStatus() - 1 == 0) {
			if (authStatus == null || authStatus - 1 != 0) {
				// 借款人必须服务费授权
				return 0;
			}
		}
		return 1;
	}

	/**
	 * 获取标的出借等级和用户等级进行校验(只适用于计划)
	 * @author wenxin
	 * @param UserLevel 用户等级，标的类型（计划，散标，债转），限制类型（从计划（hyjf_hjh_plan）标的或者散标（huiyingdai_borrow）标的表中获取）：invest_level
	 * @return true能出借 false 不能出借
	 */
	public static Boolean checkStandardInvestment(String UserLevel,String borrowFlag,String checkLeve){
		boolean bolStr = false;
		//判断入参
		if(StringUtils.isNotEmpty(UserLevel) && StringUtils.isNotEmpty(checkLeve)){
			//过滤空格
			UserLevel = UserLevel.trim();
			checkLeve = checkLeve.trim();
			//循环校验配置类型
			switch (checkLeve){
				case "保守型":
					//如果配置为保守型，校验用户类型为保守型/成长型/进取型/稳健型才可以投资
					if("保守型".equals(UserLevel) || "成长型".equals(UserLevel) || "进取型".equals(UserLevel) || "稳健型".equals(UserLevel)){
						bolStr = true;
					}else{
						bolStr = false;
					}
					break;
				case "稳健型":
					//如果配置为稳健型，校验用户类型为成长型/进取型/稳健型才可以投资
					if("成长型".equals(UserLevel) || "进取型".equals(UserLevel) || "稳健型".equals(UserLevel)){
						bolStr = true;
					}else{
						bolStr = false;
					}
					break;
				case "成长型":
					//如果配置为成长型，校验用户类型为成长型/进取型才可以投资
					if("成长型".equals(UserLevel) || "进取型".equals(UserLevel)){
						bolStr = true;
					}else{
						bolStr = false;
					}
					break;
				case "进取型":
					//如果配置的是进取型，校验用户类型为进取型才可以投资
					if("进取型".equals(UserLevel)){
						bolStr = true;
					}else{
						bolStr = false;
					}
					break;
				default:
					bolStr = false;
			}
		}
		return bolStr;
	}

	/**
	 * 数字格式化为以万和亿为单位i
	 * @param num
	 * @return
	 */
	public static Map<String,String> formatNum(BigDecimal num) {
		Map<String,String> resultMap = new HashMap<>();
		StringBuffer sb = new StringBuffer();
		BigDecimal b1 = new BigDecimal("10000");
		BigDecimal b2 = new BigDecimal("100000000");

		String unit = "";


		// 以万为单位处理
		if (num.compareTo(b1) == -1) {
			unit = "";
		} else if ((num.compareTo(b1) == 0 || num.compareTo(b1) == 1)
				&& num.compareTo(b2) == -1) {
			num = num.divide(b1);
			unit = "万";
		} else if (num.compareTo(b2) == 0 || num.compareTo(b2) == 1) {
			num = num.divide(b2);
			unit = "亿";
		}

		resultMap.put("data", num.setScale(2, BigDecimal.ROUND_DOWN).toString());
		resultMap.put("unit", unit);
		return resultMap;
	}
}
