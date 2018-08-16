/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.account.impl;

import com.hyjf.am.resquest.admin.AccountRechargeRequest;
import com.hyjf.am.trade.dao.mapper.auto.AccountRechargeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AccountRechargeCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.AccountRechargeExample;
import com.hyjf.am.trade.service.front.account.AccountRecharge;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
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
     * 充值列表总条数
     * @param params
     * @return
     * @Author : huanghui
     */
    @Override
    public Integer getAccountRechargeListCount(AccountRechargeRequest request){
        return this.accountRechargeCustomizeMapper.getAccountRechargeListCount(request);
    }

    /**
     * 充值列表
     * @param params
     * @return
     * @Author : huanghui
     */
    @Override
    public List<AccountRechargeVO> getAccountRechargeList(AccountRechargeRequest request) {
        List<AccountRechargeVO> rechargeVOList = accountRechargeCustomizeMapper.getAccountRechargeList(request);
        return rechargeVOList;
    }
}
