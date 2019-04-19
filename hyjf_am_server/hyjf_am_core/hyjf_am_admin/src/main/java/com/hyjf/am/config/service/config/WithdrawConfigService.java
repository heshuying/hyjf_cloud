package com.hyjf.am.config.service.config;

import com.hyjf.am.config.dao.model.auto.WithdrawRuleConfig;

import java.util.List;

/**
 * 提现配置
 * @author jun
 * @version WithdrawConfigService, v0.1 2019/4/19 14:14
 */
public interface WithdrawConfigService{

    /**
     * 获取提现规则配置总数
     * @return
     */
    int getWithdrawRuleConfigCount();

    /**
     * 提现规则配置列表
     * @return
     */
    List<WithdrawRuleConfig> getWithdrawRuleConfigList();

    /**
     * 修改提现规则配置
     * @param withdrawRuleConfig
     * @return
     */
    Integer updateWithdrawRuleConfig(WithdrawRuleConfig withdrawRuleConfig);

    /**
     * 提现规则配置详情
     * @param id
     * @return
     */
    WithdrawRuleConfig getWithdrawRuleConfigById(Integer id);
}
