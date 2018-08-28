package com.hyjf.common.util;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.http.HttpDeal;

/**
 * @author xiasq
 * @version WrbParseParamUtil, v0.1 2018/3/7 9:54 风车理财解析请求参数工具类
 */
public class WrbParseParamUtil {
	private static Logger logger = LoggerFactory.getLogger(WrbParseParamUtil.class);

	private static final String SPLIT_STR = "&";
	private static final String CONCAT_STR = "=";
	private static final String URL_SPLIT_STR = "?";
	private static final String ENCODE_STRING = "utf-8";

	/**
	 * @param param
	 * @return 解析风车理财请求参数 param : pf_user_name=n1
	 *         &phone=18888888888&email=xxx@qq.com
	 */
	public static Map<String, String> parseParam(String param) {
		Map<String, String> result = null;

		String signStr, key, value;
		try {
			signStr = WrbCoopDESUtil.desDecrypt(WrbCoopDESUtil.KEY, param);
		} catch (Exception e) {
			logger.error("des解密失败...", e);
			return result;
		}

		if (StringUtils.isNotBlank(signStr)) {
			result = new HashMap<>();
			String[] strArray = signStr.split(SPLIT_STR);
			for (String str : strArray) {
				if (StringUtils.isNotBlank(str) && str.contains(CONCAT_STR)) {
					key = str.substring(0, str.indexOf(CONCAT_STR));
					value = str.substring(str.indexOf(CONCAT_STR) + 1);
                    if (value != null) {
                        result.put(key, value);
                    }
                } else {
					logger.error("参数解析错误...error str is :{}", str);
				}
			}
		}
		logger.info("请求参数：{}", JSONObject.toJSONString(result));
		return result;
	}

	/**
	 * 风车理财get回调
	 *
	 * @param requestUrl
	 * @param params
	 * @return
	 */
	public static String wrbCallback(String requestUrl, Map<String, String> params) {
		String url = null;
		try {
			url = buildRequestParam(requestUrl, params);
			logger.info("请求url：{}", url);
		} catch (Exception e) {
			logger.error("参数加密失败...", e);
		}
		String result = HttpDeal.get(url);
		logger.info("风车理财回调结果result: {}", result);
		return result;
	}


	/**
	 * 将Map对象通过反射机制转换成Bean对象
	 *
	 * @param map
	 *            存放数据的map对象
	 * @param clazz
	 *            待转换的class
	 * @return 转换后的Bean对象
	 * @throws Exception
	 *             异常
	 */
	public static  <T> T mapToBean(Map<String, String> map, Class<T> clazz) throws Exception {
		T obj = clazz.newInstance();
		if (map != null && map.size() > 0) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				String propertyName = entry.getKey(); // 属性名
				Object value = entry.getValue();
				String setMethodName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
				Field field = getClassField(clazz, propertyName);
				if (field == null)
					continue;
                Class<?> fieldTypeClass = field.getType();
                if (value != null && StringUtils.isNotBlank(value.toString())) {
                    value = convertValType(value, fieldTypeClass);
                    clazz.getMethod(setMethodName, field.getType()).invoke(obj, value);
                }
            }
		}
		return obj;
	}

	/**
	 * 获取指定字段名称查找在class中的对应的Field对象(包括查找父类)
	 *
	 * @param clazz
	 *            指定的class
	 * @param fieldName
	 *            字段名称
	 * @return Field对象
	 */
	private static Field getClassField(Class<?> clazz, String fieldName) {
		if (Object.class.getName().equals(clazz.getName())) {
			return null;
		}
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}

		Class<?> superClass = clazz.getSuperclass();
		if (superClass != null) {// 简单的递归一下
			return getClassField(superClass, fieldName);
		}
		return null;
	}

	/**
	 * 将Object类型的值，转换成bean对象属性里对应的类型值
	 *
	 * @param value
	 *            Object对象值
	 * @param fieldTypeClass
	 *            属性的类型
	 * @return 转换后的值
	 */
	private static Object convertValType(Object value, Class<?> fieldTypeClass) {
		Object retVal = null;
		if (Long.class.getName().equals(fieldTypeClass.getName())
				|| long.class.getName().equals(fieldTypeClass.getName())) {
			retVal = Long.parseLong(value + "");
		} else if (Integer.class.getName().equals(fieldTypeClass.getName())
				|| int.class.getName().equals(fieldTypeClass.getName())) {
			retVal = Integer.parseInt(value + "");
		} else if (Float.class.getName().equals(fieldTypeClass.getName())
				|| float.class.getName().equals(fieldTypeClass.getName())) {
			retVal = Float.parseFloat(value + "");
		} else if (Double.class.getName().equals(fieldTypeClass.getName())
				|| double.class.getName().equals(fieldTypeClass.getName())) {
			retVal = Double.parseDouble(value + "");
		} else {
			retVal = value;
		}
		return retVal;
	}

    /**
     * 封装风车理财回调路径
     *
     * @param requestUrl
     * @param params
     * @return
     */
    private static String buildRequestParam(String requestUrl, Map<String, String> params) throws Exception {
        if (StringUtils.isBlank(requestUrl)) {
            return "";
        }
        String url = requestUrl.concat(URL_SPLIT_STR).concat("ts=").concat(String.valueOf(System.currentTimeMillis()))
                .concat("&from=hyjf&").concat("param=").concat(buildParam(params));
        return url;
    }

    /**
     * 构造请求风车理财param参数
     *
     * @param params
     * @return
     */
    private static String buildParam(Map<String, String> params) throws Exception {
        StringBuffer sb = new StringBuffer();
        if (!CollectionUtils.isEmpty(params)) {
            for (Map.Entry entry : params.entrySet()) {
                sb.append(entry.getKey()).append(CONCAT_STR).append(entry.getValue()).append(SPLIT_STR);
            }
        }
        if (sb.length() < 1) {
            return "";
        }
        String param = sb.substring(0, sb.length() - 1);
        return URLEncoder.encode(WrbCoopDESUtil.desEncrypt(WrbCoopDESUtil.PF_KEY, param), ENCODE_STRING);
    }


    public static void main(String[] args) throws Exception {
        String str = "pf_user_id=1&invest_record_id=14982935328214308934&bid_id=HBD17060008";
        str = "end_time=2014-06-10 00:00:00&limit=10&offset=10&pf_user_id=1&start_time=2014-05-10 00:00:00";
        String signStr = WrbCoopDESUtil.desEncrypt(WrbCoopDESUtil.KEY, str);
        System.out.println("未做编码的字符串：" + signStr);
        String encodeStr = URLEncoder.encode(signStr, ENCODE_STRING);
        System.out.println("编码后的字符串：" + encodeStr);
        parseParam(signStr);
        // System.out.println("解密:" + WrbCoopDESUtil.desDecrypt(WrbCoopDESUtil.KEY,
        // URLDecoder.decode(encodeStr,"utf-8")));
        //Map<String, String> params = new HashMap<>();
        //params.put("key1", "value1");
        // params.put("key2", "value2");
        //System.out.println(buildRequestParam("", params));
    }

}
