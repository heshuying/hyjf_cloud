package com.hyjf.admin.service.impl.config;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.config.WithdrawConfigService;
import com.hyjf.am.response.admin.config.WithdrawRuleConfigResponse;
import com.hyjf.am.response.admin.config.WithdrawTimeConfigResponse;
import com.hyjf.am.resquest.admin.config.WithdrawRuleConfigRequest;
import com.hyjf.am.resquest.admin.config.WithdrawTimeConfigRequest;
import com.hyjf.am.vo.admin.config.WithdrawRuleConfigVO;
import com.hyjf.am.vo.admin.config.WithdrawTimeConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 提现配置
 * @author jun
 * @version WithdrawConfigServiceImpl, v0.1 2019/4/19 14:03
 */
@Service
public class WithdrawConfigServiceImpl extends BaseServiceImpl implements WithdrawConfigService {

    @Autowired
    AmConfigClient amConfigClient;

    /**
     *假期时间配置列表
     * @return
     * @param request
     */
    @Override
    public WithdrawTimeConfigResponse getWithdrawTimeConfigList(WithdrawTimeConfigRequest request) {
        return amConfigClient.getWithdrawTimeConfigList(request);
    }

    /**
     * 提现规则配置列表
     * @return
     * @param request
     */
    @Override
    public WithdrawRuleConfigResponse getWithdrawRuleConfigList(WithdrawRuleConfigRequest request) {
        return amConfigClient.getWithdrawRuleConfigList(request);
    }

    /**
     * 提现规则详情
     * @param id
     * @return
     */
    @Override
    public WithdrawRuleConfigResponse getWithdrawRuleConfigById(Integer id) {
        return amConfigClient.getWithdrawRuleConfigById(id);
    }

    /**
     * 提现规则配置修改
     * @param form
     * @return
     */
    @Override
    public int updateWithdrawRuleConfig(WithdrawRuleConfigVO form) {
        return amConfigClient.updateWithdrawRuleConfig(form);
    }

    /**
     * 保存假期时间配置
     * @param form
     * @return
     */
    @Override
    public int saveWithdrawTimeConfig(WithdrawTimeConfigVO form) {
        return amConfigClient.saveWithdrawTimeConfig(form);
    }

    /**
     * 提现时间配置详情
     * @param id
     * @return
     */
    @Override
    public WithdrawTimeConfigResponse getWithdrawTimeConfigById(Integer id) {
        return amConfigClient.getWithdrawTimeConfigById(id);
    }

}
