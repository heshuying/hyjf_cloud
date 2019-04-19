/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.WithdrawRuleConfig;

/**
 * 提现规则配置Service
 *
 * @author liuyang
 * @version WithdrawRuleConfigService, v0.1 2019/4/19 16:41
 */
public interface WithdrawRuleConfigService extends BaseService {
    /**
     * 获取提现规则配置
     *
     * @param userType
     * @param withdrawmoney
     * @return
     */
    WithdrawRuleConfig selectWithdrawRuleConfig(Integer userType, String withdrawmoney);
}
