package com.hyjf.cs.trade.bean;

import io.swagger.annotations.ApiModelProperty;

/**
 * @version AssetStatusRequestBean, v0.1 2018/8/27 11:26
 * @Author: Zha Daojian
 */
public class AssetStatusRequestBean extends BaseBean {

    @ApiModelProperty(value = "机构编号（必填）")
    private String instCode;
    @ApiModelProperty(value = "资产编号")
    private String assetId;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    @Override
    public String getInstCode() {
        return instCode;
    }

    @Override
    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }
}
