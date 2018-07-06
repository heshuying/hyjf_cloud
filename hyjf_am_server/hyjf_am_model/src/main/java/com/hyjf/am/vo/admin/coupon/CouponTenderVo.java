package com.hyjf.am.vo.admin.coupon;

import java.util.List;
import java.util.Map;

public class CouponTenderVo<T> {
    //项目状态
    private Map<String, String> couponReciveStatusList;
    private String investTotal;
    private List<T>  recordList;
    private CouponTenderDetailVo detail;
    private List<T> couponRecoverlist;
    private String recoverInterest;

    public Map<String, String> getCouponReciveStatusList() {
        return couponReciveStatusList;
    }

    public void setCouponReciveStatusList(Map<String, String> couponReciveStatusList) {
        this.couponReciveStatusList = couponReciveStatusList;
    }

    public String getInvestTotal() {
        return investTotal;
    }

    public void setInvestTotal(String investTotal) {
        this.investTotal = investTotal;
    }

    public List<T> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<T> recordList) {
        this.recordList = recordList;
    }

    public CouponTenderDetailVo getDetail() {
        return detail;
    }

    public void setDetail(CouponTenderDetailVo detail) {
        this.detail = detail;
    }

    public List<T> getCouponRecoverlist() {
        return couponRecoverlist;
    }

    public void setCouponRecoverlist(List<T> couponRecoverlist) {
        this.couponRecoverlist = couponRecoverlist;
    }

    public String getRecoverInterest() {
        return recoverInterest;
    }

    public void setRecoverInterest(String recoverInterest) {
        this.recoverInterest = recoverInterest;
    }
}
