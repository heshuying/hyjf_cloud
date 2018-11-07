package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MspConfigure implements Serializable {
    /**
     * 主键 id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 标的名称
     *
     * @mbggenerated
     */
    private String configureName;

    /**
     * 借款用途 ‘01’代表经营，‘02’代表消费，‘99’代表其他
     *
     * @mbggenerated
     */
    private String loanType;

    /**
     * 业务类型 01 信用额度(不设置该选项)；02 一般借贷；03消费信贷；04 循环贷；05其他
     *
     * @mbggenerated
     */
    private String serviceType;

    /**
     * 审批结果 01 审批通过；02 审批拒绝；04 重新审批；05 客户取消
     *
     * @mbggenerated
     */
    private String approvalResult;

    /**
     * 借款金额
     *
     * @mbggenerated
     */
    private BigDecimal loanMoney;

    /**
     * 借款期数
     *
     * @mbggenerated
     */
    private Integer loanTimeLimit;

    /**
     * 借款城市（字典）
     *
     * @mbggenerated
     */
    private String creditAddress;

    /**
     * 担保类型 A 抵押；B 质押；C 担保；D 信用；E 保证；Y 其他
     *
     * @mbggenerated
     */
    private String guaranteeType;

    /**
     * 未偿还本金
     *
     * @mbggenerated
     */
    private BigDecimal unredeemedMoney;

    /**
     * 当前还款状态 01 正常（借款人已按时归还该月还款金额的全部）。提前归还该月应还款金额的全部（但尚未结清），也归入“01 正常”；02 逾期中；03 逾期核销；04 正常结清
     *
     * @mbggenerated
     */
    private String repaymentStatus;

    /**
     * 逾期总金额 本金、利息、滞纳金等总和
     *
     * @mbggenerated
     */
    private BigDecimal overdueAmount;

    /**
     * 逾期开始日期
     *
     * @mbggenerated
     */
    private String overdueDate;

    /**
     * 逾期时长
     *
     * @mbggenerated
     */
    private Integer overdueLength;

    /**
     * 逾期原因 01 能力下降；02 恶意拖欠；03 身份欺诈；04 逃逸；05 犯罪入狱；06 疾病；07 死亡；99 其他
     *
     * @mbggenerated
     */
    private String overdueReason;

    /**
     * 删除标识
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private Integer createUser;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private Integer updateUser;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConfigureName() {
        return configureName;
    }

    public void setConfigureName(String configureName) {
        this.configureName = configureName == null ? null : configureName.trim();
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType == null ? null : loanType.trim();
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType == null ? null : serviceType.trim();
    }

    public String getApprovalResult() {
        return approvalResult;
    }

    public void setApprovalResult(String approvalResult) {
        this.approvalResult = approvalResult == null ? null : approvalResult.trim();
    }

    public BigDecimal getLoanMoney() {
        return loanMoney;
    }

    public void setLoanMoney(BigDecimal loanMoney) {
        this.loanMoney = loanMoney;
    }

    public Integer getLoanTimeLimit() {
        return loanTimeLimit;
    }

    public void setLoanTimeLimit(Integer loanTimeLimit) {
        this.loanTimeLimit = loanTimeLimit;
    }

    public String getCreditAddress() {
        return creditAddress;
    }

    public void setCreditAddress(String creditAddress) {
        this.creditAddress = creditAddress == null ? null : creditAddress.trim();
    }

    public String getGuaranteeType() {
        return guaranteeType;
    }

    public void setGuaranteeType(String guaranteeType) {
        this.guaranteeType = guaranteeType == null ? null : guaranteeType.trim();
    }

    public BigDecimal getUnredeemedMoney() {
        return unredeemedMoney;
    }

    public void setUnredeemedMoney(BigDecimal unredeemedMoney) {
        this.unredeemedMoney = unredeemedMoney;
    }

    public String getRepaymentStatus() {
        return repaymentStatus;
    }

    public void setRepaymentStatus(String repaymentStatus) {
        this.repaymentStatus = repaymentStatus == null ? null : repaymentStatus.trim();
    }

    public BigDecimal getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(BigDecimal overdueAmount) {
        this.overdueAmount = overdueAmount;
    }

    public String getOverdueDate() {
        return overdueDate;
    }

    public void setOverdueDate(String overdueDate) {
        this.overdueDate = overdueDate == null ? null : overdueDate.trim();
    }

    public Integer getOverdueLength() {
        return overdueLength;
    }

    public void setOverdueLength(Integer overdueLength) {
        this.overdueLength = overdueLength;
    }

    public String getOverdueReason() {
        return overdueReason;
    }

    public void setOverdueReason(String overdueReason) {
        this.overdueReason = overdueReason == null ? null : overdueReason.trim();
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
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
}