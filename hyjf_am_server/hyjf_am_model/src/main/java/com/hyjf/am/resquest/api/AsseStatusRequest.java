package com.hyjf.am.resquest.api;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

/**
 *
 * @author Zha Daojian
 * @date 2018/11/13 17:47
 * @param 
 * @return 
 **/
public class AsseStatusRequest extends BasePage implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	// 资产编号
	private String assetId;
	// 资产来源
	private String instCode;

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getInstCode() {
		return instCode;
	}

	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}
}
