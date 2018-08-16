package com.hyjf.am.trade.dao.model.customize;

/**
 * @author pangchengchao
 * @version AdminBorrowRepaymentInfoListCustomize, v0.1 2018/7/10 11:19
 */
public class AdminBorrowRepaymentInfoListCustomize {
    // ========================参数=============================
    private String nid;// 投资nid,还款订单号
    private String borrowNid;// 借款编号
    private String userId;// 借款人ID
    private String borrowUserName;// 借款人用户名
    private String borrowName;// 借款标题
    private String projectType;// 项目类型id
    private String projectTypeName;// 项目类型名称
    private String borrowPeriod;// 借款期限
    private String borrowApr;// 年化收益
    private String borrowAccount;// 借款金额
    private String borrowAccountYes;// 借到金额
    private String repayType;// 还款方式
    private String recoverPeriod;// 还款期次
    private String recoverUserId;// 投资人ID
    private String recoverUserName;// 投资人用户名
    private String recoverTotal;// 投资金额
    private String recoverCapital;// 应还本金
    private String recoverInterest;// 应还利息
    private String recoverAccount;// 应还本息
    private String recoverFee;// 管理费
    private String chargeDays;// 提前天数
    private String chargeInterest;// 少还利息
    private String delayDays;// 延期天数
    private String delayInterest;// 延期利息
    private String lateDays;// 逾期天数
    private String lateInterest;// 逾期利息
    private String recoverAccountYes;// 实还总额
    private String status;// 还款状态
    private String recoverActionTime;// 实际还款日期
    private String recoverLastTime;// 应还日期
    private String accedeOrderId;//加入订单号
    private String isMonth;//是否分期
    private String instName; //机构名称
    private String repayBatchNo;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
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

    public String getRepayType() {
        return repayType;
    }

    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }

    public String getRecoverPeriod() {
        return recoverPeriod;
    }

    public void setRecoverPeriod(String recoverPeriod) {
        this.recoverPeriod = recoverPeriod;
    }

    public String getRecoverUserId() {
        return recoverUserId;
    }

    public void setRecoverUserId(String recoverUserId) {
        this.recoverUserId = recoverUserId;
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

    public String getRecoverAccount() {
        return recoverAccount;
    }

    public void setRecoverAccount(String recoverAccount) {
        this.recoverAccount = recoverAccount;
    }

    public String getRecoverFee() {
        return recoverFee;
    }

    public void setRecoverFee(String recoverFee) {
        this.recoverFee = recoverFee;
    }

    public String getChargeDays() {
        return chargeDays;
    }

    public void setChargeDays(String chargeDays) {
        this.chargeDays = chargeDays;
    }

    public String getChargeInterest() {
        return chargeInterest;
    }

    public void setChargeInterest(String chargeInterest) {
        this.chargeInterest = chargeInterest;
    }

    public String getDelayDays() {
        return delayDays;
    }

    public void setDelayDays(String delayDays) {
        this.delayDays = delayDays;
    }

    public String getDelayInterest() {
        return delayInterest;
    }

    public void setDelayInterest(String delayInterest) {
        this.delayInterest = delayInterest;
    }

    public String getLateDays() {
        return lateDays;
    }

    public void setLateDays(String lateDays) {
        this.lateDays = lateDays;
    }

    public String getLateInterest() {
        return lateInterest;
    }

    public void setLateInterest(String lateInterest) {
        this.lateInterest = lateInterest;
    }

    public String getRecoverAccountYes() {
        return recoverAccountYes;
    }

    public void setRecoverAccountYes(String recoverAccountYes) {
        this.recoverAccountYes = recoverAccountYes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRecoverActionTime() {
        return recoverActionTime;
    }

    public void setRecoverActionTime(String recoverActionTime) {
        this.recoverActionTime = recoverActionTime;
    }

    public String getRecoverLastTime() {
        return recoverLastTime;
    }

    public void setRecoverLastTime(String recoverLastTime) {
        this.recoverLastTime = recoverLastTime;
    }

    public String getAccedeOrderId() {
        return accedeOrderId;
    }

    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId;
    }

    public String getIsMonth() {
        return isMonth;
    }

    public void setIsMonth(String isMonth) {
        this.isMonth = isMonth;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getRepayBatchNo() {
        return repayBatchNo;
    }

    public void setRepayBatchNo(String repayBatchNo) {
        this.repayBatchNo = repayBatchNo;
    }
}
