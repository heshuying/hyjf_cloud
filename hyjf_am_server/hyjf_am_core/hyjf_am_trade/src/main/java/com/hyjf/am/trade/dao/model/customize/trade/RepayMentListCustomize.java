package com.hyjf.am.trade.dao.model.customize.trade;

import java.io.Serializable;

/**
 * web端已回款列表
 */
public class RepayMentListCustomize implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 358190081082338992L;
	/**项目编号*/
	private String borrowNid;
	/**项目期限*/
	private String borrowPeriod;
	/**项目还款方式*/
    private String borrowStyle;
    /**项目类别*/
    private String borrowClass;
    /**项目类别*/
    private String projectType;
	/**项目年化收益率*/
	private String borrowApr;
	/**用户id*/
	private String tenUserId;
	/**投资订单号*/
	private String nid;
	/**投资金额*/
	private String account;
	/**还款总额*/
	private String recoverAccountAll;
	/**还款收益*/
	private String recoverAccountInterest;
	/**还款时间*/
	private String repayOrddate;
	/**类别标示*/
	private String type;
	/**说明*/
	private String data;
	/**优惠券编号*/
	private String couponType;
    public String getBorrowNid() {
        return borrowNid;
    }
    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }
    public String getBorrowPeriod() {
        return borrowPeriod;
    }
    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }
    public String getBorrowApr() {
        return borrowApr;
    }
    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }
    public String getTenUserId() {
        return tenUserId;
    }
    public void setTenUserId(String tenUserId) {
        this.tenUserId = tenUserId;
    }
    public String getNid() {
        return nid;
    }
    public void setNid(String nid) {
        this.nid = nid;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getRecoverAccountAll() {
        return recoverAccountAll;
    }
    public void setRecoverAccountAll(String recoverAccountAll) {
        this.recoverAccountAll = recoverAccountAll;
    }
    public String getRepayOrddate() {
        return repayOrddate;
    }
    public void setRepayOrddate(String repayOrddate) {
        this.repayOrddate = repayOrddate;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getCouponType() {
        return couponType;
    }
    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }
    public String getBorrowStyle() {
        return borrowStyle;
    }
    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle;
    }
    public String getBorrowClass() {
        return borrowClass;
    }
    public void setBorrowClass(String borrowClass) {
        this.borrowClass = borrowClass;
    }
    public String getProjectType() {
        return projectType;
    }
    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }
    public String getRecoverAccountInterest() {
        return recoverAccountInterest;
    }
    public void setRecoverAccountInterest(String recoverAccountInterest) {
        this.recoverAccountInterest = recoverAccountInterest;
    }
    
	
}