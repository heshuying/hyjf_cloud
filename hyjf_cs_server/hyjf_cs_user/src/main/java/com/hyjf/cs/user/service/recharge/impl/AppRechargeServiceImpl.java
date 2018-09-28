/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.recharge.impl;

import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.service.recharge.AppRechargeService;
import org.springframework.stereotype.Service;

/**
 * @author fq
 * @version AppRechargeServiceImpl, v0.1 2018/7/30 9:45
 */
@Service
public class AppRechargeServiceImpl extends BaseUserServiceImpl implements AppRechargeService {

    /**
     * 根据userId查询银行卡信息
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    @Override
    public BankCardVO selectBankCardByUserId(Integer userId) {
        return amUserClient.getBankCardByUserId(userId);
    }

    /**
     * 根据id查询银行配置
     * @auth sunpeikai
     * @param bankId 主键id
     * @return
     */
    @Override
    public JxBankConfigVO getJxBankConfigByBankId(Integer bankId) {
        return amConfigClient.getJxBankConfigById(bankId);
    }
}
