package com.hyjf.cs.user.bean;

import io.swagger.annotations.ApiModelProperty;

public class AemsSynBalanceRequestBean extends BaseBean {

	@ApiModelProperty(value = "详情服务")
	private String host;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }


	
}
