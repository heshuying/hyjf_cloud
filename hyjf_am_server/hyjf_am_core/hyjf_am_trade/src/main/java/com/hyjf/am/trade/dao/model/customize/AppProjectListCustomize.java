/**
 * Description:用户开户列表前端显示查询所用po
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: jijun
 * @version: 1.0
 * Created at: 20180726
 * Modification History:
 * Modified by :
 */

package com.hyjf.am.trade.dao.model.customize;

import java.math.BigDecimal;

/**
 * app 实体
 */
public class AppProjectListCustomize {

	/**
	 *序列化id
	 */
	private static final long serialVersionUID = 5748630051215873837L;
	// 项目id
	private String borrowNid;
	// 项目标题
	private String borrowName;
	// 项目类型描述
	private String borrowDesc;
	// 项目类型
	private String borrowType;
	// 项目年华收益率
	private String borrowApr;
	// 项目期限
	private String borrowPeriod;
	// 项目期限int值
	private Integer borrowPeriodInt;
	// 项目期限计量单位
	private String borrowPeriodType;
	
	// 项目进度
	private String borrowSchedule;
	// 项目状态
	private String status;
	// 定时发标时间
	private String onTime;
	// 项目详情H5url
	private String borrowUrl;
	//项目总额
	private String borrowTotalMoney;
	//剩余可投金额
	private String borrowAccountWait;
	//是否可用优惠券投资1代表可用，0代表不可用
	private String couponEnable = "1";
	//融通宝加息
	private String borrowExtraYield ;
	// 项目类型编码
	private String projectType;


	// add by xiashuqing 20171108 begin 首页汇计划展示
	// 计划年华收益率
	private String planApr;
	// 计划期限
	private String planPeriod;
	// 计划可投金额
	private String availableInvestAccount;
	// 计划状态名称
	private String statusName;
	//add by xiashuqing 20171108 end 首页汇计划展示

	// add by fuqiang 20171204 begin 首页项目展示
	// 标的第一项
	private String borrowTheFirst;

	// 标的第一项描述
	private String borrowTheFirstDesc;

	// 标的第二项
	private String borrowTheSecond;

	// 标的第二项描述
	private String borrowTheSecondDesc;

	// 计划状态名字描述
	private String statusNameDesc;
	// add by fuqiang 20171204 end 首页项目展示
	/**是否产品加息*/
	private Integer increaseInterestFlag;
	/**产品加息数据库字段*/
	private BigDecimal borrowExtraYieldOld;

	/**
	 * 构造方法
	 */

	public AppProjectListCustomize() {
		super();
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

	public String getBorrowUrl() {
		return borrowUrl;
	}

	public void setBorrowUrl(String borrowUrl) {
		this.borrowUrl = borrowUrl;
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

    public String getCouponEnable() {
        return couponEnable;
    }

    public void setCouponEnable(String couponEnable) {
        this.couponEnable = couponEnable;
    }

	public String getBorrowExtraYield() {
		return borrowExtraYield;
	}

	public void setBorrowExtraYield(String borrowExtraYield) {
		this.borrowExtraYield = borrowExtraYield;
	}

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getBorrowAccountWait() {
        return borrowAccountWait;
    }

    public void setBorrowAccountWait(String borrowAccountWait) {
        this.borrowAccountWait = borrowAccountWait;
    }

	public String getPlanApr() {
		return planApr;
	}

	public void setPlanApr(String planApr) {
		this.planApr = planApr;
	}

	public String getPlanPeriod() {
		return planPeriod;
	}

	public void setPlanPeriod(String planPeriod) {
		this.planPeriod = planPeriod;
	}

	public String getAvailableInvestAccount() {
		return availableInvestAccount;
	}

	public void setAvailableInvestAccount(String availableInvestAccount) {
		this.availableInvestAccount = availableInvestAccount;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
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

	public String getStatusNameDesc() {
		return statusNameDesc;
	}

	public void setStatusNameDesc(String statusNameDesc) {
		this.statusNameDesc = statusNameDesc;
	}

	public String getBorrowPeriodType() {
		return borrowPeriodType;
	}

	public void setBorrowPeriodType(String borrowPeriodType) {
		this.borrowPeriodType = borrowPeriodType;
	}

	public Integer getBorrowPeriodInt() {
		return borrowPeriodInt;
	}

	public void setBorrowPeriodInt(Integer borrowPeriodInt) {
		this.borrowPeriodInt = borrowPeriodInt;
	}

	public Integer getIncreaseInterestFlag() {
		return increaseInterestFlag;
	}

	public void setIncreaseInterestFlag(Integer increaseInterestFlag) {
		this.increaseInterestFlag = increaseInterestFlag;
	}

	public BigDecimal getBorrowExtraYieldOld() {
		return borrowExtraYieldOld;
	}

	public void setBorrowExtraYieldOld(BigDecimal borrowExtraYieldOld) {
		this.borrowExtraYieldOld = borrowExtraYieldOld;
	}
}
