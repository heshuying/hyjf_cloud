/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.bean;

/**
 * AEMS系统:还款计划查询请求Bean
 *
 * @author liuyang
 * @version AemsBorrowRepayPlanRequestBean, v0.1 2018/12/12 11:14
 */
public class AemsBorrowRepayPlanRequestBean extends BaseBean {

    // 最后还款开始时间
    private String startDate;
    // 最后还款开始时间
    private String endDate;
    // 查询类型:0:待还款 1:已还款
    private String repayType;
    // 是否分期
    private String isMonth;
    // 资产编号
    private String productId;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRepayType() {
        return repayType;
    }

    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }

    public String getIsMonth() {
        return isMonth;
    }

    public void setIsMonth(String isMonth) {
        this.isMonth = isMonth;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
