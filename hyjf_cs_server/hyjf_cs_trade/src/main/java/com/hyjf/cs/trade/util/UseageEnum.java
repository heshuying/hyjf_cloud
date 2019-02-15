/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.util;

/**
 * @author dangzw
 * @version UseageEnum, v0.1 2019/2/14 14:14
 */
public enum UseageEnum {
    ONE("01","个人消费"),
    TWO("02","个人经营"),
    THREE("03","个人资金周转"),
    FOUR("04","房贷"),
    FIVE("05","企业经营"),
    SIX("06","企业周转"),
    SEVEN("99","其他");

    private String key;//编码
    private String use;//用途

    UseageEnum(String key ,String use){
        this.key = key;
        this.use = use;
    }

    public static String getUse(String key){

        for (UseageEnum e:UseageEnum.values()){

            if(key.equals(e.key)){
                return e.use;
            }
        }
        return null;
    }

    public static String getKey(String use){

        for (UseageEnum e:UseageEnum.values()){

            if(use.equals(e.use)){
                return e.key;
            }
        }
        return null;
    }
}
