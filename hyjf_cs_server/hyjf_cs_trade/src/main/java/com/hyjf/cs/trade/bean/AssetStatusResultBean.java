package com.hyjf.cs.trade.bean;

import io.swagger.annotations.ApiModelProperty;

/**
 * @version AssetStatusResultBean, v0.1 2018/8/27 13:38
 * @Author: Zha Daojian
 */
public class AssetStatusResultBean extends BaseResultBean{

    @ApiModelProperty(value = "资产状态")
    private String assetStatus;

    @ApiModelProperty(value = "资产编号")
    private String borrowNid;

    public String getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }
}
