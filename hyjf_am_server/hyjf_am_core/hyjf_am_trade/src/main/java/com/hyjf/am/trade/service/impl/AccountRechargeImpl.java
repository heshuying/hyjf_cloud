/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.customize.trade.AccountRechargeCustomizeMapper;
import com.hyjf.am.trade.service.AccountRecharge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author fuqiang
 * @version AccountRechargeImpl, v0.1 2018/7/17 11:12
 */
@Service
public class AccountRechargeImpl implements AccountRecharge {
    @Autowired
    private AccountRechargeCustomizeMapper accountRechargeCustomizeMapper;

    @Override
    public BigDecimal getRechargePrice(List<Integer> list) {
        return accountRechargeCustomizeMapper.getRechargePrice(list);
    }
}
