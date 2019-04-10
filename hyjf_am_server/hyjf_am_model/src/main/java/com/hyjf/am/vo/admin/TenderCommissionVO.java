package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author pangchengchao
 * @version TenderAgreementVO, v0.1 2018/6/24 12:10
 */
public class TenderCommissionVO extends BaseVO implements Serializable {
    private Integer id;

    private String borrowNid;

    private Integer tenderType;

    private String ordid;

    private Integer tenderId;

    private Integer userId;

    private Integer departmentId;

    private Integer tenderUserId;

    private Integer tenderDepartmentId;

    private BigDecimal accountTender;

    private Integer tenderTime;

    private Integer sendTime;

    private BigDecimal commission;

    private Integer status;

    private String remark;

    private Integer computeTime;

    private Integer regionId;

    private Integer branchId;
    
    private String loginUserName;
    
    // 此account取自 BankOpenAccount 的 account,使用的是 Commission表的 userid取值
    private String account;
    
    // 此userName 取自 userinfo 的 username ,使用的是  Commission表的 userid取值
    private String userName;
    // 此trueName 取自 userinfo 的 username ,使用的是  Commission表的 userid取值
    private String trueName;
    // 此sex 取自 userinfo 的 username ,使用的是  Commission表的 userid取值
    private String sex;
    // 此mobile 取自 userinfo 的 username ,使用的是  Commission表的 userid取值
    private String mobile;
    // 此attribute 取自 userinfo 的 username ,使用的是  Commission表的 userid取值
    private Integer attribute;
    // 此attribute 取自 userinfo 的 username ,使用的是  Commission表的 userid取值
    private Integer is51;

    private String logOrderId;
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

    public Integer getTenderType() {
        return tenderType;
    }

    public void setTenderType(Integer tenderType) {
        this.tenderType = tenderType;
    }

    public String getOrdid() {
        return ordid;
    }

    public void setOrdid(String ordid) {
        this.ordid = ordid;
    }

    public Integer getTenderId() {
        return tenderId;
    }

    public void setTenderId(Integer tenderId) {
        this.tenderId = tenderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getTenderUserId() {
        return tenderUserId;
    }

    public void setTenderUserId(Integer tenderUserId) {
        this.tenderUserId = tenderUserId;
    }

    public Integer getTenderDepartmentId() {
        return tenderDepartmentId;
    }

    public void setTenderDepartmentId(Integer tenderDepartmentId) {
        this.tenderDepartmentId = tenderDepartmentId;
    }

    public BigDecimal getAccountTender() {
        return accountTender;
    }

    public void setAccountTender(BigDecimal accountTender) {
        this.accountTender = accountTender;
    }

    public Integer getTenderTime() {
        return tenderTime;
    }

    public void setTenderTime(Integer tenderTime) {
        this.tenderTime = tenderTime;
    }

    public Integer getSendTime() {
        return sendTime;
    }

    public void setSendTime(Integer sendTime) {
        this.sendTime = sendTime;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getComputeTime() {
        return computeTime;
    }

    public void setComputeTime(Integer computeTime) {
        this.computeTime = computeTime;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    
	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getAttribute() {
		return attribute;
	}

	public void setAttribute(Integer attribute) {
		this.attribute = attribute;
	}

	public Integer getIs51() {
		return is51;
	}

	public void setIs51(Integer is51) {
		this.is51 = is51;
	}

	private String regionName;

    private String branchName;

    private String departmentName;

    private String accountId;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public String getLogOrderId() {
        return logOrderId;
    }

    public void setLogOrderId(String logOrderId) {
        this.logOrderId = logOrderId;
    }
}
