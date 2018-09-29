package com.hyjf.am.resquest.callcenter;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author libin
 * @version CallCenterUserInfoRequest, v0.1 2018/6/11 17:52
 */
public class CallcenterHztInvestRequest extends BaseVO implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String userId;

	private Integer limitStart;

	private Integer limitSize;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
