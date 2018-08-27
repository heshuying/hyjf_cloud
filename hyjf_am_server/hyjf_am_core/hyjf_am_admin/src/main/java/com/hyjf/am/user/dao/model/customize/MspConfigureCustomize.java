package com.hyjf.am.user.dao.model.customize;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class MspConfigureCustomize implements Serializable {
    private Integer id;

    private String configureName;

    private String loanType;
    
    private String loanTypeName;

    private String serviceType;
    
    private String serviceTypeName;

    private String approvalResult;
    
    private String approvalResultName;

    private BigDecimal loanMoney;

    private Integer loanTimeLimit;

    private String creditAddress;
    
    private String creditAddresss;

    public String getCreditAddresss() {
		return creditAddresss;
	}

	public void setCreditAddresss(String creditAddresss) {
		this.creditAddresss = creditAddresss;
	}

	private String guaranteeType;
    
    private String guaranteeTypeName;

    private BigDecimal unredeemedMoney;

    private String repaymentStatus;
    
    private String repaymentStatusName;

    private BigDecimal overdueAmount;

    private String overdueDate;

    private Integer overdueLength;

    private String overdueReason;

    private Integer createUser;

    private Date createTime;

    private Integer updateUser;

    private Date updateTime;

    private Integer delFlg;
    

    public String getLoanTypeName() {
		return loanTypeName;
	}

	public void setLoanTypeName(String loanTypeName) {
		this.loanTypeName = loanTypeName;
	}

	public String getServiceTypeName() {
		return serviceTypeName;
	}

	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}

	public String getApprovalResultName() {
		return approvalResultName;
	}

	public void setApprovalResultName(String approvalResultName) {
		this.approvalResultName = approvalResultName;
	}

	public String getGuaranteeTypeName() {
		return guaranteeTypeName;
	}

	public void setGuaranteeTypeName(String guaranteeTypeName) {
		this.guaranteeTypeName = guaranteeTypeName;
	}

	public String getRepaymentStatusName() {
		return repaymentStatusName;
	}

	public void setRepaymentStatusName(String repaymentStatusName) {
		this.repaymentStatusName = repaymentStatusName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

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

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }
}