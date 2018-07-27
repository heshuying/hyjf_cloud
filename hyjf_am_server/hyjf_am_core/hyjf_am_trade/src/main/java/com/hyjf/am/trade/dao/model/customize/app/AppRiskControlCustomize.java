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

public class AppRiskControlCustomize implements Serializable {

	/** 序列化id */
	private static final long serialVersionUID = -3789069653000541937L;
	/* 合作机构 partner */
	private String partner;
	/* 机构介绍 agencyIntroduction */
	private String agencyIntroduction;
	/* 操作流程 operatingProcess */
	private String operatingProcess;
	/* 风控措施 controlMeasures */
	private String controlMeasures;
	// 抵押物信息
	private String controlMort;

	public AppRiskControlCustomize() {
		super();
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getAgencyIntroduction() {
		return agencyIntroduction;
	}

	public void setAgencyIntroduction(String agencyIntroduction) {
		this.agencyIntroduction = agencyIntroduction;
	}

	public String getOperatingProcess() {
		return operatingProcess;
	}

	public void setOperatingProcess(String operatingProcess) {
		this.operatingProcess = operatingProcess;
	}

	public String getControlMeasures() {
		return controlMeasures;
	}

	public void setControlMeasures(String controlMeasures) {
		this.controlMeasures = controlMeasures;
	}

	public String getControlMort() {
		return controlMort;
	}

	public void setControlMort(String controlMort) {
		this.controlMort = controlMort;
	}

}
