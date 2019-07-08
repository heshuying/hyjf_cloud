/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.account.impl;

import com.hyjf.am.trade.dao.mapper.auto.AccountRechargeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AccountRechargeCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.AccountRechargeExample;
import com.hyjf.am.trade.service.front.account.AccountRecharge;
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
    @Autowired
    private AccountRechargeMapper accountRechargeMapper;

    @Override
    public BigDecimal getRechargePrice(List<Integer> list) {
        return accountRechargeCustomizeMapper.getRechargePrice(list);
    }

    @Override
    public List<com.hyjf.am.trade.dao.model.auto.AccountRecharge> getAccountRechargeByUserId(Integer userId) {
        AccountRechargeExample accountRechargeExample = new AccountRechargeExample();
        AccountRechargeExample.Criteria criteria = accountRechargeExample.createCriteria().andUserIdEqualTo(userId);
        List<com.hyjf.am.trade.dao.model.auto.AccountRecharge> rechargeList = accountRechargeMapper.selectByExample(accountRechargeExample);
        return rechargeList;
    }

    /**
     * 根据用户ID查询用户充值成功记录
     *
     * @param userId
     * @return
     */
    @Override
    public List<com.hyjf.am.trade.dao.model.auto.AccountRecharge> selectRechargeListByUserId(Integer userId) {
        AccountRechargeExample example = new AccountRechargeExample();
        AccountRechargeExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        // 充值状态:2:充值成功
        cra.andStatusEqualTo(2);
        example.setOrderByClause("id ASC");
        List<com.hyjf.am.trade.dao.model.auto.AccountRecharge> list = this.accountRechargeMapper.selectByExample(example);
        return list;
    }

    /**
     * 根据用户Id查询用户第一笔充值记录
     *
     * @param userId
     * @return
     */
    @Override
    public com.hyjf.am.trade.dao.model.auto.AccountRecharge selectAccountRechargeByUserId(Integer userId) {
        AccountRechargeExample example = new AccountRechargeExample();
        AccountRechargeExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        cra.andStatusEqualTo(2);
        example.setOrderByClause("create_time ASC");
        List<com.hyjf.am.trade.dao.model.auto.AccountRecharge> list = this.accountRechargeMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
