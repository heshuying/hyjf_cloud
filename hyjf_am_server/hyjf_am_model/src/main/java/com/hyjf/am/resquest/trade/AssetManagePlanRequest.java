package com.hyjf.am.resquest.trade;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class AssetManagePlanRequest implements Serializable {

    @ApiModelProperty(value = "加入订单号")
    private String accedeOrderId;

    @ApiModelProperty(value = "类型：0是投资中 1是锁定中 2是已回款")
    private String type;

    public String getAccedeOrderId() {
        return accedeOrderId;
    }

    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
