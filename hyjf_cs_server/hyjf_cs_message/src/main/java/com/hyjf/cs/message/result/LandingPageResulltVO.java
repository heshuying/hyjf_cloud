/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.result;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wangjun
 * @version LandingPageResulltVO, v0.1 2018/7/30 14:03
 */
public class LandingPageResulltVO extends BaseResultBean {
    @ApiModelProperty("为用户赚取收益,亿元")
    private BigDecimal profitSum;

    @ApiModelProperty("累计投资者,万人")
    private Integer serveUserSum;

    public BigDecimal getProfitSum() {
        return profitSum;
    }

    public void setProfitSum(BigDecimal profitSum) {
        this.profitSum = profitSum;
    }

    public Integer getServeUserSum() {
        return serveUserSum;
    }

    public void setServeUserSum(Integer serveUserSum) {
        this.serveUserSum = serveUserSum;
    }
}
