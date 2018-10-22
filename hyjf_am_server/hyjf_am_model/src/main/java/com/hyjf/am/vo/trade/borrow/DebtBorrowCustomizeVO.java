/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: Administrator
 * @version: 1.0
 * Created at: 2015年11月20日 下午5:24:10
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.vo.trade.borrow;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author Administrator
 */

public class DebtBorrowCustomizeVO extends BaseVO implements Serializable {

	/**
	 * serialVersionUID:
	 */

	private static final long serialVersionUID = 1L;

	/**
	 * 借款编码
	 */
	private String borrowNid;
	/**
	 * 借款用户
	 */
	private String username;
	private Integer userId;
	
	public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
	 * 项目申请人
	 */
	private String applicant;
	/**
	 * 借款标题
	 */
	private String borrowName;
	/**
	 * 借款金额
	 */
	private String account;
	/**
	 * 年利率
	 */
	private String borrowApr;
	/**
	 * 借款期限
	 */
	private String borrowPeriod;
	/**
	 * 还款方式
	 */
	private String borrowStyle;
	/**
	 * 还款方式名称
	 */
	private String borrowStyleName;
	/**
	 * 项目类型
	 */
	private String projectType;
	/**
	 * 项目类型名称
	 */
	private String projectTypeName;
	/**
	 * 发布时间
	 */
	private String addtime;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 满标时间
	 */
	private String borrowFullTime;
	/**
	 * 放款时间
	 */
	private String recoverLastTime;
	   /**
     * 放款时间
     */
    private String recoverLastDay;

	/**
	 * 已借到金额
	 */
	private String borrowAccountYes;

	/**
	 * 剩余金额
	 */
	private String borrowAccountWait;

	/**
	 * 借款进度
	 */
	private String borrowAccountScale;
	
	/***
	 * 复审时间
	 */
	private String reverifyTime;
	
	/**
	 * recoverLastTime
	 * 
	 * @return the recoverLastTime
	 */

	public String getRecoverLastTime() {
		return recoverLastTime;
	}

	/**
	 * @param recoverLastTime
	 *            the recoverLastTime to set
	 */

	public void setRecoverLastTime(String recoverLastTime) {
		this.recoverLastTime = recoverLastTime;
	}

	/**
	 * borrowAccountYes
	 * 
	 * @return the borrowAccountYes
	 */

	public String getBorrowAccountYes() {
		return borrowAccountYes;
	}

	/**
	 * @param borrowAccountYes
	 *            the borrowAccountYes to set
	 */

	public void setBorrowAccountYes(String borrowAccountYes) {
		this.borrowAccountYes = borrowAccountYes;
	}

	/**
	 * borrowAccountWait
	 * 
	 * @return the borrowAccountWait
	 */

	public String getBorrowAccountWait() {
		return borrowAccountWait;
	}

	/**
	 * @param borrowAccountWait
	 *            the borrowAccountWait to set
	 */

	public void setBorrowAccountWait(String borrowAccountWait) {
		this.borrowAccountWait = borrowAccountWait;
	}

	/**
	 * borrowAccountScale
	 * 
	 * @return the borrowAccountScale
	 */

	public String getBorrowAccountScale() {
		return borrowAccountScale;
	}

	/**
	 * @param borrowAccountScale
	 *            the borrowAccountScale to set
	 */

	public void setBorrowAccountScale(String borrowAccountScale) {
		this.borrowAccountScale = borrowAccountScale;
	}

	/**
	 * borrowFullTime
	 * 
	 * @return the borrowFullTime
	 */

	public String getBorrowFullTime() {
		return borrowFullTime;
	}

	/**
	 * @param borrowFullTime
	 *            the borrowFullTime to set
	 */

	public void setBorrowFullTime(String borrowFullTime) {
		this.borrowFullTime = borrowFullTime;
	}

	/**
	 * borrowStyle
	 * 
	 * @return the borrowStyle
	 */

	public String getBorrowStyle() {
		return borrowStyle;
	}

	/**
	 * @param borrowStyle
	 *            the borrowStyle to set
	 */

	public void setBorrowStyle(String borrowStyle) {
		this.borrowStyle = borrowStyle;
	}

	/**
	 * borrowStyleName
	 * 
	 * @return the borrowStyleName
	 */

	public String getBorrowStyleName() {
		return borrowStyleName;
	}

	/**
	 * @param borrowStyleName
	 *            the borrowStyleName to set
	 */

	public void setBorrowStyleName(String borrowStyleName) {
		this.borrowStyleName = borrowStyleName;
	}

	/**
	 * projectType
	 * 
	 * @return the projectType
	 */

	public String getProjectType() {
		return projectType;
	}

	/**
	 * @param projectType
	 *            the projectType to set
	 */

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	/**
	 * projectTypeName
	 * 
	 * @return the projectTypeName
	 */

	public String getProjectTypeName() {
		return projectTypeName;
	}

	/**
	 * @param projectTypeName
	 *            the projectTypeName to set
	 */

	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName;
	}

	/**
	 * status
	 * 
	 * @return the status
	 */

	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * borrowNid
	 * 
	 * @return the borrowNid
	 */

	public String getBorrowNid() {
		return borrowNid;
	}

	/**
	 * @param borrowNid
	 *            the borrowNid to set
	 */

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	/**
	 * username
	 * 
	 * @return the username
	 */

	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * borrowName
	 * 
	 * @return the borrowName
	 */

	public String getBorrowName() {
		return borrowName;
	}

	/**
	 * @param borrowName
	 *            the borrowName to set
	 */

	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}

	/**
	 * account
	 * 
	 * @return the account
	 */

	public String getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */

	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * borrowApr
	 * 
	 * @return the borrowApr
	 */

	public String getBorrowApr() {
		return borrowApr;
	}

	/**
	 * @param borrowApr
	 *            the borrowApr to set
	 */

	public void setBorrowApr(String borrowApr) {
		this.borrowApr = borrowApr;
	}

	/**
	 * borrowPeriod
	 * 
	 * @return the borrowPeriod
	 */

	public String getBorrowPeriod() {
		return borrowPeriod;
	}

	/**
	 * @param borrowPeriod
	 *            the borrowPeriod to set
	 */

	public void setBorrowPeriod(String borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}

	/**
	 * addtime
	 * 
	 * @return the addtime
	 */

	public String getAddtime() {
		return addtime;
	}

	/**
	 * @param addtime
	 *            the addtime to set
	 */

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	/**
	 * serialversionuid
	 * 
	 * @return the serialversionuid
	 */

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

    public String getRecoverLastDay() {
        return recoverLastDay;
    }

    public void setRecoverLastDay(String recoverLastDay) {
        this.recoverLastDay = recoverLastDay;
    }

	public String getReverifyTime() {
		return reverifyTime;
	}

	public void setReverifyTime(String reverifyTime) {
		this.reverifyTime = reverifyTime;
	}

}
