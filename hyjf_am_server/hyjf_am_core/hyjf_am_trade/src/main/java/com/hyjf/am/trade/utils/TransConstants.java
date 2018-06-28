package com.hyjf.am.trade.utils;

import com.hyjf.common.util.GetOrderIdUtils;

public class TransConstants {

	/** 空格定义 */
	public static final String BLANK = " ";

    /**币种 156-人民币*/
    public static final String CURRENCY = "156";
    
    /**江西sit时间*/
    public static final String SIT_TIME = GetOrderIdUtils.getTxDate();
    
    /**符号*/
    public static final String SYMBOL = "-";
    
    /**FOURZERO*/
    public static final String FOURZERO = "0000";
    
    /**TWOZERO*/
    public static final String TWOZERO = "00";
    
    /**开户结果文件长度*/
    public static final Integer ALEVELENGTH = 371;
    public static final Integer EVELENGTH = 139;
    
    
}
