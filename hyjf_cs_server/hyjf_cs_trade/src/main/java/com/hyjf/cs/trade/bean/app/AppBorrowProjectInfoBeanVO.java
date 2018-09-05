/**
 * Description:项目详情查询所用vo
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 下午2:17:31
 * Modification History:
 * Modified by : 
 */
package com.hyjf.cs.trade.bean.app;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

public class AppBorrowProjectInfoBeanVO extends BaseVO implements Serializable {

	/** 序列化id */
	private static final long serialVersionUID = -2913028255458205989L;

	/**
	 * 项目剩余金额
	 * number
	 * example: 1000000
	 */
	private String borrowRemain;

	/**
	 * 项目进度 (不带百分号)
	 * string
	 * example: 53
	 */
	private String borrowProgress;

	/**
	 * 开标时间
	 * string
	 * example: 2017-10-10 10:10
	 */
	private String onTime;

	/**
	 * 项目金额 (总金额)
	 * number
	 * example: 1000000
	 */
	private String account;

	/**
	 * 项目历史年回报率
	 * number
	 * example: 8.5
	 */
	private String borrowApr;

	/**
	 * 产品加息收益率
	 * number
	 * example: 0.5%.
	 */
	private String borrowExtraYield;

	/**
	 * 项目编号
	 * string
	 * example: ZXH17071271
	 */
	private String borrowId;

	/**
	 * 计息时间
	 * string
	 * example: 复审成功立即计息
	 */
	private String onAccrual;

	/**
	 * 项目进行状态 如 1：复审中
	 * number
	 * example: 1
	 */
	private String status;

	/**
	 * 项目进度状态 0:审核 1:信息发布 2:投资 3:计息 4:回款
	 * number
	 * example: 2
	 */
	private String borrowProgressStatus;

	/**
	 * 项目期限
	 * number
	 * example: 30
	 */
	private String borrowPeriod;

	/**
	 * 项目期限单位
	 * number
	 * example: 天
	 */
	private String borrowPeriodUnit;

	/**
	 * 项目类型 对应号待查 如 RTB:融通宝
	 * string
	 * example: RTB
	 */
	private String type;

	/**
	 * 项目类型标签
	 * string
	 * example: 尊享
	 */
	private String tag;

	/**
	 * 还款方式
	 * string
	 * example: 等额本息
	 */
	private String repayStyle;
	public AppBorrowProjectInfoBeanVO() {
		super();
	}
	public String getBorrowRemain() {
		return borrowRemain;
	}
	public void setBorrowRemain(String borrowRemain) {
		this.borrowRemain = borrowRemain;
	}
	public String getBorrowProgress() {
		return borrowProgress;
	}
	public void setBorrowProgress(String borrowProgress) {
		this.borrowProgress = borrowProgress;
	}
	public String getOnTime() {
		return onTime;
	}
	public void setOnTime(String onTime) {
		this.onTime = onTime;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getBorrowApr() {
		return borrowApr;
	}
	public void setBorrowApr(String borrowApr) {
		this.borrowApr = borrowApr;
	}
	public String getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}
	public String getOnAccrual() {
		return onAccrual;
	}
	public void setOnAccrual(String onAccrual) {
		this.onAccrual = onAccrual;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBorrowProgressStatus() {
		return borrowProgressStatus;
	}
	public void setBorrowProgressStatus(String borrowProgressStatus) {
		this.borrowProgressStatus = borrowProgressStatus;
	}
	public String getBorrowPeriod() {
		return borrowPeriod;
	}
	public void setBorrowPeriod(String borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}
	public String getBorrowPeriodUnit() {
		return borrowPeriodUnit;
	}
	public void setBorrowPeriodUnit(String borrowPeriodUnit) {
		this.borrowPeriodUnit = borrowPeriodUnit;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getRepayStyle() {
		return repayStyle;
	}
	public void setRepayStyle(String repayStyle) {
		this.repayStyle = repayStyle;
	}

	public String getBorrowExtraYield() { return borrowExtraYield; }
	public void setBorrowExtraYield(String borrowExtraYield) { this.borrowExtraYield = borrowExtraYield; }


}
