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
package com.hyjf.am.trade.dao.model.customize.app;

import java.io.Serializable;

public class AppHzcDisposalPlanCustomize implements Serializable {

	/** 序列化id */
	private static final long serialVersionUID = 8219831069954985000L;

	/* 售价预估 priceEstimate */
	private String priceEstimate;
	/* 处置周期 disposalPeriod */
	private String disposalPeriod;
	/* 处置渠道 disposalChannel */
	private String disposalChannel;
	/* 处置预案 disposalPlan */
	private String disposalPlan;
	/* 备注说明 remark	 */
	/*private String remark;*/

	public AppHzcDisposalPlanCustomize() {
		super();
	}

	public String getPriceEstimate() {
		return priceEstimate;
	}

	public void setPriceEstimate(String priceEstimate) {
		this.priceEstimate = priceEstimate;
	}

	public String getDisposalPeriod() {
		return disposalPeriod;
	}

	public void setDisposalPeriod(String disposalPeriod) {
		this.disposalPeriod = disposalPeriod;
	}

	public String getDisposalChannel() {
		return disposalChannel;
	}

	public void setDisposalChannel(String disposalChannel) {
		this.disposalChannel = disposalChannel;
	}

	public String getDisposalPlan() {
		return disposalPlan;
	}

	public void setDisposalPlan(String disposalPlan) {
		this.disposalPlan = disposalPlan;
	}

	/*public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}*/

}
