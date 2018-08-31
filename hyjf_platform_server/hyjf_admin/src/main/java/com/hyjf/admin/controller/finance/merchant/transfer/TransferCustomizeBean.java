package com.hyjf.admin.controller.finance.merchant.transfer;

import com.hyjf.am.vo.admin.UserTransferVO;

import java.io.Serializable;

public class TransferCustomizeBean extends UserTransferVO implements Serializable {

	/** 序列化id */
	private static final long serialVersionUID = -432561655172605057L;
	/** 转账账户余额 */
	private String balance;

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

}
