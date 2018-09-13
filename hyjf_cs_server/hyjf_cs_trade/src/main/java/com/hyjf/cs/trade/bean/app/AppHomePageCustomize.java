package com.hyjf.cs.trade.bean.app;

import java.io.Serializable;

/**
 * app端返回前台数据bean
 * @author zhangyk
 * @date 2018/7/9 9:46
 */
public class AppHomePageCustomize implements Serializable {

	private static final long serialVersionUID = 8721262544424025910L;

	// 项目id
	private String borrowNid;
	// 项目标题
	private String borrowName;
	// 项目类型描述
	private String borrowDesc;
	// 项目类型
	private String borrowType;
	// 标的第一项
	private String borrowTheFirst;
	// 标的第一项描述
	private String borrowTheFirstDesc;
	// 标的第二项
	private String borrowTheSecond;
	// 标的第二项描述
	private String borrowTheSecondDesc;
	// 计划状态名称
	private String statusName;
	// 计划状态名字描述
	private String statusNameDesc;
	// 项目详情H5的url
	private String borrowUrl;
	// 年化收益
	private String borrowApr;
	// 项目期限
	private String borrowPeriod;
	// 项目状态
	private String status;
	// 开标时间
	private String onTime;
	// 完成率
	private String borrowSchedule;
	// 项目总额
	private String borrowTotalMoney = "0";

	// 项目期限int值
	private Integer borrowPeriodInt;
	// 项目期限计量单位
	private String borrowPeriodType;
	// 产品加息
	private String borrowExtraYield;

	public String getBorrowApr() {
		return borrowApr;
	}

	public void setBorrowApr(String borrowApr) {
		this.borrowApr = borrowApr;
	}

	public String getBorrowPeriod() {
		return borrowPeriod;
	}

	public void setBorrowPeriod(String borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOnTime() {
		return onTime;
	}

	public void setOnTime(String onTime) {
		this.onTime = onTime;
	}

	public String getBorrowSchedule() {
		return borrowSchedule;
	}

	public void setBorrowSchedule(String borrowSchedule) {
		this.borrowSchedule = borrowSchedule;
	}

	public String getBorrowTotalMoney() {
		return borrowTotalMoney;
	}

	public void setBorrowTotalMoney(String borrowTotalMoney) {
		this.borrowTotalMoney = borrowTotalMoney;
	}

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public String getBorrowName() {
		return borrowName;
	}

	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}

	public String getBorrowDesc() {
		return borrowDesc;
	}

	public void setBorrowDesc(String borrowDesc) {
		this.borrowDesc = borrowDesc;
	}

	public String getBorrowType() {
		return borrowType;
	}

	public void setBorrowType(String borrowType) {
		this.borrowType = borrowType;
	}

	public String getBorrowTheFirst() {
		return borrowTheFirst;
	}

	public void setBorrowTheFirst(String borrowTheFirst) {
		this.borrowTheFirst = borrowTheFirst;
	}

	public String getBorrowTheFirstDesc() {
		return borrowTheFirstDesc;
	}

	public void setBorrowTheFirstDesc(String borrowTheFirstDesc) {
		this.borrowTheFirstDesc = borrowTheFirstDesc;
	}

	public String getBorrowTheSecond() {
		return borrowTheSecond;
	}

	public void setBorrowTheSecond(String borrowTheSecond) {
		this.borrowTheSecond = borrowTheSecond;
	}

	public String getBorrowTheSecondDesc() {
		return borrowTheSecondDesc;
	}

	public void setBorrowTheSecondDesc(String borrowTheSecondDesc) {
		this.borrowTheSecondDesc = borrowTheSecondDesc;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getStatusNameDesc() {
		return statusNameDesc;
	}

	public void setStatusNameDesc(String statusNameDesc) {
		this.statusNameDesc = statusNameDesc;
	}

	public String getBorrowUrl() {
		return borrowUrl;
	}

	public void setBorrowUrl(String borrowUrl) {
		this.borrowUrl = borrowUrl;
	}

	public Integer getBorrowPeriodInt() {
		return borrowPeriodInt;
	}

	public void setBorrowPeriodInt(Integer borrowPeriodInt) {
		this.borrowPeriodInt = borrowPeriodInt;
	}

	public String getBorrowPeriodType() {
		return borrowPeriodType;
	}

	public void setBorrowPeriodType(String borrowPeriodType) {
		this.borrowPeriodType = borrowPeriodType;
	}

	public String getBorrowExtraYield() {
		return borrowExtraYield;
	}

	public void setBorrowExtraYield(String borrowExtraYield) {
		this.borrowExtraYield = borrowExtraYield;
	}
}
