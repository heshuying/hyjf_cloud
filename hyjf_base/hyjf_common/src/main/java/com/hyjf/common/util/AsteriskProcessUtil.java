package com.hyjf.common.util;

import org.apache.commons.lang3.StringUtils;

public class AsteriskProcessUtil {
    /**
     *
     * 根据用户权限返回是否加星的数据
     * @author hsy
     * @param originValue
     * @param permission
     * @return
     */
  /*  public static String getAsteriskedValue(String originValue, String permission){
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
    }*/

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

    public static String getAsteriskedValue(String originValue){
        String phoneNumAsterisked = "";
        if(StringUtils.isEmpty(originValue)){
            return "";
        }
        if(originValue.length() <= 7){
            return originValue;
        }
        phoneNumAsterisked = originValue.substring(0, 3) + getAsterisked(originValue.length() - 7) + originValue.substring(originValue.length() - 4);
        return phoneNumAsterisked;
    }

    /**
     * 手机号脱敏(前1后1显示，其余脱敏显示*)
     * @param mobile
     * @return
     */
    public static String getAsteriskedMobile(String mobile){
        if (StringUtils.isEmpty(mobile) || (mobile.length() != 11)) {
            return mobile;
        }
        return mobile.replaceAll("(\\d{1})\\d{9}(\\d{1})", "$1*********$2");
    }

    /**
     * 姓名脱敏(只显示第一个字，其余脱敏显示*)
     * @param cnName
     * @return
     */
    public static String getAsteriskedCnName(String cnName){
        if (StringUtils.isEmpty(cnName)){
            return cnName;
        }

        String subStr = cnName.substring(1,cnName.length());
        if (StringUtils.isEmpty(subStr)){
            return cnName;
        }
        String stars = "";
        for (int i = 0; i < subStr.length(); i++) {
            stars+="*";
        }
        return cnName.substring(0,1)+stars;
    }


    public static void main(String[] args) {
        System.out.println(AsteriskProcessUtil.getAsteriskedCnName(""));
    }

}
