/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.common.util;

import com.hyjf.common.validator.Validator;

/**
 * @Description 银行卡相关操作
 * @Author sss
 * @Version v0.1
 * @Date 2018/6/5 14:20
 */
public class BankCardUtil {

    /**
     *
     * 银行卡脱敏显示
     * @author sunss
     * @param cardNo
     * @return
     */
    public static String getCardNo(String cardNo){
        String str = "";
        if(Validator.isNotNull(cardNo)&&cardNo.length()>10){
            str = "****  ****  **** " + cardNo.substring(cardNo.length() - 4, cardNo.length());
        }
        // add by nxl 银行卡号是短号的情况下,对银行进行脱敏显示
        if(Validator.isNotNull(cardNo)&&cardNo.length()<10){
            str = "*****" + cardNo.substring(cardNo.length() - 4, cardNo.length());
        }
        return str;
    }

    /**
     * 获取格式化后的银行卡，充值页面使用
     * add by cwyang
     * @param cardNo
     * @return
     */
    public static String getFormatCardNo(String cardNo){
        String str = "";
        if(Validator.isNotNull(cardNo)&&cardNo.length()>10){
            str = "尾号 " + cardNo.substring(cardNo.length() - 4, cardNo.length());
        }
        return str;
    }
}
