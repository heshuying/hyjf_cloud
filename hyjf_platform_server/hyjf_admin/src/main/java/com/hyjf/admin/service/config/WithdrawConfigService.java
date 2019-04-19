package com.hyjf.admin.service.config;

import com.hyjf.am.response.admin.config.WithdrawRuleConfigResponse;
import com.hyjf.am.response.admin.config.WithdrawTimeConfigResponse;
import com.hyjf.am.vo.admin.config.WithdrawRuleConfigVO;

/**
 * 提现配置
 */
public interface WithdrawConfigService {

    /**
     * 假期时间配置
     * @return
     */
    WithdrawTimeConfigResponse getWithdrawTimeConfigList();

    /**
     * 提现规则配置
     * @return
     */
    WithdrawRuleConfigResponse getWithdrawRuleConfigList();

    /**
     * 提现规则配置详情
     * @param id
     * @return
     */
    WithdrawRuleConfigResponse getWithdrawRuleConfigById(Integer id);

    /**
     * 提现规则配置修改
     * @param form
     * @return
     */
    int updateWithdrawRuleConfig(WithdrawRuleConfigVO form);
}
