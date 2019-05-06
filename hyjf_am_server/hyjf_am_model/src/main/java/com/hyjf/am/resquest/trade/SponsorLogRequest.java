package com.hyjf.am.resquest.trade;

import java.util.Date;

import com.hyjf.am.vo.BasePage;

import io.swagger.annotations.ApiModelProperty;

public class SponsorLogRequest extends BasePage {
	private Integer id;

	/**
	 * 标号
	 *
	 * @mbggenerated
	 */
	private String borrowNid;

	/**
	 * 原始担保人id
	 *
	 * @mbggenerated
	 */
	private String oldSponsorId;

	/**
	 * 原始担保人username
	 *
	 * @mbggenerated
	 */
	private String oldSponsor;

	/**
	 * 新担保人id
	 *
	 * @mbggenerated
	 */
	private String newSponsorId;

	/**
	 * 新担保人username
	 *
	 * @mbggenerated
	 */
	private String newSponsor;

    /**
     * 0初始1修改成功2修改失败
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 0初始 1关闭 2删除
     *
     * @mbggenerated
     */
    private Integer delFlag;

	/**
	 * 创建用户名
	 *
	 * @mbggenerated
	 */
	private String createUserName;

	/**
	 * 更新用户名
	 *
	 * @mbggenerated
	 */
	private String updateUserName;

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

	@ApiModelProperty(value = "操作开始时间")
	private String timeStart;

	@ApiModelProperty(value = "操作结束时间")
	private String timeEnd;
	
	private String adminUserName;
	private int adminUserId;
	
	public int getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(int adminUserId) {
		this.adminUserId = adminUserId;
	}

	public String getAdminUserName() {
		return adminUserName;
	}

	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}

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

	public String getOldSponsorId() {
		return oldSponsorId;
	}

	public void setOldSponsorId(String oldSponsorId) {
		this.oldSponsorId = oldSponsorId;
	}

	public String getOldSponsor() {
		return oldSponsor;
	}

	public void setOldSponsor(String oldSponsor) {
		this.oldSponsor = oldSponsor;
	}

	public String getNewSponsorId() {
		return newSponsorId;
	}

	public void setNewSponsorId(String newSponsorId) {
		this.newSponsorId = newSponsorId;
	}

	public String getNewSponsor() {
		return newSponsor;
	}

	public void setNewSponsor(String newSponsor) {
		this.newSponsor = newSponsor;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
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

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

}
