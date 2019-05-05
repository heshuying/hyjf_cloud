package com.hyjf.cs.market.util;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.primitives.Doubles.asList;

/**
 * @Author walter.limeng
 * @Description //518活动奖品
 * @Date 16:45 2019-05-05
 * @Param
 * @return
 **/
public enum Activity518Prize {
    /*
     *  奖品 0：18元代金券
     *  奖品 1：58元代金券
     *  奖品 2：518元代金券
     *  奖品 3：0.8%加息券
     *  奖品 4：1.0%加息券
     *  奖品 5：iPhone XS（256G）
     *  奖品 6：华为P30（256G）
     *
     */

    PRIZE00(0,"18元代金券"),
    PRIZE01(1,"58元代金券"),
    PRIZE02(2,"518元代金券"),
    PRIZE03(3,"0.8%加息券"),
    PRIZE04(4,"1.0%加息券"),
    PRIZE05(5,"iPhone XS（256G）"),
    PRIZE06(6,"华为P30（256G）");

    /**
     *
     *
     */

    // 成员变量
    private Integer code;
    private String value;
    public static List<Double> prizeList = asList(30d, 20d, 10d,30d,10d);;

    Activity518Prize(Integer code, String value){
        this.code = code;
        this.value = value;
    }
    public static String getValue(Integer code) {
        for (Activity518Prize c : Activity518Prize.values()) {
            if (c.getCode().equals(code) ) {
                return c.value;
            }
        }
        return null;
    }

    public static List<Double> getPrize(){
        return prizeList;
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    }
