package com.hyjf.am.vo.trade.hjh;

import com.hyjf.am.vo.BaseVO;



/**
 * 首页hjh实体bean
 * @author zhangyk
 * @date 2018/7/4 19:27
 */
public class HjhPlanHomeVO extends BaseVO {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 5748630051215873837L;
	// 计划nid
	private String planNid;
	// 计划年华收益率
	private String planApr;
	// 计划期限
	private String planPeriod;
	// 天标OR月标
	private String isMonth;
	// 计划可投金额
	private String availableInvestAccount;
	// 计划状态
	private String status;
	// 计划状态名称
	private String statusName;
	// 计划名称
	private String planName;

	// add by xiashuqing 20171108 begin app使用
	private String couponEnable;
	// add by xiashuqing 20171108 end app使用

	public HjhPlanHomeVO() {
		super();
	}

	public String getPlanNid() {
		return planNid;
	}

	public void setPlanNid(String planNid) {
		this.planNid = planNid;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
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

	public String getCouponEnable() {
		return couponEnable;
	}

	public void setCouponEnable(String couponEnable) {
		this.couponEnable = couponEnable;
	}

	public String getIsMonth() {
		return isMonth;
	}

	public void setIsMonth(String isMonth) {
		this.isMonth = isMonth;
	}
}
