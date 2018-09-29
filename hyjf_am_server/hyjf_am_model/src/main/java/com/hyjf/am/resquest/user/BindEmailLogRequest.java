package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.BaseVO;

import java.util.Date;

public class BindEmailLogRequest extends BaseVO{
	private Integer userId;

    private String userEmail;

    private Integer userEmailStatus;

    private Date createtime;

    private String emailActiveCode;

    private Date emailActiveUrlDeadtime;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Integer getUserEmailStatus() {
		return userEmailStatus;
	}

	public void setUserEmailStatus(Integer userEmailStatus) {
		this.userEmailStatus = userEmailStatus;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getEmailActiveCode() {
		return emailActiveCode;
	}

	public void setEmailActiveCode(String emailActiveCode) {
		this.emailActiveCode = emailActiveCode;
	}

	public Date getEmailActiveUrlDeadtime() {
		return emailActiveUrlDeadtime;
	}

	public void setEmailActiveUrlDeadtime(Date emailActiveUrlDeadtime) {
		this.emailActiveUrlDeadtime = emailActiveUrlDeadtime;
	}
    
    
}
