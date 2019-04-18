package com.hyjf.am.vo.app;

import com.hyjf.am.vo.trade.JxBankConfigVO;

import java.util.List;

/**
 * @author wgx
 * @date 2019/4/18
 */
public class AppRechargeVo {

    // 充值规则
    private List rechargeRule;

    // 充值限额
    private List<JxBankConfigVO> rechargeLimit;

    public List getRechargeRule() {
        return rechargeRule;
    }

    public void setRechargeRule(List rechargeRule) {
        this.rechargeRule = rechargeRule;
    }

    public List<JxBankConfigVO> getRechargeLimit() {
        return rechargeLimit;
    }

    public void setRechargeLimit(List<JxBankConfigVO> rechargeLimit) {
        this.rechargeLimit = rechargeLimit;
    }
}
