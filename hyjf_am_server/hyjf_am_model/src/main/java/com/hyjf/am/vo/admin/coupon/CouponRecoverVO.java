package com.hyjf.am.vo.admin.coupon;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CouponRecoverVO extends BaseVO implements Serializable {


    private Integer id;

    /**
     * 项目编号
     */
    private String borrowNid;

    private String tenderId;

    private String transferId;

    private Integer recoverStatus;

    private Integer receivedFlg;

    /**
     * 收益领取状态原始值
     */
    private String receivedFlgOrigin;

    private String recoverPeriod;

    private Integer transferTime;

    private Integer recoverTime;

    private Integer recoverYestime;

    private Integer mainRecoverYestime;

    private BigDecimal recoverInterest;

    private BigDecimal recoverInterestYes;

    private BigDecimal recoverAccount;

    private BigDecimal recoverAccountYes;

    private BigDecimal recoverCapital;

    private BigDecimal recoverCapitalYes;

    private Integer currentRecoverFlg;

    private Integer recoverType;

    private Integer noticeFlg;

    private Integer expTime;

    private Integer delFlag;

    private Integer createUserId;

    private Integer updateUserId;

    private Date createTime;

    private Date updateTime;
    /**
     * 优惠券投资编号
     */
    private String tenderNid;
    /**
     * 优惠券类别
     */
    private int couponType;
    /**
     * 操作ip
     */
    private String addip;
    /**
     * 用户优惠券编号
     */
    private String couponUserCode;
    /**
     * 检索条件 时间开始
     */
    private String timeStartSrch;

    /**
     * 检索条件 时间结束
     */
    private String timeEndSrch;


    /**
     * 检索条件 limitStart
     */
    private int limitStart = -1;

    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;

    /**
     * 检索条件 用户id
     */
    private String  userId;

    /**
     * 检索条件 项目编号
     */
    private String  bNid;
    /**
     * 检索条件 订单id
     */
    private String orderId;


    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenderId() {
        return tenderId;
    }

    public void setTenderId(String tenderId) {
        this.tenderId = tenderId == null ? null : tenderId.trim();
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId == null ? null : transferId.trim();
    }

    public Integer getRecoverStatus() {
        return recoverStatus;
    }

    public void setRecoverStatus(Integer recoverStatus) {
        this.recoverStatus = recoverStatus;
    }

    public Integer getReceivedFlg() {
        return receivedFlg;
    }

    public void setReceivedFlg(Integer receivedFlg) {
        this.receivedFlg = receivedFlg;
    }

    public String getRecoverPeriod() {
        return recoverPeriod;
    }

    public void setRecoverPeriod(String recoverPeriod) {
        this.recoverPeriod = recoverPeriod;
    }

    public Integer getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Integer transferTime) {
        this.transferTime = transferTime;
    }

    public Integer getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(Integer recoverTime) {
        this.recoverTime = recoverTime;
    }

    public Integer getRecoverYestime() {
        return recoverYestime;
    }

    public void setRecoverYestime(Integer recoverYestime) {
        this.recoverYestime = recoverYestime;
    }

    public Integer getMainRecoverYestime() {
        return mainRecoverYestime;
    }

    public void setMainRecoverYestime(Integer mainRecoverYestime) {
        this.mainRecoverYestime = mainRecoverYestime;
    }

    public BigDecimal getRecoverInterest() {
        return recoverInterest;
    }

    public void setRecoverInterest(BigDecimal recoverInterest) {
        this.recoverInterest = recoverInterest;
    }

    public BigDecimal getRecoverInterestYes() {
        return recoverInterestYes;
    }

    public void setRecoverInterestYes(BigDecimal recoverInterestYes) {
        this.recoverInterestYes = recoverInterestYes;
    }

    public BigDecimal getRecoverAccount() {
        return recoverAccount;
    }

    public void setRecoverAccount(BigDecimal recoverAccount) {
        this.recoverAccount = recoverAccount;
    }

    public BigDecimal getRecoverAccountYes() {
        return recoverAccountYes;
    }

    public void setRecoverAccountYes(BigDecimal recoverAccountYes) {
        this.recoverAccountYes = recoverAccountYes;
    }

    public BigDecimal getRecoverCapital() {
        return recoverCapital;
    }

    public void setRecoverCapital(BigDecimal recoverCapital) {
        this.recoverCapital = recoverCapital;
    }

    public BigDecimal getRecoverCapitalYes() {
        return recoverCapitalYes;
    }

    public void setRecoverCapitalYes(BigDecimal recoverCapitalYes) {
        this.recoverCapitalYes = recoverCapitalYes;
    }

    public Integer getCurrentRecoverFlg() {
        return currentRecoverFlg;
    }

    public void setCurrentRecoverFlg(Integer currentRecoverFlg) {
        this.currentRecoverFlg = currentRecoverFlg;
    }

    public Integer getRecoverType() {
        return recoverType;
    }

    public void setRecoverType(Integer recoverType) {
        this.recoverType = recoverType;
    }

    public Integer getNoticeFlg() {
        return noticeFlg;
    }

    public void setNoticeFlg(Integer noticeFlg) {
        this.noticeFlg = noticeFlg;
    }

    public Integer getExpTime() {
        return expTime;
    }

    public void setExpTime(Integer expTime) {
        this.expTime = expTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getReceivedFlgOrigin() {
        return receivedFlgOrigin;
    }

    public void setReceivedFlgOrigin(String receivedFlgOrigin) {
        this.receivedFlgOrigin = receivedFlgOrigin;
    }

    public String getTenderNid() {
        return tenderNid;
    }

    public void setTenderNid(String tenderNid) {
        this.tenderNid = tenderNid;
    }

    public int getCouponType() {
        return couponType;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }

    public String getAddip() {
        return addip;
    }

    public void setAddip(String addip) {
        this.addip = addip;
    }

    public String getCouponUserCode() {
        return couponUserCode;
    }

    public void setCouponUserCode(String couponUserCode) {
        this.couponUserCode = couponUserCode;
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
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getbNid() {
        return bNid;
    }

    public void setbNid(String bNid) {
        this.bNid = bNid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
