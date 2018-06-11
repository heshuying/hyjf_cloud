package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.BaseVO;

/**
 * 用户通知设置
 * @author hesy
 */
public class UserNoticeSetRequest extends BaseVO {
	
	private Integer userId;

	/**
	 * 充值成功短信 0发送1不发送
	 */
	private Integer rechargeSms;

	/**
	 * 提现成功短线 0发送1不发送
	 */
	private Integer withdrawSms;

	/**
	 * 投资成功短信 0发送1不发送
	 */
	private Integer investSms;

	/**
	 * 是否发送投资协议邮件 0发送 1不发送
	 */
	private Integer isSmtp;
	/**
	 * 回收成功短信 0发送1不发送
	 */
	private Integer recieveSms;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getRechargeSms() {
		return rechargeSms;
	}
	public void setRechargeSms(Integer rechargeSms) {
		this.rechargeSms = rechargeSms;
	}
	public Integer getWithdrawSms() {
		return withdrawSms;
	}
	public void setWithdrawSms(Integer withdrawSms) {
		this.withdrawSms = withdrawSms;
	}
	public Integer getInvestSms() {
		return investSms;
	}
	public void setInvestSms(Integer investSms) {
		this.investSms = investSms;
	}
	public Integer getIsSmtp() {
		return isSmtp;
	}
	public void setIsSmtp(Integer isSmtp) {
		this.isSmtp = isSmtp;
	}
	public Integer getRecieveSms() {
		return recieveSms;
	}
	public void setRecieveSms(Integer recieveSms) {
		this.recieveSms = recieveSms;
	}
	
}
