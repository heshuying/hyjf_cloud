package com.hyjf.am.vo.trade.repay;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wgx
 * @date 2018/10/13
 */
public class BankRepayOrgFreezeLogVO extends BaseVO {
    private Integer id;
    @ApiModelProperty(value = "还款人用户userId")
    private Integer repayUserId;
    @ApiModelProperty(value = "还款人用户名")
    private String repayUserName;
    @ApiModelProperty(value = "借款人userId")
    private Integer borrowUserId;
    @ApiModelProperty(value = "借款人用户名")
    private String borrowUserName;
    @ApiModelProperty(value = "电子账号")
    private String account;
    @ApiModelProperty(value = "订单号:txDate+txTime+seqNo")
    private String orderId;
    @ApiModelProperty(value = "借款编号")
    private String borrowNid;
    @ApiModelProperty(value = "计划编号")
    private String planNid;
    @ApiModelProperty(value = "资产来源")
    private String instCode;
    @ApiModelProperty(value = "借款金额")
    private BigDecimal amount;
    @ApiModelProperty(value = "冻结金额")
    private BigDecimal amountFreeze;
    @ApiModelProperty(value = "应还本息")
    private BigDecimal repayAccount;
    @ApiModelProperty(value = "还款服务费")
    private BigDecimal repayFee;
    @ApiModelProperty(value = "减息金额")
    private BigDecimal lowerInterest;
    @ApiModelProperty(value = "违约金")
    private BigDecimal penaltyAmount;
    @ApiModelProperty(value = "逾期罚息")
    private BigDecimal defaultInterest;
    @ApiModelProperty(value = "借款期限")
    private String borrowPeriod;
    @ApiModelProperty(value = "总期数")
    private Integer totalPeriod;
    @ApiModelProperty(value = "当前期数")
    private Integer currentPeriod;
    @ApiModelProperty(value = "是否全部还款 0否 1是")
    private Integer allRepayFlag;
    @ApiModelProperty(value = "是否有效 0有效 1无效记录")
    private Integer delFlag;
    @ApiModelProperty(value = "提交的逾期期数(一期或多期的最后一期)")
    private Integer latePeriod;

    private Date createTime;

    private Integer createUserId;

    private String createUserName;

    private Date updateTime;

    private Integer updateUserId;

    private String updateUserName;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRepayUserId() {
        return repayUserId;
    }

    public void setRepayUserId(Integer repayUserId) {
        this.repayUserId = repayUserId;
    }

    public String getRepayUserName() {
        return repayUserName;
    }

    public void setRepayUserName(String repayUserName) {
        this.repayUserName = repayUserName == null ? null : repayUserName.trim();
    }

    public Integer getBorrowUserId() {
        return borrowUserId;
    }

    public void setBorrowUserId(Integer borrowUserId) {
        this.borrowUserId = borrowUserId;
    }

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName == null ? null : borrowUserName.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid == null ? null : planNid.trim();
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode == null ? null : instCode.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmountFreeze() {
        return amountFreeze;
    }

    public void setAmountFreeze(BigDecimal amountFreeze) {
        this.amountFreeze = amountFreeze;
    }

    public BigDecimal getRepayAccount() {
        return repayAccount;
    }

    public void setRepayAccount(BigDecimal repayAccount) {
        this.repayAccount = repayAccount;
    }

    public BigDecimal getRepayFee() {
        return repayFee;
    }

    public void setRepayFee(BigDecimal repayFee) {
        this.repayFee = repayFee;
    }

    public BigDecimal getLowerInterest() {
        return lowerInterest;
    }

    public void setLowerInterest(BigDecimal lowerInterest) {
        this.lowerInterest = lowerInterest;
    }

    public BigDecimal getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(BigDecimal penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    public BigDecimal getDefaultInterest() {
        return defaultInterest;
    }

    public void setDefaultInterest(BigDecimal defaultInterest) {
        this.defaultInterest = defaultInterest;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod == null ? null : borrowPeriod.trim();
    }

    public Integer getTotalPeriod() {
        return totalPeriod;
    }

    public void setTotalPeriod(Integer totalPeriod) {
        this.totalPeriod = totalPeriod;
    }

    public Integer getCurrentPeriod() {
        return currentPeriod;
    }

    public void setCurrentPeriod(Integer currentPeriod) {
        this.currentPeriod = currentPeriod;
    }

    public Integer getAllRepayFlag() {
        return allRepayFlag;
    }

    public void setAllRepayFlag(Integer allRepayFlag) {
        this.allRepayFlag = allRepayFlag;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName == null ? null : updateUserName.trim();
    }

    public Integer getLatePeriod() {
        return latePeriod;
    }

    public void setLatePeriod(Integer latePeriod) {
        this.latePeriod = latePeriod;
    }
}
