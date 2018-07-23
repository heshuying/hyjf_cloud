package com.hyjf.am.vo.trade.wrb;

import java.math.BigDecimal;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/20 14:14
 * @Description: WrbTenderNotifyCustomizeVO
 */
public class WrbTenderNotifyCustomizeVO {
    private Integer userId;
    // 投资时间
    private String addtime;
    //投资订单号
    private String nid;
    // 投资金额
    private BigDecimal account;
    // 投资期限 按天计算 每月30天
    private String investPeriod;
    // 投资利率 整数
    private String borrowApr;
    // 还款方式 中文翻译
    private String borrowStyleName;
    // 借款标题
    private String borrowName;
    // 借款id
    private String borrowNid;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public String getInvestPeriod() {
        return investPeriod;
    }

    public void setInvestPeriod(String investPeriod) {
        this.investPeriod = investPeriod;
    }

    public String getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }

    public String getBorrowStyleName() {
        return borrowStyleName;
    }

    public void setBorrowStyleName(String borrowStyleName) {
        this.borrowStyleName = borrowStyleName;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }
}
