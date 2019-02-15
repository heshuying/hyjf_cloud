package com.hyjf.cs.trade.bean;

import io.swagger.annotations.ApiModelProperty;

/**
 * 资产接口返回参数
 * @version AssetStatusResultBean, v0.1 2018/8/27 13:38
 * @Author: Zha Daojian
 */
public class AemsAssetStatusResultBean extends BaseResultBean {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资产状态")
    private String assetStatus;

    @ApiModelProperty(value = "资产状态描述")
    private String statusDesc;

    @ApiModelProperty(value = "资产编号")
    private String borrowNid;

    @ApiModelProperty(value = "放款订单号")
    private String nid;

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    /**
     * assetStatus
     * @return the assetStatus
     */

    public String getAssetStatus() {
        return assetStatus;
    }

    /**
     * @param assetStatus the assetStatus to set
     */

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    @Override
    public String getStatusDesc() {
        return statusDesc;
    }

    @Override
    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
}
