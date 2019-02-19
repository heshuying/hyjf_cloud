package com.hyjf.cs.trade.bean;

import io.swagger.annotations.ApiModelProperty;

public class AemsTrusteePayResultBean  extends BaseMapBean   {




    // 请求处理是否成功  0成功 1失败
    @ApiModelProperty(value = "授权状态")
    private int status = 0;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



}
