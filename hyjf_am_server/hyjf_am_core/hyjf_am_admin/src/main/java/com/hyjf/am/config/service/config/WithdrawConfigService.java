package com.hyjf.am.config.service.config;

import com.hyjf.am.config.dao.model.auto.WithdrawRuleConfig;
import com.hyjf.am.config.dao.model.auto.WithdrawTimeConfig;
import com.hyjf.am.resquest.admin.config.AdminWithdrawRuleConfigRequest;
import com.hyjf.am.resquest.admin.config.AdminWithdrawTimeConfigRequest;

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
    List<WithdrawRuleConfig> getWithdrawRuleConfigList(AdminWithdrawRuleConfigRequest request);

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

    /**
     * 保存提现时间配置
     * @param form
     * @return
     */
    Integer saveWithdrawTimeConfig(WithdrawTimeConfig form);

    /**
     * 提现时间配置详情
     * @param id
     * @return
     */
    WithdrawTimeConfig getWithdrawTimeConfigById(Integer id);

    /**
     * 获取提现时间配置总数
     * @return
     */
    int getWithdrawTimeConfigCount();

    /**
     * 获取提现时间配置列表
     * @param request
     * @return
     */
    List<WithdrawTimeConfig> getWithdrawTimeConfigList(AdminWithdrawTimeConfigRequest request);
}
