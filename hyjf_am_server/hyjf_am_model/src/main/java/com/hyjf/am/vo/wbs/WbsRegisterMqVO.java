/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.wbs;

import com.google.common.base.MoreObjects;

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
	private String customerId;
	// 资产端客户id
	private String assetCustomerId;

	//消息UUID,便于查找对应的消费者
	private String requestUUID;

	public String getUtmId() {
		return utmId;
	}

	public void setUtmId(String utmId) {
		this.utmId = utmId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getAssetCustomerId() {
		return assetCustomerId;
	}

	public void setAssetCustomerId(String assetCustomerId) {
		this.assetCustomerId = assetCustomerId;
	}

	public String getRequestUUID() {
		return requestUUID;
	}

	public void setRequestUUID(String requestUUID) {
		this.requestUUID = requestUUID;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("requestUUID",requestUUID).add("utmId", utmId).add("customerId", customerId)
				.add("assetCustomerId", assetCustomerId).toString();
	}
}
