package com.hyjf.am.trade.dao.model.customize;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;

/**
 * @Author: yinhui
 * @Date: 2019/6/3 14:22
 * @Version 1.0
 */
public class ZeroOneCaiJingCustomize {

    //用户id
    private Integer userIds;
    //出借订单号
    private String nid;
    //标的号
    private String borrowNid;
    //出借金额
    private BigDecimal account;
    //操作平台
    private String client;
    //投资时间or债转时间
    private String createTime;
    //标示是投资还是债转
    private String flag;
    //冻结订单号
    private String freezeOrderId;
    //实际借款天数
    private String spentday;
    //还款日期
    private String repayTime;

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getUserIds() {
        return userIds;
    }

    public void setUserIds(Integer userIds) {
        this.userIds = userIds;
    }

    public String getFreezeOrderId() {
        return freezeOrderId;
    }

    public void setFreezeOrderId(String freezeOrderId) {
        this.freezeOrderId = freezeOrderId;
    }

    public String getSpentday() {
        return spentday;
    }

    public void setSpentday(String spentday) {
        this.spentday = spentday;
    }

    public String getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(String repayTime) {
        this.repayTime = repayTime;
    }
}
