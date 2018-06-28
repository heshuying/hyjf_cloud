/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.hjh;

import java.io.Serializable;

/**
 * 汇计划加入订单计算VO
 * @author liuyang
 * @version HjhCalculateFairValueVO, v0.1 2018/6/26 16:30
 */
public class HjhCalculateFairValueVO implements Serializable {
    private static final long serialVersionUID = -4063164049843296435L;

    // 加入订单号
    private String accedeOrderId;

    // 计算类型:0:清算,1:计算
    private Integer calculateType;

    public String getAccedeOrderId() {
        return accedeOrderId;
    }

    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId;
    }

    public Integer getCalculateType() {
        return calculateType;
    }

    public void setCalculateType(Integer calculateType) {
        this.calculateType = calculateType;
    }
}
