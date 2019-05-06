package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 批次还款记录VO
 * @Auther:hesy
 */
public class BatchBorrowRecoverLogVo extends BaseVO implements Serializable {

    private Integer id;
    /**
     * 借款编号
     */
    private String borrowNid;

    /**
     * 批次编号
     */
    private String batchNo;

    /**
     * 用户编号
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 当前期数
     */
    private String periodNow;

    /**
     * 总期数
     */
    private String borrowPeriod;

    /**
     * 借款金额
     */
    private BigDecimal borrowAccount;

    /**
     * 应还/应放款金额
     */
    private BigDecimal batchAmount;

    /**
     * 总笔数
     */
    private String batchCounts;

    /**
     * 应收服务费
     */
    private BigDecimal batchServiceFee;

    /**
     * 成功金额
     */
    private BigDecimal sucAmount;

    /**
     * 成功笔数
     */
    private String sucCounts;

    /**
     * 失败金额
     */
    private BigDecimal failAmount;

    /**
     * 失败笔数
     */
    private String failCounts;

    /**
     * 状态
     */
    private String status;

    /**
     * 前台状态
     */
    private String statusStr;

    /**
     * 提交时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 是否是担保机构还款(0:否,1:是)
     */
    private String isRepayOrgFlag;

    /**
     * 银行回执说明
     */
    private String data;

    /**
     * 批次类型
     */
    private Integer apiType;

    /**
     * 批次提交时间开始  检索条件
     */
    private String submitTimeStartSrch;
    /**
     * 批次提交时间结束  检索条件
     */
    private String submitTimeEndSrch;

    /***
     *
     */
    private String nameClass;

    /*---add by LSY START-----*/
    /***
     * 资产来源 检索条件
     */
    private String instCodeSrch;

    /***
     * 资产来源
     */
    private String instName;
    /**
     * 借款金额合计值
     */
    private String sumBorrowAccount;
    /**
     * 应放款合计值
     */
    private String sumBatchAmount;
    /**
     * 应收服务费合计值
     */
    private String sumBatchServiceFee;
    /**
     * 总笔数合计值
     */
    private String sumBatchCounts;
    /**
     * 成功金额合计值
     */
    private String sumSucAmount;
    /**
     * 成功笔数合计值
     */
    private String sumSucCounts;
    /**
     * 失败金额合计值
     */
    private String sumFailAmount;
    /**
     * 失败笔数合计值
     */
    private String sumFailCounts;

    //add by cwyang 20180809 任务增加加息状态标识
    /**
     * 是否加息
     */
    private String increaseInterestFlag;
    /**
     * 加息放款状态
     */
    private String extraYieldStatus;
    /**
     * 加息还款状态
     */
    private String extraYieldRepayStatus;

    /**
     * 交易流水号
     */
    private String seqNo;

    /**
     * 银行交易流水号
     */
    private String bankSeqNo;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPeriodNow() {
        return periodNow;
    }

