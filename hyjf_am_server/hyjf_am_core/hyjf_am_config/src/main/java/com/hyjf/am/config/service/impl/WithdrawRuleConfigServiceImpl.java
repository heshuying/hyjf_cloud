/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.model.auto.WithdrawRuleConfig;
import com.hyjf.am.config.dao.model.auto.WithdrawRuleConfigExample;
import com.hyjf.am.config.service.WithdrawRuleConfigService;
import com.hyjf.common.util.GetDate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 提现规则配置Service实现类
 *
 * @author liuyang
 * @version WithdrawRuleConfigServiceImpl, v0.1 2019/4/19 16:43
 */
@Service
public class WithdrawRuleConfigServiceImpl extends BaseServiceImpl implements WithdrawRuleConfigService {
    /**
     * 获取提现规则配置
     *
     * @param userType
     * @param withdrawmoney
     * @return
     */
    @Override
    public WithdrawRuleConfig selectWithdrawRuleConfig(Integer userType, String withdrawmoney) {
        // 提现时间
        String withdrawTime = GetDate.formatShortTimehhmmss();
        WithdrawRuleConfigExample example = new WithdrawRuleConfigExample();
        WithdrawRuleConfigExample.Criteria cra = example.createCriteria();
        // 可否提现 1可以 0不可以
        cra.andCouldWithdrawEqualTo(1);
        // 0删除 1可用
        cra.andDelFlagEqualTo(1);
        // 最小金额 <= 提现金额
        cra.andMinMoneyLessThanOrEqualTo(new BigDecimal(withdrawmoney));
        // 最大金额 >= 提现金额
        cra.andMaxMoneyGreaterThanOrEqualTo(new BigDecimal(withdrawmoney));
        // 用户类型 1个人 2企业
        cra.andCustomerTypeEqualTo(userType);
        // 最小提现时间 <= 当前时间
        cra.andStartTimeLessThanOrEqualTo(withdrawTime);
        // 最大提现时间 >= 当前时间
        cra.andEndTimeGreaterThanOrEqualTo(withdrawTime);
        List<WithdrawRuleConfig> list = this.withdrawRuleConfigMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }
}
