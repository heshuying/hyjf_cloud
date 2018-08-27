package com.hyjf.am.resquest.user;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.hyjf.am.vo.BasePage;

import io.swagger.annotations.ApiModelProperty;

/**
 * @package com.hyjf.admin.maintenance.AlllBorrowCustomize;
 * @author dongzeshan
 * @date
 * @version V1.0  
 */
public class MspRequest extends BasePage implements Serializable {

	/**
	 * serialVersionUID
	 */

	/**
	 * 业务类型查询
	 */
	@ApiModelProperty(value = "业务类型查询")
	private String serviceTypeSrch;
	/**
	 * 借款类型查询
	 */
	@ApiModelProperty(value = "借款类型查询")
	private String loanTypeSrch;
	/**
	 * 担保类型查询
	 */
	@ApiModelProperty(value = "担保类型查询")
	private String sourceTypeSrch;

	private String ids;
	@ApiModelProperty(value = "sourceId")
	private String sourceId;
	@ApiModelProperty(value = "sourceName")
	private String sourceName;

	@ApiModelProperty(value = "备注")
	private String remark;
	@ApiModelProperty(value = "sourceType")
	private Integer sourceType;
	@ApiModelProperty(value = "借款金额")
	private String loanMoneys;

	/**
	 * sourceId
	 * 
	 * @return the sourceId
	 */

	public String getSourceId() {
		return sourceId;
	}

	/**
	 * @param sourceId
	 *            the sourceId to set
	 */

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	/**
	 * sourceName
	 * 
	 * @return the sourceName
	 */

	public String getSourceName() {
		return sourceName;
	}

	/**
	 * @param sourceName
	 *            the sourceName to set
	 */

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	/**
	 * remark
	 * 
	 * @return the remark
	 */

	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 检索条件 limitStart
	 */
	private int limitStart = -1;
	/**
	 * 检索条件 limitEnd
	 */
	private int limitEnd = -1;

	public String getServiceTypeSrch() {
		return serviceTypeSrch;
	}

	public void setServiceTypeSrch(String serviceTypeSrch) {
		this.serviceTypeSrch = serviceTypeSrch;
	}

	public String getLoanTypeSrch() {
		return loanTypeSrch;
	}

	public void setLoanTypeSrch(String loanTypeSrch) {
		this.loanTypeSrch = loanTypeSrch;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * limitStart
	 * 
	 * @return the limitStart
	 */

	public int getLimitStart() {
		return limitStart;
	}

	/**
	 * @param limitStart
	 *            the limitStart to set
	 */

	public void setLimitStart(int limitStart) {
		this.limitStart = limitStart;
	}

	/**
	 * limitEnd
	 * 
	 * @return the limitEnd
	 */

	public int getLimitEnd() {
		return limitEnd;
	}

	/**
	 * @param limitEnd
	 *            the limitEnd to set
	 */

	public void setLimitEnd(int limitEnd) {
		this.limitEnd = limitEnd;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public String getSourceTypeSrch() {
		return sourceTypeSrch;
	}

	public void setSourceTypeSrch(String sourceTypeSrch) {
		this.sourceTypeSrch = sourceTypeSrch;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getLoanMoneys() {
		return loanMoneys;
	}

	public void setLoanMoneys(String loanMoneys) {
		this.loanMoneys = loanMoneys;
	}

	@ApiModelProperty(value = "配置名字")
	private String configureName;

	@ApiModelProperty(value = "主键")
	private Integer id;

	@ApiModelProperty(value = "‘01’代表经营，‘02’代表消费，‘99’代表其他")
	private String loanType;
	@ApiModelProperty(value = "借款金额")
	private BigDecimal loanMoney;
	@ApiModelProperty(value = "借款期数")
	private Integer loanTimeLimit;
	@ApiModelProperty(value = "借款城市(字典)")
	private String creditAddress;
	@ApiModelProperty(value = "共享标识0为未共享,1为已共享")
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
	private Integer overdueLength;
	@ApiModelProperty(value = "逾期原因 01 能力下降；02 恶意拖欠；03 身份欺诈；04 逃逸；05 犯罪入狱；06 疾病；07 死亡；99 其他")
	private String overdueReason;
	@ApiModelProperty(value = "审批结果 01 审批通过；02 审批拒绝；04 重新审批；05 客户取消")
	private String approvalResult;
	@ApiModelProperty(value = "担保类型A 抵押；B 质押；C 担保；D 信用；E 保证；Y 其他")
	private String guaranteeType;
	@ApiModelProperty(value = "创建人")
	private Integer createUser;
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	@ApiModelProperty(value = "修改人")
	private Integer updateUser;
	@ApiModelProperty(value = "修改时间")
	private Date updateTime;
	private Integer delFlag;
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
