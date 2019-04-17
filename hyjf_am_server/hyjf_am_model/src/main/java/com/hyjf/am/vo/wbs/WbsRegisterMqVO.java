/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.wbs;

/**
 * wbs 客户注册MQ消息封装
 * 
 * @author cui
 * @version WbsRegisterMqVO, v0.1 2019/4/17 15:00
 */
public class WbsRegisterMqVO {

	// 财富端id
	private String utmId;
	// 财富端客户id
	private String thirdpartyId;
	// 资产端客户id
	private String assetCustomerId;

	public String getUtmId() {
		return utmId;
	}

	public void setUtmId(String utmId) {
		this.utmId = utmId;
	}

	public String getThirdpartyId() {
		return thirdpartyId;
	}

	public void setThirdpartyId(String thirdpartyId) {
		this.thirdpartyId = thirdpartyId;
	}

	public String getAssetCustomerId() {
		return assetCustomerId;
	}

	public void setAssetCustomerId(String assetCustomerId) {
		this.assetCustomerId = assetCustomerId;
	}
}
