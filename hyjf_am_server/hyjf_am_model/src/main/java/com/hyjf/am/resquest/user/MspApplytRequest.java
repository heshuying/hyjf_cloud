package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class MspApplytRequest extends BasePage {

	/**
	 * 前台时间接收
	 */
	@ApiModelProperty(value = "主键")
	private String ids;
	@ApiModelProperty(value = "创建时间开始")
	private String startCreate;
	@ApiModelProperty(value = "创建时间结束")
	private String endCreate;
	/**
	 * 翻页机能用的隐藏变量
	 */
	private int paginatorPage = 1;
	private String adminId;
	//默认为true ,获取全部数据，为false时，获取部分数据
	private boolean limitFlg = false;

	public boolean isLimitFlg() {
		return limitFlg;
	}

	public void setLimitFlg(boolean limitFlg) {
		this.limitFlg = limitFlg;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public int getPaginatorPage() {
		if (paginatorPage == 0) {
			paginatorPage = 1;
		}
		return paginatorPage;
	}

	public void setPaginatorPage(int paginatorPage) {
		this.paginatorPage = paginatorPage;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getStartCreate() {
		return startCreate;
	}

	public void setStartCreate(String startCreate) {
		this.startCreate = startCreate;
	}

	public String getEndCreate() {
		return endCreate;
	}

	public void setEndCreate(String endCreate) {
		this.endCreate = endCreate;
	}

	@ApiModelProperty(value = "主键")
	private Integer id;
	@ApiModelProperty(value = "applyId")
	private String applyId;
	@ApiModelProperty(value = "名字")
	private String name;
	@ApiModelProperty(value = "证件号")
	private String identityCard;
	@ApiModelProperty(value = "手机")
	private String mobileNo;
	@ApiModelProperty(value = "申请日期")
	private String applyDate;
	@ApiModelProperty(value = "‘01’代表经营，‘02’代表消费，‘99’代表其他")
	private String loanType;
	@ApiModelProperty(value = "借款金额")
	private BigDecimal loanMoney;
	@ApiModelProperty(value = "借款期数")
	private Integer loanTimeLimit;
	@ApiModelProperty(value = "借款城市(字典)")
	private String creditAddress;
	@ApiModelProperty(value = "共享标识0为未共享,1为已共享")
	private Integer shareIdentification;
	@ApiModelProperty(value = "业务类型")
	private String serviceType;
	@ApiModelProperty(value = "未偿还本金")
	private BigDecimal unredeemedMoney;
	@ApiModelProperty(value = "还款状态 01 正常（借款人已按时归还该月还款金额的全部）。提前归还该月应还款金额的全部（但尚未结清），也归入“01 正常”；02 逾期中；03 逾期核销；04 正常结清")
	private String repaymentStatus;
	@ApiModelProperty(value = "逾期总金额")
	private BigDecimal overdueAmount;
	@ApiModelProperty(value = "逾期开始日期")
	private String overdueDate;
	@ApiModelProperty(value = "逾期时长")
	private String overdueLength;
	@ApiModelProperty(value = "逾期原因 01 能力下降；02 恶意拖欠；03 身份欺诈；04 逃逸；05 犯罪入狱；06 疾病；07 死亡；99 其他")
	private String overdueReason;
	@ApiModelProperty(value = "审批结果 01 审批通过；02 审批拒绝；04 重新审批；05 客户取消")
	private String approvalResult;
	@ApiModelProperty(value = "审批日期")
	private String approvalDate;
	@ApiModelProperty(value = "合同开始日期")
	private String contractBegin;
	@ApiModelProperty(value = "合同结束日期")
	private String contractEnd;
	@ApiModelProperty(value = "担保类型A 抵押；B 质押；C 担保；D 信用；E 保证；Y 其他")
	private String guaranteeType;
	@ApiModelProperty(value = "创建人")
	private String createUser;
	@ApiModelProperty(value = "创建时间")
	private Integer createTime;
	@ApiModelProperty(value = "修改人")
	private String updateUser;
	@ApiModelProperty(value = "修改时间")
	private Integer updateTime;

	private Integer delFlg;
	@ApiModelProperty(value = "配置id")
	private Integer configureId;

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
