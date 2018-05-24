package com.hyjf.cs.user.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;

/**
 * 
 * 安全工具类
 * 
 * @author renxingchen
 * @version hyjf 1.0
 * @since hyjf 1.0 2016年2月18日
 */
public class SecretUtil {

    private static Map<String, String> redis = new HashMap<String, String>();

    private static String letter = "abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ";

    /**
     * 验证安全码
     *
     * @param appId
     * @param appKey
     * @param randomString
     * @param secretKey
     * @return
     */
    public static boolean checkSecretKey(String appId, String appKey, String randomString, String secretKey) {
        String encryptedAppId = DES.encryptDES_ECB(appId, appKey);
        String checkValue = encryptedAppId + randomString;
        String ascii = sortByAscii(checkValue);

        return secretKey != null && secretKey.equals(ascii);
    }

    // public static void main(String[] args) {
    // String encryptedAppId = DES.encryptDES_ECB("hyjf-app-android-001",
    // "wxyzABCD");
    // StringBuffer sb = new StringBuffer(8);
    // Random random = new Random();
    // for (int i = 0; i < 8; i++) {
    // sb.append(encryptedAppId.charAt(random.nextInt(32)));
    // }
    // System.err.println(sb.toString());
    // }

    /**
     * 生成唯一标识符
     *
     * 生成规则： uuid + 三位随机字母 + 三位随机数
     *
     * @return
     */
    public static String createSign() {
        String sign = UUID.randomUUID().toString() + RandomUtils.nextInt(0, 9);
        Random random = new Random();
        // 生成随机三位字母
        for (int i = 0; i < 3; i++) {
            sign = sign + letter.charAt(random.nextInt(52));
        }
        // 生成一个随机的三位数
        sign = sign + GetOrderIdUtils.getRandomNum(1000);

        return sign;
    }

    /**
     * 生成Token
     *
     * 生成规则：
     *
     * @return
     */
    public static String createToken(String sign, Integer userId, String username) {
        String encryptValue;// 加密后的token
        // 参数校验
        if (StringUtils.isEmpty(sign.trim()) || userId <= 0 || StringUtils.isEmpty(username.trim())) {
            throw new RuntimeException("参数异常");
        }
        // 获取sign对应的加密key
        String value = RedisUtils.get(sign);
        SignValue signValue;
        if (null != value) {
            signValue = JSON.parseObject(value, SignValue.class);
            AppUserToken token = new AppUserToken(userId, username);
            String encryptString = JSON.toJSONString(token);
            encryptValue = DES.encryptDES_ECB(encryptString, signValue.getKey());
            signValue.setToken(encryptValue);
            RedisUtils.set(sign, JSON.toJSONString(signValue), RedisUtils.signExpireTime);
        } else {
            throw new RuntimeException("参数异常");
        }
        return encryptValue;
    }

    /**
     * 验证Order
     *
     * @param key
     * @param token
     * @param randomString
     * @param order
     * @return
     */
    public static boolean checkOrder(String key, String token, String randomString, String order) {
    	if(StringUtils.isBlank(key) || StringUtils.isBlank(randomString) || StringUtils.isBlank(order) ){
    		return false;
    	}
    	String ascii = sortByAscii(token + randomString);
        String checkValue = DES.encryptDES_ECB(ascii, key);
        return order.equals(checkValue);
    }

    /**
     * 验证Order
     * @param key
     * @param randomString
     * @param order
     * @return
     */
    public static boolean checkOrder(String key, String randomString, String order) {
    	if(StringUtils.isBlank(key) || StringUtils.isBlank(randomString) || StringUtils.isBlank(order) ){
    		return false;
    	}
        String ascii = sortByAscii(randomString);
        String checkValue = DES.encryptDES_ECB(ascii, key);
        return order.equals(checkValue);
    }

    /**
     * 保存Redis
     *
     * @return
     */
    public static void saveRedis(String key, String value) {
        redis.put(key, value);
    }

    /**
     * 取得Redis
     *
     * @return
     */
    public static String getRedis(String key) {
        return redis.get(key);
    }

    /**
     * 删除Redis
     *
     * @return
     */
    public static String removeRedis(String key) {
        return redis.remove(key);
    }

    /**
     * 保存InitKey
     *
     * @return
     */
    public static void saveInitKey(String sign, String value) {
        saveRedis(sign + "_initKey", value);
    }

    /**
     * 取得InitKey
     *
     * @return
     */
    public static String getInitKey(String sign) {
        return getRedis(sign + "_initKey");
    }

    /**
     * 清除InitKey
     *
     * @return
     */
    public static String removeInitKey(String sign) {
        return removeRedis(sign + "_initKey");
    }

    /**
     * 保存InitKey
     *
     * @return
     */
    public static void saveKey(String sign, String value) {
        saveRedis(sign + "_Key", value);
    }

    /**
     * 取得InitKey
     *
     * @return
     */
    public static String getKey(String sign) {
        String value = RedisUtils.get(sign);
        if (value != null) {
            SignValue signValue = JSON.parseObject(value, SignValue.class);
            return signValue.getKey();
        } else {
            return null;
        }
    }

