package com.hyjf.am.vo.app.recharge;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author wgx
 * @date 2019/4/18
 */
@ApiModel(value = "充值说明", description = "充值说明")
public class AppRechargeVO implements Serializable {
    private static final long serialVersionUID = 7498073184357071490L;
    @ApiModelProperty(value = "充值规则")
    private List<AppRechargeRuleVO> rechargeRule;
    @ApiModelProperty(value = "充值限额")
    private List<AppRechargeLimitVO> rechargeLimit;

    public List<AppRechargeRuleVO> getRechargeRule() {
        return rechargeRule;
    }

    public void setRechargeRule(List<AppRechargeRuleVO> rechargeRule) {
        this.rechargeRule = rechargeRule;
    }

    public List<AppRechargeLimitVO> getRechargeLimit() {
        return rechargeLimit;
    }

    public void setRechargeLimit(List<AppRechargeLimitVO> rechargeLimit) {
        this.rechargeLimit = rechargeLimit;
    }
}
