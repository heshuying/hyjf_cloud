package com.hyjf.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;

public class AsteriskProcessUtil {
    /**
     *
     * 根据用户权限返回是否加星的数据
     * @author hsy
     * @param originValue
     * @param permission
     * @return
     */
    public static String getAsteriskedValue(String originValue, String permission){
        String phoneNumAsterisked = "";
        if(StringUtils.isEmpty(originValue)){
            return "";
        }

        if(originValue.length() <= 7){
            return originValue;
        }

        phoneNumAsterisked = originValue.substring(0, 3) + getAsterisked(originValue.length() - 7) + originValue.substring(originValue.length() - 4);

        boolean isHasPermission = SecurityUtils.getSubject().isPermitted(permission);
        if(isHasPermission){
            return originValue;
        }else{
            return phoneNumAsterisked;
        }
    }

    /**
     *
     * 获取*字符串
     * @author hsy
     * @param count
     * @return
     */
    public static String getAsterisked(int count){
        String result = "";
        for(int i=0; i<count; i++){
            result += "*";
        }

        return result;
    }

    /**
     * 数据脱敏加星
     * @param originStr 原始数据
     * @param startCnt  开始加星位数
     * @param num		加星数量
     * @return
     */
    public static String getAsteriskedValue(String originStr,int startCnt,int num){
        String desensitizationData = "";
        if(StringUtils.isEmpty(originStr)){
            return "";
        }
        if(originStr.length() <= startCnt){
            return originStr;
        }
        desensitizationData = originStr.substring(0, startCnt) + getAsterisked(num);
        return desensitizationData;
    }
    /**
     * 数据脱敏加星
     * @param originStr 原始数据
     * @param startCnt  开始加星位数
     * @return
     */
    public static String getAsteriskedValue(String originStr,int startCnt){
        String desensitizationData = "";
        if(StringUtils.isEmpty(originStr)){
            return "";
        }
        if(startCnt < 0){
            return originStr;
        }
        if(originStr.length() <= startCnt){
            return originStr;
        }
        desensitizationData = getAsterisked(startCnt) + originStr.substring(startCnt, startCnt+2);
        return desensitizationData;
    }
}
