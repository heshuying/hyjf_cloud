/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.util;

/**
 * 职业类型
 * @author dangzw
 * @version PositionEnum, v0.1 2019/2/14 15:07
 */
public enum PositionEnum {
    ONE("10000","机关企事业单位负责人"),
    TWO("20000","专业技术人员"),
    THREE("30000","办事人员和有关人员"),
    FOUR("40000","会生产服务和生活服务人员"),
    FIVE("50000","农、林、牧、渔业生产及辅助人员"),
    SIX("60000","生产制造及有关人员"),
    SEVEN("70000","军人"),
    EIGHT("80000","不便分类的其他从业人员");

    private String key;//编码
    private String job;//职业

    PositionEnum(String key,String job){
        this.key = key;
        this.job = job;
    }

    public static String getJob(String key){

        for (PositionEnum e:PositionEnum.values()){

            if(key.equals(e.key)){
                return e.job;
            }
        }
        return null;
    }
}
