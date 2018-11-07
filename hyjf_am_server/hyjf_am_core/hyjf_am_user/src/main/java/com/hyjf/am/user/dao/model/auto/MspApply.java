package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;

public class MspApply implements Serializable {
    /**
     * 主键 id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 申请编号
     *
     * @mbggenerated
     */
    private String applyId;

    /**
     * 借款人姓名
     *
     * @mbggenerated
     */
    private String name;

    /**
     * 身份证
     *
     * @mbggenerated
     */
    private String identityCard;

    /**
     * 手机号
     *
     * @mbggenerated
     */
    private String mobileNo;

    /**
     * 申请日期
     *
     * @mbggenerated
     */
    private String applyDate;

    /**
     * ‘01’代表经营，‘02’代表消费，‘99’代表其他
     *
     * @mbggenerated
     */
    private String loanType;

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
     * 共享标识0为未共享,1为已共享
     *
     * @mbggenerated
     */
    private Integer shareIdentification;

    /**
     * 业务类型
     *
     * @mbggenerated
     */
    private String serviceType;

    /**
     * 未偿还本金
     *
     * @mbggenerated
     */
    private BigDecimal unredeemedMoney;

    /**
     * 还款状态 01 正常（借款人已按时归还该月还款金额的全部）。提前归还该月应还款金额的全部（但尚未结清），也归入“01 正常”；02 逾期中；03 逾期核销；04 正常结清
     *
     * @mbggenerated
     */
    private String repaymentStatus;

    /**
     * 逾期总金额
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
    private String overdueLength;

    /**
     * 逾期原因 01 能力下降；02 恶意拖欠；03 身份欺诈；04 逃逸；05 犯罪入狱；06 疾病；07 死亡；99 其他
     *
     * @mbggenerated
     */
    private String overdueReason;

    /**
     * 审批结果 01 审批通过；02 审批拒绝；04 重新审批；05 客户取消
     *
     * @mbggenerated
     */
    private String approvalResult;

    /**
     * 审批日期
     *
     * @mbggenerated
     */
    private String approvalDate;

    /**
     * 合同开始日期
     *
     * @mbggenerated
     */
    private String contractBegin;

    /**
     * 合同结束日期
     *
     * @mbggenerated
     */
    private String contractEnd;

    /**
     * 担保类型A 抵押；B 质押；C 担保；D 信用；E 保证；Y 其他
     *
     * @mbggenerated
     */
    private String guaranteeType;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private String createUser;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Integer createTime;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private String updateUser;

    /**
     * 修改时间
     *
     * @mbggenerated
     */
    private Integer updateTime;

    /**
     * 是否删除
     *
     * @mbggenerated
     */
    private Integer delFlg;

    /**
     * 配置id
     *
     * @mbggenerated
     */
    private Integer configureId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard == null ? null : identityCard.trim();
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo == null ? null : mobileNo.trim();
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate == null ? null : applyDate.trim();
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType == null ? null : loanType.trim();
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

    public Integer getShareIdentification() {
        return shareIdentification;
    }

    public void setShareIdentification(Integer shareIdentification) {
        this.shareIdentification = shareIdentification;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType == null ? null : serviceType.trim();
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

    public String getOverdueLength() {
        return overdueLength;
    }

    public void setOverdueLength(String overdueLength) {
        this.overdueLength = overdueLength == null ? null : overdueLength.trim();
    }

    public String getOverdueReason() {
        return overdueReason;
    }

    public void setOverdueReason(String overdueReason) {
        this.overdueReason = overdueReason == null ? null : overdueReason.trim();
    }

    public String getApprovalResult() {
        return approvalResult;
    }

    public void setApprovalResult(String approvalResult) {
        this.approvalResult = approvalResult == null ? null : approvalResult.trim();
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate == null ? null : approvalDate.trim();
    }

    public String getContractBegin() {
        return contractBegin;
    }

    public void setContractBegin(String contractBegin) {
        this.contractBegin = contractBegin == null ? null : contractBegin.trim();
    }

    public String getContractEnd() {
        return contractEnd;
    }

    public void setContractEnd(String contractEnd) {
        this.contractEnd = contractEnd == null ? null : contractEnd.trim();
    }

    public String getGuaranteeType() {
        return guaranteeType;
    }

    public void setGuaranteeType(String guaranteeType) {
        this.guaranteeType = guaranteeType == null ? null : guaranteeType.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }

    public Integer getConfigureId() {
        return configureId;
    }

    public void setConfigureId(Integer configureId) {
        this.configureId = configureId;
    }
}