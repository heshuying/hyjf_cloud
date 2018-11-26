package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;

/**
 * @version ApiBorrowRepaymentInfoCustomize, v0.1 2018/8/28 15:37
 * @Author: Zha Daojian
 */
public class ApiBorrowRepaymentInfoCustomize  implements Serializable {
    /**
     * serialVersionUID:
     */
    private static final long serialVersionUID = 1L;

    /**
     * 第三方接口机构编号
     */
    private String instCode;
    // ========================参数=============================
    private String assetId;//资产编号
    private String borrowNid;// 借款编号
    private String projectTypeName;// 项目类型名称
    private String borrowPeriod;// 借款期限
    private String borrowApr;// 年化收益
    private String recoverFee;// 管理费
    private String accedeOrderId;//投资订单号
    private String borrowAccount;// 借款金额
    private String borrowAccountYes;// 借到金额
    private String manageFeeRate; //管理费费率
    private String repayType;// 还款方式
    private String recoverTrueName;// 投资人姓名
    private String recoverUserName;// 投资人用户名
    private String recoverTotal;// 投资金额
    private String recoverCapital;// 应还本金
    private String recoverInterest;// 应还利息
    private String serviceRate; //服务费率
    private String serviceFee; //服务费
    private String recoverSumTotal; //应还总额

    /**
     * 还款冻结订单号
     */
    private String freezeOrderId;

   /* private String planNid;//计划编号
    private String userId;// 借款人ID
    private String borrowUserName;// 借款人用户名
    private String borrowStyle;// 类型
    private String borrowName;// 借款标题
    private String projectType;// 项目类型id






    private String recoverUserAttribute;// 投资人用户属性（当前）
    private String recoverRegionName;// 投资人所属一级分部（当前）
    private String recoverBranchName;// 投资人所属二级分部（当前）
    private String recoverDepartmentName;// 投资人所属团队（当前）
    private String referrerName;// 推荐人（当前）
    private String referrerUserId;// 推荐人ID（当前）
    private String referrerTrueName;// 推荐人姓名（当前）
    private String referrerRegionName;// 推荐人所属一级分部（当前）
    private String referrerBranchName;//推荐人所属二级分部（当前）
    private String referrerDepartmentName; //推荐人所属团队（当前）
    private String recoverPeriod;// 投资期限

    private String recoverAccount;// 应还本息
    private String recoverFee;// 管理费
    private String recoverCapitalYes;// 已还本金
    private String recoverInterestYes;// 已还利息
    private String recoverAccountYes;// 已换本息
    private String recoverCapitalWait;// 未还本金
    private String recoverInterestWait;// 未还利息
    private String recoverAccountWait;// 未还本息
    private String status;// 还款状态
    private String recoverLastTime;// 最后还款日
    private String repayActionTime; //实际还款时间
    private String repayOrdid; //还款订单号
    private String repayBatchNo; //还款批次号
    private String instName; //机构名称*/


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getProjectTypeName() {
        return projectTypeName;
    }

    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName;
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

    public String getRecoverFee() {
        return recoverFee;
    }

    public void setRecoverFee(String recoverFee) {
        this.recoverFee = recoverFee;
    }

    public String getAccedeOrderId() {
        return accedeOrderId;
    }

    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId;
    }

    public String getBorrowAccount() {
        return borrowAccount;
    }

    public void setBorrowAccount(String borrowAccount) {
        this.borrowAccount = borrowAccount;
    }

    public String getBorrowAccountYes() {
        return borrowAccountYes;
    }

    public void setBorrowAccountYes(String borrowAccountYes) {
        this.borrowAccountYes = borrowAccountYes;
    }

    public String getManageFeeRate() {
        return manageFeeRate;
    }

    public void setManageFeeRate(String manageFeeRate) {
        this.manageFeeRate = manageFeeRate;
    }

    public String getRepayType() {
        return repayType;
    }

    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }

    public String getRecoverTrueName() {
        return recoverTrueName;
    }

    public void setRecoverTrueName(String recoverTrueName) {
        this.recoverTrueName = recoverTrueName;
    }

    public String getRecoverUserName() {
        return recoverUserName;
    }

    public void setRecoverUserName(String recoverUserName) {
        this.recoverUserName = recoverUserName;
    }

    public String getRecoverTotal() {
        return recoverTotal;
    }

    public void setRecoverTotal(String recoverTotal) {
        this.recoverTotal = recoverTotal;
    }

    public String getRecoverCapital() {
        return recoverCapital;
    }

    public void setRecoverCapital(String recoverCapital) {
        this.recoverCapital = recoverCapital;
    }

    public String getRecoverInterest() {
        return recoverInterest;
    }

    public void setRecoverInterest(String recoverInterest) {
        this.recoverInterest = recoverInterest;
    }

    public String getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(String serviceRate) {
        this.serviceRate = serviceRate;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getRecoverSumTotal() {
        return recoverSumTotal;
    }

    public void setRecoverSumTotal(String recoverSumTotal) {
        this.recoverSumTotal = recoverSumTotal;
    }

    public String getFreezeOrderId() {
        return freezeOrderId;
    }

    public void setFreezeOrderId(String freezeOrderId) {
        this.freezeOrderId = freezeOrderId;
    }
}
