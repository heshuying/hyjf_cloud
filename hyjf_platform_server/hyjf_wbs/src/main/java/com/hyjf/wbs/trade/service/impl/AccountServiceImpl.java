package com.hyjf.wbs.trade.service.impl;

import com.hyjf.wbs.trade.dao.model.auto.Account;
import com.hyjf.wbs.trade.dao.model.auto.AccountExample;
import com.hyjf.wbs.trade.service.AccountService;
import com.hyjf.wbs.trade.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: wxd
 * @Date: 2019-04-11 09:24
 * @Description:
 */
@Service
public class AccountServiceImpl extends BaseServiceImpl implements AccountService {

    @Override
    public Account getAccount(Integer userId) {
        AccountExample example = new AccountExample();
        AccountExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<Account> listAccount = this.accountMapper.selectByExample(example);
        if (listAccount != null && listAccount.size() > 0) {
            return listAccount.get(0);
        }
        return null;
    }
}
