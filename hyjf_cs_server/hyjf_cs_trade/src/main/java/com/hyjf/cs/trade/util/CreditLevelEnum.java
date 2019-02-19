/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.util;

/**
 * 借款人信用等级
 * @author dangzw
 * @version CreditLevelEnum, v0.1 2019/2/14 15:07
 */
public enum CreditLevelEnum {
    ONE("1","BBB"),
    TWO("2","A"),
    THREE("3","AA-"),
    FOUR("4","AA"),
    FIVE("5","AA+"),
    SIX("6","AAA");

    private String key;//编码
    private String level;//等级

    CreditLevelEnum(String key,String level){
        this.key = key;
        this.level = level;
    }

    public static String getKey(String level){

        for (CreditLevelEnum e:CreditLevelEnum.values()){

            if(level.equals(e.level)){
                return e.key;
            }
        }
        return null;
    }
}