    public void setPeriodNow(String periodNow) {
        this.periodNow = periodNow;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public BigDecimal getBorrowAccount() {
        return borrowAccount;
    }

    public void setBorrowAccount(BigDecimal borrowAccount) {
        this.borrowAccount = borrowAccount;
    }

    public BigDecimal getBatchAmount() {
        return batchAmount;
    }

    public void setBatchAmount(BigDecimal batchAmount) {
        this.batchAmount = batchAmount;
    }

    public String getBatchCounts() {
        return batchCounts;
    }

    public void setBatchCounts(String batchCounts) {
        this.batchCounts = batchCounts;
    }

    public BigDecimal getBatchServiceFee() {
        return batchServiceFee;
    }

    public void setBatchServiceFee(BigDecimal batchServiceFee) {
        this.batchServiceFee = batchServiceFee;
    }

    public BigDecimal getSucAmount() {
        return sucAmount;
    }

    public BigDecimal getRepaid(){
        return sucAmount;
    }

    public void setSucAmount(BigDecimal sucAmount) {
        this.sucAmount = sucAmount;
    }

    public String getSucCounts() {
        return sucCounts;
    }

    public void setSucCounts(String sucCounts) {
        this.sucCounts = sucCounts;
    }

    public BigDecimal getFailAmount() {
        return failAmount;
    }

    public void setFailAmount(BigDecimal failAmount) {
        this.failAmount = failAmount;
    }

    public String getFailCounts() {
        return failCounts;
    }

    public void setFailCounts(String failCounts) {
        this.failCounts = failCounts;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getIsRepayOrgFlag() {
        return isRepayOrgFlag;
    }

    public void setIsRepayOrgFlag(String isRepayOrgFlag) {
        this.isRepayOrgFlag = isRepayOrgFlag;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getApiType() {
        return apiType;
    }

    public void setApiType(Integer apiType) {
        this.apiType = apiType;
    }

    public String getSubmitTimeStartSrch() {
        return submitTimeStartSrch;
    }

    public void setSubmitTimeStartSrch(String submitTimeStartSrch) {
        this.submitTimeStartSrch = submitTimeStartSrch;
    }

    public String getSubmitTimeEndSrch() {
        return submitTimeEndSrch;
    }

    public void setSubmitTimeEndSrch(String submitTimeEndSrch) {
        this.submitTimeEndSrch = submitTimeEndSrch;
    }

    public String getNameClass() {
        return nameClass;
    }

    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }

    public String getInstCodeSrch() {
        return instCodeSrch;
    }

    public void setInstCodeSrch(String instCodeSrch) {
        this.instCodeSrch = instCodeSrch;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getSumBorrowAccount() {
        return sumBorrowAccount;
    }

    public void setSumBorrowAccount(String sumBorrowAccount) {
        this.sumBorrowAccount = sumBorrowAccount;
    }

    public String getSumBatchAmount() {
        return sumBatchAmount;
    }

    public void setSumBatchAmount(String sumBatchAmount) {
        this.sumBatchAmount = sumBatchAmount;
    }

    public String getSumBatchServiceFee() {
        return sumBatchServiceFee;
    }

    public void setSumBatchServiceFee(String sumBatchServiceFee) {
        this.sumBatchServiceFee = sumBatchServiceFee;
    }

    public String getSumBatchCounts() {
        return sumBatchCounts;
    }

    public void setSumBatchCounts(String sumBatchCounts) {
        this.sumBatchCounts = sumBatchCounts;
    }

    public String getSumSucAmount() {
        return sumSucAmount;
    }

    public void setSumSucAmount(String sumSucAmount) {
        this.sumSucAmount = sumSucAmount;
    }

    public String getSumSucCounts() {
        return sumSucCounts;
    }

    public void setSumSucCounts(String sumSucCounts) {
        this.sumSucCounts = sumSucCounts;
    }

    public String getSumFailAmount() {
        return sumFailAmount;
    }

    public void setSumFailAmount(String sumFailAmount) {
        this.sumFailAmount = sumFailAmount;
    }

    public String getSumFailCounts() {
        return sumFailCounts;
    }

    public void setSumFailCounts(String sumFailCounts) {
        this.sumFailCounts = sumFailCounts;
    }

    public String getIncreaseInterestFlag() {
        return increaseInterestFlag;
    }

    public void setIncreaseInterestFlag(String increaseInterestFlag) {
        this.increaseInterestFlag = increaseInterestFlag;
    }

    public String getExtraYieldStatus() {
        return extraYieldStatus;
    }

    public void setExtraYieldStatus(String extraYieldStatus) {
        this.extraYieldStatus = extraYieldStatus;
    }

    public String getExtraYieldRepayStatus() {
        return extraYieldRepayStatus;
    }

    public void setExtraYieldRepayStatus(String extraYieldRepayStatus) {
        this.extraYieldRepayStatus = extraYieldRepayStatus;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getBankSeqNo() {
        return bankSeqNo;
    }

    public void setBankSeqNo(String bankSeqNo) {
        this.bankSeqNo = bankSeqNo;
    }
}
