package com.hyjf.am.resquest.user;

import java.util.Date;

import com.hyjf.am.vo.BaseVO;

/**
 * @author hesy
 */
public class UsersContractRequest extends BaseVO {
	private Integer userId;

	private Integer relation;

	private String rlName;

	private String rlPhone;

	private Date createTime;

	private Date updateTime;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRelation() {
		return relation;
	}

	public void setRelation(Integer relation) {
		this.relation = relation;
	}

	public String getRlName() {
		return rlName;
	}

	public void setRlName(String rlName) {
		this.rlName = rlName;
	}

	public String getRlPhone() {
		return rlPhone;
	}

	public void setRlPhone(String rlPhone) {
		this.rlPhone = rlPhone;
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