    /**
     * 清除InitKey
     *
     * @return
     */
    public static String removeKey(String sign) {
        return removeRedis(sign + "_Key");
    }

    /**
     * 保存Sign
     *
     * @return
     */
    public static void saveSignVal(String sign, String value) {
        saveRedis(sign, value);
    }

    /**
     * 取得Sign
     *
     * @return
     */
    public static String getSignVal(String sign) {
        return getRedis(sign);
    }

    /**
     * 清除Sign
     *
     * @return
     */
	public static void clearToken(String sign) {
		// 获取sign缓存
		String value = RedisUtils.get(sign);
		if (!Validator.isNull(value)) {
			SignValue signValue = JSON.parseObject(value, SignValue.class);
			// 清除token
			signValue.setToken(null);
			// 更新缓存
			RedisUtils.set(sign, JSON.toJSONString(signValue), RedisUtils.signExpireTime);
		}
	}

    /**
     * 删除Sign
     *
     * @return
     */
    public static String removeSign(String sign) {
        return removeRedis(sign);
    }

    /**
     * 判断是否存在
     *
     * @return
     */
    public static boolean isExists(String key) {
        return redis.containsKey(key);
    }

    /**
     * 验证token 业务代码不需要再单独调用此方法，废弃
     * @param sign
     * @param token
     * @return
     */
    public static boolean checkToken(String sign, String token) {
        String value = RedisUtils.get(sign);
        SignValue signValue = JSON.parseObject(value, SignValue.class);
        if (null != token) {
            if (token.equals(signValue.getToken())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 从token中取得用户ID
     *
     * @param sign
     * @return
     */
    public static Integer getUserId(String sign) {
    	// 获取sign缓存
        String value = RedisUtils.get(sign);
        if(Validator.isNull(value)){
        	return null;
        }
        SignValue signValue = JSON.parseObject(value, SignValue.class);
        if(Validator.isNull(signValue)){
        	return null;
        }
        String token = signValue.getToken();
        AppUserToken appUserToken;
        if (null != token) {
            appUserToken = JSON.parseObject(DES.decodeValue(signValue.getKey(), token), AppUserToken.class);
        } else {
            throw new RuntimeException("用户未登陆");
        }
        if(appUserToken.getUserId()==null){
            throw new RuntimeException("用户未登陆");
        }
        return appUserToken.getUserId();
    }
    
    /**
     * 从token中取得用户ID
     *
     * @param sign
     * @return
     */
    public static Integer getUserIdNoException(String sign) {
        if(StringUtils.isEmpty(sign)){
            return null;
        }
        // 获取sign缓存
        String value = RedisUtils.get(sign);
        if(StringUtils.isEmpty(value)){
            return null;
        }
        
        SignValue signValue = JSON.parseObject(value, SignValue.class);
        String token = signValue.getToken();
        AppUserToken appUserToken;
        if (null != token) {
            appUserToken = JSON.parseObject(DES.decodeValue(signValue.getKey(), token), AppUserToken.class);
            return appUserToken.getUserId();
        } 
        return null;
    }

    /**
     * 从token中取得用户名称
     *
     * @param sign
     * @return
     */
    public static String getUserName(String sign) {
        // 获取sign缓存
        String value = RedisUtils.get(sign);
        if(Validator.isNull(value)){
        	return null;
        }
        SignValue signValue = JSON.parseObject(value, SignValue.class);
        if(Validator.isNull(signValue)){
        	return null;
        }
        String token = signValue.getToken();
        AppUserToken appUserToken;
        if (null != token) {
            appUserToken = JSON.parseObject(DES.decodeValue(signValue.getKey(), token), AppUserToken.class);
        } else {
            throw new RuntimeException("用户未登陆");
        }
        return appUserToken.getUsername();
    }

    /**
     * 字符串转换为ascii
     *
     * @param content
     * @return
     */
    public static String stringToAscii(String content) {
        String result = "";
        int max = content.length();
        for (int i = 0; i < max; i++) {
            char c = content.charAt(i);
            int b = (int) c;
            result = result + b;
        }
        return result;
    }

    /**
     * 字符串按照ascii排序
     *
     * @param content
     * @return
     */
    public static String sortByAscii(String content) {

        if (Validator.isNull(content)) {
            return "";
        }
        char[] ch = content.toCharArray();
        Arrays.sort(ch);

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ch.length; i++) {
            sb.append(ch[i]);
        }

        return sb.toString();
    }

    /**
     * 
     * sign拷贝重新生成一个新的sign
     * @author renxingchen
     * @param sign
     * @return
     */
    public static String createSignSec(String sign) {
        String signSec = createSign();
        RedisUtils.set(signSec, RedisUtils.get(sign));
        return signSec;
    }

    /**
     * 
     * 获取token
     * @author renxingchen
     * @param sign
     * @return
     */
    public static String getToken(String sign) {
        String value = RedisUtils.get(sign);
        if(Validator.isNull(value)){
        	return null;
        }
        SignValue signValue = JSON.parseObject(value, SignValue.class);
        return signValue.getToken();
    }
}
