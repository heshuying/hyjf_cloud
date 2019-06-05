package com.hyjf.common.enums;

/**
 * 零壹财经枚举
 * @Author: yinhui
 * @Date: 2019/6/5 16:06
 * @Version 1.0
 */
public enum ZeroOneCaiJingEnum {
    /** 借款 */
    LEND("lend"),
    /** 出借 */
    INVEST("invest"),
    /** 提前还款 */
    ADVANCEDREPAY("advanced-repay");

    private ZeroOneCaiJingEnum(String name){
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
