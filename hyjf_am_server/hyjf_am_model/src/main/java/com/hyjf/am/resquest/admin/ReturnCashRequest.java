package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

import java.math.BigDecimal;

/**
 * @author lisheng
 * @version NaMiMarketingRequest, v0.1 2018/12/26 14:39
 */

public class ReturnCashRequest extends BasePage {
    private Integer userId;
    private String orderId;
    private Integer productType;

    private BigDecimal investMoney;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public BigDecimal getInvestMoney() {
        return investMoney;
    }

    public void setInvestMoney(BigDecimal investMoney) {
        this.investMoney = investMoney;
    }
}
