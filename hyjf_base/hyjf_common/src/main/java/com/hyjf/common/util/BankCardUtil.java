/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.common.util;

import com.hyjf.common.validator.Validator;
import org.apache.commons.lang3.StringUtils;

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
        return str;
    }
}
