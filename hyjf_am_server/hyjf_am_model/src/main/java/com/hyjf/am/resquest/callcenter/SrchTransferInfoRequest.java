package com.hyjf.am.resquest.callcenter;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author libin
 * @version CallCenterUserInfoRequest, v0.1 2018/6/11 17:52
 */
public class SrchTransferInfoRequest extends BaseVO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private String usernameSrch;

	private Integer limitStart;

	private Integer limitSize;

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

	public String getUsernameSrch() {
		return usernameSrch;
	}

	public void setUsernameSrch(String usernameSrch) {
		this.usernameSrch = usernameSrch;
	}
	
}
