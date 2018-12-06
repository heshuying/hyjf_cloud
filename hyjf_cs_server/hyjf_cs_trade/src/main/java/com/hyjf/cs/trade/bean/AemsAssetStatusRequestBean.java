package com.hyjf.cs.trade.bean;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 资产查询请求参数
 */
public class AemsAssetStatusRequestBean extends BaseBean implements Serializable{
	
	private static final long serialVersionUID = 32143440657771846L;

	@ApiModelProperty(value = "资产编号")
    private String assetId;
    
	/**
	 * assetId
	 * @return the assetId
	 */
	
	public String getAssetId() {
		return assetId;
	}

	/**
	 * @param assetId the assetId to set
	 */
	
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

}

