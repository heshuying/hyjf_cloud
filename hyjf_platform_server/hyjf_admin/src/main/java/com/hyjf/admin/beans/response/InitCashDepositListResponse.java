/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import java.util.List;

import com.hyjf.admin.beans.vo.AdminAssetListCustomizeVO;
import com.hyjf.admin.beans.vo.AdminHjhInstConfigAPIVO;

import io.swagger.annotations.ApiModelProperty;

/**
 * 保证金不足的列表初始化的返回内容
 * @author jun
 * @version InitCashDepositResponse, v0.1 2018/7/20 9:05
 */
public class InitCashDepositListResponse {

	@ApiModelProperty(value = "资金列表")
	List<AdminAssetListCustomizeVO> assetList;

	@ApiModelProperty(value = "汇计划机构配置列表")
	List<AdminHjhInstConfigAPIVO>  HjhInstConfigList;

	public List<AdminAssetListCustomizeVO> getAssetList() {
		return assetList;
	}


	public void setAssetList(List<AdminAssetListCustomizeVO> assetList) {
		this.assetList = assetList;
	}

	public List<AdminHjhInstConfigAPIVO> getHjhInstConfigList() {
		return HjhInstConfigList;
	}

	public void setHjhInstConfigList(List<AdminHjhInstConfigAPIVO> hjhInstConfigList) {
		HjhInstConfigList = hjhInstConfigList;
	}
}
