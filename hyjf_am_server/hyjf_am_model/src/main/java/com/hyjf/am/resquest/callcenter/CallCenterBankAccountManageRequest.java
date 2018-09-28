package com.hyjf.am.resquest.callcenter;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author libin
 * @version CallCenterBankAccountManageRequest, v0.1 2018/6/11 17:52
 */
public class CallCenterBankAccountManageRequest extends BaseVO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String userName;

	private Integer limitStart;

	private Integer limitSize;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(Integer limitStart) {
		this.limitStart = limitStart;
	}

	public Integer getLimitSize() {
		return limitSize;
	}

	public void setLimitSize(Integer limitSize) {
		this.limitSize = limitSize;
	}
}
