/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.common.enums;

/**
 * @author yinhui
 * @version DateEnum, v0.1 2018/6/20 17:28
 */
public enum DateEnum {
    January("1","January"),
    February("2","February"),
    March("3","First"),//第一季度
    April("4","April"),
    May("5","May"),
    June("6","半年度"),
    July("7","July"),
    August("8","August"),
    September("9","third"),//第三季度
    October("10","October"),
    November("11","November"),
    December("12","全年度");

    private String key;
    private String value;

    private DateEnum(String key, String value){
        this.key = key;
        this.value = value;
    }

    // 普通方法
    public static String getValue(String key) {
        for (DateEnum c : DateEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return null;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
