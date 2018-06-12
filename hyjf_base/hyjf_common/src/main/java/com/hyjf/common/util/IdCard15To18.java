package com.hyjf.common.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdCard15To18 {
	/** 
     * 根据15位的身份证号码获得18位身份证号码 
     * @param fifteenIDCard 15位的身份证号码 
     * @return 升级后的18位身份证号码 
     * @throws Exception 如果不是15位的身份证号码，则抛出异常 
     */  
    public static String getEighteenIDCard(String fifteenIDCard) throws Exception{  
        if(fifteenIDCard != null && fifteenIDCard.length() == 15){  
            StringBuilder sb = new StringBuilder();  
            sb.append(fifteenIDCard.substring(0, 6))  
              .append("19")  
              .append(fifteenIDCard.substring(6));  
            sb.append(getVerifyCode(sb.toString()));  
            return sb.toString();  
        } else {  
            throw new Exception("不是15位的身份证");  
        }  
    } 
    
    /** 
     * 获取校验码 
     * @param idCardNumber 不带校验位的身份证号码（17位） 
     * @return 校验码 
     * @throws Exception 如果身份证没有加上19，则抛出异常 
     */  
    private static char getVerifyCode(String idCardNumber) throws Exception{  
        if(idCardNumber == null || idCardNumber.length() < 17) {  
            throw new Exception("不合法的身份证号码");  
        }  
        char[] Ai = idCardNumber.toCharArray();  
        int[] Wi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};  
        char[] verifyCode = {'1','0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};  
        int S = 0;  
        int Y;  
        for(int i = 0; i < Wi.length; i++){  
            S += (Ai[i] - '0') * Wi[i];  
        }  
        Y = S % 11;  
        return verifyCode[Y];  
    }  
      
    /** 
     * 校验18位的身份证号码的校验位是否正确 
     * @param idCardNumber 18位的身份证号码 
     * @return  
     * @throws Exception 
     */  
    public static boolean verify(String idCardNumber) throws Exception{  
        if(idCardNumber == null || idCardNumber.length() != 18) {  
            throw new Exception("不是18位的身份证号码");  
        }  
        return getVerifyCode(idCardNumber) == idCardNumber.charAt(idCardNumber.length() - 1);  
    }
	
	/**
     * 方法描述： 根据身份证获取年龄
     * @param idNum
     * @return 
     */
    public static int getAgeById(String idNum) {
        int age = 0;
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getDefault());//获取系统当前时间
        int currentYear = calendar.get(Calendar.YEAR);
        if (idNum.matches("^\\d{15}$|^\\d{17}[\\dxX]$")) {
            if (idNum.length() == 18) {
                Pattern pattern = Pattern.compile("\\d{6}(\\d{4})\\d{6}(\\d{1})[\\dxX]{1}");
                Matcher matcher = pattern.matcher(idNum);
                if (matcher.matches()) {
                    age = currentYear - Integer.parseInt(matcher.group(1));
                }
            } else if (idNum.length() == 15) {
                Pattern p = Pattern.compile("\\d{6}(\\d{2})\\d{5}(\\d{1})\\d{1}");
                Matcher m = p.matcher(idNum);
                if (m.matches()) {
                    int year = Integer.parseInt(m.group(1));
                    year = 2000 + year;
                    if (year > 2020) {
                        year = year - 100;
                    }
                    age = currentYear - year;
                }
            }
        }
        return age;
    }
}