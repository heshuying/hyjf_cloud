/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.bean.crmtender;

import java.io.Serializable;

/**
 * CRM投资推送Bean
 *
 * @author liuyang
 * @version CrmInvestMsgBean, v0.1 2018/11/7 10:32
 */
public class CrmInvestMsgBean implements Serializable {
    private static final long serialVersionUID = 60229271189495986L;

    // 投资订单号或加入订单号
    private String orderId;

    // 投资类型:0:散标投资,1:智投服务
    private Integer investType;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getInvestType() {
        return investType;
    }

    public void setInvestType(Integer investType) {
        this.investType = investType;
    }
}
