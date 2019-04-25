package com.hyjf.am.vo.app.recharge;

import com.hyjf.am.vo.trade.JxBankConfigVO;

import java.util.List;

/**
 * @author wgx
 * @date 2019/4/18
 */
public class AppRechargeVO {

    // 充值规则
    private List rechargeRule;

    // 充值限额
    private List<AppRechargeLimitVO> rechargeLimit;

    public List getRechargeRule() {
        return rechargeRule;
    }

    public void setRechargeRule(List rechargeRule) {
        this.rechargeRule = rechargeRule;
    }

    public List<AppRechargeLimitVO> getRechargeLimit() {
        return rechargeLimit;
    }

    public void setRechargeLimit(List<AppRechargeLimitVO> rechargeLimit) {
        this.rechargeLimit = rechargeLimit;
    }
}
