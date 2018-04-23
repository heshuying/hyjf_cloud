/*
 * Copyright(c) 2012-2016 JD Pharma.Ltd. All Rights Reserved.
 */
package com.hyjf.common.chinapnr;

import java.math.BigDecimal;

/**
 * 汇付天下商户私有域对象
 * @author renxingchen
 * @version hyjf 1.0
 * @since hyjf 1.0 2016年3月28日
 * @see 上午11:44:28
 */
public class MerPriv {

    private String uuid;

    private Integer userId;

    private String feeFrom;

    private BigDecimal fee;

    private String borrowNid;

    private String client;

    // add by zhangjp 优惠券相关 start
    // 用户优惠券编号
    private String couponGrantId;

    // 更新时间（排他用）
    private Integer couponOldTime;

    // add by zhangjp 优惠券相关 end
    private Integer recordId;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFeeFrom() {
        return feeFrom;
    }

    public void setFeeFrom(String feeFrom) {
        this.feeFrom = feeFrom;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getCouponGrantId() {
        return couponGrantId;
    }

    public void setCouponGrantId(String couponGrantId) {
        this.couponGrantId = couponGrantId;
    }

    public Integer getCouponOldTime() {
        return couponOldTime;
    }

    public void setCouponOldTime(Integer couponOldTime) {
        this.couponOldTime = couponOldTime;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

}
