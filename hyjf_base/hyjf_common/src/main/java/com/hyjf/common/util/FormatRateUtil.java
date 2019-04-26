/**
 * @description 类说明
 * @author nxl
 * @version FormatRateUtil, v0.1 2019/4/10 17:11
 */

package com.hyjf.common.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * @author nxl
 * @version FormatRateUtil, v0.1 2019/4/10 17:11
 * 平台所有利率（参考年回报率，历史年回报率，折让率，加息利率）全部统一为：
 * 小数点后一位（除非后台配置为小数点后两位且不为0时，则展示小数点后两位）
 */
public class FormatRateUtil {
    /**
     * 格式化利率(判断两位小数的后一位是否为0，如果是，则转换为一位小数）
     * @param borrowApr
     * @return
     */
    public static String formatBorrowApr(String borrowApr) {
    	if(borrowApr==null) {
    		return null;
    	}
        if (StringUtils.isEmpty(borrowApr)) {
            return "";
        }
        String[] stringArr = borrowApr.split("\\.");
        if (stringArr.length <= 0) {
            return "0.0";
        }
        //整数或只有一位小数的情况下，格式化利率
//        if (stringArr.length == 1 || stringArr[1].length() == 1) {
//            return borrowApr;
//        }
 //       String spitArr = stringArr[1];
        //两位小数的情况下，判断最后一位是否为0
    //    String subBorr = spitArr.substring(spitArr.length() - 1, spitArr.length());
        if ((borrowApr.substring( borrowApr.length()-1,borrowApr.length())).equals("0")&&!(borrowApr.substring( borrowApr.length()-3,borrowApr.length()-2)).equals(".")) {
//            BigDecimal big = new BigDecimal(borrowApr).setScale(1);
        	return borrowApr.substring(0, borrowApr.length()-1);
        }
        if ((borrowApr.substring( borrowApr.length()-1,borrowApr.length())).equals("%")&&(borrowApr.substring( borrowApr.length()-2,borrowApr.length()-1)).equals("0")&&!(borrowApr.substring( borrowApr.length()-3,borrowApr.length()-2)).equals(".")) {
//          BigDecimal big = new BigDecimal(borrowApr).setScale(1);
      	return borrowApr.substring(0, borrowApr.length()-2)+"%";
      }
        return borrowApr;
    }

}
