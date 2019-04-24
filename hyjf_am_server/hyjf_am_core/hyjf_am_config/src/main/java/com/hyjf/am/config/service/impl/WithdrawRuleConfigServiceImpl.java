/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.model.auto.WithdrawRuleConfig;
import com.hyjf.am.config.dao.model.auto.WithdrawRuleConfigExample;
import com.hyjf.am.config.service.WithdrawRuleConfigService;
import com.hyjf.am.resquest.config.WithdrawRuleConfigRequest;
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
     * @param request
     * @return
     */
    @Override
    public WithdrawRuleConfig selectWithdrawRuleConfig(WithdrawRuleConfigRequest request) {
        // 提现金额
        String withdrawMoney = request.getWithdrawMoney();
        // 用户类型
        Integer userType = request.getUserType();
        // 是否是节假日
        Integer isHoliday = request.getIsHoliday();

        logger.info("withdrawMoney:" + withdrawMoney);
        logger.info("userType:" + userType);
        logger.info("isHoliday:" + isHoliday);
        // 提现时间
        String withdrawTime = GetDate.formatShortTimehhmmss();
        WithdrawRuleConfigExample example = new WithdrawRuleConfigExample();
        WithdrawRuleConfigExample.Criteria cra = example.createCriteria();
        // 可否提现 1可以 0不可以
        cra.andCouldWithdrawEqualTo(1);
        // 0 可用 1 删除
        cra.andDelFlagEqualTo(0);
        // 最小金额 <= 提现金额
        cra.andMinMoneyLessThanOrEqualTo(new BigDecimal(withdrawMoney));
        // 最大金额 >= 提现金额
        cra.andMaxMoneyGreaterThanOrEqualTo(new BigDecimal(withdrawMoney));
        // 用户类型 0个人 1企业
        cra.andCustomerTypeEqualTo(userType);
        // 最小提现时间 <= 当前时间
        cra.andStartTimeLessThanOrEqualTo(withdrawTime);
        // 最大提现时间 >= 当前时间
        cra.andEndTimeGreaterThanOrEqualTo(withdrawTime);
        // 是否是节假日
        cra.andIsHolidayEqualTo(isHoliday);
        List<WithdrawRuleConfig> list = this.withdrawRuleConfigMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }
}
