package com.hyjf.admin.service.config;

import com.hyjf.am.response.config.WithdrawRuleConfigResponse;
import com.hyjf.am.response.config.WithdrawTimeConfigResponse;
import com.hyjf.am.resquest.admin.config.AdminWithdrawRuleConfigRequest;
import com.hyjf.am.resquest.admin.config.AdminWithdrawTimeConfigRequest;
import com.hyjf.am.vo.config.WithdrawRuleConfigVO;
import com.hyjf.am.vo.config.WithdrawTimeConfigVO;

/**
 * 提现配置
 */
public interface WithdrawConfigService {

    /**
     * 假期时间配置
     * @return
     * @param request
     */
    WithdrawTimeConfigResponse getWithdrawTimeConfigList(AdminWithdrawTimeConfigRequest request);

    /**
     * 提现规则配置
     * @return
     * @param request
     */
    WithdrawRuleConfigResponse getWithdrawRuleConfigList(AdminWithdrawRuleConfigRequest request);

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

    /**
     * 保存提现时间配置
     * @param form
     * @return
     */
    int saveWithdrawTimeConfig(WithdrawTimeConfigVO form);

    /**
     * 提现时间配置详情
     * @param id
     * @return
     */
    WithdrawTimeConfigResponse getWithdrawTimeConfigById(Integer id);
}
