package com.hyjf.am.resquest.admin;

import com.hyjf.am.resquest.Request;

import java.io.Serializable;
import java.math.BigDecimal;

public class CouponTenderRequest extends Request implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    //主键id
    private Integer id;
    //投资订单号
    private String orderId;
    //用户名
    private String username;
    //用户名
    private String userId;
    //优惠券编号
    private String couponUserCode;
    //优惠券编号
    private String couponCode;
    //优惠券类型描述
    private String couponTypeStr;
    //优惠券类型
    private String couponType;
    //优惠券额度
    private String couponQuota;
    //项目编号
    private String borrowNid;
    //状态
    private String receivedFlg;
    //投资金额
    private BigDecimal account;
    //预期收益
    private BigDecimal recoverAccountAll;
    //预期收益
    private BigDecimal recoverAccountInterestWait;


    //投资平台
    private String operatingDeck;
    //年化利率
    private String borrowApr;
    //借款期限
    private String borrowPeriod;
    //使用时间
    private String orderDate;

    //使用时间
    private String couponFrom;

    //使用时间
    private String couponContent;
    //
    private Integer couponGrantId;

    private String attribute;

    /**
     * 还款状态（分期标的所有分期都已还款才统计为已还款）
     */
    private String receivedFlgAll;

    /**
     * 检索条件 时间开始
     */
    private String timeStartSrch;

    /**
     * 检索条件 时间结束
     */
    private String timeEndSrch;

    /**
     * 页数
     */
    private Integer currPage = 1;
    /**
     * 大小
     */
    private Integer pageSize = 10;

    /**
     * 检索条件 limitStart
     */
    private int limitStart = -1;

    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;

    /**
     * 用户优惠券编号
     */
    private String couponUserId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCouponUserCode() {
        return couponUserCode;
    }

    public void setCouponUserCode(String couponUserCode) {
        this.couponUserCode = couponUserCode;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponTypeStr() {
        return couponTypeStr;
    }

    public void setCouponTypeStr(String couponTypeStr) {
        this.couponTypeStr = couponTypeStr;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponQuota() {
        return couponQuota;
    }

    public void setCouponQuota(String couponQuota) {
        this.couponQuota = couponQuota;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getReceivedFlg() {
        return receivedFlg;
    }

    public void setReceivedFlg(String receivedFlg) {
        this.receivedFlg = receivedFlg;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public BigDecimal getRecoverAccountAll() {
        return recoverAccountAll;
    }

    public void setRecoverAccountAll(BigDecimal recoverAccountAll) {
        this.recoverAccountAll = recoverAccountAll;
    }

    public BigDecimal getRecoverAccountInterestWait() {
        return recoverAccountInterestWait;
    }

    public void setRecoverAccountInterestWait(BigDecimal recoverAccountInterestWait) {
        this.recoverAccountInterestWait = recoverAccountInterestWait;
    }

    public String getOperatingDeck() {
        return operatingDeck;
    }

    public void setOperatingDeck(String operatingDeck) {
        this.operatingDeck = operatingDeck;
    }

    public String getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCouponFrom() {
        return couponFrom;
    }

    public void setCouponFrom(String couponFrom) {
        this.couponFrom = couponFrom;
    }

    public String getCouponContent() {
        return couponContent;
    }

    public void setCouponContent(String couponContent) {
        this.couponContent = couponContent;
    }

    public Integer getCouponGrantId() {
        return couponGrantId;
    }

    public void setCouponGrantId(Integer couponGrantId) {
        this.couponGrantId = couponGrantId;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getReceivedFlgAll() {
        return receivedFlgAll;
    }

    public void setReceivedFlgAll(String receivedFlgAll) {
        this.receivedFlgAll = receivedFlgAll;
    }

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

    public String getCouponUserId() {
        return couponUserId;
    }

    public void setCouponUserId(String couponUserId) {
        this.couponUserId = couponUserId;
    }
}
