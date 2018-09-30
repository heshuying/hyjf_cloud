package com.hyjf.am.vo.admin.coupon;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

public class CouponBackMoneyCustomize extends BaseVO implements Serializable {
    /**
     * serialVersionUID:
     */

    private static final long serialVersionUID = 1L;

    //用户优惠券id
    private Integer id;
    //优惠券用户编号
    private String couponUserCode;
    //订单号
    private String nid;
    //用户名
    private String username;
    //优惠券编号
    private String couponCode;
    //项目编号
    private String borrowNid;
    //回款期数
    private String recoverPeriod;
    //体验金收益（元）
    private String recoverInterest;
    //转账订单号
    private String transferId;
    //状态
    private String receivedFlg;
    //应回款日期
    private String recoverTime;
    //真实投资金额
    private String recoverCapital;
    //优惠券使用时间
    private String addTime;
    //优惠券来源
    private String couponSource;
    //内容
    private String couponContent;

    //项目年化收益
    private String borrowApr;
    //优惠券面值
    private String couponQuota;
    //体验金收益期限
    private String couponProfitTime;
    //姓名
    private String truename;
    //手机号
    private String mobile;
    //客户号
    private String chinapnrUsrcustid;
    //项目名称
    private String borrowName;
    //项目期限
    private String borrowPeriod;
    //放款时间
    private String hcrAddTime;
    //实际回款时间
    private String recoverYestime;
    /**
     * 检索条件 时间开始
     */
    private String timeStartSrch;

    /**
     * 检索条件 时间结束
     */
    private String timeEndSrch;

    /**
     * 检索条件 时间结束
     */
    private String couponType;
    /**
     * 检索条件 limitStart
     */
    private int limitStart = -1;

    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;

    /**
     * 页数
     */
    private Integer currPage = 1;
    /**
     * 大小
     */
    private Integer pageSize = 10;

    private String father;

    private String son;

    public String getTimeStartSrch() {
        return timeStartSrch;
    }

    public void setTimeStartSrch(String timeStartSrch) {
        this.timeStartSrch = timeStartSrch;
    }

    public String getTimeEndSrch() {
        return timeEndSrch;
    }

    public void setTimeEndSrch(String timeEndSrch) {
        this.timeEndSrch = timeEndSrch;
    }

    public int getLimitStart() {
        return (getCurrPage() -1)*(getPageSize());
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return (getCurrPage())*(getPageSize());
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public Integer getCurrPage() {
        return currPage;
    }

    public void setCurrPage(Integer currPage) {
        this.currPage = currPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getRecoverPeriod() {
        return recoverPeriod;
    }

    public void setRecoverPeriod(String recoverPeriod) {
        this.recoverPeriod = recoverPeriod;
    }

    public String getRecoverInterest() {
        return recoverInterest;
    }

    public void setRecoverInterest(String recoverInterest) {
        this.recoverInterest = recoverInterest;
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public String getReceivedFlg() {
        return receivedFlg;
    }

    public void setReceivedFlg(String receivedFlg) {
        this.receivedFlg = receivedFlg;
    }

    public String getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(String recoverTime) {
        this.recoverTime = recoverTime;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCouponUserCode() {
        return couponUserCode;
    }

    public void setCouponUserCode(String couponUserCode) {
        this.couponUserCode = couponUserCode;
    }

    public String getRecoverCapital() {
        return recoverCapital;
    }

    public void setRecoverCapital(String recoverCapital) {
        this.recoverCapital = recoverCapital;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getCouponSource() {
        return couponSource;
    }

    public void setCouponSource(String couponSource) {
        this.couponSource = couponSource;
    }

    public String getCouponContent() {
        return couponContent;
    }

    public void setCouponContent(String couponContent) {
        this.couponContent = couponContent;
    }

    public String getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }

    public String getCouponQuota() {
        return couponQuota;
    }

    public void setCouponQuota(String couponQuota) {
        this.couponQuota = couponQuota;
    }

    public String getCouponProfitTime() {
        return couponProfitTime;
    }

    public void setCouponProfitTime(String couponProfitTime) {
        this.couponProfitTime = couponProfitTime;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getChinapnrUsrcustid() {
        return chinapnrUsrcustid;
    }

    public void setChinapnrUsrcustid(String chinapnrUsrcustid) {
        this.chinapnrUsrcustid = chinapnrUsrcustid;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getHcrAddTime() {
        return hcrAddTime;
    }

    public void setHcrAddTime(String hcrAddTime) {
        this.hcrAddTime = hcrAddTime;
    }

    public String getRecoverYestime() {
        return recoverYestime;
    }

    public void setRecoverYestime(String recoverYestime) {
        this.recoverYestime = recoverYestime;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getSon() {
        return son;
    }

    public void setSon(String son) {
        this.son = son;
    }
}
