package com.hyjf.am.vo.user;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * 
 * @author jun
 *
 */
public class LoanSubjectCertificateAuthorityVO extends BaseVO implements Serializable{

	 	private Integer id;

	 	private Integer userId;

	    private String name;

	    private String mobile;

	    private Integer idType;

	    private String idNo;

	    private String email;

	    private String status;

	    private String code;

	    private String customerId;

	    private String remark;

	    private Integer createTime;

	    private Integer createUserId;

	    private String createUserName;

	    private Integer updateTime;

	    private Integer updateUserId;

	    private String updateUserName;

	    private static final long serialVersionUID = 1L;

	    public Integer getId() {
	        return id;
	    }

	    public void setId(Integer id) {
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name == null ? null : name.trim();
	    }

	    public String getMobile() {
	        return mobile;
	    }

	    public void setMobile(String mobile) {
	        this.mobile = mobile == null ? null : mobile.trim();
	    }

	    public Integer getIdType() {
	        return idType;
	    }

	    public void setIdType(Integer idType) {
	        this.idType = idType;
	    }

	    public String getIdNo() {
	        return idNo;
	    }

	    public void setIdNo(String idNo) {
	        this.idNo = idNo == null ? null : idNo.trim();
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email == null ? null : email.trim();
	    }

	    public String getStatus() {
	        return status;
	    }

	    public void setStatus(String status) {
	        this.status = status == null ? null : status.trim();
	    }

	    public String getCode() {
	        return code;
	    }

	    public void setCode(String code) {
	        this.code = code == null ? null : code.trim();
	    }

	    public String getCustomerId() {
	        return customerId;
	    }

	    public void setCustomerId(String customerId) {
	        this.customerId = customerId == null ? null : customerId.trim();
	    }

	    public String getRemark() {
	        return remark;
	    }

	    public void setRemark(String remark) {
	        this.remark = remark == null ? null : remark.trim();
	    }

	    public Integer getCreateTime() {
	        return createTime;
	    }

	    public void setCreateTime(Integer createTime) {
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

	    public Integer getUpdateTime() {
	        return updateTime;
	    }

	    public void setUpdateTime(Integer updateTime) {
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
