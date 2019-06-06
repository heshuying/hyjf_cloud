package com.hyjf.wbs.sign;

import com.hyjf.common.util.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.*;

/**
 * 加签验签工具类
 * Created by cui on 2019/4/11.
 */
public class WbsSignUtil {
    private static Logger logger = LoggerFactory.getLogger(WbsSignUtil.class);

    /**
     * 请求数据验签
     *
     * @param object
     * @param sign
     * @param secret
     * @return
     */

    public static boolean verify(Object object, String sign, String secret) {

        return sign.equalsIgnoreCase(encrypt(object, secret));

    }

    /**
     * 请求数据加签
     *
     * @param object
     * @param secret
     * @return
     */

    public static String encrypt(Object object, String secret) {

        String sign = "";
        try {
            String signText = buildSign(object, secret);

            sign = md5(signText);

        } catch (Exception e) {
            throw new IllegalArgumentException("加签失败！" + e.getMessage(), e);
        }

        return sign;
    }

    private static String buildSign(Object object, String secret) throws Exception {

        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap = ConvertUtils.convertObjectToMap(object);
        Set<String> keySet = paramsMap.keySet();
        List<String> paramNames = new ArrayList<String>(keySet);

        Collections.sort(paramNames);

        StringBuilder paramNameValue = new StringBuilder();

        for (String paramName : paramNames) {
            paramNameValue.append(paramName).append(paramsMap.get(paramName));
        }

        String source = secret + paramNameValue.toString() + secret;

        return source;
    }

    /**
     * 生成md5,全部大写
     *
     * @param message
     * @return
     */
    public static String md5(String message) {
        try {
            // 1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 2 将消息变成byte数组
            byte[] input = message.getBytes();

            // 3 计算后获得字节数组,这就是那128位了
            byte[] buff = md.digest(input);

            // 4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串
            return byte2hex(buff);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 二进制转十六进制字符串
     *
     * @param bytes
     * @return
     */
    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }


}
