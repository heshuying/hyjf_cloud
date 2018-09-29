/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.hjh;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author libin
 * @version HjhAccedeSumVO.java, v0.1 2018年7月10日 上午10:57:17
 */
public class HjhAccedeSumVO extends BaseVO implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "加入金额合计")
	private String sumAccedeAccount;

	@ApiModelProperty(value = "已投资金额合计")
	private String sumAlreadyInvest;

	@ApiModelProperty(value = "待(收)还总额合计")
	private String sumWaitTotal;

	@ApiModelProperty(value = "待(收)还本金合计")
	private String sumWaitCaptical;

	@ApiModelProperty(value = "待(收)还利息合计")
	private String sumWaitInterest;
	
	@ApiModelProperty(value = "剩余可投金额合计")
	private String sumAvailableInvestAccount;
	
	@ApiModelProperty(value = "冻结金额合计")
	private String sumFrostAccount;
	
	@ApiModelProperty(value = "公允价值合计")
	private String sumFairValue;
	
	public String getSumAccedeAccount() {
		return sumAccedeAccount;
	}
	public void setSumAccedeAccount(String sumAccedeAccount) {
		this.sumAccedeAccount = sumAccedeAccount;
	}
	public String getSumAlreadyInvest() {
		return sumAlreadyInvest;
	}
	public void setSumAlreadyInvest(String sumAlreadyInvest) {
		this.sumAlreadyInvest = sumAlreadyInvest;
	}
	public String getSumWaitTotal() {
		return sumWaitTotal;
	}
	public void setSumWaitTotal(String sumWaitTotal) {
		this.sumWaitTotal = sumWaitTotal;
	}
	public String getSumWaitCaptical() {
		return sumWaitCaptical;
	}
	public void setSumWaitCaptical(String sumWaitCaptical) {
		this.sumWaitCaptical = sumWaitCaptical;
	}
	public String getSumWaitInterest() {
		return sumWaitInterest;
	}
	public void setSumWaitInterest(String sumWaitInterest) {
		this.sumWaitInterest = sumWaitInterest;
	}
	public String getSumAvailableInvestAccount() {
		return sumAvailableInvestAccount;
	}
	public void setSumAvailableInvestAccount(String sumAvailableInvestAccount) {
		this.sumAvailableInvestAccount = sumAvailableInvestAccount;
	}
	public String getSumFrostAccount() {
		return sumFrostAccount;
	}
	public void setSumFrostAccount(String sumFrostAccount) {
		this.sumFrostAccount = sumFrostAccount;
	}
	public String getSumFairValue() {
		return sumFairValue;
	}
	public void setSumFairValue(String sumFairValue) {
		this.sumFairValue = sumFairValue;
	}
}
