/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.admin.finance;

import com.hyjf.am.trade.dao.mapper.auto.AccountMapper;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.AccountExample;
import com.hyjf.am.trade.service.admin.finance.CustomerTransferService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: CustomerTransferServiceImpl, v0.1 2018/7/6 10:17
 */
@Service
public class CustomerTransferServiceImpl extends BaseServiceImpl implements CustomerTransferService {

    @Autowired
    private AccountMapper accountMapper;

    /**
     * 根据userId查询账户信息
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<Account> searchAccountByUserId(Integer userId) {
        AccountExample example = new AccountExample();
        AccountExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<Account> accountList = accountMapper.selectByExample(example);
        return accountList;
    }
}
